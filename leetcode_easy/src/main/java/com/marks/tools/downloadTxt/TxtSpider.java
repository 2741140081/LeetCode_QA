package com.marks.tools.downloadTxt;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/*
 * 1. 爬取网站上面的小说, 保存为txt
 * 2. 目标网站 "https://bcshuku.com/novel50252/"
 *
 */
public class TxtSpider {
    public static void main(String[] args) throws IOException {
        TxtSpider txtSpider = new TxtSpider();
        // txtSpider.getHtml("https://bcshuku.com/novel50252/");
        txtSpider.getHtml("https://bcshuku.com/novel50252/chapter0.html");
    }

    public void getHtml(String url) throws IOException {
        // 使用 jsoup 爬取网页
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36")
                .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                .header("Accept-encoding", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                .header("Referer", "https://bcshuku.com/")
                .timeout(30000)
                .get();

        // 打印网页
        System.out.println(doc.html());
    }
}
