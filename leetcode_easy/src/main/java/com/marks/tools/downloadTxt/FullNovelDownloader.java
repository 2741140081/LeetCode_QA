package com.marks.tools.downloadTxt;

import com.marks.tools.webcrawler.UtilGetChapterContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 完整版小说下载器
 * 支持解析目录、下载章节、保存为TXT文件
 */
public class FullNovelDownloader {
    
    private static final String BASE_URL = "https://bcshuku.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/145.0.0.0 Safari/537.36";
    private static final String SAVE_DIRECTORY = "D:/novels/"; // 保存目录

    private String chromeDriverPath = ""; // ChromeDriver路径，将在构造函数中设置

    
    /**
     * 构造函数
     */
    public FullNovelDownloader() {
        // 设置默认ChromeDriver路径（如果环境变量中存在）
        String systemChromeDriverPath = System.getenv("CHROMEDRIVER_PATH");
        if (systemChromeDriverPath != null && !systemChromeDriverPath.isEmpty()) {
            this.chromeDriverPath = systemChromeDriverPath;
        }
    }
    
    /**
     * 带ChromeDriver路径的构造函数
     */
    public FullNovelDownloader(String chromeDriverPath) {
        this(); // 调用默认构造函数
        if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
            this.chromeDriverPath = chromeDriverPath;
        }
    }
    
    /**
     * 章节信息类
     */
    public static class Chapter {
        private String title;
        private String url;
        private int number;
        private String content;
        
        public Chapter(String title, String url, int number) {
            this.title = title;
            this.url = url;
            this.number = number;
        }
        
        // Getters and Setters
        public String getTitle() { return title; }
        public String getUrl() { return url; }
        public int getNumber() { return number; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        
        @Override
        public String toString() {
            return String.format("第%d章: %s", number, title);
        }
    }
    
    /**
     * 解析小说目录（支持分页）
     */
    public List<Chapter> parseNovelCatalog(String catalogUrl) throws IOException {
        System.out.println("=== 开始解析小说目录（支持分页）===");
        List<Chapter> allChapters = new ArrayList<>();
        String currentPageUrl = catalogUrl;
        int pageNumber = 1;
        int totalChapterNum = 1;
        
        do {
            System.out.println("\n--- 解析第 " + pageNumber + " 页 ---");
            System.out.println("页面URL: " + currentPageUrl);
            
            Document doc = connectWithRetry(currentPageUrl, 3);
            List<Chapter> pageChapters = new ArrayList<>();
            
            // 根据您提供的HTML结构解析 - 通过ID定位div容器，然后找内部的ul
            Element containerDiv = doc.selectFirst("div#list-chapter");
            Element chapterList = null;
            
            if (containerDiv != null) {
                // 在div容器内查找ul元素
                chapterList = containerDiv.selectFirst("ul");
                if (chapterList != null) {
                    System.out.println("已通过ID div#list-chapter 精确定位章节列表容器");
                } else {
                    throw new IOException("找到div#list-chapter但未找到内部的ul元素");
                }
            } else {
                // 如果没有ID容器，尝试直接通过class定位ul
                chapterList = doc.selectFirst("ul.list-chapter");
                if (chapterList == null) {
                    throw new IOException("未找到章节列表容器 div#list-chapter 或 ul.list-chapter");
                } else {
                    System.out.println("警告: 使用class选择器定位ul.list-chapter，可能存在重复结构");
                }
            }
            
            // 提取当前页的所有章节项
            var chapterItems = chapterList.select("li");
            System.out.println("当前页找到章节项数量: " + chapterItems.size());
            
            for (Element item : chapterItems) {
                try {
                    Element link = item.selectFirst("a[href]");
                    if (link != null) {
                        String href = link.attr("href");
                        String title = link.attr("title");
                        
                        // 构造完整URL
                        String fullUrl = href.startsWith("http") ? href : BASE_URL + href;
                        
                        Chapter chapter = new Chapter(title, fullUrl, totalChapterNum++);
                        pageChapters.add(chapter);
                    }
                } catch (Exception e) {
                    System.err.println("解析章节项失败: " + e.getMessage());
                }
            }
            
            System.out.println("第 " + pageNumber + " 页解析到 " + pageChapters.size() + " 个章节");
            allChapters.addAll(pageChapters);
            
            // 查找下一页链接
            String nextPageUrl = findNextPageUrl(doc);
            nextPageUrl = null; // 测试用, 方便运行流程
            if (nextPageUrl != null) {
                currentPageUrl = nextPageUrl.startsWith("http") ? nextPageUrl : BASE_URL + nextPageUrl;
                pageNumber++;
                System.out.println("找到下一页链接: " + currentPageUrl);
                
                // 添加延迟避免请求过快
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {
                System.out.println("已到达最后一页");
                break;
            }
            
        } while (true);
        
        System.out.println("\n=== 目录解析完成 ===");
        System.out.println("总共解析到 " + allChapters.size() + " 个章节");
        System.out.println("共 " + pageNumber + " 页");
        
        return allChapters;
    }
    
    /**
     * 查找下一页链接
     * @param doc 当前页面的Document对象
     * @return 下一页的URL，如果没有则返回null
     */
    private String findNextPageUrl(Document doc) {
        try {
            // 方法1: 通过id="pagination"定位分页容器
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
     * 获取章节内容（使用新的动态内容获取器）
     */
    public String getChapterContent(String chapterUrl) throws IOException {
        System.out.println("正在获取章节内容: " + chapterUrl);
        
        try {
            // 使用动态内容获取器（推荐方式）
            // 使用Selenium获取完整加载后的页面内容
            String content = getSeleniumContent(chapterUrl);
            if (content != null && !content.trim().isEmpty() && content.length() > 50) {
                System.out.println("✓ 使用Selenium成功获取内容，长度: " + content.length() + " 字符");
                return cleanContent(content);
            }
        } catch (Exception e) {
            System.out.println("动态获取器失败，回退到传统方法: " + e.getMessage());
        }
        
        // 回退到传统方法
        return "";
    }
    
    /**
     * 使用Selenium获取章节内容
     */
    private String getSeleniumContent(String chapterUrl) {
        try {
            System.out.println("正在使用Selenium获取章节内容...");
            UtilGetChapterContent utils = new UtilGetChapterContent();
            String chapterContent = utils.fetchChapterContent(chapterUrl, "chapter-content", 1000);
            if (chapterContent.length() > 1000) {
                System.out.println("✓ 通过fetchChapterContent获取章节内容成功");
                return chapterContent;
            }

        } catch (Exception e) {
            System.out.println("Selenium获取内容失败: " + e.getMessage());
        }
        
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
    public Document connectWithRetry(String url, int maxRetries) throws IOException {
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
     * 保存单个章节到文件
     */
    public void saveChapterToFile(Chapter chapter, String directory) throws IOException {
        Path dirPath = Paths.get(directory);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        String filename = String.format("%04d_%s.txt", chapter.getNumber(), 
                                      sanitizeFilename(chapter.getTitle()));
        Path filePath = dirPath.resolve(filename);
        
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write("第" + chapter.getNumber() + "章: " + chapter.getTitle());
            writer.newLine();
            writer.newLine();
            writer.write(chapter.getContent());
        }
        
        System.out.println("已保存: " + filename);
    }
    
    /**
     * 保存完整小说到单个文件
     */
    public void saveCompleteNovel(List<Chapter> chapters, String novelName) throws IOException {
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
            writer.newLine();
            writer.write("目录");
            writer.newLine();
            writer.write("====");
            writer.newLine();
            
            // 写入目录
            for (Chapter chapter : chapters) {
                writer.write(String.format("第%d章: %s", chapter.getNumber(), chapter.getTitle()));
                writer.newLine();
            }
            
            writer.newLine();
            writer.write("正文");
            writer.newLine();
            writer.write("====");
            writer.newLine();
            writer.newLine();
            
            // 写入各章节内容
            for (Chapter chapter : chapters) {
                writer.write("第" + chapter.getNumber() + "章: " + chapter.getTitle());
                writer.newLine();
                writer.newLine();
                writer.write(chapter.getContent());
                writer.newLine();
                writer.newLine();
                writer.newLine();
                
                System.out.println("已写入章节: " + chapter.getTitle());
            }
        }
        
        System.out.println("完整小说已保存至: " + filePath.toString());
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
     * 主要下载方法
     */
    public void downloadNovel(String catalogUrl, String novelName, boolean saveSeparateFiles) {
        try {
            // 1. 解析目录
            System.out.println("=== 开始下载小说 ===");
            List<Chapter> chapters = parseNovelCatalog(catalogUrl);
            System.out.println("共找到 " + chapters.size() + " 个章节");
            
            // 2. 逐章下载内容
            System.out.println("\n=== 开始下载章节内容 ===");
            for (int i = 0; i < chapters.size(); i++) {
                Chapter chapter = chapters.get(i);
                System.out.println(String.format("[%d/%d] %s", 
                    i + 1, chapters.size(), chapter));
                
                try {
                    String content = getChapterContent(chapter.getUrl());
                    chapter.setContent(content);
                    
                    // 如果需要保存单独文件
                    if (saveSeparateFiles) {
                        String directory = SAVE_DIRECTORY + sanitizeFilename(novelName) + "/chapters";
                        saveChapterToFile(chapter, directory);
                    }
                    
                    // 添加延迟避免请求过快
                    if (i < chapters.size() - 1) {
                        TimeUnit.MILLISECONDS.sleep(500);
                    }
                    
                } catch (Exception e) {
                    System.err.println("下载章节失败: " + chapter + " - " + e.getMessage());
                }
            }
            
            // 3. 保存完整小说
            System.out.println("\n=== 保存完整小说 ===");
            saveCompleteNovel(chapters, novelName);
            
            System.out.println("\n=== 下载完成 ===");
            
        } catch (Exception e) {
            System.err.println("下载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 测试方法
     */
    public static void main(String[] args) {
        // 屏蔽Selenium的CDP版本警告（可选）
        Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(Level.OFF);
        
        FullNovelDownloader downloader = null;
        
        try {
            String catalogUrl = "https://bcshuku.com/novel50252/";
            String novelName = "测试小说"; // 请替换为实际小说名
            
            // 创建下载器实例
            downloader = new FullNovelDownloader();
            
            System.out.println("=== 开始使用Selenium下载小说 ===");
            long startTime = System.currentTimeMillis();
            // 下载小说（第二个参数true表示同时保存单独章节文件）
            downloader.downloadNovel(catalogUrl, novelName, true);
            long endTime = System.currentTimeMillis();
            System.out.println("下载完成，耗时: " + (endTime - startTime) / 1000 + " 秒");
        } catch (Exception e) {
            System.err.println("下载失败: " + e.getMessage());
            System.err.println("请确保：");
            System.err.println("1. 已安装Chrome浏览器");
            System.err.println("2. 已下载对应版本的ChromeDriver");
            System.err.println("3. ChromeDriver路径配置正确");
            e.printStackTrace();
        }
    }
}