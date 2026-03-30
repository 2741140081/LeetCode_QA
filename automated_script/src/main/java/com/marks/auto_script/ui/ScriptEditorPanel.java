package com.marks.auto_script.ui;


import com.marks.auto_script.model.Script;
import com.marks.auto_script.model.ScriptCommand;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ScriptEditorPanel </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class ScriptEditorPanel extends JPanel {

    private MainFrame mainFrame;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public ScriptEditorPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setLineWrap(false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadScript(Script script) {
        StringBuilder content = new StringBuilder();
        for (ScriptCommand command : script.getCommands()) {
            content.append(command.toString()).append(";\n");
        }
        textArea.setText(content.toString());
    }

    public void saveToScript(Script script) {
        String[] lines = textArea.getText().split("\n");
        List<ScriptCommand> commands = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("//")) {
                continue;
            }

            try {
                ScriptCommand command = ScriptCommand.parse(line);
                commands.add(command);
            } catch (IllegalArgumentException e) {
                System.err.println("Skipping invalid command: " + line);
            }
        }

        script.setCommands(commands);
    }

    public void insertTemplate(String template) {
        int caretPos = textArea.getCaretPosition();
        String currentText = textArea.getText();

        String newText = currentText.substring(0, caretPos) + template + ";\n" +
                currentText.substring(caretPos);
        textArea.setText(newText);
        textArea.setCaretPosition(caretPos + template.length() + 2);
        textArea.requestFocus();
    }
}

