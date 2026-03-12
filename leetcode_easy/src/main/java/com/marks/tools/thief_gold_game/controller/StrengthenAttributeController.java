package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;
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

    private static final String ATTACK_SPEED_UP = "4/attack_speed_up";
    private static final String RANGE_UP = "4/range_up";
    private static final String MULTI_SHOT_UP = "4/multi_shot_up";
    private static final String ATTACK_1_UP = "4/attack_1_up";
    private static final String ATTACK_2_UP = "4/attack_2_up";
    private static final String ATTACK_3_UP = "4/attack_3_up";
    private static final String ATTACK_4_UP = "4/attack_4_up";

    private long gameStartTime;
    private long lastUpgradeTime;

    public StrengthenAttributeController(ImageRecognitionAutomation automation) {
        super(automation);
        this.gameStartTime = System.currentTimeMillis();
        this.lastUpgradeTime = System.currentTimeMillis();
    }

    /**
     * 设置游戏开始时间
     */
    public void setGameStartTime(long startTime) {
        this.gameStartTime = startTime;
        this.lastUpgradeTime = startTime;
    }

    /**
     * 强化攻击速度（点击 2 下，间隔 1 秒）
     * @return 是否成功
     */
    public boolean upgradeAttackSpeed() {
        LogUtil.info("=== 强化攻击速度 ===");

        if (!findAndClickImage(ATTACK_SPEED_UP)) {
            return false;
        }

        automation.delay(1000);

        if (!findAndClickImage(ATTACK_SPEED_UP)) {
            return false;
        }

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

        if (!findAndClickImage(ATTACK_1_UP)) {
            success = false;
        }
        automation.delay(300);

        if (!findAndClickImage(ATTACK_2_UP)) {
            success = false;
        }
        automation.delay(300);

        if (!findAndClickImage(ATTACK_3_UP)) {
            success = false;
        }
        automation.delay(300);

        if (!findAndClickImage(ATTACK_4_UP)) {
            success = false;
        }

        return success;
    }

    /**
     * 执行一次完整的属性强化
     * @return 是否成功
     */
    public boolean performUpgrade() {
        LogUtil.info("=== 执行属性强化 ===");

        boolean success = true;

        if (!upgradeAttackSpeed()) {
            success = false;
        }
        automation.delay(500);

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

        lastUpgradeTime = System.currentTimeMillis();
        return success;
    }

    /**
     * 检查是否需要强化属性（每隔 1 分钟）
     * @return 是否需要强化
     */
    public boolean shouldUpgrade() {
        long currentTime = System.currentTimeMillis();
        long elapsedSinceLastUpgrade = currentTime - lastUpgradeTime;
        return elapsedSinceLastUpgrade >= 60000;
    }

    /**
     * 检查游戏是否已经运行了 15 分钟
     * @return 是否达到 15 分钟
     */
    public boolean isGameTimeReached15Minutes() {
        long currentTime = System.currentTimeMillis();
        long elapsedGameTime = currentTime - gameStartTime;
        return elapsedGameTime >= 15 * 60 * 1000;
    }

    /**
     * 获取游戏已运行时间（分钟）
     * @return 已运行时间（分钟）
     */
    public long getElapsedGameTimeInMinutes() {
        long currentTime = System.currentTimeMillis();
        long elapsedGameTime = currentTime - gameStartTime;
        return elapsedGameTime / (60 * 1000);
    }

    /**
     * 循环检查并强化属性，直到游戏时间达到 15 分钟
     */
    public void loopUntil15Minutes() {
        LogUtil.info("=== 开始循环强化属性，直到 15 分钟 ===");

        while (!isGameTimeReached15Minutes()) {
            long elapsedMinutes = getElapsedGameTimeInMinutes();
            LogUtil.info("当前游戏时间：" + elapsedMinutes + " 分钟");

            if (shouldUpgrade()) {
                LogUtil.info("执行属性强化");
                performUpgrade();
            }

            automation.delay(10000);
        }

        LogUtil.info("游戏时间已达到 15 分钟，停止强化");
    }

}
