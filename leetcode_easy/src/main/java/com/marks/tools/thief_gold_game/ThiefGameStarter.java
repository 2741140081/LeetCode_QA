package com.marks.tools.thief_gold_game;



import com.marks.tools.CmdUtils;
import com.marks.tools.kkplatform.ImageRecognitionAutomation;
import com.marks.tools.thief_gold_game.controller.GameFlowController;
import com.marks.utils.LogUtil;

import java.awt.*;
import java.io.File;

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

    private static final int DEFAULT_DIFFICULTY = 28; // 默认难度 28

    public static void main(String[] args) {
        LogUtil.info("=== 《小偷偷金 TD》自动化脚本启动 ===");
        CmdUtils cmdUtils = new CmdUtils(); // 命令行工具
        try {
            // 解析命令行参数
            int difficulty = parseDifficulty(args);

            // 初始化自动化环境
            ImageRecognitionAutomation automation = initializeAutomation();

            // 创建游戏流程控制器
            GameFlowController gameFlowController = new GameFlowController(automation);
            // 启动游戏流程, 循环执行游戏主体
            int count = 0;
            int maxCount = 3; // 执行10次
//            difficulty = 24;
            while (count < maxCount) {
                LogUtil.info("第 " + count + " 次执行游戏");
                if (!gameFlowController.startGame(difficulty)) {
                    LogUtil.info("第{}轮游戏流程执行失败, 退出程序", count);
                    break;
                } else {
                    LogUtil.info("第{}轮游戏流程执行成功", count);
                    // 删除 result 目录下的所有文件，防止无用截图太多了，目标目录:D:\images\automation\results
                    deleteFilesInDirectory("D:\\images\\automation\\results");
                }
                count++;
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            LogUtil.error("启动失败：" + e.getMessage(), e);
            System.exit(1);
        } finally {
            LogUtil.info("=== 脚本执行完毕, 将在 60 秒后自动关机... ===");
            // 关闭计算机, 先暂时注释掉, 等流程可以正常运行后, 取消注释
//            cmdUtils.shutDownWindows(60);
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
                if (difficulty >= 1 && difficulty <= 40) {
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

    /**
     * 删除指定目录下的所有文件
     * @param directoryPath 目录路径
     */
    private static void deleteFilesInDirectory(String directoryPath) {
        try {
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                LogUtil.warn("目录不存在或不是目录：{}", directoryPath);
                return;
            }

            File[] files = directory.listFiles();
            if (files == null || files.length == 0) {
                LogUtil.info("目录为空，无需删除：{}", directoryPath);
                return;
            }

            int deletedCount = 0;
            for (File file : files) {
                if (file.isFile()) {
                    if (file.delete()) {
                        deletedCount++;
                    } else {
                        LogUtil.error("删除文件失败：{}", file.getAbsolutePath());
                    }
                }
            }

            LogUtil.info("已删除 {} 个文件，目录：{}", deletedCount, directoryPath);
        } catch (Exception e) {
            LogUtil.error("删除文件时发生错误：" + e.getMessage(), e);
        }
    }
}
