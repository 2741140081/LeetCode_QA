package com.mangareader.service.impl;

import com.mangareader.config.MangaDownloadProperties;
import com.mangareader.service.MangaDownloadService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.TreeMap;

@Service
public class MangaDownloadServiceImpl implements MangaDownloadService {

    @Autowired
    private MangaDownloadProperties downloadProperties;

    @Override
    public void downloadManga(String mangaName, String mangaUrl, int threadCount) throws IOException {
        System.out.println("Downloading manga: " + mangaName);
        System.out.println("Manga URL: " + mangaUrl);
        System.out.println("Thread count: " + threadCount);

        MangaDownloadProperties.Headers headers = downloadProperties.getHeaders();

        Document doc = Jsoup.connect(mangaUrl)
                .userAgent(downloadProperties.getUserAgent())
                .header("Accept", headers.getAccept())
                .header("Accept-encoding", headers.getAcceptEncoding())
                .header("Accept-Language", headers.getAcceptLanguage())
                .header("Referer", downloadProperties.getReferer())
                .timeout(downloadProperties.getTimeout())
                .get();

        Elements liElements = doc.select(downloadProperties.getCssSelector());
        TreeMap<String, String> chapterMap = new TreeMap<>();
        for (Element liElement : liElements) {
            String chapterUrlId = liElement.attr("href").trim();
            String chapterName = liElement.attr("title").trim();
            chapterMap.put(chapterUrlId, chapterName);
        }

        // 保存章节信息到数据库

    }
}