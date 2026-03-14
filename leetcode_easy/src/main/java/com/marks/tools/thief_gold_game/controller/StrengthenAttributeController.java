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
    private static final String ATTACK_1_UP = "4/attack_1_up";
    private static final String ATTACK_2_UP = "4/attack_2_up";
    private static final String ATTACK_3_UP = "4/attack_3_up";
    private static final String ATTACK_4_UP = "4/attack_4_up";
    private static final String STRENGTHEN_ATTRIBUTE_BUILDING = "common/strengthen_attribute_building";

    private long gameStartTime;

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
            automation.delay(CLICK_DELAY);
            automation.click(attr_point.x, attr_point.y);
            automation.delay(CLICK_DELAY);
        }
        // 对已选的属性建筑物进行编号
        selectNumber(STRENGTHEN_ATTRIBUTE_NUMBER);
        // 延迟1s
        automation.delay(CLICK_DELAY);
        // 设置游戏开始时间
        this.gameStartTime = startTime;
        return true;
    }

    /**
     * 开启 10连点击按钮
     * 可以通过快捷键 T 来触发
     * @return 是否成功
     */
    public boolean openTenClicks() {
        LogUtil.info("=== 开启 10连点击按钮 ===");
        // 点击键盘 T 键
        pressKey(KeyEvent.VK_T);
        // 延迟
        automation.delay(CLICK_DELAY);
        return true;
    }

    /**
     * 强化攻击速度
     * @return 是否成功
     */
    public boolean upgradeAttackSpeed() {
        LogUtil.info("=== 强化攻击速度 ===");
        if (!findAndClickImage(ATTACK_SPEED_UP)) {
            return false;
        }
        automation.delay(1000);
        return true;
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
     * 强化攻击力（1-4）
     * @return 是否成功
     */
    public boolean upgradeAttack() {
        LogUtil.info("=== 强化攻击力 ===");

        boolean success = true;

        if (!findAndClickImage(ATTACK_4_UP)) {
            success = false;
        }
        automation.delay(500);

        if (!findAndClickImage(ATTACK_3_UP)) {
            success = false;
        }
        automation.delay(500);

        return success;
    }

    /**
     * 执行一次完整的属性强化
     * @return 是否成功
     */
    public boolean performUpgrade() {
        LogUtil.info("=== 执行属性强化 ===");
        // 切换到属性强化建筑编号
        pressNumber(STRENGTHEN_ATTRIBUTE_NUMBER);
        // 延迟1s
        automation.delay(500);

        boolean success = true;

        if (!upgradeAttackSpeed()) {
            success = false;
        }

        if (!upgradeRange()) {
            success = false;
        }
        automation.delay(500);

        if (!upgradeMultiShot()) {
            success = false;
        }
        automation.delay(500);

        if (!upgradeAttack()) {
            success = false;
        }

        return success;
    }


    /**
     * 检查游戏是否已经运行了 13 分钟
     * 1. 由于内部执行的是一个中断方法, 不会实时检测时间, 需要提前一个 3 分钟判断
     * @return 是否达到 13 分钟
     */
    public boolean isGameTimeReached15Minutes() {
        long currentTime = System.currentTimeMillis();
        long elapsedGameTime = currentTime - gameStartTime;
        return elapsedGameTime >= 13 * 60 * 1000;
    }

    /**
     * 循环执行强化流程，
     */
    public boolean loopExecuteEnhancedProcess() throws InterruptedException {
        LogUtil.info("=== 开始循环强化属性，直到 15 分钟 ===");
        while (!isGameTimeReached15Minutes()) {
            // 执行强化操作
            if (!performUpgrade()) {
                LogUtil.error("属性强化失败");
                return false;
            }
            // 每2分钟执行一次强化操作
            Thread.sleep(120000);
        }

        LogUtil.info("游戏时间已达到 15 分钟，停止强化");
        return true;
    }

}
