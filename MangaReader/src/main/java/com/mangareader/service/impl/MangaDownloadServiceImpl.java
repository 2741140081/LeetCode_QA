package com.mangareader.service.impl;

import com.mangareader.config.MangaDownloadProperties;
import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    private String BASE_URL;

    @Override
    public void downloadManga(Manga manga, int threadCount) throws IOException {
        String mangaUrl = manga.getMangaUrl();
        System.out.println("Downloading manga: " + manga.getMangaName());
        System.out.println("Manga URL: " + mangaUrl);
        System.out.println("Thread count: " + threadCount);
        BASE_URL = downloadProperties.getBaseUrl();
        Document doc = getDocument(mangaUrl);

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
        // 通过 mangaId 获取章节信息, 用于创建目录, 由于数据库只存需要1,2,3, 需要将其转成 001, 002, 003 的格式后再去创建目录
        String mangaDir = manga.getDirId();
        List<Chapter> chapterInfos = chapterMapper.findByMangaId(mangaId);
        for (Chapter chapterInfo : chapterInfos) {
            int currChapterNum = chapterInfo.getChapterNum();
            // 将 1 转成 001 格式
            String chapterNum = String.format("%03d", currChapterNum);
            String fullPath = mangaDir + File.separator + chapterNum;
            // 根据 fullPath 创建本地文件夹
            File file = new File(fullPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            String pageUrl = chapterInfo.getChapterUrl();
            Long chapterId = chapterInfo.getChapterId(); // 标记该章节的id
            // 解析图片信息并保存到数据库
            parseAndSaveImagesPlus(pageUrl, chapterId, fullPath); // todo: 需要添加失败重试机制, 防止网络波动导致网页加载失败问题
        }


    }

    // todo: 待优化, 可以使用多线程并行处理每一个章节
    private void parseAndSaveImagesPlus(String pageSuffixUrl, Long chapterId, String savePath) {
        String pageUrl = BASE_URL + pageSuffixUrl; // 前缀 + 后缀组合成完整 url
        Document doc = getDocument(pageUrl);

        // 使用List替代Map，提高遍历效率
        List<MangaImage> imageList = new ArrayList<>();

        // 使用Jsoup的CSS选择器替代正则表达式，效率更高
        Elements imgElements = doc.select("img[data-original][id]");
        for (Element img : imgElements) {
            String imageAddress = img.attr("data-original");
            String imageId = img.attr("id");
            if (!imageAddress.isEmpty()) {  // 确保图片地址不为空
                MangaImage image = new MangaImage();
                image.setChapterId(chapterId);
                image.setImageName(imageId);
                image.setImageUrl(savePath);
                image.setDownloadUrl(imageAddress);
                image.setDownloadStatus(1);

                imageList.add(image);
            }
        }
        if (!imageList.isEmpty()) {
            // 存储到数据库
            int insertedCount = mangaImageMapper.batchInsert(imageList);
            System.out.println("Successfully inserted " + insertedCount + " images for chapter: " + chapterId);
        }
        // 获取下一页 url
        String nextPageUrl = findNextPageUrl(doc);
        if (nextPageUrl != null) {
            // 递归调用
            parseAndSaveImagesPlus(nextPageUrl, chapterId, savePath);
        }
    }

    /**
     * 查找下一页链接
     * @param doc 网页文档
     * @return 下一页URL
     */
    private String findNextPageUrl(Document doc) {
        // 提取下一页地址（正则匹配）
        Pattern nextPagePattern = Pattern.compile("<a href=\"([^\"]*)\" class=\"down-page\"[^>]*>下一页</a>");
        Matcher matcher = nextPagePattern.matcher(doc.html());
        if (matcher.find()) {
            System.out.println("下一页地址: " + matcher.group(1));
            return matcher.group(1);
        }
        return null;
    }

    private Document getDocument(String url) {
        MangaDownloadProperties.Headers headers = downloadProperties.getHeaders();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(downloadProperties.getUserAgent())
                    .header("Accept", headers.getAccept())
                    .header("Accept-encoding", headers.getAcceptEncoding())
                    .header("Accept-Language", headers.getAcceptLanguage())
                    .header("Referer", downloadProperties.getReferer())
                    .timeout(downloadProperties.getTimeout())
                    .get();
            return doc;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}