package com.marks.tools.kkplatform;

import com.marks.tools.kkplatform.common.GameOperationCommon;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: KingOfBeastsArchive </p>
 * <p>描述: 自动化存档斗兽之王
 * 1. 按下"空格键"(任意键, 习惯性用空格, 其他键也可), 进入游戏
 * 2. 通过图片识别, 找到对应的难度选项, 点击选择难度
 * 3. 选择英雄, 英雄通过(F1, F2, F3) 的按钮进行选择, 自动化过程中只选择F1 的英雄, 按下F1 键, 通过图片识别, 找到"确认"按钮, 点击确认,
 * 按下组合键 "Ctrl + 1", 对英雄进行编号(当按下 1键时, 会选择该英雄)
 * 4. 切换到修改器app 界面 并且执行相关操作, 返回 boolean 值, true 表示成功, false 表示失败
 * 5. 如果返回 true 后, 切换到游戏界面, 返回false, 则程序并且记录错误日志
 * 6. 找到并且点击天赋按钮(一个图标), 图片识别并且点击 "火焰天赋" 按钮
 * 7. 按下 "1键" 选择英雄, 找到并点击 "准备" 按钮, 鼠标一直位于 "准备" 按钮位置处, 每隔 5s 点击一次"准备"按钮
 * 8. 过了15分钟后, 识别"存档"按钮, 是否存在, 如果不存在, 延迟30s后, 再次识别"存档" 按钮,
 * 如果存在, 点击"存档" 按钮, 找到并且点击 "开始存档" 按钮, 图片识别 并且 点击 "存档属性A" 选项
 * 如果过了 25 分钟, 还是没有找到存档按钮, 则截取当前游戏页面截图并且保存, 然后程序运行结束
 * 9. 在13步骤存档完成后, 图片识别 并点击 "退出游戏" 按钮
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 11:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class KingOfBeastsArchive extends GameOperationCommon {
    private static final String DIFFICULTY_btn = "difficulty_btn";
    private static final String CONFIRM_btn = "confirm_btn";
    private static final String TALENT_btn = "talent_btn";
    private static final String FIRE_TALENT_btn = "fire_talent_btn";
    private static final String READY_btn = "ready_btn";
    private static final String ARCHIVE_btn = "archive_btn";
    private static final String START_ARCHIVE_btn = "start_archive_btn";
    private static final String ARCHIVE_SLOT_A = "archive_slot_a";
    private static final String EXIT_GAME_btn = "exit_game_btn";

    private ModifiersOperation modifiersOperation;
    private boolean shouldStop = false;

    public KingOfBeastsArchive(ImageRecognitionAutomation automation, ModifiersOperation modifiersOperation) {
        super(automation);
        this.modifiersOperation = modifiersOperation;
    }

    /**
     * 执行完整的游戏流程
     * @return 是否成功完成
     */
    public boolean executeGameLoop() {
        try {
            LogUtil.info("=== 开始游戏流程 ===");

            // 等待游戏加载完成, 按下空格键进入游戏
            if (!enterGame()) {
                return false;
            }

            // 选择难度
            if (!selectDifficulty()) {
                return false;
            }

            // 选择英雄
            if (!selectHero()) {
                return false;
            }

            if (!modifyAttributes()) {
                LogUtil.error("修改属性失败");
                return false;
            }

            if (!selectTalent()) {
                return false;
            }

            if (!prepareAndWait()) {
                return false;
            }

            if (!archiveGame()) {
                return false;
            }

            exitGame();

            LogUtil.info("=== 游戏流程完成 ===");
            return true;

        } catch (Exception e) {
            LogUtil.error("游戏流程异常：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 步骤 1: 按空格键进入游戏
     */
    private boolean enterGame() {
        LogUtil.info("=== 步骤 1: 进入游戏 ===");
        automation.robot.keyPress(KeyEvent.VK_SPACE);
        automation.delay(50);
        automation.robot.keyRelease(KeyEvent.VK_SPACE);
        automation.delay(2000);
        LogUtil.info("已按空格键进入游戏");
        return true;
    }

    /**
     * 步骤 2: 选择难度
     */
    private boolean selectDifficulty() {
        LogUtil.info("=== 步骤 2: 选择难度 ===");
        return findAndClickImage(DIFFICULTY_btn);
    }

    /**
     * 步骤 3: 选择英雄
     */
    private boolean selectHero() {
        LogUtil.info("=== 步骤 3: 选择英雄 ===");

        pressFunctionKey(KeyEvent.VK_F1);

        if (!findAndClickImage(CONFIRM_btn)) {
            return false;
        }

        // 按下 "Ctrl + 1" 给英雄编号
        pressCombinationKey(KeyEvent.VK_CONTROL, KeyEvent.VK_1);

        LogUtil.info("英雄选择完成");
        return true;
    }

    /**
     * 步骤 4: 切换到修改器修改属性
     */
    private boolean modifyAttributes() {
        LogUtil.info("=== 步骤 4: 修改属性 ===");
        return modifiersOperation.execute();
    }

    /**
     * 步骤 5: 选择天赋
     */
    private boolean selectTalent() {
        LogUtil.info("=== 步骤 5: 选择天赋 ===");

        if (!findAndClickImage(TALENT_btn)) {
            return false;
        }

        return findAndClickImage(FIRE_TALENT_btn);
    }

    /**
     * 步骤 6: 准备并等待
     */
    private boolean prepareAndWait() {
        LogUtil.info("=== 步骤 6: 准备并等待 ===");

        automation.robot.keyPress(KeyEvent.VK_1);
        automation.delay(50);
        automation.robot.keyRelease(KeyEvent.VK_1);
        automation.delay(CLICK_DELAY);

        Point readyPoint = automation.findImage(READY_btn);
        if (readyPoint == null) {
            LogUtil.error("未找到准备按钮");
            return false;
        }

        automation.click(readyPoint.x, readyPoint.y);

        Thread clickThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            while (!shouldStop && System.currentTimeMillis() - startTime < 15 * 60 * 1000) {
                automation.delay(5000);
                automation.click(readyPoint.x, readyPoint.y);
                LogUtil.info("自动点击准备按钮");
            }
        });

        clickThread.start();

        automation.delay(15 * 60 * 1000);
        shouldStop = true;

        try {
            clickThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 步骤 7: 存档
     */
    private boolean archiveGame() {
        LogUtil.info("=== 步骤 7: 存档 ===");

        long startTime = System.currentTimeMillis();
        boolean archiveFound = false;

        while (!archiveFound && System.currentTimeMillis() - startTime < 25 * 60 * 1000) {
            if (waitForImage(ARCHIVE_btn, 30000)) {
                archiveFound = true;

                if (!findAndClickImage(ARCHIVE_btn)) {
                    return false;
                }

                if (!findAndClickImage(START_ARCHIVE_btn)) {
                    return false;
                }

                if (!findAndClickImage(ARCHIVE_SLOT_A)) {
                    return false;
                }

                LogUtil.info("存档完成");
                break;
            } else {
                LogUtil.info("未找到存档按钮，30 秒后重试...");
                automation.delay(30000);
            }
        }

        if (!archiveFound) {
            LogUtil.error("超过 25 分钟未找到存档按钮，截取当前画面");
            captureErrorScreen();
            return false;
        }

        return true;
    }

    /**
     * 步骤 8: 退出游戏
     */
    private void exitGame() {
        LogUtil.info("=== 步骤 8: 退出游戏 ===");
        findAndClickImage(EXIT_GAME_btn);
        LogUtil.info("已点击退出游戏按钮");
    }

    /**
     * 截取错误截图
     */
    private void captureErrorScreen() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String screenshotPath = "D:\\images\\automation\\error_screenshots\\error_" + timestamp + ".png";

        File dir = new File("D:\\images\\automation\\error_screenshots\\");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        automation.captureScreen(screenshotPath);
        LogUtil.error("错误截图已保存：" + screenshotPath);
    }
}
