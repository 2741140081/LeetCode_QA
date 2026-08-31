package com.mangareader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaProperties </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/19 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@ConfigurationProperties(prefix = "manga")
@Data
public class MangaProperties {
    private Storage storage = new Storage();
    private Reader reader = new Reader();
    private Image image = new Image();

    /**
     * 存储配置
     */
    @Data
    public static class Storage {
        private String root;
        private String imagePath;
        private String cacheRoot;
    }

    /**
     * 阅读器配置
     */
    @Data
    public static class Reader {
        private int preloadCount;
        private int saveInterval;
        private double defaultScale;
        private double scrollStep;
    }

    /**
     * 图片配置
     */
    @Data
    public static class Image {
        private String supportedFormats;
        private int maxMemoryCacheSize;
        private String defaultErrorImage;
    }
}
