package com.marks.tools.thief_gold_game;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * 小偷金币游戏启动器
 * 功能：按'7'键 + 左键点击循环（F8 启动/继续，F9 暂停）或 右键点击循环（F7 启动/继续，F9 暂停），两种模式互斥
 */
public class TSGameStarter implements NativeKeyListener {

    private volatile boolean running = false;
    private volatile boolean paused = true;
    private volatile boolean isRightClickMode = false; // false: F8 左键模式，true: F7 右键模式
    private Robot robot;
    private Thread taskThread;

    public TSGameStarter() {
        try {
            robot = new Robot();
            initGlobalHotkeys();
        } catch (Exception e) {
            System.err.println("初始化失败：" + e.getMessage());
        }
    }

    /**
     * 注册全局键盘监听器（F8 启动/继续左键模式，F7 启动/继续右键模式，F9 暂停）
     */
    private void initGlobalHotkeys() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            System.out.println("全局快捷键已启动：F7 右键模式，F8 左键模式，F9 暂停");
        } catch (NativeHookException e) {
            System.err.println("注册本地钩子失败：" + e.getMessage());
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
            if (paused) {
                startTask(false); // 左键模式
            } else {
                System.out.println("任务已在运行中...");
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_F7) {
            if (paused) {
                startTask(true); // 右键模式
            } else {
                System.out.println("任务已在运行中...");
            }
        } else if (e.getKeyCode() == NativeKeyEvent.VC_F9) {
            pauseTask();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}

    /**
     * 启动/继续执行任务
     * @param rightClickMode true: 右键模式，false: 左键模式
     */
    private synchronized void startTask(boolean rightClickMode) {
        if (taskThread != null && taskThread.isAlive()) {
            return;
        }
        
        running = true;
        paused = false;
        isRightClickMode = rightClickMode;
        taskThread = new Thread(this::executeTask);
        taskThread.start();
        System.out.println("任务已启动：" + (rightClickMode ? "右键模式" : "左键模式"));
    }

    /**
     * 暂停任务
     */
    private synchronized void pauseTask() {
        running = false;
        paused = true;
        System.out.println("任务已暂停");
    }

    /**
     * 执行循环任务：F8 模式 - 按'7'键 + 左键点击；F7 模式 - 右键点击
     */
    private void executeTask() {
        System.out.println("开始执行循环任务..." + (isRightClickMode ? " [右键模式]" : " [左键模式]"));
        while (running) {
            try {
                if (!isRightClickMode) {
                    // F8 左键模式：按下并释放小键盘数字键'7' → 延迟 50ms → 左键点击 → 延迟 1.2 秒
                    pressAndReleaseKey(KeyEvent.VK_NUMPAD7);
                    robot.delay(50);
                    clickLeftMouse();
                    robot.delay(1200);
                } else {
                    // F7 右键模式：右键点击 → 延迟 100ms
                    clickRightMouse();
                    robot.delay(100);
                }
                
            } catch (Exception ex) {
                System.err.println("任务执行出错：" + ex.getMessage());
                break;
            }
        }
    }

    /**
     * 按下并释放指定键
     */
    private void pressAndReleaseKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
    }

    /**
     * 点击鼠标左键
     */
    private void clickLeftMouse() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 点击鼠标右键
     */
    private void clickRightMouse() {
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public static void main(String[] args) {
        new TSGameStarter();
        
        // 保持程序运行
        synchronized (TSGameStarter.class) {
            while (true) {
                try {
                    TSGameStarter.class.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
