package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 超时配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.timeout")
public class TimeoutConfig {

    /**
     * 默认超时时间 (毫秒)
     */
    private Integer defaultTimeout;

    /**
     * 短超时 (毫秒)
     */
    private Integer shortTimeout;

    /**
     * 中等超时 (毫秒)
     */
    private Integer mediumTimeout;

    /**
     * 长超时 (毫秒)
     */
    private Integer longTimeout;

    /**
     * 窗口切换超时 (毫秒)
     */
    private Integer windowSwitch;

    /**
     * 查找图片超时 (毫秒)
     */
    private Integer findImage;
}
