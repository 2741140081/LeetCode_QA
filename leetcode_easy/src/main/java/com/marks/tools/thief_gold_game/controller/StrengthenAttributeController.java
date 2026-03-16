package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: StrengthenAttributeController </p>
 * <p>描述: 属性强化控制器，每隔 1 分钟强化各项属性 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class StrengthenAttributeController extends CommonController {
    private static final int STRENGTHEN_ATTRIBUTE_NUMBER = 4;

    private static final String ATTACK_SPEED_UP = "4/attack_speed_up";
    private static final String RANGE_UP = "4/range_up";
    private static final String MULTI_SHOT_UP = "4/multi_shot_up";
    private static final String STRENGTHEN_ATTRIBUTE_BUILDING = "common/strengthen_attribute_building";

    public StrengthenAttributeController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 初始化
     * 1. 对强化属性建筑物进行编号4
     * 2. 从gameStartTime = startTime
     * @param startTime 游戏开始时间
     * @return 是否成功
     */
    public boolean initialize(long startTime) {
        LogUtil.info("=== 属性强化控制器初始化 ===");
        // 找到属性建筑物, 并返回坐标
        Point attr_point = findImage(STRENGTHEN_ATTRIBUTE_BUILDING);
        if (attr_point == null) {
            LogUtil.error("未找到属性建筑物");
            return false;
        } else {
            // 点击属性建筑物
            LogUtil.info("点击属性建筑物");
            automation.click(attr_point.x, attr_point.y);
        }
        // 对已选的属性建筑物进行编号
        selectNumber(STRENGTHEN_ATTRIBUTE_NUMBER);
        return true;
    }

    /**
     * 开启 10连点击按钮
     * 可以通过快捷键 T 来触发
     * @return 是否成功
     */
    public boolean openTenClicks() {
        LogUtil.info("=== 按下T键, 开启10连点击 ===");
        // 点击键盘 T 键
        pressKey(KeyEvent.VK_T);
        return true;
    }

    /**
     * 强化攻击速度
     * @return 是否成功
     */
    public boolean upgradeAttackSpeed() {
        LogUtil.info("=== 强化攻击速度 ===");
        return findAndClickImage(ATTACK_SPEED_UP);
    }

    /**
     * 强化射程
     * @return 是否成功
     */
    public boolean upgradeRange() {
        LogUtil.info("=== 强化射程 ===");
        return findAndClickImage(RANGE_UP);
    }

    /**
     * 强化多重射击
     * @return 是否成功
     */
    public boolean upgradeMultiShot() {
        LogUtil.info("=== 强化多重射击 ===");
        return findAndClickImage(MULTI_SHOT_UP);
    }

    /**
     * 执行一次完整的属性强化
     * @return 是否成功
     */
    private boolean performUpgrade() {
        LogUtil.info("=== 执行属性强化 ===");
        // 切换到属性强化建筑编号
        pressNumber(STRENGTHEN_ATTRIBUTE_NUMBER);

        boolean success = true;
        for (int i = 0; i < 3; i++) {
            // 强化攻击速度
            if (!upgradeAttackSpeed()) {
                success = false;
            }
            // 强化射程
            if (!upgradeRange()) {
                success = false;
            }
            automation.delay(CLICK_DELAY);
        }

        // 强化多重射击
        if (!upgradeMultiShot()) {
            success = false;
        }
        return success;
    }

    /**
     * 循环执行强化流程，
     */
    public boolean loopExecuteEnhancedProcess() {
        LogUtil.info("=== 开始强化属性 ===");
        if (!performUpgrade()) {
            LogUtil.error("属性强化失败");
            return false;
        }

        return true;
    }

}
