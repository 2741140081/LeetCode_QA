package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 准备房间配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.prepare-room")
public class PrepareRoomConfig {
    /**
     * 准备房间图片存放目录
     */
    private String imageDir;

    /**
     * 开始游戏按钮图片名称
     */
    private String startGameButtonImage;

    /**
     * 房间号标识图片名称
     */
    private String roomNumberLabelImage;

    /**
     * 房间号输入框相对于标识的偏移量 X
     */
    private Integer roomNumberOffsetX;

    /**
     * 房间号输入框相对于标识的偏移量 Y
     */
    private Integer roomNumberOffsetY;
}
