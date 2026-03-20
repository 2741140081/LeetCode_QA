package com.marks.kkPlatformGameAuto.service;


import java.awt.Point;
import java.util.List;
/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：HeroOperationService </p>
 * <p>描述：英雄操作服务 - 小偷游戏角色行为抽象 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19 15:00
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface HeroOperationService {
    /**
     * 释放非指向性技能行为 - 通过图片识别技能图标
     * 流程：识别技能图片 -> 点击技能图标 -> 释放技能
     * @param skillImagePath 技能图片完整路径
     * @return true 如果释放成功，否则 false
     */
    boolean useNonTargetedSkillByImage(String skillImagePath);

    /**
     * 释放非指向性技能行为 - 通过图片识别技能图标（支持多次释放）
     * 流程：识别技能图片 -> 点击技能图标 -> 释放技能 -> 等待 CD -> 重复执行
     * @param skillImagePath 技能图片完整路径
     * @param executeCount 执行次数（点击次数）
     * @param intervalMs 执行间隔（技能 CD 时间，毫秒）
     * @return true 如果全部执行成功，否则 false
     */
    boolean useNonTargetedSkillByImage(String skillImagePath, int executeCount, int intervalMs);


    /**
     * 释放指向性技能行为 - 通过图片识别技能图标
     * 流程：识别技能图片 -> 点击技能图标 -> 移动到目标点 -> 释放技能
     * @param skillImagePath 技能图片完整路径
     * @param targetPoint 释放技能的目标地点
     * @return true 如果释放成功，否则 false
     */
    boolean useTargetedSkillByImage(String skillImagePath, Point targetPoint);

    /**
     * 释放指向性技能行为 - 通过键盘快捷键
     * 流程：按下快捷键 -> 移动到目标点 -> 释放技能
     * @param keyCode 技能快捷键的键值
     * @param targetPoint 释放技能的目标地点
     * @return true 如果释放成功，否则 false
     */
    boolean useTargetedSkillByKey(int keyCode, Point targetPoint);

    /**
     * 释放非指向性技能行为 - 通过键盘快捷键
     * 流程：按下快捷键 -> 移释放技能
     * @param keyCode 技能快捷键的键值
     * @return true 如果释放成功，否则 false
     */
    boolean useNonTargetedSkillByKey(int keyCode);

    /**
     * 移交一件物品到目标点
     * @param itemImagePath 物品栏物品的图片完整路径
     * @param targetPoint 目标地点坐标
     * @return true 如果移交成功，否则 false
     */
    boolean transferItems(List<String> itemImagePath, Point targetPoint);

    /**
     * 移交所有相同的物品到目标点
     * 流程：重复调用移交一件物品，直到物品栏没有该物品(或者达到最大次数6次, 物品栏是 3 * 2的矩阵, 只有6个格子)
     * @param itemImagePath 物品栏物品的图片完整路径
     * @param targetPoint 目标地点坐标
     * @return 实际移交的物品数量
     */
    int transferAllSameItems(List<String> itemImagePath, Point targetPoint);

    /**
     * 移交物品栏所有物品到目标点
     * 流程：通过物品栏 logo 定位所有物品位置，逐个移交
     * @param targetPoint 目标地点坐标
     * @param itemLogoImagePath 物品栏上方 logo 图片完整路径（用于定位所有物品）
     * @return 移交的物品数量
     */
    int transferAllItems(Point targetPoint, String itemLogoImagePath);
}
