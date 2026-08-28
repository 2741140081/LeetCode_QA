package com.mangareader.service.impl;

import com.mangareader.config.MangaDownloadProperties;
import com.mangareader.enums.ProcessStatus;
import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaChapterPageRecordMapper;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.mapper.MangaMapper;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.entity.MangaChapterPageRecord;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.MangaDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class MangaDownloadServiceImpl implements MangaDownloadService {

    @Autowired
    private MangaDownloadProperties downloadProperties;

    @Autowired
    private MangaMapper mangaMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private MangaImageMapper mangaImageMapper;

    @Autowired
    private MangaChapterPageRecordMapper pageRecordMapper;

    @Autowired
    @Qualifier("chapterProcessExecutor")
    private ExecutorService chapterExecutor;

    private String BASE_URL;
    // 最大重试次数配置，可根据实际网络情况调整
    private static final int MAX_RETRY_TIMES = 3;
    private static final long BASE_SLEEP_MS = 1000; // 基础休眠1秒

    @Override
    public void downloadManga(Manga manga) {
        String mangaUrl = manga.getMangaUrl();
        Long mangaId = manga.getMangaId();
        log.info("开始下载漫画: {}, URL: {}", manga.getMangaName(), mangaUrl);
        BASE_URL = downloadProperties.getBaseUrl();
        Document doc;
        try {
            doc = getDocumentWithRetry(mangaUrl);
            log.info("成功获取漫画目录页: {}", mangaUrl);
        } catch (RuntimeException e) {
            log.error("加载漫画网页失败, 漫画: {}, URL: {}", manga.getMangaName(), mangaUrl);
            mangaMapper.updateMangaStatus(mangaId, ProcessStatus.FAILED.getCode());
            throw new RuntimeException(e);
        }

        Elements liElements = doc.select(downloadProperties.getCssSelector());
        TreeMap<String, String> chapterMap = new TreeMap<>();
        for (Element liElement : liElements) {
            String chapterUrlId = liElement.attr("href").trim();
            String chapterName = liElement.attr("title").trim();
            chapterMap.put(chapterUrlId, chapterName);
        }
        log.info("解析到 {} 个章节", chapterMap.size());

        // 批量保存章节信息到数据库
        List<Chapter> chapters = new ArrayList<>();
        int idx = 0;
        for (Map.Entry<String, String> entry : chapterMap.entrySet()) {
            Chapter chapter = new Chapter();
            chapter.setMangaId(mangaId);
            chapter.setChapterNum(idx++);
            chapter.setChapterUrl(entry.getKey());
            chapter.setTitle(entry.getValue());
            chapter.setCreatedAt(LocalDateTime.now());
            chapters.add(chapter);
        }

        if (!chapters.isEmpty()) {
            int insertedCount = chapterMapper.batchInsert(chapters);
            log.info("成功插入 {} 个章节到数据库", insertedCount);
            // 更新总章节数
            mangaMapper.updateTotalChapters(mangaId, chapters.size());
        } else {
            mangaMapper.updateMangaStatus(mangaId, ProcessStatus.FAILED.getCode());
            log.error("漫画[{}]没有可下载的章节，已标记为异常中断状态", manga.getMangaName());
        }
        // 通过 mangaId 获取章节信息, 用于创建目录
        String mangaDir = manga.getDirId();
        List<Chapter> chapterInfos = chapterMapper.findByMangaId(mangaId);

        log.info("开始处理 {} 个章节，存储路径: {}", chapterInfos.size(), mangaDir);
        // 提交所有章节的并行处理任务
        for (Chapter chapterInfo : chapterInfos) {
            chapterExecutor.submit(() -> {
                try {
                    processSingleChapter(chapterInfo, mangaDir, mangaId);
                } catch (Exception e) {
                    log.error("章节[{}]处理失败: {}", chapterInfo.getChapterId(), e.getMessage(), e);
                }
            });
        }
    }

    /**
     * 单线程内处理单个章节的全流程：创建目录 + 遍历所有分页 + 图片入库 + 状态记录
     * todo: 需要新增更新 manga 表状态, 实现心跳更新
     */
    private void processSingleChapter(Chapter chapterInfo, String mangaDir, long mangaId) {
        Manga manga = mangaMapper.selectMangaById(mangaId);
        if (manga == null || manga.getMangaStatus() == null) {
            log.warn("漫画信息不存在或状态为空，跳过章节[{}]的处理", chapterInfo.getChapterId());
            return;
        }
        if (manga.getMangaStatus() != ProcessStatus.PROCESSING) {
            log.warn("漫画[{}]当前状态为[{}]，不是正在处理状态，跳过章节[{}]的处理",
                    manga.getMangaName(),
                    manga.getMangaStatus().getDesc(),
                    chapterInfo.getChapterId());
            return;
        }

        int currChapterNum = chapterInfo.getChapterNum();
        // 将章节号格式化为001、002格式作为目录名
        String chapterNum = String.format("%03d", currChapterNum);
        String fullPath = mangaDir + File.separator + chapterNum;
        // 提前创建章节本地存储文件夹
        File chapterDir = new File(fullPath);
        if (!chapterDir.exists()) {
            chapterDir.mkdirs();
        }

        Long chapterId = chapterInfo.getChapterId();
        log.info("开始处理章节: ID={}, 序号={}, 标题={}", chapterId, currChapterNum, chapterInfo.getTitle());

        String firstPageUrl = BASE_URL + chapterInfo.getChapterUrl();
        int currentPageNum = 1;

        // 循环拉取当前章节所有分页，直到不存在next_page标签
        String currentPageUrl = firstPageUrl;
        while (currentPageUrl != null) {
            MangaChapterPageRecord pageRecord = buildInitPageRecord(chapterId, currentPageNum, currentPageUrl);
            int executeRetryTimes = 0;
            Document currentDoc;
            try {
                currentDoc = getDocumentWithRetry(currentPageUrl);
            } catch (RuntimeException e) {
                // 标记页面加载失败
                pageRecord.setDownloadStatus(ProcessStatus.FAILED);
                pageRecord.setRetryCount(executeRetryTimes);
                pageRecord.setLastFailReason(e.getMessage());
                pageRecord.setGmtModify(LocalDateTime.now());
                pageRecordMapper.updateByPrimaryKeySelective(pageRecord);
                log.error("章节[{}]第[{}]页加载失败: {}", chapterId, currentPageNum, e.getMessage());
                mangaMapper.updateMangaStatus(mangaId, ProcessStatus.FAILED.getCode());
                throw new RuntimeException(e);
            }

            // 更新页面状态为下载成功
            pageRecord.setDownloadStatus(ProcessStatus.COMPLETED);
            pageRecord.setRetryCount(executeRetryTimes);
            pageRecord.setGmtModify(LocalDateTime.now());
            pageRecordMapper.updateByPrimaryKeySelective(pageRecord);

            // 解析当前页的所有图片信息批量入库
            List<MangaImage> imageList = new ArrayList<>();
            Elements imgElements = currentDoc.select("img[data-original][id]");
            for (Element img : imgElements) {
                String imageAddress = img.attr("data-original");
                String imageId = img.attr("id");
                if (!imageAddress.isEmpty()) {
                    MangaImage image = new MangaImage();
                    image.setChapterId(chapterId);
                    image.setImageName(imageId);
                    image.setImageUrl(fullPath);
                    image.setDownloadUrl(imageAddress);
                    image.setImageType(extractImageType(imageAddress));
                    image.setDownloadStatus(ProcessStatus.PENDING);
                    image.setSortOrder(Integer.parseInt(imageId.substring(3))); // imageId = img56, 需要提取56
                    imageList.add(image);
                }
            }
            if (!imageList.isEmpty()) {
                mangaImageMapper.batchInsert(imageList);
                log.info("章节[{}]第{}页成功入库{}张图片", chapterId, currentPageNum, imageList.size());
            }
            // 原子更新 manga 表心跳时间 - 使用行级锁保证并发安全
            mangaMapper.updateHeartBeat(mangaId, LocalDateTime.now());

            // 通过next_page标签获取下一页地址
            String nextPageUrl = findNextPageUrl(currentDoc);
            if (nextPageUrl != null) {
                currentPageUrl = BASE_URL + nextPageUrl;
                currentPageNum++;
            } else {
                // 不存在next_page标签说明已经到当前章节最后一页，结束循环
                currentPageUrl = null;
                // 原子递增已处理章节数 - 使用数据库原子操作避免并发问题
                mangaMapper.incrementProcessedChapters(mangaId);
                log.info("章节[{}]处理完成，已更新处理进度", chapterId);
            }
        }
    }

    /**
     * 初始化页面下载记录
     */
    private MangaChapterPageRecord buildInitPageRecord(Long chapterId, int pageNum, String pageUrl) {
        MangaChapterPageRecord record = new MangaChapterPageRecord();
        record.setChapterId(chapterId);
        record.setPageNum(pageNum);
        record.setPageUrl(pageUrl);
        record.setDownloadStatus(ProcessStatus.PENDING);
        record.setRetryCount(0);
        record.setGmtCreate(LocalDateTime.now());
        record.setGmtModify(LocalDateTime.now());
        pageRecordMapper.insertSelective(record);
        return record;
    }

    /**
     * 原有的通过class匹配获取下一页的逻辑保留为兼容备用
     * @param doc 网页文档
     * @return 下一页URL
     */
    private String findNextPageUrl(Document doc) {
        Pattern nextPagePattern = Pattern.compile("<a href=\"([^\"]*)\" class=\"down-page\"[^>]*>下一页</a>");
        Matcher matcher = nextPagePattern.matcher(doc.html());
        if (matcher.find()) {
            log.info("下一页 url: {}", matcher.group(1));
            return matcher.group(1);
        }
        log.info("当前是最后一页");
        return null;
    }

    /**
     * 从URL中提取图片类型 (如 jpg, png, webp)
     */
    private String extractImageType(String url) {
        if (url == null) return null;
        String path = url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
        int dotIndex = path.lastIndexOf('.');
        int slashIndex = path.lastIndexOf('/');
        if (dotIndex > slashIndex && dotIndex < path.length() - 1) {
            return path.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    /**
     * 自带基础重试逻辑的网页加载方法，基础请求失败自动重试
     */
    private Document getDocumentWithRetry(String url) {
        int retryCount = 0;
        Exception lastException = null;
        log.debug("开始请求URL: {}", url);
        while (retryCount < MAX_RETRY_TIMES) {
            try {
                // 1. 构建连接
                Connection connection = Jsoup.connect(url)
                        .userAgent(downloadProperties.getUserAgent())
                        .timeout(downloadProperties.getTimeout())
                        .followRedirects(false) // 禁止自动重定向，避免陷入重定向循环
                        .header("Accept", downloadProperties.getHeaders().getAccept())
                        .header("Accept-Encoding", downloadProperties.getHeaders().getAcceptEncoding())
                        .header("Accept-Language", downloadProperties.getHeaders().getAcceptLanguage())
                        .header("Referer", downloadProperties.getReferer());

                // 2. 执行请求并获取响应对象（关键：先拿Response，再判断状态码）
                Connection.Response response = connection.execute();

                // 3. 校验状态码
                int statusCode = response.statusCode();
                if (statusCode != 200) {
                    log.warn("HTTP状态码异常: {}, URL: {}", statusCode, url);
                    throw new RuntimeException("HTTP错误码: " + statusCode + ", URL: " + url);
                }

                // 4. 解析为Document
                // 注意：如果页面很大，确保JVM堆内存充足，或者限制maxBodySize
                log.debug("成功获取响应: URL={}, 状态码={}", url, statusCode);
                return response.parse();
            } catch (Exception e) {
                lastException = e;
                retryCount++;
                log.warn("第{}次请求失败: {}, URL: {}, 原因: {}", retryCount, e.getClass().getSimpleName(), url, e.getMessage());

                // 如果不是最后一次重试，则等待
                if (retryCount < MAX_RETRY_TIMES) {
                    long sleepTime = BASE_SLEEP_MS * (long) Math.pow(2, retryCount - 1);
                    long jitter = (long) (Math.random() * 1000);
                    try {
                        log.debug("等待 {}ms 后重试", sleepTime + jitter);
                        TimeUnit.MILLISECONDS.sleep(sleepTime + jitter);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("重试等待被中断", ie);
                    }
                }
            }
        }
        log.error("网页加载最终失败，地址: {}, 重试次数: {}", url, MAX_RETRY_TIMES);
        // 所有重试均失败
        throw new RuntimeException("网页加载最终失败，地址：" + url, lastException);
    }
}