package com.mangareader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ThreadPoolConfig </p>
 * <p>描述: 图片加载线程池配置 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Configuration
public class ThreadPoolConfig {

    /**
     * 图片下载处理线程池
     * 用于并行处理漫画图片的下载任务
     */
    @Bean("imageLoadExecutor")
    public ExecutorService imageLoadExecutor() {
        AtomicInteger threadNumber = new AtomicInteger(1);
        return new ThreadPoolExecutor(
                4,
                8,
                60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(200),
                r -> new Thread(r, "image-load-thread-" + threadNumber.getAndIncrement()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

    /**
     * 章节处理线程池
     * 用于并行处理漫画章节的下载任务
     */
    @Bean("chapterProcessExecutor")
    public ExecutorService chapterProcessExecutor() {
        AtomicInteger threadNumber = new AtomicInteger(1);
        return new ThreadPoolExecutor(
                3, // 核心线程数
                6, // 最大线程数
                60, TimeUnit.MILLISECONDS, // 空闲线程存活时间
                new LinkedBlockingDeque<>(100), // 任务队列容量
                r -> new Thread(r, "manga-chapter-process-thread-" + threadNumber.getAndIncrement()),
                new ThreadPoolExecutor.CallerRunsPolicy() // 队列满时的处理策略
        );
    }
}
