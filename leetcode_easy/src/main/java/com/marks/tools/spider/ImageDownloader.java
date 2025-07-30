package com.marks.tools.spider;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ImageDownloader {
    private static final String BASE_URL = "https://img5.qy0.ru/data/2825/55/";
    private static final String SAVE_PATH = "D:\\spider\\data\\2825_55_bak\\";
    private static final int TOTAL_IMAGES = 393;
    private static final int THREAD_COUNT = 4;
    private static final int IMAGES_PER_THREAD = 100;

    public static void main(String[] args) {
        // 创建保存目录
        new File(SAVE_PATH).mkdirs();

        // 创建并启动线程
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * IMAGES_PER_THREAD + 1;
            int end = (i == THREAD_COUNT - 1) ? TOTAL_IMAGES : (i + 1) * IMAGES_PER_THREAD;
            new DownloadThread(start, end).start();
        }
    }

    static class DownloadThread extends Thread {
        private final int startIndex;
        private final int endIndex;
        private final HttpClient httpClient;

        public DownloadThread(int start, int end) {
            this.startIndex = start;
            this.endIndex = end;

            // 配置HttpClient，添加反爬虫策略
            this.httpClient = HttpClientBuilder.create()
                    .setRedirectStrategy(new LaxRedirectStrategy()) // 允许重定向
                    .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
                    .build();
        }

        @Override
        public void run() {
            for (int i = startIndex; i <= endIndex; i++) {
                // 这行代码的作用是将数字 i 格式化为4位数字符串，不足4位时在前面补零，并加上 .jpg 后缀
                String imageName = String.format("%04d.jpg", i);
                String imageUrl = BASE_URL + imageName;
                String savePath = SAVE_PATH + imageName;

                try {
                    downloadImage(imageUrl, savePath);
                    System.out.println("下载完成: " + imageName);

                    // 添加延迟，防止访问过于频繁
                    TimeUnit.MILLISECONDS.sleep(500 + (int)(Math.random() * 1000));
                } catch (Exception e) {
                    System.err.println("下载失败: " + imageName + " - " + e.getMessage());
                }
            }
        }

        private void downloadImage(String url, String savePath) throws IOException {
            HttpGet request = new HttpGet(url);

            // 添加请求头，模拟浏览器行为
            request.addHeader("Accept", "text/css,*/*;q=0.1");
            request.addHeader("Referer", "https://www.toupaimh.com/");
            request.addHeader("Accept-Language", "zh-CN,zh;q=0.9,zh-TW;q=0.8");

            HttpResponse response = httpClient.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                FileUtils.copyInputStreamToFile(
                        response.getEntity().getContent(),
                        new File(savePath)
                );
            } else {
                throw new IOException("HTTP错误: " + response.getStatusLine().getStatusCode());
            }
        }
    }
}

