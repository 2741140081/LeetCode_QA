package com.marks.tools.thief_gold_game.controller;


import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ModifierController </p>
 * <p>描述: 修改器操作类，负责查找和修改物品信息 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ModifierController extends CommonController {
    private static final String FIND_GAME_BUTTON = "modifier/find_game_btn";
    private static final String ITEM_INFO_LABEL = "modifier/item_info_label";
    private static final String MODIFY_BUTTON = "modifier/modify_btn";

    private static final int ITEM_CELL_WIDTH = 100;
    private static final int ITEM_CELL_HEIGHT = 30;
    private static final int FIRST_ITEM_X_OFFSET = 50;
    private static final int FIRST_ITEM_Y_OFFSET = 50;

    public ModifierController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 找到'物品信息'信息栏，返回坐标
     * @return 物品信息栏坐标，未找到返回 null
     */
    public Point findItemInfoLabel() {
        LogUtil.info("=== 查找物品信息栏 ===");
        return findImage(ITEM_INFO_LABEL);
    }

    /**
     * 计算并定位物品坐标
     * @param basePoint 基础坐标点（物品信息栏）
     * @param itemIndex 物品索引（0-4）
     * @return 物品坐标
     */
    public Point calculateItemPosition(Point basePoint, int itemIndex) {
        if (basePoint == null) {
            LogUtil.error("基础坐标点为空");
            return null;
        }

        int x = basePoint.x + FIRST_ITEM_X_OFFSET;
        int y = basePoint.y + FIRST_ITEM_Y_OFFSET + (itemIndex * ITEM_CELL_HEIGHT);

        LogUtil.info("计算物品坐标，索引：" + itemIndex + ", 坐标：(" + x + ", " + y + ")");
        return new Point(x, y);
    }

    /**
     * 修改物品值
     * @param itemName 物品名称（如 I291）
     * @return 是否成功
     */
    public boolean modifyItemValue(String itemName) {
        LogUtil.info("=== 修改物品值：" + itemName + " ===");

        Point itemInfoPoint = findItemInfoLabel();
        if (itemInfoPoint == null) {
            LogUtil.error("未找到物品信息栏");
            return false;
        }

        moveTo(itemInfoPoint.x, itemInfoPoint.y);
        automation.delay(300);

        pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
        automation.delay(200);

        inputText(itemName);
        automation.delay(300);

        Point modifyButtonPoint = findImage(MODIFY_BUTTON);
        if (modifyButtonPoint == null) {
            LogUtil.error("未找到修改按钮");
            return false;
        }

        automation.click(modifyButtonPoint.x, modifyButtonPoint.y);
        automation.delay(500);

        LogUtil.info("修改物品值完成：" + itemName);
        return true;
    }

    /**
     * 批量修改 5 个物品
     * @param itemNames 物品名称数组（如 ["I290", "I293", ...]）
     * @return 是否全部成功
     */
    public boolean modifyMultipleItems(String[] itemNames) {
        LogUtil.info("=== 批量修改 5 个物品 ===");

        Point basePoint = findItemInfoLabel();
        if (basePoint == null) {
            return false;
        }

        boolean allSuccess = true;

        for (int i = 0; i < itemNames.length && i < 5; i++) {
            Point itemPoint = calculateItemPosition(basePoint, i);
            if (itemPoint == null) {
                allSuccess = false;
                continue;
            }

            moveTo(itemPoint.x, itemPoint.y);
            automation.delay(300);

            pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
            automation.delay(200);

            inputText(itemNames[i]);
            automation.delay(300);

            Point modifyButtonPoint = findImage(MODIFY_BUTTON);
            if (modifyButtonPoint != null) {
                automation.click(modifyButtonPoint.x, modifyButtonPoint.y);
                automation.delay(500);
                LogUtil.info("第 " + (i + 1) + " 个物品修改完成：" + itemNames[i]);
            } else {
                allSuccess = false;
                LogUtil.error("未找到修改按钮，第 " + (i + 1) + " 个物品修改失败");
            }
        }

        return allSuccess;
    }

    /**
     * 执行完整的修改器操作流程
     * 1. 切换到修改器窗口
     * 2. 点击'查找游戏' 按钮
     * 3. 找到'物品信息' 栏
     * 4. 修改物品值
     * 5. 返回游戏窗口
     * @param itemNames 要修改的物品名称数组
     * @return 是否成功
     */
    public boolean executeModifierOperation(String[] itemNames) {
        LogUtil.info("=== 执行修改器操作流程 ===");

        if (!switchToModifierWindow()) {
            return false;
        }

        automation.delay(500);

        if (!findAndClickImage(FIND_GAME_BUTTON)) {
            LogUtil.error("点击查找游戏按钮失败");
            return false;
        }

        automation.delay(1000);

        if (!modifyMultipleItems(itemNames)) {
            return false;
        }

        switchToGameWindow();
        automation.delay(500);

        LogUtil.info("修改器操作流程完成");
        return true;
    }
}
