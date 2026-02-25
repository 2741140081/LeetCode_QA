package com.marks.tools.webcrawler;

import com.marks.tools.download_txt_multi_threading.entity.ChapterInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.marks.tools.download_txt_multi_threading.constant.DownloadConstant.BASE_URL;
import static com.marks.tools.download_txt_multi_threading.constant.DownloadConstant.USER_AGENT;
import static com.marks.tools.webcrawler.OptimizedUtilGetChapterContentTest.CSV_FILE_PATH;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SaveChapterUrl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SaveChapterUrlTest {

    @Test
    void saveChapterUrl() throws IOException {
        Path csvPath = Paths.get(CSV_FILE_PATH);
        List<ChapterInfo> chapterInfoList = parseNovelCatalog("https://www.biquge5200.cc/book/1/");
        // 将chapterInfoList 存储到 downloadChapterTestData.csv 文件中
        // 写入CSV文件
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvPath.toFile()))) {
            for (ChapterInfo info : chapterInfoList) {
                String[] row = new String[]{ info.getUrl(), info.getTitle()};
                writer.println(String.join(",", row));
            }
        }

        System.out.println("测试数据文件创建完成");
    }


    private Document connectWithRetry(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent(USER_AGENT)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
                .header("Referer", BASE_URL)
                .timeout(30000)
                .get();
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

            Document doc = connectWithRetry(currentPageUrl);
            Element containerDiv = doc.selectFirst("div#list-chapter");
            Element chapterList;

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
}
