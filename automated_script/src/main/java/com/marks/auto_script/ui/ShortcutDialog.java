package com.marks.auto_script.ui;


import com.marks.auto_script.model.ExecutionMode;
import com.marks.auto_script.model.Script;

import javax.swing.*;
import java.awt.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ShortcutDialog </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class ShortcutDialog extends JDialog {

    private Script script;
    private JComboBox<String> startKeyCombo;
    private JComboBox<String> pauseKeyCombo;
    private JRadioButton fixedTimesRadio;
    private JRadioButton infiniteRadio;
    private SpinnerNumberModel countModel;
    private boolean saved = false;

    private static final String[] FUNCTION_KEYS = {
            "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12"
    };

    public ShortcutDialog(Frame owner, Script script) {
        super(owner, "快捷键设置", true);
        this.script = script;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("启动快捷键:"), gbc);

        startKeyCombo = new JComboBox<>(FUNCTION_KEYS);
        startKeyCombo.setSelectedItem(script.getStartKey());
        gbc.gridx = 1;
        mainPanel.add(startKeyCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("暂停快捷键:"), gbc);

        pauseKeyCombo = new JComboBox<>(FUNCTION_KEYS);
        pauseKeyCombo.setSelectedItem(script.getPauseKey());
        gbc.gridx = 1;
        mainPanel.add(pauseKeyCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modePanel.setBorder(BorderFactory.createTitledBorder("执行模式"));

        ButtonGroup group = new ButtonGroup();

        infiniteRadio = new JRadioButton("无限循环");
        infiniteRadio.setSelected(script.getExecutionMode() == ExecutionMode.INFINITE);

        fixedTimesRadio = new JRadioButton("固定次数");
        fixedTimesRadio.setSelected(script.getExecutionMode() == ExecutionMode.FIXED_TIMES);

        group.add(infiniteRadio);
        group.add(fixedTimesRadio);

        countModel = new SpinnerNumberModel(script.getExecutionCount(), 1, 999, 1);
        JSpinner countSpinner = new JSpinner(countModel);
        countSpinner.setEnabled(fixedTimesRadio.isSelected());

        fixedTimesRadio.addItemListener(e -> countSpinner.setEnabled(fixedTimesRadio.isSelected()));

        modePanel.add(infiniteRadio);
        modePanel.add(fixedTimesRadio);
        modePanel.add(countSpinner);

        mainPanel.add(modePanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(e -> saveSettings());
        buttonPanel.add(saveButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveSettings() {
        script.setStartKey((String) startKeyCombo.getSelectedItem());
        script.setPauseKey((String) pauseKeyCombo.getSelectedItem());
        script.setExecutionMode(fixedTimesRadio.isSelected() ? ExecutionMode.FIXED_TIMES : ExecutionMode.INFINITE);
        script.setExecutionCount(countModel.getNumber().intValue());

        saved = true;
        dispose();
    }

    public boolean isSaved() {
        return saved;
    }
}

