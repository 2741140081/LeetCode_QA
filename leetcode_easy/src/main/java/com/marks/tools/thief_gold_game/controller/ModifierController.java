package com.marks.tools.thief_gold_game.controller;


import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.WindowSwitcherUtils;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
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
    private static final String TARGET_VALUE_LABEL = "modifier/target_value_label";

    // 物品栏偏移量配置
    private static final int ITEM_X_OFFSET = 50;      // X 坐标偏移量 (px)
    private static final int ITEM_Y_OFFSET = 12;      // Y 坐标偏移量 (px)
    private static final int ITEM_HEIGHT = 14;        // 物品栏高度 (px)


    public ModifierController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 修改物品完整流程
     * 1. 切换到修改器窗口
     * 2. 点击查找游戏按钮
     * 3. 找到物品信息标签，计算第一件物品坐标
     * 4. 循环修改每个物品
     * 5. 切换回游戏窗口
     * @param itemNames 要修改的物品名称列表
     * @return 是否成功
     */
    public boolean modifyItems(List<String> itemNames) {
        LogUtil.info("=== 修改器：开始修改物品，数量：{"+ itemNames.size() +"} ===");

        if (itemNames.isEmpty()) {
            LogUtil.error("物品名称列表为空");
            return false;
        }

        try {
            // 1. 切换到修改器窗口
            if (!switchToModifierWindow()) {
                LogUtil.error("切换到修改器窗口失败");
                return false;
            }
            automation.delay(CLICK_DELAY);

            // 2. 点击查找游戏按钮，让修改器自动获取储物柜物品栏信息
            if (!findAndClickImage(FIND_GAME_BUTTON)) {
                LogUtil.error("点击查找游戏按钮失败");
                return false;
            }
            automation.delay(CLICK_DELAY);

            // 3. 找到物品信息标签，获取基准坐标
            Point itemInfoPoint = findImage(ITEM_INFO_LABEL);
            if (itemInfoPoint == null) {
                LogUtil.error("未找到物品信息标签");
                return false;
            }
            LogUtil.info("找到物品信息标签，坐标：({" + itemInfoPoint.x + "}, {" + itemInfoPoint.y + "})");

            // 4. 计算第一件物品的坐标点
            // 根据物品信息坐标，通过计算得到第一件物品的坐标点
            Point firstItemPoint = calculateFirstItemPoint(itemInfoPoint);
            LogUtil.info("计算得到第一件物品坐标：({" + firstItemPoint.x + "}, {" + firstItemPoint.x + "})");

            // 5. 循环修改每个物品
            for (int i = 0; i < itemNames.size(); i++) {
                String itemName = itemNames.get(i);
                LogUtil.info("正在修改第{" + (i + 1) + "}件物品：{ "+ itemName +"}");

                // 计算第 i 件物品的坐标点
                Point itemPoint = calculateItemPoint(firstItemPoint, i);
                LogUtil.info("计算得到第{" + (i + 1) + "}件物品坐标：({" + itemPoint.x + "}, {" + itemPoint.x + "})");

                // 双击该坐标点
                automation.click(itemPoint.x, itemPoint.y);
                automation.delay(CLICK_DELAY);

                // 找到目标值标签
                Point targetValuePoint = findImage(TARGET_VALUE_LABEL);
                if (targetValuePoint == null) {
                    LogUtil.error("未找到目标值标签");
                    return false;
                }
                targetValuePoint.y += 12;
                // 点击
                automation.click(targetValuePoint.x, targetValuePoint.y);

                // Ctrl + A 全选
                pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
                automation.delay(CLICK_DELAY);

                // 输入新的物品名称
                inputText(itemName);
                automation.delay(CLICK_DELAY);

                // 判断 itemName 是否等于 ys04, 如果等于 ys04, 需要修改物品数量
                if (itemName.equals("ys04")) {
                    targetValuePoint.y += 20; // 下移20px
                    // 点击
                    automation.click(targetValuePoint.x, targetValuePoint.y);
                    automation.delay(CLICK_DELAY);
                    // Ctrl + A 全选
                    pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
                    automation.delay(CLICK_DELAY);
                    // 输入物品数量
                    inputText("1");
                    automation.delay(CLICK_DELAY);
                }

                // 点击修改按钮
                if (!findAndClickImage(MODIFY_BUTTON)) {
                    LogUtil.error("点击修改按钮失败");
                    return false;
                }
                // 延迟1s
                automation.delay(CLICK_DELAY);
            }

            LogUtil.info("所有物品修改完成，共{" + itemNames.size() + "}件");

            return true;
        } catch (Exception e) {
            LogUtil.error("修改物品异常: "+ e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            LogUtil.info("=== 修改器：修改物品结束 ===");
            // 6. 切换回游戏主体窗口
            switchToGameWindow();
            automation.delay(CLICK_DELAY);
        }
    }

    /**
     * 计算第一件物品的坐标点
     * 基于物品信息标签的坐标，加上固定的偏移量
     * @param itemInfoPoint 物品信息标签坐标
     * @return 第一件物品的坐标点
     */
    private Point calculateFirstItemPoint(Point itemInfoPoint) {
        int x = itemInfoPoint.x + ITEM_X_OFFSET;
        int y = itemInfoPoint.y + ITEM_Y_OFFSET;
        return new Point(x, y);
    }

    /**
     * 计算第 i 件物品的坐标点
     * 物品的 y 值相同，x 坐标按物品栏高度递增
     * @param firstItemPoint 第一件物品的坐标点
     * @param index 物品索引 (从 0 开始)
     * @return 第 i 件物品的坐标点
     */
    private Point calculateItemPoint(Point firstItemPoint, int index) {
        int x = firstItemPoint.x;
        int y = firstItemPoint.y + (index * ITEM_HEIGHT);
        return new Point(x, y);
    }


}
