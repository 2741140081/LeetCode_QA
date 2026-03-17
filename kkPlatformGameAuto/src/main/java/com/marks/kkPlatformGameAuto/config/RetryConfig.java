package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 重试配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.retry")
public class RetryConfig {

    /**
     * 最大重试次数
     */
    private Integer maxAttempts;

    /**
     * 重试间隔 (毫秒)
     */
    private Integer delay;

    /**
     * 退避乘数
     */
    private Double backoffMultiplier;
}
