package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 截图配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.screenshot")
public class ScreenshotConfig {
    /**
     * 结果保存目录
     * 如果未设置，默认使用 ${game.auto.base-dir}/results
     */
    private String resultDir;

    /**
     * 临时截图目录
     * 如果未设置，默认使用 ${game.auto.base-dir}/temp
     */
    private String tempDir;

    /**
     * 图片格式
     */
    private String format = "png";
}
