package com.marks.kkPlatformGameAuto.config.properties;


import com.marks.kkPlatformGameAuto.config.ThiefGameConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

/**
 * 小偷游戏配置属性工具类
 * 提供便捷的配置访问方法
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19
 */
@Slf4j
@Data
@Component
public class ThiefGameProperties {
    @Autowired
    private ThiefGameConfig thiefGameConfig;
    /**
     * 获取小偷人物编号
     * @return 小偷编号
     */
    public Integer getThiefNumber() {
        return thiefGameConfig.getNumbering().getThiefNumber();
    }

    /**
     * 获取储物柜编号
     * @return 储物柜编号
     */
    public Integer getLockerNumber() {
        return thiefGameConfig.getNumbering().getLockerNumber();
    }

    /**
     * 获取最终 BOSS 建筑编号
     * @return BOSS 建筑编号
     */
    public Integer getFinalBossBuildingNumber() {
        return thiefGameConfig.getNumbering().getFinalBossBuildingNumber();
    }

    /**
     * 获取属性强化建筑编号
     * @return 属性强化建筑编号
     */
    public Integer getAttributeEnhanceBuildingNumber() {
        return thiefGameConfig.getNumbering().getAttributeEnhanceBuildingNumber();
    }

    /**
     * 获取第一次修改的物品列表
     * @return 物品列表
     */
    public List<String> getFirstModifyItems() {
        return thiefGameConfig.getModifyItem().getFirstModify();
    }

    /**
     * 获取第二次修改的物品列表
     * @return 物品列表
     */
    public List<String> getSecondModifyItems() {
        return thiefGameConfig.getModifyItem().getSecondModify();
    }

    public List<String> getThirdModifyItems() {
        return thiefGameConfig.getModifyItem().getThirdModify();
    }

    /**
     * 获取默认修改的物品列表
     * @return 物品列表
     */
    public List<String> getDefaultModifyItems() {
        return thiefGameConfig.getModifyItem().getDefaultModify();
    }

    /**
     * 获取小偷游戏图片存储路径
     * @return 路径
     */
    public String getThiefImageDir() {
        return thiefGameConfig.getImageDir();
    }

    /**
     * 获取公共图片目录
     * @return 目录路径
     */
    public String getCommonImageDir() {
        return buildImageDir(thiefGameConfig.getFolder().getCommon());
    }

    /**
     * 获取存档建筑图片目录
     * @return 目录路径
     */
    public String getArchiverImageDir(int number) {
        return buildImageDir(thiefGameConfig.getFolder().getArchiverPrefix() + number);
    }

    /**
     * 获取装备图片目录
     * @return 目录路径
     */
    public String getEquipmentImageDir() {
        return buildImageDir(thiefGameConfig.getFolder().getEquipment());
    }

    /**
     * 获取储物柜图片目录
     * @return 目录路径
     */
    public String getLockerImageDir() {
        return buildImageDir(thiefGameConfig.getFolder().getLocker());
    }

    /**
     * 获取小偷人物图片目录
     * @return 目录路径
     */
    public String getThiefHeroImageDir() {
        return buildImageDir(thiefGameConfig.getFolder().getThiefHero());
    }

    /**
     * 获取最终 BOSS 图片目录
     * @return 目录路径
     */
    public String getFinalBossImageDir() {
        return buildImageDir(thiefGameConfig.getFolder().getFinalBoss());
    }

    /**
     * 获取属性强化建筑图片目录
     * @return 目录路径
     */
    public String getEnhanceBuildingImageDir() {
        return buildImageDir(thiefGameConfig.getFolder().getEnhanceBuilding());
    }

    public Point getGoldMineOffset() {
        return new Point(thiefGameConfig.getGoldMineOffsetX(), thiefGameConfig.getGoldMineOffsetY());
    }

    /**
     * 构建图片目录路径
     * @param subDir 子目录
     * @return 完整目录路径
     */
    private String buildImageDir(String subDir) {
        String baseDir = thiefGameConfig.getImageDir();
        if (subDir == null || subDir.isEmpty()) {
            return baseDir;
        }
        return baseDir + "/" + subDir;
    }


}
