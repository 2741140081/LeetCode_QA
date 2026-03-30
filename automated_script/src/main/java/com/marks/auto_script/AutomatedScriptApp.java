package com.marks.auto_script;

import com.marks.auto_script.ui.MainFrame;

import javax.swing.*;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
