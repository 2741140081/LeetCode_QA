package com.marks.skillsoft;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/18 16:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
import java.util.concurrent.*;
import java.util.*;

public class ThreadPoolRecursiveExample {
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);
    private static final int MAX_ITERATIONS = 6; // 最大迭代次数
    private static int currentIteration = 0;

    private static int maxCount = 400;

    private static void downloadOperation(Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("key:" +entry.getKey() + ", value:" + entry.getValue());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void downloadSomething(int index) {
        if (index >= maxCount) {
            return;
        }

        final Map<String, Integer> dataMap = getData(index, 60);

        int nextIndex = index + 60;

        executor.submit(() -> {
            System.out.println("map size:" + dataMap.size() + ", nextIndex:" + nextIndex);
            downloadOperation(dataMap);
        });

        downloadSomething(nextIndex); // 递归调用
    }

    private static Map<String, Integer> getData(int index, int count) {
        Map<String, Integer> map = new HashMap<>();
        while (count > 0) {
            count--;
            map.put("id_" + index, index);
            index++;
        }
        return map;
    }

    public static void main(String[] args) {
        int index = 0;
        downloadSomething(index); // 初始调用

        executor.shutdown();
        try {
            if (!executor.awaitTermination(600, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
