package com.marks.tools.thief_gold_game;



import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.thief_gold_game.controller.GameFlowController;
import com.marks.utils.LogUtil;

import java.awt.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ThiefGameStarter </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/12 11:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ThiefGameStarter {

    private static final int DEFAULT_DIFFICULTY = 16; // 默认难度 16

    public static void main(String[] args) {
        LogUtil.info("=== 《小偷偷金 TD》自动化脚本启动 ===");

        try {
            // 解析命令行参数
            int difficulty = parseDifficulty(args);

            // 初始化自动化环境
            ImageRecognitionAutomation automation = initializeAutomation();

            // 创建游戏流程控制器
            GameFlowController gameFlowController = new GameFlowController(automation);

            // 启动游戏流程
            gameFlowController.startGame(difficulty);

        } catch (Exception e) {
            LogUtil.error("启动失败：" + e.getMessage(), e);
            System.exit(1);
        }
    }

    /**
     * 解析难度参数
     * @param args 命令行参数
     * @return 难度等级
     */
    private static int parseDifficulty(String[] args) {
        if (args.length > 0) {
            try {
                int difficulty = Integer.parseInt(args[0]);
                if (difficulty >= 1 && difficulty <= 30) {
                    LogUtil.info("使用自定义难度：" + difficulty);
                    return difficulty;
                } else {
                    LogUtil.warn("难度超出范围 (1-30)，使用默认难度：" + DEFAULT_DIFFICULTY);
                }
            } catch (NumberFormatException e) {
                LogUtil.warn("无效的难度参数，使用默认难度：" + DEFAULT_DIFFICULTY);
            }
        }
        LogUtil.info("使用默认难度：" + DEFAULT_DIFFICULTY);
        return DEFAULT_DIFFICULTY;
    }

    /**
     * 初始化图像识别自动化环境
     * @return ImageRecognitionAutomation 实例
     */
    private static ImageRecognitionAutomation initializeAutomation() throws AWTException {
        LogUtil.info("正在初始化图像识别自动化环境...");

        ImageRecognitionAutomation automation = new ImageRecognitionAutomation();

        // 延迟 2 秒，确保环境准备就绪
        automation.delay(2000);

        LogUtil.info("自动化环境初始化完成");
        return automation;
    }
}
