package com.marks.tools.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/22 14:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ExecutorDemo {

    // 线程池配置
    private static final int THREAD_POOL_SIZE = 20;
    private static ExecutorService executor;
    public static void main(String[] args) {
        // 初始化线程池
        executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        int[] numbers = new int[1000];
        for (int i = 0; i < 1000; i++) {
            numbers[i] = i;
        }
        try {
            function(numbers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

    }

    private static void function(int[] numbers) {
        // 获取当前系统时间
        long startTime = System.currentTimeMillis();
        List<DownloadTaskDemo> tasks = new ArrayList<>();
        for (int number : numbers) {
            tasks.add(new DownloadTaskDemo(number));
        }
        // 提交下载任务到线程池
        List<Future<String>> futures = new ArrayList<>();
        for (DownloadTaskDemo task : tasks) {
            futures.add(executor.submit(task));
        }

        // 等待所有下载任务完成
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                System.out.println(result);
            } catch (Exception e) {
                System.err.println("下载任务执行出错: " + e.getMessage());
            }
        }
        // 获取当前结束时的系统时间
        long endTime = System.currentTimeMillis();
        System.out.println("下载完成，耗时: " + (endTime - startTime) + "ms");
    }

    static class DownloadTaskDemo implements Callable<String> {
        private final int id;

        public DownloadTaskDemo(int id) {
            this.id = id;
        }


        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public String call() throws Exception {
            String result = "图片下载完成: " + id;
            Thread.sleep(100);
            return result;
        }
    }
}
