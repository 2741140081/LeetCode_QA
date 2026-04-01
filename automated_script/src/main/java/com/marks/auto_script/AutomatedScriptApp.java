package com.marks.auto_script;

import com.marks.auto_script.ui.MainFrame;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: AutomatedScriptApp </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class AutomatedScriptApp {

    private static final Logger logger = Logger.getLogger(AutomatedScriptApp.class.getName());

    public static void main(String[] args) {
        // 加载日志配置
        try {
            String loggingConfigPath = "automated_script/src/main/resources/logging.properties";
            LogManager.getLogManager().readConfiguration(new FileInputStream(loggingConfigPath));
            logger.info("日志配置加载成功");
        } catch (IOException e) {
            System.err.println("日志配置加载失败：" + e.getMessage());
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            logger.info("启动主界面");
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
            logger.info("主界面已显示");
        });
    }
}
