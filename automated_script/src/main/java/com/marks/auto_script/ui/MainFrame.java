package com.marks.auto_script.ui;


import com.marks.auto_script.model.Script;
import com.marks.auto_script.service.HotkeyService;
import com.marks.auto_script.service.ScriptExecutor;
import com.marks.auto_script.service.ScriptService;
import com.marks.auto_script.util.ScriptParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MainFrame </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */



public class MainFrame extends JFrame {

    private static final Logger logger = Logger.getLogger(MainFrame.class.getName());

    private ScriptService scriptService;
    private ScriptExecutor executor;
    private HotkeyService hotkeyService;

    private JList<Script> scriptList;
    private DefaultListModel<Script> listModel;
    private ScriptEditorPanel editorPanel;
    private JLabel statusLabel;

    private JMenuItem runMenuItem;
    private JMenuItem stopMenuItem;
    private JMenuItem shortcutMenuItem;
    private JMenuItem recordMenuItem;

    private Script currentScript;

    public MainFrame() {
        scriptService = new ScriptService();
        executor = new ScriptExecutor();
        hotkeyService = new HotkeyService(executor);

        initializeUI();
        loadScripts();
    }

    private void initializeUI() {
        setTitle("自动化脚本工具");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        listModel = new DefaultListModel<>();
        scriptList = new JList<>(listModel);
        scriptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scriptList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectScript(scriptList.getSelectedValue());
            }
        });

        JScrollPane listScrollPane = new JScrollPane(scriptList);
        listScrollPane.setPreferredSize(new Dimension(250, 0));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(listScrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("刷新");
        refreshButton.addActionListener(e -> loadScripts());
        leftPanel.add(refreshButton, BorderLayout.SOUTH);

        editorPanel = new ScriptEditorPanel(this);

        JPanel templatePanel = createTemplatePanel();

        JSplitPane rightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        rightSplitPane.setLeftComponent(editorPanel);
        rightSplitPane.setRightComponent(templatePanel);
        rightSplitPane.setResizeWeight(0.75);

        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightSplitPane);
        mainSplitPane.setResizeWeight(0.25);

        getContentPane().add(mainSplitPane, BorderLayout.CENTER);

        statusLabel = new JLabel("就绪");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        getContentPane().add(statusLabel, BorderLayout.SOUTH);
    }

    private JPanel createTemplatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("模板指令"));
        panel.setPreferredSize(new Dimension(200, 0));

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        JButton clickButton = new JButton("点击按键");
        clickButton.addActionListener(e -> editorPanel.insertTemplate("click []"));
        buttonPanel.add(clickButton, gbc);

        gbc.gridy++;
        JButton delayButton = new JButton("延迟");
        delayButton.addActionListener(e -> editorPanel.insertTemplate("delay []"));
        buttonPanel.add(delayButton, gbc);

        gbc.gridy++;
        JButton keyPressButton = new JButton("按键按下");
        keyPressButton.addActionListener(e -> editorPanel.insertTemplate("keyPress []"));
        buttonPanel.add(keyPressButton, gbc);

        gbc.gridy++;
        JButton keyReleaseButton = new JButton("按键释放");
        keyReleaseButton.addActionListener(e -> editorPanel.insertTemplate("keyRelease []"));
        buttonPanel.add(keyReleaseButton, gbc);

        gbc.gridy++;
        JButton leftClickButton = new JButton("左键点击");
        leftClickButton.addActionListener(e -> editorPanel.insertTemplate("leftClick"));
        buttonPanel.add(leftClickButton, gbc);

        gbc.gridy++;
        JButton rightClickButton = new JButton("右键点击");
        rightClickButton.addActionListener(e -> editorPanel.insertTemplate("rightClick"));
        buttonPanel.add(rightClickButton, gbc);

        gbc.gridy++;
        JButton moveMouseButton = new JButton("移动鼠标");
        moveMouseButton.addActionListener(e -> editorPanel.insertTemplate("moveMouse [],[]"));
        buttonPanel.add(moveMouseButton, gbc);

        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("文件");

        JMenuItem newItem = new JMenuItem("新建脚本");
        newItem.addActionListener(e -> createNewScript());
        fileMenu.add(newItem);

        JMenuItem openItem = new JMenuItem("打开脚本");
        openItem.addActionListener(e -> openScript());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("保存脚本");
        saveItem.addActionListener(e -> saveCurrentScript());
        fileMenu.add(saveItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);


        menuBar.add(Box.createHorizontalStrut(10));

        shortcutMenuItem = new JMenuItem("快捷键设置");
        shortcutMenuItem.setMaximumSize(shortcutMenuItem.getPreferredSize());
        shortcutMenuItem.addActionListener(e -> showShortcutDialog());
        menuBar.add(shortcutMenuItem);

        runMenuItem = new JMenuItem("运行脚本 ");
        runMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
        runMenuItem.setMaximumSize(runMenuItem.getPreferredSize());
        runMenuItem.addActionListener(e -> {
            logger.info("菜单项 [运行脚本] 被触发");
            runScript();
        });
        menuBar.add(runMenuItem);
        logger.info("注册菜单项快捷键：F8 - 运行脚本");

        recordMenuItem = new JMenuItem("录制脚本 ");
        recordMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0));
        recordMenuItem.setMaximumSize(recordMenuItem.getPreferredSize());
        recordMenuItem.addActionListener(e -> {
            logger.info("菜单项 [录制脚本] 被触发");
            startRecording();
        });
        menuBar.add(recordMenuItem);
        logger.info("注册菜单项快捷键：F10 - 录制脚本");

        stopMenuItem = new JMenuItem("停止 ");
        stopMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
        stopMenuItem.setMaximumSize(stopMenuItem.getPreferredSize());
        stopMenuItem.addActionListener(e -> {
            logger.info("菜单项 [停止] 被触发");
            stopScript();
        });
        menuBar.add(stopMenuItem);
        logger.info("注册菜单项快捷键：F9 - 停止");

        return menuBar;
    }

    private void loadScripts() {
        listModel.clear();
        List<Script> scripts = scriptService.loadAllScripts();
        for (Script script : scripts) {
            listModel.addElement(script);
        }

        if (!scripts.isEmpty()) {
            scriptList.setSelectedIndex(0);
        }
    }

    private void selectScript(Script script) {
        if (script != null) {
            currentScript = script;
            editorPanel.loadScript(script);
            hotkeyService.setCurrentScript(script);
            statusLabel.setText("已加载脚本：" + script.getName());
        }
    }

    private void createNewScript() {
        String name = JOptionPane.showInputDialog(this, "请输入脚本名称:", "新建脚本", JOptionPane.QUESTION_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            Script newScript = scriptService.createNewScript(name + ".txt");
            currentScript = newScript;
            editorPanel.loadScript(newScript);
            listModel.addElement(newScript);
            scriptList.setSelectedValue(newScript, true);
            statusLabel.setText("已创建新脚本：" + newScript.getName());
        }
    }

    private void openScript() {
        JFileChooser fileChooser = new JFileChooser(new File(com.marks.auto_script.config.AppConfig.SCRIPT_DIR));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("脚本文件 (*.txt)", "txt"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Script script = ScriptParser.loadScript(fileChooser.getSelectedFile().getAbsolutePath());
                currentScript = script;
                editorPanel.loadScript(script);

                boolean found = false;
                for (int i = 0; i < listModel.size(); i++) {
                    if (listModel.getElementAt(i).getFilePath().equals(script.getFilePath())) {
                        scriptList.setSelectedIndex(i);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    listModel.addElement(script);
                    scriptList.setSelectedValue(script, true);
                }

                statusLabel.setText("已打开脚本：" + script.getName());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "打开脚本失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveCurrentScript() {
        if (currentScript != null) {
            try {
                editorPanel.saveToScript(currentScript);
                scriptService.saveScript(currentScript);
                statusLabel.setText("脚本已保存：" + currentScript.getName());
                JOptionPane.showMessageDialog(this, "脚本保存成功！");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "保存脚本失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "没有当前脚本", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showShortcutDialog() {
        if (currentScript == null) {
            JOptionPane.showMessageDialog(this, "请先选择或创建一个脚本", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ShortcutDialog dialog = new ShortcutDialog(this, currentScript);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            hotkeyService.setCurrentScript(currentScript);
            statusLabel.setText("快捷键设置已保存 - 启动：" + currentScript.getStartKey() + ", 暂停：" + currentScript.getPauseKey());
        }
    }

    private void runScript() {
        logger.info("runScript() 方法被调用");
        if (currentScript == null) {
            logger.warning("尝试运行脚本但 currentScript 为 null");
            JOptionPane.showMessageDialog(this, "请先选择或创建一个脚本", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        logger.info("当前脚本：" + currentScript.getName());

        if (hotkeyService == null) {
            logger.warning("hotkeyService 为 null，创建新的实例");
            hotkeyService = new HotkeyService(executor);
            hotkeyService.setCurrentScript(currentScript);
        }

        executor.execute(currentScript);
        statusLabel.setText("脚本正在运行... 按 F9 停止");
        logger.info("脚本开始执行，状态：" + executor.isRunning());
    }

    private void startRecording() {
        logger.info("startRecording() 方法被调用");
        if (executor.isRecording()) {
            logger.info("正在录制中，执行停止录制");
            executor.stopRecording();
            statusLabel.setText("录制已停止");
            recordMenuItem.setText("录制脚本 (F10)");
        } else {
            logger.info("开始录制");
            executor.startRecording();
            statusLabel.setText("正在录制... 按 F9 停止录制");
            recordMenuItem.setText("停止录制");
        }
    }

    private void stopScript() {
        logger.info("stopScript() 方法被调用");
        if (executor != null) {
            logger.info("executor 状态 - isRunning: " + executor.isRunning() + ", isRecording: " + executor.isRecording());
            if (executor.isRunning() || executor.isRecording()) {
                if (executor.isRunning()) {
                    logger.info("停止运行中的脚本");
                    executor.stop();
                    statusLabel.setText("脚本已停止");
                    recordMenuItem.setText("录制脚本 (F10)");
                }
                if (executor.isRecording()) {
                    logger.info("停止录制");
                    executor.stopRecording();
                    statusLabel.setText("录制已停止");
                    recordMenuItem.setText("录制脚本 (F10)");
                }
            } else {
                logger.info("没有正在运行的脚本");
                statusLabel.setText("没有正在运行的脚本");
            }
        } else {
            logger.warning("executor 为 null");
        }
    }
}

