package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 图像识别配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.image-recognition")
public class ImageRecognitionConfig {
    /**
     * 默认缩放比例
     */
    private Double defaultScale;
    /**
     * 置信度
     */
    private Double similarityThreshold;

    /**
     * 图片匹配间隔
     */
    private int interval;

    /**
     * 矩形框粗细
     */
    private int rectThickness;

    /**
     * 矩形颜色配置 (BGR 格式)
     */
    private double rectColorB;
    private double rectColorG;
    private double rectColorR;
}
