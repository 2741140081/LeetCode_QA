package com.mangareader.service.impl;

import com.mangareader.model.entity.Chapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class MangaDownloadServiceImplTest {
    private static final Pattern LINK_PATTERN =
            Pattern.compile("<a href=\"/chapter/(\\d+).html\" title=\"([^\"]+)\">");

    @Test
    void downloadManga() throws IOException {
        String mangaUrl = "https://tpmh3.com/book/51236.html";
        // 使用Jsoup连接网页
        Document doc = Jsoup.connect(mangaUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36 Edg/138.0.0.0")
                .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                .header("Accept-encoding", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("Referer", "https://tpmh3.com/")
                .timeout(30000)
                .get();

        // <ul class="list-unstyled bookAll-item-list">
        Elements liElements = doc.select("ul.list-unstyled.bookAll-item-list li a");
        TreeMap<String, String> chapterMap = new TreeMap<>();
        for (Element liElement : liElements) {
            String chapterUrlId = liElement.attr("href").trim();
            String chapterName = liElement.attr("title").trim();
            chapterMap.put(chapterUrlId, chapterName);
        }
        // 遍历 TreeMap
        for (String chapterUrlId : chapterMap.keySet()) {
            String chapterName = chapterMap.get(chapterUrlId);
            System.out.println("Chapter URL: " + chapterUrlId);
            System.out.println("Chapter Name: " + chapterName);
        }
    }
}