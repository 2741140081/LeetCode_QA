package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 等待时间配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.wait-time")
public class WaitTimeConfig {
    /**
     * 默认超时时间 (毫秒)
     */
    private Integer defaultTime;

    /**
     * 短超时 (毫秒)
     */
    private Integer shortTime;

    /**
     * 中等超时 (毫秒)
     */
    private Integer mediumTime;

    /**
     * 长超时 (毫秒)
     */
    private Integer longTime;
}
