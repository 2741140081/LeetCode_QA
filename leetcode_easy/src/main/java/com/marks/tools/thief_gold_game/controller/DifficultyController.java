package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.utils.LogUtil;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: DifficultyController </p>
 * <p>描述: 难度选择控制器，负责选择难度、模式和挑战 </p>
 * 1. 当前难度选择流程架构搭建完成, 后续需要根据实际情况, 补全相关的图片, 以及测试是否可以正常选择难度
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:17
 * @update [01][2026-03-12] [marks][添加选择难度范围和具体难度值, 由原本的固定难度变为由输入参数控制选择难度]
 */
public class DifficultyController extends CommonController {
    private static final String DIFFICULTY = "start/difficulty_";
    private static final String MODE_4 = "start/mode_4";
    private static final String CHALLENGE_1 = "start/challenge_1";
    private static final String CHALLENGE_2 = "start/challenge_2";
    private static final String CHALLENGE_3 = "start/challenge_3";
    private static final String CHALLENGE_4 = "start/challenge_4";
    private static final String START_GAME_BUTTON = "start/start_game_btn";
    private static final String ELEMENTARY_DIFFICULTY_BUTTON = "start/elementary_difficulty_btn"; // 初等难度按钮
    private static final String MEDIUM_DIFFICULTY_BUTTON = "start/medium_difficulty_btn"; // 中等难度按钮
    private static final String HIGH_DIFFICULTY_BUTTON = "start/high_difficulty_btn"; // 高级难度按钮

    public DifficultyController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 选择难度
     * 根据数字选择难度初中高三个等级, 然后在拼接具体难度, eg: "start/difficulty_" + difficultyNumber
     * @return 是否成功
     */
    public boolean selectDifficulty(int difficultyNumber) {
        // 选择难度范围
        if (difficultyNumber < 1 || difficultyNumber > 30) {
            LogUtil.error("难度选择范围：1~30");
            return false;
        }
        if (difficultyNumber <= 10) {
            LogUtil.info("=== 选择难度：初级 ===");
            findAndClickImage(ELEMENTARY_DIFFICULTY_BUTTON);
        } else if (difficultyNumber <= 20) {
            LogUtil.info("=== 选择难度：中级 ===");
            findAndClickImage(MEDIUM_DIFFICULTY_BUTTON);
        } else {
            LogUtil.info("=== 选择难度：高级 ===");
            findAndClickImage(HIGH_DIFFICULTY_BUTTON);
        }
        // 延迟1s
        automation.delay(1000);
        // 拼接具体难度
        String difficulty = DIFFICULTY + difficultyNumber;
        LogUtil.info("=== 选择难度：难度" + difficulty + "===");
        return findAndClickImage(difficulty);
    }

    /**
     * 选择模式（模式四）
     * @return 是否成功
     */
    public boolean selectMode() {
        LogUtil.info("=== 选择模式：模式四 ===");
        return findAndClickImage(MODE_4);
    }

    /**
     * 选择挑战（挑战 1~挑战 4）
     * 不需要返回值, 选择挑战失败不影响整体游戏流程
     */
    public void selectChallenges() {
        LogUtil.info("=== 选择挑战：挑战 1-4 ===");

        if (!findAndClickImage(CHALLENGE_1)) {
            LogUtil.error("选择挑战 1 失败");
        }
        automation.delay(500);

        if (!findAndClickImage(CHALLENGE_2)) {
            LogUtil.error("选择挑战 2 失败");
        }
        automation.delay(500);

        if (!findAndClickImage(CHALLENGE_3)) {
            LogUtil.error("选择挑战 3 失败");
        }
        automation.delay(500);

        if (!findAndClickImage(CHALLENGE_4)) {
            LogUtil.error("选择挑战 4 失败");
        }
    }

    /**
     * 点击开始游戏
     * @return 是否成功
     */
    public boolean startGame() {
        LogUtil.info("=== 点击开始游戏 ===");
        return findAndClickImage(START_GAME_BUTTON);
    }

    /**
     * 完整的难度选择流程
     * @return 是否成功
     */
    public boolean executeDifficultySelection(int difficultyNumber) {
        LogUtil.info("=== 开始难度选择流程 ===");

        // 选择难度
        if (!selectDifficulty(difficultyNumber)) {
            return false;
        }
        automation.delay(1000);

        // 选择模式
        if (!selectMode()) {
            return false;
        }
        automation.delay(1000);

        // 选择挑战
        selectChallenges();

        automation.delay(1000);

        return startGame();
    }
}
