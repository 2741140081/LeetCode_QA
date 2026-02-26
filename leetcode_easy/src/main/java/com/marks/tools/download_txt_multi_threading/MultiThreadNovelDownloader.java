package com.marks.tools.download_txt_multi_threading;

import com.marks.tools.download_txt_multi_threading.entity.ChapterDownloadResult;
import com.marks.tools.download_txt_multi_threading.entity.ChapterInfo;
import com.marks.tools.webcrawler.OptimizedUtilGetChapterContent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.marks.tools.download_txt_multi_threading.constant.DownloadConstant.*;

/**
 * 多线程小说下载器（简化版本 - 使用共享浏览器实例）
 * 基于线程安全的SeleniumWebContentFetcher单例模式
 */
public class MultiThreadNovelDownloader {

    /**
     * 启动类
     */
    public static void main(String[] args) {
        MultiThreadNovelDownloader downloader = new MultiThreadNovelDownloader();
        String catalogUrl = BASE_URL + "/novel55197/";
        String novelName = "测试小说";
        downloader.start(catalogUrl, novelName);
    }

    // 线程池配置
    private static final int CORE_POOL_SIZE = 8;           // 核心线程数
    private static final int MAX_POOL_SIZE = 18;           // 最大线程数
    private static final int QUEUE_CAPACITY = 100;         // 队列容量
    private static final long KEEP_ALIVE_TIME = 60L;       // 空闲线程存活时间（秒）

    private String chromeDriverPath = "D:/Project/chrome/chromedriver-win64/chromedriver.exe";

    private ExecutorService executorService;
    
    // 分页处理配置
    private static final int DEFAULT_BATCH_SIZE = 100;                     // 默认每批处理章节数
    private int batchSize = DEFAULT_BATCH_SIZE;                           // 批处理大小

    // 添加WebDriver池实例
    private OptimizedUtilGetChapterContent contentFetcher;

    public MultiThreadNovelDownloader() {
        this.executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(QUEUE_CAPACITY),
            new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // 初始化优化的内容获取器
        this.contentFetcher = new OptimizedUtilGetChapterContent();
    }
    
    /**
     * 主要下载方法（多线程版本）- 支持分页处理
     */
    public void downloadNovel(String catalogUrl, String novelName, boolean saveSeparateFiles) {
        try {
            System.out.println("=== 开始多线程下载小说 ===");
            System.out.println("小说名称: " + novelName);
            System.out.println("目录URL: " + catalogUrl);
            System.out.println("线程池配置: 核心线程=" + CORE_POOL_SIZE + ", 最大线程=" + MAX_POOL_SIZE);
            System.out.println("分批处理大小: " + batchSize + " 章节/批");
            
            long startTime = System.currentTimeMillis();
            
            // 1. 解析目录（单线程，因为需要顺序解析分页）
            long parseStartTime = System.currentTimeMillis();
            List<ChapterInfo> chapters = parseNovelCatalog(catalogUrl);
            System.out.println("共找到 " + chapters.size() + " 个章节");
            long parseTime = System.currentTimeMillis() - parseStartTime;
            
            if (chapters.isEmpty()) {
                System.err.println("未找到任何章节，下载终止");
                return;
            }
            
            // 2. 分批处理下载章节内容
            System.out.println("\n=== 开始分批下载章节内容 ===");
            System.out.println("总章节数: " + chapters.size() + ", 每批处理: " + batchSize + " 章节");
            System.out.println("预计需要处理 " + ((chapters.size() + batchSize - 1) / batchSize) + " 批");
            
            List<ChapterInfo> completedChapters = new ArrayList<>();
            AtomicInteger failedCount = new AtomicInteger(0);
            AtomicLong totalContentLength = new AtomicLong(0);
            
            // 分批处理
            int totalBatches = (chapters.size() + batchSize - 1) / batchSize;
            for (int batchIndex = 0; batchIndex < totalBatches; batchIndex++) {
                int startIndex = batchIndex * batchSize;
                int endIndex = Math.min(startIndex + batchSize, chapters.size());
                List<ChapterInfo> batchChapters = chapters.subList(startIndex, endIndex);
                
                System.out.println(String.format("\n--- 处理第 %d/%d 批 (%d-%d 章) ---", 
                    batchIndex + 1, totalBatches, startIndex + 1, endIndex));
                
                // 并行下载当前批次的所有章节
                List<Future<ChapterDownloadResult>> futures = new ArrayList<>();
                for (int i = 0; i < batchChapters.size(); i++) {
                    ChapterInfo chapter = batchChapters.get(i);
                    ChapterDownloadTask task = new ChapterDownloadTask(chapter, startIndex + i + 1, chapters.size());
                    Future<ChapterDownloadResult> future = executorService.submit(task);
                    futures.add(future);
                }
                
                // 收集当前批次的结果
                for (Future<ChapterDownloadResult> future : futures) {
                    try {
                        ChapterDownloadResult result = future.get(120, TimeUnit.SECONDS); // 2分钟超时
                        if (result.success) {
                            completedChapters.add(result.chapter);
                            result.chapter.setContent(cleanContent(result.content));
                            totalContentLength.addAndGet(result.content.length());
                            
                            // 如果需要保存单独文件，则立即保存
                            if (saveSeparateFiles) {
                                String directory = SAVE_DIRECTORY + sanitizeFilename(novelName) + "/章节/";
                                saveChapterToFile(result.chapter, directory);
                            }
                        } else {
                            failedCount.incrementAndGet();
                            System.err.println("章节下载失败: " + result.errorMessage);
                        }
                    } catch (TimeoutException e) {
                        System.err.println("章节下载超时");
                        failedCount.incrementAndGet();
                    } catch (Exception e) {
                        System.err.println("章节下载异常: " + e.getMessage());
                        failedCount.incrementAndGet();
                    }
                }
                // 批次的间隔等待1s, 防止内存或者cpu太高了
                Thread.sleep(1000);
                System.out.println(String.format("批次完成: 成功 %d 章, 失败 %d 章", 
                    completedChapters.size() - (batchIndex * batchSize + failedCount.get()), failedCount.get()));
            }
            
            // 3. 保存结果
            long downloadTime = System.currentTimeMillis() - parseStartTime - parseTime;
            
            System.out.println("\n=== 下载统计 ===");
            System.out.println("解析目录耗时: " + parseTime / 1000 + " 秒");
            System.out.println("下载内容耗时: " + downloadTime / 1000 + " 秒");
            System.out.println("总耗时: " + (System.currentTimeMillis() - startTime) / 1000 + " 秒");
            System.out.println("成功下载: " + completedChapters.size() + " 章");
            System.out.println("下载失败: " + failedCount.get() + " 章");
            System.out.println("总内容长度: " + totalContentLength.get() + " 字符");
            
            if (!completedChapters.isEmpty()) {
                // 保存完整小说
                saveCompleteNovel(completedChapters, novelName);
                System.out.println("小说下载完成: " + novelName);
            } else {
                System.err.println("没有成功下载任何章节");
            }
            
        } catch (Exception e) {
            System.err.println("下载过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 确保资源被正确释放
            shutdownExecutor();
        }
    }
    
    /**
     * 解析小说目录（支持分页）
     */
    private List<ChapterInfo> parseNovelCatalog(String catalogUrl) throws IOException {
        List<ChapterInfo> chapters = new ArrayList<>();
        String currentPageUrl = catalogUrl;
        int pageNumber = 1;
        
        System.out.println("开始解析小说目录...");
        
        do {
            System.out.println("解析第 " + pageNumber + " 页: " + currentPageUrl);
            
            Document doc = connectWithRetry(currentPageUrl, 3);
            Element containerDiv = doc.selectFirst("div#list-chapter");
            Element chapterList = null;

            if (containerDiv != null) {
                // 在div容器内查找ul元素
                chapterList = containerDiv.selectFirst("ul");
            } else {
                // 如果没有ID容器，尝试直接通过class定位ul
                chapterList = doc.selectFirst("ul.list-chapter");
            }

            // 提取当前页的所有章节项
            var chapterItems = chapterList.select("li");

            
            System.out.println("在第 " + pageNumber + " 页找到 " + chapterItems.size() + " 个章节链接");
            
            int chapterNumber = chapters.size() + 1;
            for (Element item : chapterItems) {
                Element link = item.selectFirst("a[href]");

                String title = link.attr("title");
                String href = link.attr("href");
                
                if (!href.startsWith("http")) {
                    href = BASE_URL + href;
                }
                
                if (!title.isEmpty() && href.contains("/chapter")) {
                    chapters.add(new ChapterInfo(title, href, chapterNumber++));
                }
            }
            
            // 查找下一页链接
            String nextPageUrl = findNextPageLink(doc);
            if (nextPageUrl != null) {
                if (!nextPageUrl.startsWith("http")) {
                    nextPageUrl = BASE_URL + nextPageUrl;
                }
                currentPageUrl = nextPageUrl;
                pageNumber++;
                
                // 添加延迟避免请求过于频繁
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                break;
            }
            
        } while (true);
        
        return chapters;
    }
    
    /**
     * 查找下一页链接
     */
    private String findNextPageLink(Document doc) {
        try {
            // 方法1: 查找分页容器中的下一页链接
            Element paginationDiv = doc.selectFirst("div#pagination");
            if (paginationDiv != null) {
                System.out.println("找到分页容器 div#pagination");
                
                // 在分页容器中查找包含"Next"文本的链接
                Element nextLink = paginationDiv.selectFirst("li:contains(Next) a[href]");
                if (nextLink != null) {
                    String href = nextLink.attr("href");
                    System.out.println("通过分页容器找到下一页链接: " + href);
                    return href;
                }
            }
            
            // 方法2: 直接查找包含Next的链接
            Element nextLink = doc.selectFirst("a:contains(Next)[href]");
            if (nextLink != null) {
                String href = nextLink.attr("href");
                System.out.println("直接找到下一页链接: " + href);
                return href;
            }
            
            // 方法3: 查找具有特定class的下一页链接
            Elements nextLinks = doc.select("a[href].next-page, a[href].next, li.next a");
            if (!nextLinks.isEmpty()) {
                String href = nextLinks.first().attr("href");
                System.out.println("通过class找到下一页链接: " + href);
                return href;
            }
            
            System.out.println("未找到下一页链接");
            return null;
            
        } catch (Exception e) {
            System.err.println("查找下一页链接时发生错误: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * 使用Selenium获取章节内容（简化版本 - 使用共享浏览器实例）
     */
    private String getSeleniumContent(String chapterUrl) {
        int maxRetries = 3;
        int retryCount = 0;
        int baseDelay = 500;  // 基础延迟时间
        int delay = baseDelay;
            
        while (retryCount < maxRetries) {
            try {
                if (DEBUG_MODE && retryCount > 0) {
                    System.out.println("第" + (retryCount + 1) + "次尝试获取章节内容，延迟: " + delay + "ms");
                }
                    
//                UtilGetChapterContent utils = new UtilGetChapterContent();
//                String chapterContent = utils.fetchChapterContent(chapterUrl, "chapter-content", delay);

                // 使用优化后的内容获取器
                String chapterContent = contentFetcher.fetchChapterContent(chapterUrl, "chapter-content", delay);

                // 检查内容是否有效
                if (chapterContent != null && !chapterContent.trim().isEmpty() && chapterContent.length() > 100) {
                    if (DEBUG_MODE) {
                        System.out.println("✓ 成功获取章节内容，长度: " + chapterContent.length());
                    }
                    return chapterContent;
                } else {
                    if (DEBUG_MODE) {
                        String reason = (chapterContent == null) ? "内容为null" : 
                                       (chapterContent.trim().isEmpty()) ? "内容为空" : 
                                       "内容长度不足(" + chapterContent.length() + "字符)";
                        System.out.println("✗ 第" + (retryCount + 1) + "次尝试失败: " + reason);
                    }
                }
                    
            } catch (Exception e) {
                System.err.println("获取章节内容时发生错误 (第" + (retryCount + 1) + "次尝试): " + e.getMessage());
                if (DEBUG_MODE) {
                    e.printStackTrace();
                }
            }
                
            retryCount++;
                
            // 如果还需要重试，则等待并增加延迟时间
            if (retryCount < maxRetries) {
                try {
                    Thread.sleep(delay);
                    delay += baseDelay;  // 每次重试延迟时间递增500ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("重试等待被中断");
                    break;
                }
            }
        }
            
        System.err.println("✗ 获取章节内容失败，已重试" + maxRetries + "次: " + chapterUrl);
        return null;
    }
    
    /**
     * 清理文本内容
     */
    private String cleanContent(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        
        // 移除多余的空白行
        content = content.replaceAll("\\n\\s*\\n", "\n\n");
        
        // 移除行首行尾空白
        String[] lines = content.split("\\n");
        StringBuilder cleaned = new StringBuilder();
        for (String line : lines) {
            line = line.trim();
            if (!line.isEmpty()) {
                cleaned.append(line).append("\n");
            }
        }
        
        return cleaned.toString().trim();
    }
    
    /**
     * 带重试机制的网络连接
     */
    private Document connectWithRetry(String url, int maxRetries) throws IOException {
        IOException lastException = null;
        
        for (int i = 0; i < maxRetries; i++) {
            try {
                return Jsoup.connect(url)
                        .userAgent(USER_AGENT)
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                        .header("Referer", BASE_URL)
                        .timeout(30000)
                        .get();
            } catch (IOException e) {
                lastException = e;
                if (i < maxRetries - 1) {
                    System.out.println("连接失败，" + (i + 1) + "秒后重试...");
                    try {
                        TimeUnit.SECONDS.sleep(i + 1);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new IOException("连接被中断", ie);
                    }
                }
            }
        }
        
        throw new IOException("连接失败，已重试" + maxRetries + "次", lastException);
    }
    
    /**
     * 保存完整小说到单个文件
     */
    private void saveCompleteNovel(List<ChapterInfo> chapters, String novelName) throws IOException {
        String directory = SAVE_DIRECTORY + sanitizeFilename(novelName);
        Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        Path filePath = dirPath.resolve(novelName + ".txt");
        
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            // 写入小说标题
            writer.write("《" + novelName + "》");
            writer.newLine();
            
            // 写入各章节内容
            for (ChapterInfo chapter : chapters) {
                // 写入章节标题
                writer.write(chapter.getTitle());
                writer.newLine();
                // 写入章节内容
                writer.write(chapter.getContent());
                writer.newLine();
                // 章节结束标识
                writer.write("############");
                writer.newLine();
            }
        }
        
        System.out.println("完整小说已保存至: " + filePath.toString());
    }
    
    /**
     * 保存单个章节到文件
     */
    private void saveChapterToFile(ChapterInfo chapter, String directory) throws IOException {
        Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        String filename = String.format("%04d_%s.txt", chapter.getNumber(), 
                                      sanitizeFilename(chapter.getTitle()));
        Path filePath = dirPath.resolve(filename);
        
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write(chapter.getTitle());
            writer.newLine();
            writer.newLine();
            writer.write(chapter.getContent());
        }
    }
    
    /**
     * 清理文件名中的非法字符
     */
    private String sanitizeFilename(String filename) {
        if (filename == null) return "unnamed";
        
        // 替换Windows文件系统中的非法字符
        return filename.replaceAll("[<>:\"/\\\\|?*]", "_")
                      .replaceAll("\\s+", "_")
                      .trim();
    }
    
    /**
     * 关闭线程池
     */
    private void shutdownExecutor() {
        System.out.println("=== 开始关闭资源 ===");


        // 关闭WebDriver池
        if (contentFetcher != null) {
            System.out.println("正在关闭WebDriver池...");
            contentFetcher.shutdown();
            System.out.println("WebDriver池已关闭");
        }

        // 关闭线程池
        if (executorService != null && !executorService.isShutdown()) {
            System.out.println("正在关闭线程池...");
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("线程池关闭超时，强制关闭...");
                    List<Runnable> remainingTasks = executorService.shutdownNow();
                    System.out.println("剩余未执行任务数: " + remainingTasks.size());
                    if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                        System.err.println("线程池强制关闭失败");
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("线程池关闭被中断");
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("✓ 资源关闭完成");
    }
    
    /**
     * 设置批处理大小
     * @param batchSize 每批处理的章节数
     */
    public MultiThreadNovelDownloader setBatchSize(int batchSize) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("批处理大小必须大于0");
        }
        this.batchSize = batchSize;
        return this;
    }

    
    /**
     * 公共关闭方法 - 确保所有资源被正确释放
     */
    public void close() {
        System.out.println("=== 手动关闭所有资源 ===");
        shutdownExecutor();
        
        System.out.println("✓ 手动资源清理完成");
    }

    public void start(String catalogUrl, String novelName) {
        // 屏蔽Selenium的各种警告信息
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
        Logger.getLogger("org.openqa.selenium.remote.http.WebSocket").setLevel(Level.OFF);

        MultiThreadNovelDownloader downloader = null;

        try {
            // 创建下载器实例
            downloader = new MultiThreadNovelDownloader().setBatchSize(50);  // 设置每批处理50章节，可根据内存情况调整

            System.out.println("ChromeDriver路径: " + chromeDriverPath);

            System.out.println("=== 开始使用多线程下载小说 ===");
            long startTime = System.currentTimeMillis();

            // 下载小说（true表示同时保存单独章节文件）, 目前不需要单独保存单个章节文件, 所以这里传入false
            downloader.downloadNovel(catalogUrl, novelName, false);

            long endTime = System.currentTimeMillis();
            System.out.println("多线程下载完成，耗时: " + (endTime - startTime) / 1000 + " 秒");

        } catch (Exception e) {
            System.err.println("下载失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 确保资源被正确释放
            if (downloader != null) {
                try {
                    downloader.close();
                } catch (Exception e) {
                    System.err.println("关闭资源时出错: " + e.getMessage());
                }
            }

            System.out.println("程序执行完毕");
        }
    }


    class ChapterDownloadTask implements Callable<ChapterDownloadResult> {

        private final ChapterInfo chapter;
        private final int currentIndex;
        private final int totalChapters;

        public ChapterDownloadTask(ChapterInfo chapter, int currentIndex, int totalChapters) {
            this.chapter = chapter;
            this.currentIndex = currentIndex;
            this.totalChapters = totalChapters;
        }

        @Override
        public ChapterDownloadResult call() throws Exception {
            try {
                if (DEBUG_MODE) {
                    System.out.println(String.format("[%d/%d] 开始下载: %s",
                            currentIndex, totalChapters, chapter));
                }

                String content = getSeleniumContent(chapter.getUrl());

                if (content != null && !content.trim().isEmpty()) {
                    if (DEBUG_MODE) {
                        System.out.println(String.format("[%d/%d] ✓ 下载成功: %s (长度: %d)",
                                currentIndex, totalChapters, chapter.getTitle(), content.length()));
                    }
                    return new ChapterDownloadResult(true, chapter, content, null);
                } else {
                    String errorMsg = "内容为空或获取失败";
                    if (DEBUG_MODE) {
                        System.err.println(String.format("[%d/%d] ✗ 下载失败: %s - %s",
                                currentIndex, totalChapters, chapter.getTitle(), errorMsg));
                    }
                    return new ChapterDownloadResult(false, chapter, null, errorMsg);
                }

            } catch (Exception e) {
                String errorMsg = e.getMessage();
                if (DEBUG_MODE) {
                    System.err.println(String.format("[%d/%d] ✗ 下载异常: %s - %s",
                            currentIndex, totalChapters, chapter.getTitle(), errorMsg));
                }
                return new ChapterDownloadResult(false, chapter, null, errorMsg);
            }
        }
    }
}