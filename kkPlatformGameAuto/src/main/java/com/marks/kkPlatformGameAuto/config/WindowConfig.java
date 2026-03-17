package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 窗口配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.window")
public class WindowConfig {
    /**
     * 平台和准备房间窗口名称（同名）
     */
    private String platformAndRoomName;

    /**
     * 修改器窗口名称
     */
    private String modifierName;

    /**
     * 游戏主体窗口名称
     */
    private String gameName;

    /**
     * 窗口切换等待时间 (毫秒)
     */
    private Integer switchWaitTime;

    /**
     * 窗口切换超时时间（毫秒）
     */
    private Integer timeout;
}
