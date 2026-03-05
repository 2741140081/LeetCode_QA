package com.marks.tools.robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * @Description: 自动化操作类，提供全局钩子注册和定时自动化操作功能
 * 主要功能：
 * 1. 注册全局钩子监听
 * 2. 每隔3秒执行自动化操作：按下并释放键盘'1'键，等待1秒，然后按下鼠标左键
 * 3. 关键点: 需要管理员身份进行启动, 否则注册的全局钩子无法在游戏内生效
 *
 * @author marks
 * @CreateDate: 2026/2/28 22:20
 */
public class ReadyToStart {
    
    private static final Logger logger = Logger.getLogger(ReadyToStart.class.getName());
    
    private Robot robot;
    private ScheduledExecutorService scheduler;
    private volatile boolean isRunning = false;
    private Thread shutdownHook;
    private boolean isGlobalHookRegistered = false;
    
    /**
     * 构造函数，初始化Robot对象
     */
    public ReadyToStart() {
        try {
            this.robot = new Robot();
            logger.info("Robot对象初始化成功");
        } catch (AWTException e) {
            logger.severe("Robot对象初始化失败: " + e.getMessage());
            throw new RuntimeException("无法创建Robot实例", e);
        }
    }
    
    /**
     * @Description: 注册全局钩子，包括关闭钩子和键盘快捷键
     * @return void
     */
    public void registerGlobalHooks() {
        // 注册JVM关闭钩子，确保程序优雅退出
        shutdownHook = new Thread(() -> {
            logger.info("检测到程序关闭，正在清理资源...");
            stopAutomation();
            unregisterGlobalKeyboardHook();
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        
        // 注册全局键盘钩子
        registerGlobalKeyboardHook();
        
        logger.info("全局钩子注册成功");
    }
    
    /**
     * @Description: 启动自动化任务
     * @return void
     */
    public void startAutomation() {
        if (isRunning) {
            logger.warning("自动化任务已经在运行中");
            return;
        }
        
        isRunning = true;
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "AutomationTask");
            t.setDaemon(true);
            return t;
        });
        
        // 立即执行一次，然后每隔3秒执行
        scheduler.scheduleWithFixedDelay(this::performAutomationTask, 0, 2, TimeUnit.SECONDS);
        logger.info("自动化任务已启动，每3秒执行一次操作");
    }
    
    /**
     * @Description: 停止自动化任务
     * @return void
     */
    public void stopAutomation() {
        if (!isRunning) {
            logger.info("自动化任务未在运行");
            return;
        }
        
        isRunning = false;
        
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        
        logger.info("自动化任务已停止");
    }
    
    /**
     * @Description: 执行具体的自动化操作
     * 操作序列：按下并释放键盘'1'键 -> 等待1秒 -> 按下鼠标左键
     * @return void
     */
    private void performAutomationTask() {
        if (!isRunning) {
            return;
        }
        
        try {
            logger.info("开始执行自动化操作");
            
            // 1. 按下并释放键盘'1'键
            pressAndReleaseKey(KeyEvent.VK_1);
            logger.info("已按下并释放键盘'1'键");
            
            // 2. 等待1秒
            Thread.sleep(1000);
            logger.info("等待1秒完成");
            
            // 3. 按下鼠标左键
            clickLeftMouse();
            logger.info("已按下鼠标左键");
            
            logger.info("本次自动化操作执行完成");
            
        } catch (InterruptedException e) {
            logger.warning("自动化任务被中断: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.severe("执行自动化操作时发生错误: " + e.getMessage());
        }
    }
    
    /**
     * @Description: 按下并释放指定键
     * @param keyCode 键码
     * @return void
     */
    private void pressAndReleaseKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.delay(50); // 短暂延迟确保按键被识别
        robot.keyRelease(keyCode);
    }
    
    /**
     * @Description: 点击鼠标左键
     * @return void
     */
    private void clickLeftMouse() {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50); // 短暂延迟确保点击被识别
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    
    /**
     * @Description: 注册全局键盘钩子，监听F8和F9快捷键
     * @return void
     */
    private void registerGlobalKeyboardHook() {
        if (isGlobalHookRegistered) {
            logger.info("全局键盘钩子已经注册");
            return;
        }
        
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyPressed(NativeKeyEvent e) {
                    // 监听F8键（启动）
                    if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
                        logger.info("检测到F8按键，启动自动化任务");
                        startAutomation();
                    }
                    // 监听F9键（暂停）
                    else if (e.getKeyCode() == NativeKeyEvent.VC_F9) {
                        logger.info("检测到F9按键，暂停自动化任务");
                        stopAutomation();
                    }
                }
                
                @Override
                public void nativeKeyReleased(NativeKeyEvent e) {}
                
                @Override
                public void nativeKeyTyped(NativeKeyEvent e) {}
            });
            
            isGlobalHookRegistered = true;
            logger.info("全局键盘钩子注册成功 - F8:启动, F9:暂停");
            
        } catch (NativeHookException e) {
            logger.severe("注册全局键盘钩子失败: " + e.getMessage());
        }
    }
    
    /**
     * @Description: 注销全局键盘钩子
     * @return void
     */
    private void unregisterGlobalKeyboardHook() {
        if (isGlobalHookRegistered) {
            try {
                // 直接注销全局钩子
                GlobalScreen.unregisterNativeHook();
                isGlobalHookRegistered = false;
                logger.info("全局键盘钩子已注销");
            } catch (NativeHookException e) {
                logger.warning("注销全局键盘钩子时发生错误: " + e.getMessage());
            }
        }
    }
    
    /**
     * @Description: 移除关闭钩子
     * @return void
     */
    public void removeShutdownHook() {
        if (shutdownHook != null) {
            try {
                Runtime.getRuntime().removeShutdownHook(shutdownHook);
                logger.info("关闭钩子已移除");
            } catch (IllegalStateException e) {
                // JVM正在关闭时会抛出此异常，可以忽略
                logger.info("JVM正在关闭，无法移除钩子");
            }
        }
        
        // 同时注销全局键盘钩子
        unregisterGlobalKeyboardHook();
    }
    
    /**
     * @Description: 检查自动化任务是否正在运行
     * @return boolean
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * @Description: 获取Robot对象（用于测试或其他用途）
     * @return Robot
     */
    public Robot getRobot() {
        return robot;
    }
    
    /**
     * @Description: 主方法，演示如何使用此类
     * @param args 命令行参数
     * @return void
     */
    public static void main(String[] args) {
        ReadyToStart readyToStart = new ReadyToStart();
        
        // 注册全局钩子（包括键盘快捷键）
        readyToStart.registerGlobalHooks();
        
        logger.info("程序已启动");
        logger.info("使用说明：");
        logger.info("  F8 - 启动自动化任务");
        logger.info("  F9 - 暂停自动化任务");
        logger.info("  Ctrl+C - 退出程序");
        
        // 保持主线程运行
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            logger.info("主线程被中断");
            Thread.currentThread().interrupt();
        } finally {
            // 清理资源
            readyToStart.stopAutomation();
            readyToStart.removeShutdownHook();
        }
    }
}
