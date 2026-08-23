package com.mangareader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaDownloadProperties </p>
 * <p>描述: 漫画下载相关配置属性 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@ConfigurationProperties(prefix = "manga.download")
@Data
public class MangaDownloadProperties {

    private String baseUrl;
    private String referer;
    private int timeout;
    private String cssSelector;
    private String userAgent;
    private Headers headers = new Headers();

    @Data
    public static class Headers {
        private String accept;
        private String acceptEncoding;
        private String acceptLanguage;
    }
}