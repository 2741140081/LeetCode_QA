package com.mangareader.service.impl;

import com.mangareader.config.MangaDownloadProperties;
import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaChapterPageRecordMapper;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.entity.MangaChapterPageRecord;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.MangaDownloadService;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MangaDownloadServiceImpl implements MangaDownloadService {

    @Autowired
    private MangaDownloadProperties downloadProperties;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private MangaImageMapper mangaImageMapper;

    @Autowired
    private MangaChapterPageRecordMapper pageRecordMapper;

    private String BASE_URL;
    // 最大重试次数配置，可根据实际网络情况调整
    private static final int MAX_RETRY_TIMES = 3;

    @Override
    public void downloadManga(Manga manga, int threadCount) throws IOException {
        String mangaUrl = manga.getMangaUrl();
        System.out.println("Downloading manga: " + manga.getMangaName());
        System.out.println("Manga URL: " + mangaUrl);
        System.out.println("Thread count: " + threadCount);
        BASE_URL = downloadProperties.getBaseUrl();
        Document doc = getDocumentWithRetry(mangaUrl);

        Elements liElements = doc.select(downloadProperties.getCssSelector());
        TreeMap<String, String> chapterMap = new TreeMap<>();
        for (Element liElement : liElements) {
            String chapterUrlId = liElement.attr("href").trim();
            String chapterName = liElement.attr("title").trim();
            chapterMap.put(chapterUrlId, chapterName);
        }

        // 批量保存章节信息到数据库
        List<Chapter> chapters = new ArrayList<>();
        Long mangaId = manga.getMangaId();
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
            System.out.println("Successfully inserted " + insertedCount + " chapters");
        }
        // 通过 mangaId 获取章节信息, 用于创建目录
        String mangaDir = manga.getDirId();
        List<Chapter> chapterInfos = chapterMapper.findByMangaId(mangaId);

        // 初始化自定义线程池，每个线程独立处理完整章节逻辑
        ExecutorService chapterExecutor = new ThreadPoolExecutor(
                threadCount,
                threadCount,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new java.util.concurrent.ThreadFactory() {
                    private final AtomicInteger threadIndex = new AtomicInteger(1);
                    @Override
                    public Thread newThread(@NotNull Runnable r) {
                        return new Thread(r, "manga-chapter-process-thread-" + threadIndex.getAndIncrement());
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 提交所有章节的并行处理任务
        for (Chapter chapterInfo : chapterInfos) {
            chapterExecutor.submit(() -> processSingleChapter(chapterInfo, mangaDir));
        }

        // 等待所有章节任务执行完成，优雅关闭线程池
        chapterExecutor.shutdown();
        try {
            if (!chapterExecutor.awaitTermination(24, TimeUnit.HOURS)) {
                List<Runnable> unfinishedTasks = chapterExecutor.shutdownNow();
                System.err.printf("章节下载任务超时，剩余未处理任务数：%d%n", unfinishedTasks.size());
            }
        } catch (InterruptedException e) {
            chapterExecutor.shutdownNow();
            Thread.currentThread().interrupt();
            throw new RuntimeException("章节多线程下载任务被中断", e);
        }
    }

    /**
     * 单线程内处理单个章节的全流程：创建目录 + 遍历所有分页 + 图片入库 + 状态记录
     * todo: 需要新增更新 manga 表状态, 实现心跳更新
     */
    private void processSingleChapter(Chapter chapterInfo, String mangaDir) {
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
        String firstPageUrl = BASE_URL + chapterInfo.getChapterUrl();
        int currentPageNum = 1;

        // 循环拉取当前章节所有分页，直到不存在next_page标签
        String currentPageUrl = firstPageUrl;
        while (currentPageUrl != null) {
            MangaChapterPageRecord pageRecord = buildInitPageRecord(chapterId, currentPageNum, currentPageUrl);
            int executeRetryTimes = 0;
            Document currentDoc = null;
            boolean pageLoadSuccess = false;
            String lastErrorMsg = "";

            // 执行页面重试逻辑，直到达到最大重试次数
            while (executeRetryTimes < MAX_RETRY_TIMES) {
                try {
                    currentDoc = getDocumentWithRetry(currentPageUrl);
                    pageLoadSuccess = true;
                    break;
                } catch (Exception e) {
                    executeRetryTimes++;
                    lastErrorMsg = e.getMessage();
                    System.err.printf("章节[%d]第%d页加载失败，已重试%d次，错误信息：%s%n", chapterId, currentPageNum, executeRetryTimes, lastErrorMsg);
                }
            }

            if (pageLoadSuccess) {
                // 更新页面状态为下载成功
                pageRecord.setDownloadStatus(1);
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
                        image.setDownloadStatus(1);
                        imageList.add(image);
                    }
                }
                if (!imageList.isEmpty()) {
                    mangaImageMapper.batchInsert(imageList);
                    System.out.printf("章节[%d]第%d页成功入库%d张图片%n", chapterId, currentPageNum, imageList.size());
                }

                // 通过next_page标签获取下一页地址
                String nextPageUrl = findNextPageUrl(currentDoc);
                if (nextPageUrl != null) {
                    currentPageUrl = BASE_URL + nextPageUrl;
                    currentPageNum++;
                } else {
                    // 不存在next_page标签说明已经到当前章节最后一页，结束循环
                    currentPageUrl = null;
                }
            } else {
                // 页面重试次数耗尽，标记为下载失败，记录错误信息
                pageRecord.setDownloadStatus(2);
                pageRecord.setRetryCount(executeRetryTimes);
                pageRecord.setLastFailReason(lastErrorMsg);
                pageRecord.setGmtModify(LocalDateTime.now());
                pageRecordMapper.updateByPrimaryKeySelective(pageRecord);
                System.err.printf("章节[%d]第%d页重试%d次仍然失败，已记录失败状态，可后续单独重试处理%n", chapterId, currentPageNum, MAX_RETRY_TIMES);
                // 终止循环，不再尝试加载后续页面
                currentPageUrl = null;
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
        record.setDownloadStatus(0);
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
            System.out.println("下一页地址: " + matcher.group(1));
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 自带基础重试逻辑的网页加载方法，基础请求失败自动重试
     * todo: 代码逻辑存在问题
     */
    private Document getDocumentWithRetry(String url) {
        int retryCount = 0;
        MangaDownloadProperties.Headers headers = downloadProperties.getHeaders();
        while (retryCount < MAX_RETRY_TIMES) {
            try {
                return Jsoup.connect(url)
                        .userAgent(downloadProperties.getUserAgent())
                        .header("Accept", headers.getAccept())
                        .header("Accept-encoding", headers.getAcceptEncoding())
                        .header("Accept-Language", headers.getAcceptLanguage())
                        .header("Referer", downloadProperties.getReferer())
                        .timeout(downloadProperties.getTimeout())
                        .get();
            } catch (Exception e) {
                retryCount++;
                if (retryCount >= MAX_RETRY_TIMES) {
                    throw new RuntimeException("网页加载最终失败，地址：" + url, e);
                }
                try {
                    // 重试前休眠2秒，避免请求频率过高被服务器拦截
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }
}