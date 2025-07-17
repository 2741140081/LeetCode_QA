package com.marks.tools.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageCrawlerTwo {
    public static String BASE_URL = "https://www.toupaimh.com";

    public static String ID_OF_HTML = "151867";
    public static String START_URL = BASE_URL + "/chapter/"+ ID_OF_HTML +".html";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36";

    static final int THREAD_POOL_SIZE = 6;

    public static String SAVE_DIR = "D:\\spider\\data\\4173_56\\" + ID_OF_HTML +"\\";
    public static ExecutorService executor;

    public static final int MAX_PAGES = 100;
    public static final int MAX_RETRY = 5;
    public static final long RETRY_INTERVAL = 20000; // 30秒

    // 统计已爬取的页数
    public static int crawledPages = 0;


    // 在类中添加
    public static AtomicInteger successCount = new AtomicInteger(0);


    public static void main(String[] args) {
        executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        // 确保保存目录存在
        createSaveDirectory();

        crawlImages(START_URL);

        shutdown();
    }

    public static void createSaveDirectory() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void crawlImages(String url) {
        if (crawledPages >= MAX_PAGES) {
            System.out.println("已达到最大爬取页数: " + MAX_PAGES);
            return;
        }

        int retryCount = 0;

        while (retryCount < MAX_RETRY) {
            try {
                // 1. 模拟浏览器访问（反爬措施）
                System.out.println("正在爬取页面: " + url);

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36 Edg/138.0.0.0")
                        .header("Accept", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                        .header("Accept-encoding", "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8")
                        .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6")
                        .header("Referer", "https://www.toupaimh.com/")
                        .timeout(30000)
                        .get();

                // 2. 提取图片信息
                Map<String, String> imageMap = new HashMap<>();
                imageMap = extractImages(doc);

                System.out.println("找到图片数量: " + imageMap.size());
                if (imageMap.size() > 0) {
                    System.out.println("开启多线程进行下载, 数量为" + imageMap.size());
                    String savePath = SAVE_DIR;

                    List<Future<?>> futures = new ArrayList<>();

                    Map<String, String> finalImageMap = imageMap;
                    futures.add(executor.submit(() -> {
                        downloadImages(finalImageMap, savePath);
                    }));

                    // 等待所有任务完成
                    waitForCompletion(futures);
                }

                // 提取下一页链接
                String nextPageUrl = extractNextPage(doc);
                Thread.sleep(200);
                if (nextPageUrl != null) {
                    crawledPages++;
                    crawlImages(BASE_URL + nextPageUrl);
                }

                break; // 成功则退出重试循环
            } catch (Exception e) {
                retryCount++;
                if (retryCount >= MAX_RETRY) {
                    System.err.println("访问失败: " + url + "，已达最大重试次数");
                    break;
                }
                System.err.println("访问失败["+ url +"]，第" + retryCount + "次重试...");
                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static Map<String, String> extractImages(Document doc) {
        Map<String, String> map = new HashMap<>();

        // 3. 提取图片地址（正则匹配id属性）
        Pattern imgPattern = Pattern.compile("<img\\s+[^>]*data-original=[\"']([^\"']+)[\"'][^>]*id=[\"']([^\"']+)[\"'][^>]*>");
        Matcher imgMatcher = imgPattern.matcher(doc.html());

        while (imgMatcher.find()) {
            String imageAddress = imgMatcher.group(1);
            String imageId = imgMatcher.group(2);
            map.put(imageId, imageAddress);
            System.out.println(imgMatcher.group(1) + ": " + imgMatcher.group(2));
        }
        return map;
    }

    private static String extractNextPage(Document doc) {
        // 2. 提取下一页地址（正则匹配）
        Pattern nextPagePattern = Pattern.compile("<a href=\"([^\"]*)\" class=\"down-page\"[^>]*>下一页</a>");
        Matcher matcher = nextPagePattern.matcher(doc.html());
        if (matcher.find()) {

            System.out.println("下一页地址: " + matcher.group(1));
            return matcher.group(1);
        }
        return null;
    }

    private static void downloadImages(Map<String, String> map, String savePath) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String imageId = entry.getKey();
            String imageUrl = entry.getValue();

            try {
                URL url = new URL(imageUrl);
                try (InputStream in = url.openStream();
                     FileOutputStream out = new FileOutputStream(savePath + imageId + ".webp")) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    successCount.incrementAndGet();
                    System.out.println(Thread.currentThread().getName() +
                            " 下载完成: " + imageId + ", 当前已经下载数量:" + successCount);
                    // 在downloadSingleImage成功时

//                        Thread.sleep(100);
                }
            } catch (Exception e) {
                System.err.println("下载失败: " + imageId + ", 错误: " + e.getMessage());
            }
        }
    }

    private static void waitForCompletion(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get(30, TimeUnit.SECONDS);
            } catch (Exception e) {
                System.err.println("任务执行异常: " + e.getMessage());
            }
        }
    }

    public static void shutdown() {
        executor.shutdown();
    }
}

