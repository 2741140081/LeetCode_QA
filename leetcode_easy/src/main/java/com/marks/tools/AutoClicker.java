package com.marks.tools;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/18 16:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class AutoClicker extends JFrame {
    private volatile boolean running = false;
    private Robot robot;
    private TrayIcon trayIcon;
    private JButton startButton, stopButton;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    public AutoClicker() {
        initUI();
        initSystemTray();
        initGlobalHotkeys();
        createRobot();
    }

    private void initUI() {
        setTitle("鼠标连点器 v2.0");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new FlowLayout());

        // 控制按钮
        startButton = new JButton("启动 (F5)");
        stopButton = new JButton("暂停 (F12)");
        stopButton.setEnabled(false);

        // 本地快捷键
        KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        KeyStroke f12 = KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0);

        getRootPane().getInputMap().put(f5, "startClicking");
        getRootPane().getInputMap().put(f12, "stopClicking");
        getRootPane().getActionMap().put("startClicking", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { startClicking(); }
        });
        getRootPane().getActionMap().put("stopClicking", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { stopClicking(); }
        });

        startButton.addActionListener(e -> startClicking());
        stopButton.addActionListener(e -> stopClicking());

        add(startButton);
        add(stopButton);

        // 窗口隐藏快捷键
        getRootPane().getInputMap().put(KeyStroke.getKeyStroke("ctrl H"), "hideWindow");
        getRootPane().getActionMap().put("hideWindow", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    private void initSystemTray() {
        if (!SystemTray.isSupported()) return;

        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/images/icon.png"));

        PopupMenu popup = new PopupMenu();
        MenuItem startItem = new MenuItem("启动");
        MenuItem stopItem = new MenuItem("暂停");
        MenuItem showItem = new MenuItem("显示窗口");
        MenuItem exitItem = new MenuItem("退出程序");

        startItem.addActionListener(e -> startClicking());
        stopItem.addActionListener(e -> stopClicking());
        showItem.addActionListener(e -> setVisible(true));
        exitItem.addActionListener(e -> System.exit(0));

        popup.add(startItem);
        popup.add(stopItem);
        popup.addSeparator();
        popup.add(showItem);
        popup.add(exitItem);

        trayIcon = new TrayIcon(image, "鼠标连点器", popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addActionListener(e -> setVisible(true));

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private void initGlobalHotkeys() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "全局热键初始化失败");
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            public void nativeKeyPressed(NativeKeyEvent e) {
                if ((e.getModifiers() & NativeKeyEvent.CTRL_L_MASK) != 0) {
                    switch (e.getKeyCode()) {
                        case NativeKeyEvent.VC_F5:
                            startClicking();
                            break;
                        case NativeKeyEvent.VC_F12:
                            stopClicking();
                            break;
                    }
                }
            }
        });
    }

    private void createRobot() {
        try {
            robot = new Robot();
            robot.setAutoDelay(50);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(this, "无法初始化鼠标控制器");
            System.exit(1);
        }
    }

    private synchronized void startClicking() {
        if (running) return;
        running = true;

        updateUIState(false);

        new Thread(() -> {
            while (running) {
                long clickTime = System.currentTimeMillis(); // 获取点击前时间戳
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                System.out.printf("[%s][Thread-%d] 鼠标点击 @ %s | 系统时钟: %d\n",
                        sdf.format(new Date(clickTime)),
                        Thread.currentThread().getId(),
                        sdf.format(new Date()),
                        System.currentTimeMillis());
                try {
                    Thread.sleep(8000); // 点击间隔100ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private synchronized void stopClicking() {
        running = false;
        updateUIState(true);
    }

    private void updateUIState(boolean isStopped) {
        SwingUtilities.invokeLater(() -> {
            startButton.setEnabled(isStopped);
            stopButton.setEnabled(!isStopped);

            if (trayIcon != null) {
                PopupMenu menu = trayIcon.getPopupMenu();
                menu.getItem(0).setEnabled(isStopped); // 启动项
                menu.getItem(1).setEnabled(!isStopped); // 暂停项
            }
        });
    }

    @SneakyThrows
    @Override
    public void dispose() {
        GlobalScreen.unregisterNativeHook();
        super.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AutoClicker clicker = new AutoClicker();
            clicker.setVisible(true);
        });
    }
}
