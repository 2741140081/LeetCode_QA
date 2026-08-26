package com.mangareader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * 下载配置类
 * @param: null
 * @return
 * @author marks
 * @CreateDate: 2026/08/26 11:14
 * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Data
@Component
@ConfigurationProperties(prefix = "manga.download")
public class MangaDownloadConfig {
    /** 定时任务扫描Cron表达式 */
    private String scanCron = "0/2 * * * * ?";
    /** 下载中任务超时时间(秒,防止僵死任务) */
    private int downloadTimeout = 300;
    /** 线程池核心线程数 */
    private int coreThreads = 8;
    /** 线程池最大线程数 */
    private int maxThreads = 16;
    /** 每批处理数量 */
    private int batchSize = 50;
    /** 单个任务最大重试次数 */
    private int maxRetry = 3;
    /** 连接超时(ms) */
    private int connectTimeout = 10000;
    /** 读取超时(ms) */
    private int readTimeout = 30000;
    /** 下载缓冲大小(bytes) */
    private int bufferSize = 8192;
}

