package com.mangareader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaAsyncConfig </p>
 * <p>描述: 异步配置类: 线程池配置 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/26 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */


@Configuration
@EnableAsync
public class MangaAsyncConfig {

    private final MangaDownloadConfig downloadConfig;

    public MangaAsyncConfig(MangaDownloadConfig downloadConfig) {
        this.downloadConfig = downloadConfig;
    }

    @Bean("mangaDownloadExecutor")
    public Executor mangaDownloadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(downloadConfig.getCoreThreads());
        executor.setMaxPoolSize(downloadConfig.getMaxThreads());
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("manga-dl-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }
}
