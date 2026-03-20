package com.marks.kkPlatformGameAuto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 小偷游戏配置
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19
 */
@Data
@Component
@ConfigurationProperties(prefix = "game.auto.thief")
public class ThiefGameConfig {

    /**
     * 小偷游戏图片存放目录
     */
    private String imageDir;

    /**
     * 文件夹配置
     */
    private FolderConfig folder;

    /**
     * 游戏图片名称配置
     */
    private GameImageConfig gameImage;

    /**
     * 编号配置
     */
    private NumberingConfig numbering;

    /**
     * 存档建筑配置
     */
    private ArchiverConfig archiver;

    /**
     * 装备物品配置
     */
    private EquipmentConfig equipment;

    /**
     * 修改物品配置
     */
    private ModifyItemConfig modifyItem;

    /**
     * 金矿偏移量
     */
    private int goldMineOffsetX;
    private int goldMineOffsetY;

    /**
     *  吞噬装备次数
     */
    private int devourEquipmentTimes;

    /**
     *  属性强化次数
     */
    private int attackSpeedUpTimes;
    private int rangeUpTimes;
    private int multiShotUpTimes;

    /**
     * 小偷游戏主体运行时间
     */
    private long thiefGameRunTime;

    /**
     * 小偷游戏失败时间
     */
    private int thiefGameFailureTime;

    /**
     * 存档挑战需要的时间
     */
    private int thiefGameArchiverTime;


    /**
     * 快捷键配置
     */
    private char attackSKey;
    private char tenClickSKey;

    /**
     * 编号配置内部类
     */
    @Data
    public static class NumberingConfig {
        /**
         * 小偷人物编号
         */
        private Integer thiefNumber;

        /**
         * 储物柜编号
         */
        private Integer lockerNumber;

        /**
         * 最终 BOSS 挑战建筑编号
         */
        private Integer finalBossBuildingNumber;

        /**
         * 属性强化建筑编号
         */
        private Integer attributeEnhanceBuildingNumber;
    }

    /**
     * 文件夹配置内部类
     */
    @Data
    public static class FolderConfig {
        /**
         * 公共图片目录
         */
        private String common;

        /**
         * 存档建筑图片前缀目录
         */
        private String archiverPrefix;

        /**
         * 装备图片目录
         */
        private String equipment;

        /**
         * 储物柜图片目录
         */
        private String locker;

        /**
         * 小偷人物图片目录
         */
        private String thiefHero;

        /**
         * 最终 BOSS 图片目录
         */
        private String finalBoss;

        /**
         * 属性强化建筑图片目录
         */
        private String enhanceBuilding;
    }

    /**
     * 游戏图片名称配置内部类
     */
    @Data
    public static class GameImageConfig {
        /**
         * 储物柜技能图片
         */
        private String lockerImage;

        /**
         * 最终 BOSS 建筑图片
         */
        private String finalBossBuildingImage;

        /**
         * 属性强化建筑图片
         */
        private String attributeEnhanceBuildingImage;

        /**
         * 小偷掉落技能图片
         */
        private String thiefDropSkillImage;

        /**
         * 小偷左上角图片
         */
        private String thiefLeftTopImage;

        /**
         * 小偷完美丢弃点图片
         */
        private String thiefPerfectDiscardPointImage;

        /**
         * 储物柜技能图片
         */
        private String lockerSkillImage;

        /**
         * 储物柜冰环技能图片
         */
        private String lockerIceHaloSkillImage;

        /**
         * 攻击速度提升技能图片
         */
        private String attackSpeedUpImage;

        /**
         * 射程提升技能图片
         */
        private String rangeUpImage;

        /**
         * 多重射击提升技能图片
         */
        private String multiShotUpImage;

        /**
         * 技能图标图片
         */
        private String finalBossImage;

        /**
         * 游戏胜利图片
         */
        private String victoryImage;
    }

    /**
     * 存档建筑配置内部类
     */
    @Data
    public static class ArchiverConfig {
        /**
         * 存档建筑编号列表 (1-6)
         */
        private List<Integer> buildingNumbers;
    }

    /**
     * 装备物品配置内部类
     */
    @Data
    public static class EquipmentConfig {
        /**
         * 装备图片名称列表
         */
        private List<String> imageNames;
    }

    /**
     * 修改物品配置内部类
     */
    @Data
    public static class ModifyItemConfig {
        /**
         * 第一次修改的物品列表
         */
        private List<String> firstModify;

        /**
         * 第二次修改的物品列表
         */
        private List<String> secondModify;

        /**
         * 第三次修改的物品列表
         */
        private List<String> thirdModify;

        /**
         * 默认修改的物品列表
         */
        private List<String> defaultModify;
    }

}
