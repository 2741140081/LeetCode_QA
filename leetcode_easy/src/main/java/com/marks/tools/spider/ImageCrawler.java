//package com.marks.tools.spider;
//
///**
// * <p>项目名称:  </p>
// * <p>文件名称:  </p>
// * <p>描述: [类型描述] </p>
// *
// * @author marks
// * @version v1.0
// * @date 2025/5/15 17:56
// * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
// */
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import java.io.*;
//import java.net.*;
//import java.nio.file.*;
//import java.util.*;
//import java.util.concurrent.*;
//
//public class ImageCrawler {
//    // 配置参数
//    private static final String TARGET_URL = "https://example.com";
//    private static final String SAVE_DIR = "downloads/";
//    private static final int THREAD_COUNT = 5;
//    private static final long REQUEST_INTERVAL = 2000; // 2秒间隔
//
//    // 代理IP池 (示例)
//    private static final String[] PROXY_POOL = {
//            "203.156.196.49:3128",
//            "45.77.136.149:8080",
//            "138.197.157.44:8080"
//    };
//
//    // User-Agent池
//    private static final String[] USER_AGENTS = {
//            "Mozilla/5.0 (Windows NT 10.0; Win64; x64)",
//            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)",
//            "Mozilla/5.0 (iPhone; CPU iPhone OS 14_0 like Mac OS X)"
//    };
//
//    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
//        try {
//            Document doc = connectWithProxy(TARGET_URL);
//            Files.createDirectories(Paths.get(SAVE_DIR));
//
//            Elements imgs = doc.select("img");
//            for (Element img : imgs) {
//                String imgUrl = img.absUrl("src");
//                if(!imgUrl.isEmpty()){
//                    executor.submit(() -> downloadWithRetry(imgUrl));
//                    Thread.sleep(REQUEST_INTERVAL); // 控制请求频率
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            executor.shutdown();
//        }
//    }
//
//    private static Document connectWithProxy(String url) throws IOException {
//        Random rand = new Random();
//        // 随机选择代理和User-Agent
//        String proxy = PROXY_POOL[rand.nextInt(PROXY_POOL.length)];
//        String[] proxyParts = proxy.split(":");
//        String userAgent = USER_AGENTS[rand.nextInt(USER_AGENTS.length)];
//
//        return Jsoup.connect(url)
//                .userAgent(userAgent)
//                .proxy(proxyParts[0], Integer.parseInt(proxyParts[1]))
//                .timeout(30000)
//                .get();
//    }
//
//    private static void downloadWithRetry(String imgUrl) {
//        int retry = 3;
//        while (retry-- > 0) {
//            try {
//                resumeDownload(imgUrl);
//                return;
//            } catch (Exception e) {
//                System.err.println("下载失败("+retry+"): " + imgUrl);
//            }
//        }
//    }
//
//    private static void resumeDownload(String imgUrl) throws IOException {
//        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
//        Path filePath = Paths.get(SAVE_DIR + fileName);
//
//        // 断点续传实现
//        long existingSize = Files.exists(filePath) ? Files.size(filePath) : 0;
//        HttpURLConnection conn = (HttpURLConnection) new URL(imgUrl).openConnection();
//        conn.setRequestProperty("Range", "bytes=" + existingSize + "-");
//
//        try (InputStream in = conn.getInputStream();
//             OutputStream out = new FileOutputStream(filePath.toFile(), existingSize > 0)) {
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//            while ((bytesRead = in.read(buffer)) != -1) {
//                out.write(buffer, 0, bytesRead);
//            }
//            System.out.println("下载完成: " + fileName);
//        }
//    }
//}
//
