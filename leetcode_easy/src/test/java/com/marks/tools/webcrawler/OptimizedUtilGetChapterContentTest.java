package com.marks.tools.webcrawler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: OptimizedUtilGetChapterContentTest </p>
 * <p>描述: [OptimizedUtilGetChapterContent性能测试类
 * 用于找到最佳的delayMillis参数，优化网络I/O性能
 * ] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class OptimizedUtilGetChapterContentTest {
    // CSV文件路径
    public static final String CSV_FILE_PATH = "D:\\gitProject\\LeetCode_QA\\leetcode_easy\\src\\main\\resources\\data\\testCsvData\\downloadChapterTestData.csv";
    private static final String CONTENT_ID = "chapter-content";
    private static final int SUCCESS_RATE_THRESHOLD = 90; // 90%成功率阈值
    private static final int TEST_THREAD_COUNT = 5; // 测试线程数
    private static final int MAX_TEST_DURATION = 300; // 最大测试时长(秒)

    /**
     * 章节测试数据类
     */
    static class ChapterTestData {
        private String url;
        private String expectedTitle;

        public ChapterTestData(String url, String expectedTitle) {
            this.url = url;
            this.expectedTitle = expectedTitle;
        }

        public String getUrl() { return url; }
        public String getExpectedTitle() { return expectedTitle; }

        @Override
        public String toString() {
            return String.format("章节URL: %s, 预期标题: %s", url, expectedTitle);
        }
    }

    /**
     * 测试结果统计类
     */
    /**
     * 测试结果统计类 - 使用原子类优化
     */
    static class TestStatistics {
        private final int delayMillis;
        private final AtomicInteger totalTests = new AtomicInteger(0);
        private final AtomicInteger successCount = new AtomicInteger(0);
        private final AtomicLong totalTime = new AtomicLong(0);
        private final List<Long> responseTimes;

        TestStatistics(int delayMillis) {
            this.delayMillis = delayMillis;
            this.responseTimes = new ArrayList<>();
        }

        void addResult(long responseTime, boolean success) {
            // 使用原子操作更新计数器
            totalTests.incrementAndGet();
            totalTime.addAndGet(responseTime);

            if (success) {
                successCount.incrementAndGet();
            }

            // responseTimes列表仍需同步保护
            synchronized (responseTimes) {
                responseTimes.add(responseTime);
            }
        }

        int getTotalTests() {
            return totalTests.get();
        }

        int getSuccessCount() {
            return successCount.get();
        }

        long getTotalTime() {
            return totalTime.get();
        }

        double getSuccessRate() {
            int total = totalTests.get();
            return total > 0 ? (double) successCount.get() / total * 100 : 0;
        }

        long getAverageTime() {
            int total = totalTests.get();
            return total > 0 ? totalTime.get() / total : 0;
        }

        @Override
        public String toString() {
            return String.format("延迟%dms: 成功率%.1f%% (%d/%d), 平均响应%dms",
                    delayMillis, getSuccessRate(),
                    getSuccessCount(), getTotalTests(), getAverageTime());
        }
    }

    @Test
    void fetchChapterContent() throws Exception {
        // 读取测试数据
        List<ChapterTestData> testData = readTestData();
        System.out.println("读取到 " + testData.size() + " 个测试章节");

        // 使用二分法寻找最佳延迟时间
        int optimalDelay = findOptimalDelayBinarySearch(testData);
        System.out.println("\n🎯 最佳延迟时间: " + optimalDelay + "ms");
    }

    /**
     * 读取测试数据
     */
    private List<ChapterTestData> readTestData() throws IOException {
        List<ChapterTestData> testData = new ArrayList<>();
        Path csvPath = Paths.get(CSV_FILE_PATH);

        if (!Files.exists(csvPath)) {
            throw new FileNotFoundException("测试数据文件不存在: " + CSV_FILE_PATH);
        }

        try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // 跳过表头
                }

                String[] parts = line.split(",", 2);
                if (parts.length >= 2) {
                    testData.add(new ChapterTestData(parts[0].trim(), parts[1].trim()));
                }
            }
        }

        return testData;
    }

    /**
     * 使用二分法寻找最佳延迟时间
     */
    private int findOptimalDelayBinarySearch(List<ChapterTestData> testData) {
        int left = 0;
        int right = 1000;
        int bestDelay = right;

        System.out.println("🔍 开始二分法搜索最佳延迟时间...");
        System.out.println("搜索范围: " + left + "ms - " + right + "ms");
        System.out.println("成功阈值: " + SUCCESS_RATE_THRESHOLD + "%");
        System.out.println("测试章节数: " + testData.size());
        System.out.println();

        while (left <= right) {
            int mid = left + (right - left) / 2;
            System.out.printf("测试延迟时间: %dms (范围: %d-%d)%n", mid, left, right);

            TestStatistics stats = testDelayWithMultipleChapters(testData, mid);
            System.out.println(stats);

            if (stats.getSuccessRate() >= SUCCESS_RATE_THRESHOLD) {
                // 达到成功率要求，尝试更小的延迟
                bestDelay = mid;
                right = mid - 50; // 步长50ms
                System.out.println("✓ 成功率达标，尝试更小延迟");
            } else {
                // 成功率不够，需要更大延迟
                left = mid + 50; // 步长50ms
                System.out.println("✗ 成功率不足，增大延迟");
            }

            System.out.println("---");

            // 避免请求过于频繁
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("测试被中断");
                break;
            }
        }

        return bestDelay;
    }

    /**
     * 测试指定延迟时间下多个章节的表现
     */
    private TestStatistics testDelayWithMultipleChapters(List<ChapterTestData> testData, int delayMillis) {
        TestStatistics stats = new TestStatistics(delayMillis);
        OptimizedUtilGetChapterContent contentFetcher = new OptimizedUtilGetChapterContent();

        try {
            ExecutorService executor = Executors.newFixedThreadPool(TEST_THREAD_COUNT);
            CountDownLatch latch = new CountDownLatch(testData.size());

            // 为每个测试章节提交任务
            for (ChapterTestData chapter : testData) {
                executor.submit(() -> {
                    try {
                        long startTime = System.currentTimeMillis();
                        String content = contentFetcher.fetchChapterContent(
                                chapter.getUrl(), CONTENT_ID, delayMillis);
                        long endTime = System.currentTimeMillis();

                        // 判断成功标准：内容不为空且长度合理
                        boolean isSuccess = content != null &&
                                !content.trim().isEmpty() &&
                                content.length() > 50;

                        // 使用原子类更新统计信息
                        stats.addResult(endTime - startTime, isSuccess);

                    } catch (Exception e) {
                        System.err.println("章节测试异常: " + chapter.getUrl() + " - " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // 等待所有测试完成（设置超时）
            boolean completed = latch.await(MAX_TEST_DURATION, TimeUnit.SECONDS);
            if (!completed) {
                System.err.println("测试超时，部分任务未完成");
            }

            executor.shutdown();
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("测试执行被中断");
        } finally {
            contentFetcher.shutdown();
        }

        return stats;
    }

    /**
     * 验证找到的最佳延迟时间
     */
    private void validateOptimalDelay(List<ChapterTestData> testData, int optimalDelay) {
        System.out.println("\n✅ 验证最佳延迟时间: " + optimalDelay + "ms");

        TestStatistics validationStats = testDelayWithMultipleChapters(testData, optimalDelay);
        System.out.println("验证结果: " + validationStats);

        if (validationStats.getSuccessRate() >= SUCCESS_RATE_THRESHOLD) {
            System.out.println("✓ 验证通过！延迟时间 " + optimalDelay + "ms 满足要求");
        } else {
            System.out.println("✗ 验证失败！延迟时间 " + optimalDelay + "ms 不满足要求");
        }
    }
}