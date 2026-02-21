package com.marks.tools.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * 章节内容获取工具类
 * 严格按照开发规范：仅通过ID选择器提取指定内容，不执行额外处理
 */
public class UtilGetChapterContent {

    private final String chromeDriverPath = "D:/Project/chrome/chromedriver-win64/chromedriver.exe";
    
    /**
     * 获取章节内容的核心方法
     * @param chapterUrl 章节页面URL
     * @param id 目标内容的ID选择器（如"chapter-content"）
     * @return 章节内容文本，其中<br>标签已被替换为换行符
     */
    public String fetchChapterContent(String chapterUrl, String id, int delayMillis) {
        WebDriver driver = null;
        try {
            // 配置Chrome选项
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // 无头模式
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            
            // 创建WebDriver实例
            driver = new ChromeDriver(options);
            driver.get(chapterUrl);
            
            // 等待页面加载完成
            Thread.sleep(delayMillis);
            
            // 获取网页源码
            String pageSource = driver.getPageSource();
            
            // 将源码转为Document对象
            Document doc = Jsoup.parse(pageSource);
            
            // 通过id获取章节内容元素
            Element contentElement = doc.getElementById(id);
            
            if (contentElement != null) {
                // 提取纯文本内容
                String content = contentElement.text();
                
                // 将<br>标签替换为换行符
                // 注意：Jsoup的text()方法已经处理了大部分换行，这里做额外处理
                String htmlContent = contentElement.html();
                String processedContent = htmlContent.replaceAll("(?i)<br[^>]*/?>", "\n");
                
                // 清理多余的空白行和空格
                processedContent = processedContent.replaceAll("\n\s*\n", "\n\n");
                processedContent = processedContent.trim();
                
                return processedContent;
            } else {
                System.err.println("未找到ID为 '" + id + "' 的元素");
                return "";
            }
            
        } catch (Exception e) {
            System.err.println("获取章节内容时发生错误: " + e.getMessage());
            e.printStackTrace();
            return "";
        } finally {
            // 确保关闭浏览器资源
            if (driver != null) {
                try {
                    driver.quit();
                } catch (Exception e) {
                    System.err.println("关闭浏览器时发生错误: " + e.getMessage());
                }
            }
        }
    }
}
