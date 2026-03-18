package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 操作配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.operation")
public class OperationConfig {

    /**
     * 点击延迟 (毫秒)
     */
    private Integer clickDelay;

    /**
     * 双击间隔 (毫秒)
     */
    private Integer doubleClickInterval;

    /**
     * 按键持续时间 (毫秒)
     */
    private Integer keyPressDuration;

    /**
     * 鼠标移动速度
     */
    private Integer mouseMoveSpeed;

    /**
     * 移动持续时间 (毫秒)
     */
    private Integer moveDuration;

    /**
     * 事件延迟时间 (毫秒)，用于 Robot 操作后的延迟
     */
    private Integer eventDelay;
}
