package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;
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
    private static final String AUTO_CHALLENGE_GOLD_MONSTER = "3/auto_challenge_gold_monster";
    private static final String FINAL_BOSS_CHALLENGE = "3/final_boss_challenge";
    private static final String VICTORY_BUTTON = "common/victory_button";

    public BossChallengeController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 开启自动挑战金币怪
     * @return 是否成功
     */
    public boolean startAutoChallengeGoldMonster() {
        LogUtil.info("=== 开启自动挑战金币怪 ===");
        return findAndClickImage(AUTO_CHALLENGE_GOLD_MONSTER);
    }

    /**
     * 开启最终 BOSS 挑战
     * @return 是否成功
     */
    public boolean startFinalBossChallenge() {
        LogUtil.info("=== 开启最终 BOSS 挑战 ===");
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
     * 等待游戏胜利按钮出现
     * @param timeout 超时时间（毫秒）
     * @return 是否找到
     */
    public boolean waitForVictory(int timeout) {
        LogUtil.info("=== 等待游戏胜利 ===");
        return waitForImage(VICTORY_BUTTON, timeout);
    }

    /**
     * 每隔 10 秒判断游戏胜利按钮是否存在
     * @param maxWaitTime 最大等待时间（毫秒）
     * @return 是否胜利
     */
    public boolean checkVictoryPeriodically(long maxWaitTime) {
        LogUtil.info("=== 开始周期性检查游戏胜利 ===");

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < maxWaitTime) {
            if (isVictoryButtonExists()) {
                LogUtil.info("游戏胜利按钮已出现！");
                return true;
            }

            LogUtil.info("未检测到游戏胜利，等待 10 秒...");
            automation.delay(10000);
        }

        LogUtil.error("等待游戏胜利超时");
        return false;
    }

    /**
     * 执行 BOSS 挑战流程
     * 1. 到达 15 分钟后，开启最终 BOSS 挑战
     * 2. 等待 1 分钟
     * 3. 每隔 10 秒检查游戏胜利
     * @return 是否成功
     */
    public boolean executeBossChallenge() {
        LogUtil.info("=== 执行 BOSS 挑战流程 ===");

        if (!startFinalBossChallenge()) {
            return false;
        }

        LogUtil.info("等待 1 分钟让 BOSS 战进行...");
        automation.delay(60000);

        return checkVictoryPeriodically(5 * 60 * 1000);
    }
}
