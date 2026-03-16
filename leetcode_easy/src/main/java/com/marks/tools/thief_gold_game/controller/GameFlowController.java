package com.marks.tools.thief_gold_game.controller;

import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.kkplatform.PrepareRoom;
import com.marks.tools.kkplatform.WindowSwitcherUtils;
import com.marks.tools.thief_gold_game.entity.Challenge;
import com.marks.utils.LogUtil;

import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import static com.marks.tools.kkplatform.common.KingOfBeastsConstants.GAME_TITLE;
import static com.marks.tools.thief_gold_game.controller.CommonController.*;

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
    private PrepareRoom prepareRoom;
    private WindowSwitcherUtils windowSwitcher;
    private DifficultyController difficultyController;
    private ThiefController thiefController;
    private LockerController lockerController;
    private BossChallengeController bossChallengeController;
    private StrengthenAttributeController strengthenAttributeController;
    private ModifierController modifierController;
    private ArchiverChallengeController archiverChallengeController;

    // 游戏时间控制
    private long gameStartTime;
    private static final int ARCHIVER_BOSS_DURATION = 100 * 1000; // 进阶难度 2 分 30 秒, 由于每次挑战间隔10s, 所以整体挑战减少60s
//    private static final int ARCHIVER_BOSS_DURATION = 20 * 1000; // 普通难度(<31) 20 秒
    // 装备吞噬次数
    private static final int THIEF_EAT_TIMES = 8; // 难度31 需要7次, 难度21需要 5次

    private static final String ARCHIVER_RESULT_DIR = "D:/images/automation/thief_gold/archiver_result/";
    // 优先队列
    private PriorityQueue<Challenge> challengeQueue;

    public GameFlowController(ImageRecognitionAutomation automation) {
        this.automation = automation;
        initializeControllers();
    }

    /**
     * 初始化所有控制器
     */
    private void initializeControllers() {
        LogUtil.info("=== 初始化游戏控制器 ===");
        this.prepareRoom = new PrepareRoom(automation);
        this.windowSwitcher = WindowSwitcherUtils.getInstance(); // 获取窗口单例
        this.difficultyController = new DifficultyController(automation);
        this.thiefController = new ThiefController(automation);
        this.modifierController = new ModifierController(automation);
        this.bossChallengeController = new BossChallengeController(automation);
        this.strengthenAttributeController = new StrengthenAttributeController(automation);
        this.archiverChallengeController = new ArchiverChallengeController(automation);
        this.lockerController = new LockerController(automation, modifierController);
        LogUtil.info("控制器初始化完成");
        // 获取初始化的优先队列
        this.challengeQueue = difficultyController.initializeChallengeQueue();
    }



    /**
     * 完整的游戏流程入口
     * @param difficultyNumber 难度等级 (1-30)
     */
    public boolean startGame(int difficultyNumber) {
        try {
            LogUtil.info("=== 开始游戏流程 ===");

            // 步骤 1: 准备房间并开始游戏, [done]
            if (!prepareRoomAndStart()) {
                LogUtil.error("准备房间失败，终止游戏");
                return false;
            }
            // 超时时间为 10 秒
            if (!switchToGameWindow(10000)) {
                LogUtil.error("切换游戏窗口失败，执行错误处理");
                return false;
            }
            // 步骤 2: 选择难度、模式、挑战, [done]
            if (!selectDifficulty(difficultyNumber)) {
                LogUtil.error("选择难度失败，终止游戏");
                return false;
            }

            // 步骤 3: 记录游戏开始时间, [done]
            gameStartTime = System.currentTimeMillis();
            LogUtil.info("游戏正式开始时间：" + gameStartTime);

            // 步骤 4: 游戏主体流程（15 分钟）
            if (!executeMainGame()) {
                return false;
            }

            // 步骤 5: 最终 BOSS 挑战
            if (!executeFinalBossChallenge()) {
                return false;
            }

            // 步骤 6: 存档 BOSS 挑战
            executeArchiverBossChallenge();

            // 步骤 7:  截取退出前的游戏画面, 文件名称使用难度 + 时间(_年月日_时分秒)
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "difficulty_" + difficultyNumber + "_" + timestamp;
            // D:\images\automation\thief_gold\archiver_result
            automation.captureScreen(ARCHIVER_RESULT_DIR + fileName + ".png");
            // 步骤 8: 退出游戏
            exitGame();

            LogUtil.info("=== 游戏流程全部完成 ===");
        } catch (Exception e) {
            LogUtil.error("游戏执行异常：" + e.getMessage(), e);
        }
        return true;
    }

    /**
     * 步骤 1: 准备房间并开始游戏
     */
    private boolean prepareRoomAndStart() {
        LogUtil.info("=== 步骤 1: 准备房间 ===");
        // 切换到准备窗口
        if (!prepareRoom.switchToPrepareRoom()) {
            return false;
        }
        // 延迟1s
        automation.delay(1000);
        // 点击准备房间的"开始游戏"按钮
        return prepareRoom.startGame();
    }

    /**
     * 切换到游戏窗口
     * @param timeout 超时时间
     * @return 是否成功切换
     */
    private boolean switchToGameWindow(int timeout) {
        LogUtil.info("=== 切换到游戏窗口，超时时间：{}ms ===", timeout);
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            if (windowSwitcher.switchToWindow(GAME_TITLE)) {
                LogUtil.info("成功切换到游戏窗口");
                return true;
            }
            LogUtil.info("未找到游戏窗口，等待 1s 后继续查找");
            automation.delay(1000);
        }

        LogUtil.error("切换到游戏窗口超时，耗时：{}ms", System.currentTimeMillis() - startTime);
        return false;
    }

    /**
     * 步骤 2: 选择难度
     */
    private boolean selectDifficulty(int difficultyNumber) {
        LogUtil.info("=== 步骤 2: 选择难度 ===");
        return difficultyController.executeDifficultySelection(difficultyNumber, challengeQueue);
    }

    /**
     * 步骤 4: 执行游戏主流程
     */
    private boolean executeMainGame() {
        LogUtil.info("=== 步骤 4: 游戏主体流程开始 ===");

        // 初始设置：编号、属性强化、购买武器等
        initialGameSetup();

        // 开始执行小偷第一次修改装备流程
        if (!thiefController.executeThiefProcess()) {
            LogUtil.error("小偷流程执行失败，终止游戏");
            return false;
        }

        automation.delay(CLICK_DELAY);
        // 储物柜第二次修改物品
        List<String> itemNames = Arrays.asList(SECOND_ITEM_NAMES);
        if (!lockerController.executeModificationProcess(itemNames)) {
            LogUtil.error("储物柜第二次修改失败，终止游戏");
            return false;
        }
        LogUtil.info("执行第二次修改物品成功, 验证物品栏是否存在相应物品");
        // 验证小偷物品栏中物品是否存在
        if (!thiefController.verifyItemInSlot()) {
            LogUtil.error("物品验证失败");
            return false;
        }
        // 开启十连点击操作
        if (!strengthenAttributeController.openTenClicks()) {
            LogUtil.error("开启十连点击失败，终止游戏");
            return false;
        }

        // 开始强化属性
        if (!strengthenAttributeController.loopExecuteEnhancedProcess()) {
            return false;
        }
        if (upAttackByDevourEquipment()) return false;

        // 添加一个储物柜购买 寒冰光环的方法 buyIceHalo
        lockerController.buyIceHalo();

        if (!strengthenAttributeController.loopExecuteEnhancedProcess()) {
            return false;
        }
        return true;
    }

    private boolean upAttackByDevourEquipment() {
        // 获取 PRODUCER_ITEM_NAMES List
        List<String> producerItemNames = Arrays.asList(PRODUCER_ITEM_NAMES);
        // 开始吞噬装备方法
        for (int i = 0; i < THIEF_EAT_TIMES; i++) {
            // 计算一次花费的时间, 方便判断能够执行吞噬操作的次数
            long cTime = System.currentTimeMillis();
            // 执行小偷中的吞噬方法
            if (!thiefController.devourEquipment()) {
                LogUtil.error("吞噬装备失败，终止游戏");
                return true;
            }
            // 延迟 3 s
            automation.delay(500);
            // 执行储物柜中的修改方法
            if (!lockerController.executeModificationProcess(producerItemNames)) {
                LogUtil.error("储物柜修改失败，终止游戏");
                return true;
            }
            // 延迟 3 s
            automation.delay(500);
            long eTime = System.currentTimeMillis();
            LogUtil.info("第 " + (i + 1) + " 次吞噬成功, 耗时：" + (eTime - cTime) / 1000 + "s");
        }
        return false;
    }

    /**
     * 游戏初始设置
     * 1. 对小偷人物进行编号为1, 并且按下 A键 攻击 金矿
     * 2. 对挑战boss建筑进行编号
     */
    private void initialGameSetup() {
        LogUtil.info("=== 游戏初始设置 ===");

        // 1.1 对人物小偷编号
        thiefController.initialize(lockerController, modifierController);
        // 延迟5s, 等待文字消失
        automation.delay(5000);
        // 1.2 小偷攻击金矿
        thiefController.attackGoldMine();
        // 小偷初始化完成

        // 2 对挑战boss建筑编号
        bossChallengeController.initialize(gameStartTime);

        // 3. 对属性强化建筑编号, 并且设置开始时间
        strengthenAttributeController.initialize(gameStartTime);

        // 初始化完成
    }


    /**
     * 步骤 5: 最终 BOSS 挑战
     */
    private boolean executeFinalBossChallenge() {
        LogUtil.info("=== 步骤 5: 最终 BOSS 挑战 ===");
        int maxWaitTime = 1000 * 60 * 2; // 最多检测2分钟
        // 开启最终 BOSS 挑战
        if (!bossChallengeController.startFinalBossChallenge(maxWaitTime)) {
            return false;
        }
        // 等待 1s
        automation.delay(1000);
        // 执行检测胜利逻辑
        if (!bossChallengeController.checkVictoryPeriodically(maxWaitTime)) {
            LogUtil.error("最终 BOSS 挑战失败，终止游戏");
            return false;
        }
        return true;
    }

    /**
     * 步骤 6: 存档 BOSS 挑战
     */
    private void executeArchiverBossChallenge() {
        LogUtil.info("=== 步骤 6: 存档 BOSS 挑战 ===");
        // 依次挑战 所有 个存档 BOSS
        archiverChallengeController.challengeAllArchivers();
        // 等待 2 分钟
        automation.delay(ARCHIVER_BOSS_DURATION);
        LogUtil.info("存档 BOSS 挑战完成");
    }

    /**
     * 步骤 7: 退出游戏
     */
    private void exitGame() {
        LogUtil.info("=== 退出游戏 ===");
        // 使用组合键退出游戏, 先按 F10 键, 再按 E 键, 再按 X 键, 最后再按 X, 然后等待10s, 即可完全退出游戏
        automation.pressFunctionKey(KeyEvent.VK_F10);
        automation.pressFunctionKey(KeyEvent.VK_E);
        automation.pressFunctionKey(KeyEvent.VK_X);
        automation.pressFunctionKey(KeyEvent.VK_X);
        // 等待5s
        automation.delay(5000);
        LogUtil.info("已点击退出游戏按钮");
    }
}
