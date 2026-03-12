package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.PrepareRoom;
import com.marks.tools.kkplatform.WindowSwitcherUtils;
import com.marks.utils.LogUtil;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: GameFlowController </p>
 * <p>描述: 游戏流程控制类，协调整个游戏的自动化流程 </p>
 * 游戏流程如下:
 * 1. 切换到准备房间窗口, 点击开始游戏, 等待游戏窗口出现
 * 2. 通过DifficultyController选择难度、模式、挑战
 * 3. 记录 DifficultyController 执行完成后的时间戳, 这个时间就是游戏正式开始时间 long startTime; 用于对比 15 分钟用的
 * 4.
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class GameFlowController {
    private ImageRecognitionAutomation automation;
    private WindowSwitcherUtils windowSwitcher;
    private PrepareRoom prepareRoom;
    private DifficultyController difficultyController;
    private ThiefController thiefController;
    private LockerController lockerController;
    private BossChallengeController bossChallengeController;
    private StrengthenAttributeController strengthenAttributeController;
    private ModifierController modifierController;
    private ArchiverChallengeController archiverChallengeController;

    // 游戏时间控制
    private long gameStartTime;
    private static final int GAME_MAIN_DURATION = 15 * 60 * 1000; // 15 分钟
    private static final int FINAL_BOSS_DURATION = 2 * 60 * 1000; // 2 分钟
    private static final int ARCHIVER_BOSS_DURATION = 3 * 60 * 1000; // 3 分钟

    public GameFlowController(ImageRecognitionAutomation automation) {
        this.automation = automation;
        this.windowSwitcher = WindowSwitcherUtils.getInstance();
        initializeControllers();
    }

    /**
     * 初始化所有控制器
     */
    private void initializeControllers() {
        LogUtil.info("=== 初始化游戏控制器 ===");
        this.prepareRoom = new PrepareRoom(automation);
        this.difficultyController = new DifficultyController(automation);
        this.thiefController = new ThiefController(automation);
        this.lockerController = new LockerController(automation);
        this.bossChallengeController = new BossChallengeController(automation);
        this.strengthenAttributeController = new StrengthenAttributeController(automation);
        this.modifierController = new ModifierController(automation);
        this.archiverChallengeController = new ArchiverChallengeController(automation);
        LogUtil.info("控制器初始化完成");
    }

    /**
     * 完整的游戏流程入口
     * @param difficultyNumber 难度等级 (1-30)
     */
    public void startGame(int difficultyNumber) {
        try {
            LogUtil.info("=== 开始游戏流程 ===");

            // 步骤 1: 准备房间并开始游戏
            if (!prepareRoomAndStart()) {
                LogUtil.error("准备房间失败，终止游戏");
                return;
            }

            // 步骤 2: 选择难度、模式、挑战
            if (!selectDifficulty(difficultyNumber)) {
                LogUtil.error("选择难度失败，终止游戏");
                return;
            }

            // 步骤 3: 记录游戏开始时间
            gameStartTime = System.currentTimeMillis();
            LogUtil.info("游戏正式开始时间：" + gameStartTime);

            // 步骤 4: 游戏主体流程（15 分钟）
            executeMainGame();

            // 步骤 5: 最终 BOSS 挑战
            executeFinalBossChallenge();

            // 步骤 6: 存档 BOSS 挑战
            executeArchiverBossChallenge();

            // 步骤 7: 退出游戏
            exitGame();

            LogUtil.info("=== 游戏流程全部完成 ===");
        } catch (Exception e) {
            LogUtil.error("游戏执行异常：" + e.getMessage(), e);
        }
    }

    /**
     * 步骤 1: 准备房间并开始游戏
     */
    private boolean prepareRoomAndStart() {
        LogUtil.info("=== 步骤 1: 准备房间 ===");
        return prepareRoom.startGame();
    }

    /**
     * 步骤 2: 选择难度
     */
    private boolean selectDifficulty(int difficultyNumber) {
        LogUtil.info("=== 步骤 2: 选择难度 ===");
        return difficultyController.executeDifficultySelection(difficultyNumber);
    }

    /**
     * 步骤 4: 执行游戏主流程（15 分钟）
     */
    private void executeMainGame() {
        LogUtil.info("=== 步骤 4: 游戏主体流程开始 ===");

        // 初始设置：编号、属性强化、购买武器等
        initialGameSetup();

        // 主循环：每 10 秒检查一次时间，执行相应操作
        while (true) {
            long elapsed = System.currentTimeMillis() - gameStartTime;
            long remaining = GAME_MAIN_DURATION - elapsed;

            if (remaining <= 0) {
                LogUtil.info("15 分钟游戏时间已到");
                break;
            }

            LogUtil.info("游戏已进行：" + (elapsed / 1000 / 60) + "分钟，剩余：" + (remaining / 1000 / 60) + "分钟");

            // 执行游戏内操作
            executeGameCycle(remaining);

            // 每隔 10 秒检查一次
            automation.delay(10000);
        }
    }

    /**
     * 游戏初始设置
     */
    private void initialGameSetup() {
        LogUtil.info("=== 游戏初始设置 ===");

        // 1. 对人物和建筑物进行编号
        assignNumbers();

        // 2. 开启自动挑战金币怪
        bossChallengeController.startAutoChallengeGoldMonster();

        // 3. 属性强化（攻速）TODO
//        strengthenAttributeController.strengthenAttackSpeed();

        // 4. 等待购买武器（约 1 分钟）
        waitForBuyWeapon();

        // 5. 小偷丢弃武器，储物柜拾取并修改
        modifyFirstWeapon();
    }

    /**
     * 分配编号（Ctrl + 1~4）
     */
    private void assignNumbers() {
        LogUtil.info("=== 分配编号 ===");
        // 1 - 小偷英雄
        CommonController commonController = new CommonController(automation);
        commonController.selectNumber(1);
        automation.delay(500);

        // 2 - 储物柜
        commonController.selectNumber(2);
        automation.delay(500);

        // 3 - BOSS 挑战建筑
        commonController.selectNumber(3);
        automation.delay(500);

        // 4 - 属性强化建筑
        commonController.selectNumber(4);
        automation.delay(500);

        LogUtil.info("编号分配完成");
    }

    /**
     * 等待购买武器
     */
    private void waitForBuyWeapon() {
        LogUtil.info("等待金币足够购买武器（约 1 分钟）");
        automation.delay(60000);
    }

    /**
     * 修改第一件武器
     */
    private void modifyFirstWeapon() {
        LogUtil.info("=== 修改第一件武器 ===");

        // 小偷丢弃武器 TODO
//        thiefController.dropWeapon();
//
//        // 储物柜拾取
//        lockerController.pickupDroppedItem();
//
//        // 修改器修改物品
//        modifierController.findItemInfo();
//        modifierController.modifyItemTo("I291");
//
//        // 储物柜丢弃修改后的物品
//        lockerController.dropModifiedItem();
//
//        // 小偷拾取
//        thiefController.pickupInRange();

        LogUtil.info("第一件武器修改完成");
    }

    /**
     * 执行游戏循环内的操作
     * @param remainingTime 剩余时间（毫秒）
     */
    private void executeGameCycle(long remainingTime) {
        // 检查是否需要强化属性（每分钟一次） TODO
//        strengthenAttributeController.strengthenAttributesPeriodically(gameStartTime);
//
//        // 检查是否需要购买晕锤技能书（1000 金币后）
//        checkAndBuyStunHammer();
//
//        // 继续攻击金矿获取金币
//        thiefController.attackMine();
    }

    /**
     * 检查并购买晕锤技能书
     */
    private void checkAndBuyStunHammer() {
        // TODO: 实现金币检查和购买逻辑
        // 当金币达到 1000 时，切换到储物柜购买 5 次晕锤技能书
        // 然后修改这 5 件物品
    }

    /**
     * 步骤 5: 最终 BOSS 挑战
     */
    private void executeFinalBossChallenge() {
        LogUtil.info("=== 步骤 5: 最终 BOSS 挑战 ===");

        // 开启最终 BOSS 挑战
        bossChallengeController.startFinalBossChallenge();

        // 等待 1 分钟
        automation.delay(60000);

        // 每 10 秒检查游戏胜利按钮
        boolean victoryDetected = waitForVictory();

        if (victoryDetected) {
            LogUtil.info("检测到游戏胜利按钮，进入存档 BOSS 挑战");
        }
    }

    /**
     * 等待游戏胜利
     * TODO
     * @return 是否检测到胜利
     */
    private boolean waitForVictory() {
        LogUtil.info("等待游戏胜利...");
        int timeout = FINAL_BOSS_DURATION;
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
//            if (bossChallengeController.checkVictoryButton()) {
//                return true;
//            }
            automation.delay(10000);
        }
        return false;
    }

    /**
     * 步骤 6: 存档 BOSS 挑战
     */
    private void executeArchiverBossChallenge() {
        LogUtil.info("=== 步骤 6: 存档 BOSS 挑战 ===");

        // 依次挑战 4 个存档 BOSS
        archiverChallengeController.challengeAllArchivers();

        // 等待 3 分钟
        automation.delay(ARCHIVER_BOSS_DURATION);

        LogUtil.info("存档 BOSS 挑战完成");
    }

    /**
     * 步骤 7: 退出游戏
     */
    private void exitGame() {
        LogUtil.info("=== 退出游戏 ===");
        // TODO: 实现退出游戏逻辑
        // 可能包括：点击退出按钮、关闭窗口等
    }
}
