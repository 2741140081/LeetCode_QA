package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.config.ModifierConfig;
import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import com.marks.kkPlatformGameAuto.service.ImageRecognitionService;
import com.marks.kkPlatformGameAuto.service.ModifierService;
import com.marks.kkPlatformGameAuto.service.WindowSwitcherService;
import com.marks.kkPlatformGameAuto.util.ImagePathUtils;
import com.marks.kkPlatformGameAuto.util.InputController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ModifiersOperation </p>
 * <p>描述: 修改器相关操作
 * 1. 修改人物经验值
 * 2. 修改物品栏物品
 * </p>
 * 功能基本完成
 * @author marks
 * @version v1.0
 * @date 2026/3/5 11:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
public class ModifierServiceImpl implements ModifierService {
    @Autowired
    private ImageRecognitionService imageRecognitionService;

    @Autowired
    private GameAutoProperties gameAutoProperties;

    @Autowired
    private WindowSwitcherService windowSwitcherService;

    @Autowired
    private ModifierConfig modifierConfig;

    @Autowired
    private ImagePathUtils imagePathUtils;

    @Autowired
    private InputController input;

    @Override
    public boolean modifyExperience(int experienceValue) {
        log.info("=== 修改器：开始修改经验值，值：{} ===", experienceValue);
        // 获取图片识别间隔
        int delayTime = gameAutoProperties.getInterval();
        // 获取目录
        String imageDir = modifierConfig.getImageDir();
        // 获取查找游戏按钮超时时间
        int timeout = gameAutoProperties.getDefaultTimeout();
        // 判断值是否 > 0
        if (experienceValue <= 0) {
            log.warn("经验值值不能小于0");
            return false;
        }

        // 1. 切换到修改器窗口并点击查找游戏按钮
        if (!switchToModifierAndFindGame()) {
            return false;
        }
        // 2. 经验输入框图片名称
        String experienceInputBoxLabelImage = modifierConfig.getExperienceInputBoxLabelImage();
        // 获取图片路径
        String experienceInputBoxLabelImagePath = imagePathUtils.buildImagePathWithExtension(imageDir, experienceInputBoxLabelImage);
        Point experienceInputBoxLabelCenter = imageRecognitionService.findImageCenter(experienceInputBoxLabelImagePath, timeout, delayTime);
        if (experienceInputBoxLabelCenter == null) {
            log.warn("未找到经验输入框标签");
            return false;
        }

        // 4. 输入值并点击修改按钮
        if (!inputValueAndModify(experienceInputBoxLabelCenter, String.valueOf(experienceValue), imageDir, timeout, delayTime)) {
            return false;
        }
        log.info("=== 修改器：修改经验值完成 ===");
        return true;
    }

    @Override
    public boolean modifyItems(List<String> itemNames) {
        log.info("=== 修改器：开始修改物品，数量：{} ===", itemNames.size());
        // 获取图片识别间隔
        int delayTime = gameAutoProperties.getInterval();
        // 获取目录
        String imageDir = modifierConfig.getImageDir();
        // 获取查找游戏按钮超时时间
        int timeout = gameAutoProperties.getDefaultTimeout();

        // 判断列表是否为空
        if (itemNames.isEmpty()) {
            log.warn("列表为空，请检查输入参数");
            return false;
        }

        // 1. 切换到修改器窗口并点击查找游戏按钮
        if (!switchToModifierAndFindGame()) {
            return false;
        }

        // 3. 找到物品信息标签
        String itemInfoLabelImage = modifierConfig.getItemInfoLabelImage();
        String itemInfoLabelImagePath = imagePathUtils.buildImagePathWithExtension(imageDir, itemInfoLabelImage);
        // 返回中心点坐标
        Point itemInfoLabelCenter = imageRecognitionService.findImageCenter(itemInfoLabelImagePath, timeout, delayTime);
        // 判断是否找到物品信息标签
        if (itemInfoLabelCenter == null) {
            log.warn("未找到物品信息标签");
            return false;
        }
        // 获取物品信息标签的偏移量
        int itemInfoLabelOffsetX = modifierConfig.getItemOffsetX();
        int itemInfoLabelOffsetY = modifierConfig.getItemOffsetY();
        // itemNames[0]物品信息标签的坐标
        Point itemInfoLabelPoint = new Point(itemInfoLabelCenter.x + itemInfoLabelOffsetX, itemInfoLabelCenter.y + itemInfoLabelOffsetY);

        // 获取物品信息高度
        int itemInfoLabelHeight = modifierConfig.getItemLabelHeight();
        for (int i = 0; i < itemNames.size(); i++) {
            String itemName = itemNames.get(i);
            log.info("正在修改物品：{}", itemName);
            // 计算第i件物品坐标
            Point itemPoint = new Point(itemInfoLabelPoint.x, itemInfoLabelPoint.y + i * itemInfoLabelHeight);
            // 点击坐标
            input.moveAndClick(itemPoint.x, itemPoint.y);

            // 4. 找到目标值标签中心点坐标
            String targetValueLabelImage = modifierConfig.getTargetValueLabelImage();
            String targetValueLabelImagePath = imagePathUtils.buildImagePathWithExtension(imageDir, targetValueLabelImage);
            Point targetValueLabelCenter = imageRecognitionService.findImageCenter(targetValueLabelImagePath, timeout, delayTime);
            // 判断是否找到目标值标签
            if (targetValueLabelCenter == null) {
                log.warn("未找到目标值标签");
                return false;
            }
            // 获取目标值标签的偏移量
            int targetValueLabelOffsetX = modifierConfig.getInputOffsetX();
            int targetValueLabelOffsetY = modifierConfig.getInputOffsetY();
            Point targetValueLabelPoint = new Point(targetValueLabelCenter.x + targetValueLabelOffsetX, targetValueLabelCenter.y + targetValueLabelOffsetY);
            // 4. 输入值并点击修改按钮
            if (!inputValueAndModify(targetValueLabelPoint, itemName, imageDir, timeout, delayTime)) {
                return false;
            }
        }
        log.info("=== 修改器：修改物品完成 ===");
        return true;
    }


    /**
     * 切换到修改器窗口并点击查找游戏按钮（公共方法）
     * @return true 如果操作成功，否则 false
     */
    private boolean switchToModifierAndFindGame() {
        log.debug("切换到修改器窗口并点击查找游戏按钮");

        // 1. 切换到修改器窗口
        if (!windowSwitcherService.switchToModifier()) {
            log.warn("切换到修改器窗口失败");
            return false;
        }

        // 2. 查找游戏按钮名称
        String findGameButtonImage = modifierConfig.getFindGameButtonImage();
        String imageDir = modifierConfig.getImageDir();
        String imagePath = imagePathUtils.buildImagePathWithExtension(imageDir, findGameButtonImage);
        int timeout = gameAutoProperties.getDefaultTimeout();

        // 点击查找游戏按钮
        if (!imageRecognitionService.findAndClickImage(imagePath, timeout)) {
            log.warn("点击查找游戏按钮失败");
            return false;
        }

        log.debug("成功切换到修改器窗口并点击查找游戏按钮");
        return true;
    }

    /**
     * 输入值并点击修改按钮（公共方法）
     * @param inputPoint 输入框坐标
     * @param value 要输入的值
     * @param imageDir 图片目录
     * @param timeout 超时时间
     * @param delayTime 延迟时间
     * @return true 如果操作成功，否则 false
     */
    private boolean inputValueAndModify(Point inputPoint, String value, String imageDir, int timeout, int delayTime) {
        // 1. 点击输入框
        input.moveAndClick(inputPoint.x, inputPoint.y);

        // 2. Ctrl + A 全选
        input.pressCombo(KeyEvent.VK_CONTROL, KeyEvent.VK_A);

        // 3. 输入新的值
        input.type(value);

        // 4. 找到修改按钮
        String modifyButtonImage = modifierConfig.getModifyButtonImage();
        String modifyButtonImagePath = imagePathUtils.buildImagePathWithExtension(imageDir, modifyButtonImage);

        if (!imageRecognitionService.findAndClickImage(modifyButtonImagePath, timeout)) {
            log.warn("未找到修改按钮");
            return false;
        }

        log.debug("修改成功");
        return true;
    }
}
