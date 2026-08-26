package com.mangareader.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaOkHttpConfig </p>
 * <p>描述: OkHttp配置类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/26 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Configuration
public class MangaOkHttpConfig {

    private final MangaDownloadConfig downloadConfig;

    public MangaOkHttpConfig(MangaDownloadConfig downloadConfig) {
        this.downloadConfig = downloadConfig;
    }

    @Bean("mangaDownloadHttpClient")
    public OkHttpClient mangaDownloadHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(downloadConfig.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(downloadConfig.getReadTimeout(), TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(
                        downloadConfig.getMaxThreads(),
                        5, TimeUnit.MINUTES))
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
    }
}
