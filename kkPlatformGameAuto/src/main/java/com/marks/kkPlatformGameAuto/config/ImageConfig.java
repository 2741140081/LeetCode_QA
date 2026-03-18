package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 图片通用配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17 11:10
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.image")
public class ImageConfig {
    /**
     * 默认图片格式（不包含点）
     */
    private String defaultFormat;

    /**
     * 图片质量 (0.0-1.0)
     */
    private Double defaultQuality;

    /**
     * 是否自动添加文件扩展名
     */
    private Boolean autoAddExtension;
}
