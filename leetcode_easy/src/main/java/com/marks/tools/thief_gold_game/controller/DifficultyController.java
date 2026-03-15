package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.thief_gold_game.entity.Challenge;
import com.marks.utils.LogUtil;

import java.util.PriorityQueue;

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
    private static final String CHALLENGE_BASE = "start/challenge_";
    private static final int[] CHALLENGE_NUMBER = {1, 2, 3, 4, 5, 6};
    private static final String CHALLENGE_1 = "start/challenge_1";
    private static final String CHALLENGE_2 = "start/challenge_2";
    private static final String CHALLENGE_3 = "start/challenge_3";
    private static final String CHALLENGE_4 = "start/challenge_4";
    private static final String CHALLENGE_5 = "start/challenge_5";
    private static final String CHALLENGE_6 = "start/challenge_6";
    private static final String START_GAME_BUTTON = "start/start_game_btn";
    private static final String ELEMENTARY_LEVEL_DIFFICULTY_BUTTON = "start/elementary_level_difficulty_btn"; // 初等难度按钮(1~10)
    private static final String MEDIUM_LEVEL_DIFFICULTY_BUTTON = "start/medium_level_difficulty_btn"; // 中等难度按钮(11~20)
    private static final String HIGH_LEVEL_DIFFICULTY_BUTTON = "start/high_level_difficulty_btn"; // 高级难度按钮(21~30)
    private static final String EXPERT_LEVEL_DIFFICULTY_BUTTON = "start/expert_level_difficulty_btn"; // 专家级难度按钮(31~40)
    // 后续级别可以填写: 大师级, 宗师级, 传奇级; 这些级别应该够用了

    public DifficultyController(ImageRecognitionAutomation automation) {
        super(automation);
    }

    /**
     * 初始化挑战队列，设置优先级和次数
     * @return 优先队列
     */
    public PriorityQueue<Challenge> initializeChallengeQueue() {
        PriorityQueue<Challenge> queue = new PriorityQueue<>();
        // 便利challengeNumber
        for (int i = 0; i < CHALLENGE_NUMBER.length; i++) {
            if (i == 4) {
                // 不挑战 CHALLENGE_5, forever, 减少一半的偷钱太难受了, 等以后存档上来以后在挑战
                continue;
            }
//            queue.offer(new Challenge(i, CHALLENGE_NUMBER[i], CHALLENGE_BASE + CHALLENGE_NUMBER[i], 4));
        }
        return queue;
    }

    /**
     * 选择难度
     * 根据数字选择难度初中高三个等级, 然后在拼接具体难度, eg: "start/difficulty_" + difficultyNumber
     * @return 是否成功
     */
    public boolean selectDifficulty(int difficultyNumber) {
        // 选择难度范围
        if (difficultyNumber < 1 || difficultyNumber > 40) {
            LogUtil.error("难度选择范围：1~30");
            return false;
        }
        if (difficultyNumber <= 10) {
            LogUtil.info("=== 选择难度：初级 ===");
            findAndClickImage(ELEMENTARY_LEVEL_DIFFICULTY_BUTTON);
        } else if (difficultyNumber <= 20) {
            LogUtil.info("=== 选择难度：中级 ===");
            findAndClickImage(MEDIUM_LEVEL_DIFFICULTY_BUTTON);
        } else if (difficultyNumber <= 30) {
            LogUtil.info("=== 选择难度：高级 ===");
            findAndClickImage(HIGH_LEVEL_DIFFICULTY_BUTTON);
        } else {
            LogUtil.error("=== 选择难度：超级 ===");
            findAndClickImage(EXPERT_LEVEL_DIFFICULTY_BUTTON);
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
     * 选择挑战（挑战 1~挑战 6）
     * 不需要返回值, 选择挑战失败不影响整体游戏流程
     */
    public void selectChallenges(PriorityQueue<Challenge> challengeQueue) {
        LogUtil.info("=== 选择挑战：挑战 1-6 ===");
        // 拷贝一份challengeQueue
        PriorityQueue<Challenge> tempQueue = new PriorityQueue<>();
        while (!challengeQueue.isEmpty()) {
            Challenge challenge = challengeQueue.poll();
            LogUtil.info("点击挑战{} (优先级:{}, 剩余次数:{})",
                    challenge.getChallengeNumber(),
                    challenge.getPriority(),
                    challenge.getRemainingCount());

            if (!clickChallenge(challenge)) {
                LogUtil.error("点击挑战{}失败", challenge.getChallengeNumber());
            }

            challenge.setRemainingCount(challenge.getRemainingCount() - 1);
            if (challenge.getRemainingCount() > 0) {
                tempQueue.offer(challenge);
            }
            automation.delay(500);
        }
        challengeQueue.addAll(tempQueue);
    }

    /**
     * 点击挑战
     * @param challenge 挑战对象
     * @return 是否成功
     */
    private boolean clickChallenge(Challenge challenge) {
        String imageName = challenge.getImageName();
        if (imageName.equals(CHALLENGE_5)) {
            // 由于模式5下, 金币获取数量减半, 需要增加部分等待时间
            isChallenge5 = true;
        }
        return findAndClickImage(challenge.getImageName());
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
    public boolean executeDifficultySelection(int difficultyNumber, PriorityQueue<Challenge> challengeQueue) {
        LogUtil.info("=== 开始难度选择流程 ===");

        // 选择难度, 包含选择难度范围和具体难度
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
        selectChallenges(challengeQueue);

        automation.delay(1000);

        return startGame();
    }
}
