package com.marks.tools.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

/**
 * 重构后的章节内容获取工具类
 * 使用WebDriver实例池优化性能和资源管理
 */
public class OptimizedUtilGetChapterContent {
    private static final Logger logger = Logger.getLogger(OptimizedUtilGetChapterContent.class.getName());

    private final WebDriverPool webDriverPool;

    public OptimizedUtilGetChapterContent() {
        this.webDriverPool = WebDriverPool.getInstance();
    }

    public OptimizedUtilGetChapterContent(WebDriverPool pool) {
        this.webDriverPool = pool;
    }

    /**
     * 获取章节内容的核心方法（使用池化WebDriver）
     * @param chapterUrl 章节页面URL
     * @param id 目标内容的ID选择器（如"chapter-content"）
     * @param delayMillis 页面加载等待时间（毫秒）
     * @return 章节内容文本
     */
    public String fetchChapterContent(String chapterUrl, String id, int delayMillis) {
        WebDriver driver;
        try {
            // 从池中获取WebDriver实例
            driver = webDriverPool.borrowWebDriver();

            logger.fine("开始获取章节内容: " + chapterUrl);

            // 访问目标页面
            driver.get(chapterUrl);

            // 等待页面加载完成
            Thread.sleep(delayMillis);

            // 获取网页源码
            String pageSource = driver.getPageSource();

            // 添加释放WebDriver实例
            webDriverPool.returnWebDriver(driver);

            // 使用Jsoup解析HTML
            Document doc = Jsoup.parse(pageSource);

            // 通过ID获取章节内容元素
            Element contentElement = doc.getElementById(id);

            if (contentElement != null) {
                // 提取并处理内容
                String content = extractAndProcessContent(contentElement);
                logger.fine("成功获取章节内容，长度: " + content.length());
                return content;
            } else {
                logger.warning("未找到ID为 '" + id + "' 的元素");
                return "";
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.severe("获取章节内容被中断: " + e.getMessage());
            return "";
        } catch (Exception e) {
            logger.severe("获取章节内容时发生错误: " + e.getMessage());
            e.printStackTrace();
            return "";
        } finally {
            // do nothing
        }
    }

    /**
     * 提取并处理章节内容
     */
    private String extractAndProcessContent(Element contentElement) {
        // 处理HTML内容中的换行
        String htmlContent = contentElement.html();
        String processedContent = htmlContent.replaceAll("(?i)<br[^>]*/?>", "\n");

        // 清理多余空白行和空格
        processedContent = processedContent.replaceAll("\n\\s*\n", "\n\n");
        processedContent = processedContent.trim();

        return processedContent;
    }

    /**
     * 获取池状态信息
     */
    public String getPoolStatus() {
        return webDriverPool.getPoolStatus();
    }

    /**
     * 关闭资源
     */
    public void shutdown() {
        webDriverPool.shutdown();
    }
}
