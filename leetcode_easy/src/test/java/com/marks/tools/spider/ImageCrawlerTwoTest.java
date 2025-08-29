package com.marks.tools.spider;

import com.marks.tools.video.TextToCsvProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.concurrent.Executors;

import static com.marks.tools.spider.ImageCrawlerTwo.*;

class ImageCrawlerTwoTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/DownloadImgTestData.csv", numLinesToSkip = 1, nullValues = "null")
    void testDownloadImg(String index) {
        ImageCrawlerTwo crawlerTwo = new ImageCrawlerTwo();
        ID_OF_HTML = index;
        crawledPages = 0;
        executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        SAVE_DIR = "D:\\spider\\data\\4173_69\\" + ID_OF_HTML + "\\";
        START_URL = BASE_URL + "/chapter/" + ID_OF_HTML + ".html";
        // 确保保存目录存在
        createSaveDirectory();

        crawlImages(START_URL);

//        shutdown();

    }


    @Test
    void updateCsvFile() {
        new TextToCsvProcessor().solution();
    }
}