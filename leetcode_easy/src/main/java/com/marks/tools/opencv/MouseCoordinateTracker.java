package com.marks.tools.opencv;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Robot;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/7 15:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class MouseCoordinateTracker {
    private static JFrame frame;
    private static JLabel coordLabel;
    private static Robot robot;
    private static volatile boolean running = true;

    public static void main(String[] args) {
        try {
            robot = new Robot();
            createDisplayWindow();
            startTrackingLoop();

            // 初始化监听
            Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
                if (event instanceof KeyEvent) {
                    KeyEvent e = (KeyEvent) event;
                    if (e.getID() == KeyEvent.VK_ESCAPE) {
                        running = false;
                        System.out.println("按键按下: " + KeyEvent.getKeyText(e.getKeyCode()));
                    }
                }
            }, AWTEvent.KEY_EVENT_MASK);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(null, "Robot初始化失败: " + e.getMessage());
        }
    }

    private static void createDisplayWindow() {
        frame = new JFrame("鼠标坐标追踪器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setOpacity(0.8f);
        frame.setBackground(new Color(0, 0, 0, 100));

        coordLabel = new JLabel("正在获取坐标...", SwingConstants.CENTER);
        coordLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        coordLabel.setForeground(Color.WHITE);
        frame.add(coordLabel);

        // 窗口拖拽功能
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                frame.getRootPane().putClientProperty("dragOffset", new Point(e.getX(), e.getY()));
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point offset = (Point) frame.getRootPane().getClientProperty("dragOffset");
                frame.setLocation(e.getXOnScreen() - offset.x, e.getYOnScreen() - offset.y);
            }
        });

        frame.setVisible(true);
    }

    private static void startTrackingLoop() {
        // 坐标获取线程
        new Thread(() -> {
            while (running) {
                try {
                    Point mousePos = MouseInfo.getPointerInfo().getLocation();
                    updateDisplay(mousePos);
                    Thread.sleep(1000); // 1秒间隔
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void updateDisplay(Point mousePos) {
        SwingUtilities.invokeLater(() -> {
            coordLabel.setText(String.format("X: %d | Y: %d", mousePos.x, mousePos.y));
            frame.toFront(); // 保持窗口在最前
        });
    }
}




