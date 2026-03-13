package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;

import java.awt.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: BossChallengeController </p>
 * <p>描述: BOSS 挑战控制器，管理自动挑战金币怪和最终 BOSS 挑战 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BossChallengeController extends CommonController {
    // boss 挑战建筑编号
    private static final int BOSS_CHALLENGE_NUMBER = 3;

    private static final String AUTO_CHALLENGE_GOLD_MONSTER = "3/auto_challenge_gold_monster"; // 自动挑战金币怪
    private static final String AUTO_CHALLENGE_WOOD_MONSTER = "3/auto_challenge_WOOD_monster"; // 自动挑战木头怪
    private static final String FINAL_BOSS_CHALLENGE = "3/final_boss_challenge";
    private static final String BOSS_CHALLENGE_BUILDING = "common/boss_challenge_building";


    public BossChallengeController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 初始化方法
     * 找到boss 挑战 建筑物坐标并点击
     *
     */
    public boolean initialize() {
        // 识别并返回坐标
        Point bossChallengePoint = findImage(BOSS_CHALLENGE_BUILDING);
        if (bossChallengePoint == null) {
            LogUtil.error("未找到BOSS挑战建筑物！");
            return false;
        } else {
            LogUtil.info("已找到BOSS挑战建筑物坐标：{}", bossChallengePoint);
            automation.delay(CLICK_DELAY);
            automation.oneClick(bossChallengePoint.x, bossChallengePoint.y);
            automation.delay(CLICK_DELAY);
        }
        // 对 已选的boss 挑战建筑进行编号
        selectNumber(BOSS_CHALLENGE_NUMBER);
        LogUtil.info("挑战boss建筑编号为：["+ BOSS_CHALLENGE_NUMBER +"]");
        // 延迟1s
        automation.delay(1000);
        return true;
    }

    /**
     * 开启自动挑战金币怪
     * @return 是否成功
     */
    public boolean startAutoChallengeMonster() {
        LogUtil.info("=== 开启自动挑战金币怪 ===");
        // 切换到挑战建筑
        pressKey(BOSS_CHALLENGE_NUMBER);
        // 延迟1s
        automation.delay(1000);
        // 开启自动挑战金币怪
        if (!findAndClickImage(AUTO_CHALLENGE_GOLD_MONSTER)) {
            LogUtil.error("自动挑战金币怪开启失败！");
            return false;
        }
        automation.delay(1000);
        if (!findAndClickImage(AUTO_CHALLENGE_WOOD_MONSTER)) {
            LogUtil.error("自动挑战木头怪开启失败！");
            return false;
        }
        // 延迟1s
        automation.delay(1000);
        return true;
    }

    /**
     * 开启最终 BOSS 挑战
     * @return 是否成功
     */
    public boolean startFinalBossChallenge() {
        LogUtil.info("=== 开启最终 BOSS 挑战 ===");
        // 切换到挑战建筑
        pressKey(BOSS_CHALLENGE_NUMBER);
        // 延迟1s
        automation.delay(1000);
        return findAndClickImage(FINAL_BOSS_CHALLENGE);
    }

    /**
     * 检查游戏胜利按钮是否存在
     * @return 是否存在
     */
    public boolean isVictoryButtonExists() {
        LogUtil.info("=== 检查游戏胜利按钮 ===");
        return isImageExists(VICTORY_BUTTON);
    }

    /**
     * 每隔 5 秒判断游戏胜利按钮是否存在
     * @param maxWaitTime 最大等待时间（毫秒）
     * @return 是否胜利
     */
    public boolean checkVictoryPeriodically(long maxWaitTime) {
        LogUtil.info("=== 检查游戏胜利 ===");

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < maxWaitTime) {
            if (isVictoryButtonExists()) {
                LogUtil.info("游戏胜利按钮已出现！");
                // 点击胜利按钮
                findAndClickImage(VICTORY_BUTTON);
                // 延迟1s
                automation.delay(1000);
                return true;
            }

            LogUtil.info("未检测到游戏胜利，等待 5 秒...");
            automation.delay(5000);
        }

        LogUtil.error("等待游戏胜利超时");
        return false;
    }
}
