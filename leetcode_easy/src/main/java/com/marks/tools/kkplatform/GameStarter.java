package com.marks.tools.kkplatform;

import com.marks.utils.LogUtil;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: GameStarter </p>
 * <p>描述:
 * 自动化存档游戏斗兽之王启动类
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 11:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class GameStarter {

    private ImageRecognitionAutomation automation;
    private PrepareRoom prepareRoom;
    private KingOfBeastsArchive kingOfBeastsArchive;
    private ModifiersOperation modifiersOperation;
    private WindowSwitcherUtils windowSwitcher;

    private static final String PREPARE_ROOM_TITLE = "准备房间";
    private static final String GAME_TITLE = "King of Beasts";
    private static final String MODIFIER_TITLE = "xTools";

    public GameStarter() throws Exception {
        automation = new ImageRecognitionAutomation();
        windowSwitcher = new WindowSwitcherUtils();
        modifiersOperation = new ModifiersOperation(automation);
        prepareRoom = new PrepareRoom(automation);
        kingOfBeastsArchive = new KingOfBeastsArchive(automation, modifiersOperation);
    }

    /**
     * 启动自动化流程
     */
    public void start() {
        LogUtil.info("=== 斗兽之王自动化存档程序启动 ===");

        try {
            while (true) {
                LogUtil.info("\n========== 新一轮游戏流程 ==========");

                if (!executeOneRound()) {
                    LogUtil.error("本轮游戏流程执行失败，5 秒后退出...");
                    Thread.sleep(5000);
                    break;
                }

                LogUtil.info("本轮游戏流程完成，10 秒后开始下一轮...");
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            LogUtil.error("程序被中断");
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            LogUtil.error("程序异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 执行一轮完整的游戏流程
     * @return 是否成功
     */
    private boolean executeOneRound() {
        try {
            // 切换到准备房间界面
            if (!switchToPrepareRoom()) {
                return false;
            }

            // 点击"开始游戏"按钮
            if (!startGameFromPrepareRoom()) {
                return false;
            }

            // 切换到游戏窗口
            if (!switchToGameWindow()) {
                return false;
            }
            // executeGameAndSelectHero
            if (!kingOfBeastsArchive.executeGameAndSelectHero()) {
                return false;
            }

            // 切换到修改器窗口
            if (!switchToModifiersWindow()) {
                return false;
            }

            // 执行修改器操作
            if (!modifiersOperation.execute()) {
                return false;
            }

            // 运行游戏循环
            if (!kingOfBeastsArchive.executeGameLoop()) {
                return false;
            }

            return true;

        } catch (Exception e) {
            LogUtil.error("执行一轮游戏流程异常：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 切换到准备房间界面
     */
    private boolean switchToPrepareRoom() {
        LogUtil.info("=== 切换到准备房间界面 ===");
        return windowSwitcher.switchToWindow(PREPARE_ROOM_TITLE);
    }

    /**
     * 从准备房间开始游戏
     */
    private boolean startGameFromPrepareRoom() {
        LogUtil.info("=== 准备房间：开始游戏 ===");
        return prepareRoom.startGame();
    }

    /**
     * 切换到游戏窗口
     */
    private boolean switchToGameWindow() {
        LogUtil.info("=== 切换到游戏窗口 ===");
        return windowSwitcher.switchToWindow(GAME_TITLE);
    }

    /**
     * 切换到游戏窗口
     */
    private boolean switchToModifiersWindow() {
        LogUtil.info("=== 切换到修改器窗口 ===");
        return windowSwitcher.switchToWindow(MODIFIER_TITLE);
    }

    /**
     * 主方法
     */
    public static void main(String[] args) {
        try {
            GameStarter starter = new GameStarter();
            starter.start();
        } catch (Exception e) {
            LogUtil.error("启动失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

}
