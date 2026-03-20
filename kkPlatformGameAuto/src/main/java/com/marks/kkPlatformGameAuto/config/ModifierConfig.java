package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ModifierConfig </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.modifier")
public class ModifierConfig {
    /**
     * 修改器图片存放目录
     */
    private String imageDir;

    /**
     * 查找游戏按钮图片名称
     */
    private String findGameButtonImage;

    /**
     * 人物属性标签图片名称
     */
    private String characterAttributeLabelImage;

    /**
     * 经验值输入框标签图片名称
     */
    private String experienceInputBoxLabelImage;

    /**
     * 物品信息标签图片名称
     */
    private String itemInfoLabelImage;

    /**
     * 物品信息标签栏下的所有物品相对于信息标签的偏移量 X
     */
    private Integer itemOffsetX;

    /**
     * 物品信息标签栏下的所有物品相对于信息标签的偏移量 Y
     */
    private Integer itemOffsetY;

    /**
     * 物品标签图片的高度
     */
    private Integer itemLabelHeight;

    /**
     * 目标值标签图片名称
     */
    private String targetValueLabelImage;

    /**
     * 输入框相对于目标值标签的偏移量 X
     */
    private Integer inputOffsetX;

    /**
     * 输入框相对于目标值标签的偏移量 Y
     */
    private Integer inputOffsetY;

    /**
     * 修改按钮图片名称
     */
    private String modifyButtonImage;

    /**
     * 锁定间隔图片名称
     */
    private String lockIntervalImage;
}
