package com.marks.tools.spider.txt;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: TxtCrawler </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/5 17:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TxtCrawler {
    private static final String ChromeDriver = "D:\\chromeTest\\chromedriver-win64\\chromedriver.exe";

    public static void main(String[] args) {
        // 设置ChromeDriver 的路径
        System.setProperty("webdriver.chrome.driver", ChromeDriver);
        // 初始化 WebDriver 实例
        WebDriver driver = new ChromeDriver();
        TxtCrawler txtCrawler = new TxtCrawler();
        try {
            txtCrawler.processPage( driver, "https://www.17k.com/list/1075763.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 关闭 WebDriver 实例
        driver.quit();
    }

    private void processPage(WebDriver driver, String pageUrl) throws Exception {
        // 打开页面
        driver.get(pageUrl);
        // 等待页面加载完成
        Thread.sleep(5000);
        // 获取页面源码
        String pageSource = driver.getPageSource();
        // 解析页面
        Document doc = Jsoup.parse(pageSource);

        // 使用 select 选择器提取章节信息
        Elements volumeElements = doc.select("dl.Volume"); // 获取所有卷
        for (Element volume : volumeElements) {
            String volumeTitle = volume.select("dt .tit").text(); // 卷标题
            System.out.println("卷标题: " + volumeTitle);

            Elements chapterLinks = volume.select("dd a"); // 获取当前卷的所有章节链接
            for (Element link : chapterLinks) {
                String chapterName = link.select("span.ellipsis").text(); // 章节名称
                String chapterUrl = link.attr("href"); // 章节 URL
                System.out.println("章节名称: " + chapterName + ", 章节URL: " + chapterUrl);
            }
        }
    }
}
