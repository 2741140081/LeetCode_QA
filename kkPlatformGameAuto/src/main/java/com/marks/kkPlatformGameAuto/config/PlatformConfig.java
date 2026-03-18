package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 平台配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.platform")
public class PlatformConfig {
    /**
     * 平台图片存放目录
     */
    private String imageDir;

    /**
     * 平台 logo 图片名称
     */
    private String platformLogoImage;

    /**
     * 创建房间按钮图片名称
     */
    private String createRoomButtonImage;
}
