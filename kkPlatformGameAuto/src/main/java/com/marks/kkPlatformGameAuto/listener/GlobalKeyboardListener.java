package com.marks.kkPlatformGameAuto.listener;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.marks.kkPlatformGameAuto.service.GameCommonService;
import com.marks.kkPlatformGameAuto.state.GameStateManager;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 全局键盘监听器
 * 监听 F8（暂停）和 F9（恢复）快捷键
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/23
 */
@Slf4j
@Component
public class GlobalKeyboardListener implements CommandLineRunner, NativeKeyListener {

    @Autowired
    private GameCommonService gameCommonService;

    @Autowired
    private GameStateManager gameStateManager;

    private boolean isRegistered = false;

    // Deleted:private static final Logger logger = Logger.getLogger(GlobalKeyboardListener.class.getName());

    @Override
    public void run(String... args) throws Exception {
        registerGlobalKeyboardHook();
    }

    /**
     * 注册全局键盘钩子
     */
    private void registerGlobalKeyboardHook() {
        if (isRegistered) {
            log.info("全局键盘钩子已经注册");
            return;
        }

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            isRegistered = true;
            log.info("全局键盘钩子注册成功");
            log.info("快捷键说明：F8 - 暂停游戏，F9 - 恢复游戏");
        } catch (NativeHookException e) {
            log.error("注册全局键盘钩子失败：{}", e.getMessage());
            log.error("注册全局键盘钩子失败，请确保以管理员权限运行", e);
        }

        // 注册 JVM 关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("检测到程序关闭，正在清理资源...");
            unregisterGlobalKeyboardHook();
        }));
    }

    /**
     * 注销全局键盘钩子
     */
    @PreDestroy
    public void unregisterGlobalKeyboardHook() {
        if (!isRegistered) {
            return;
        }

        try {
            GlobalScreen.removeNativeKeyListener(this);
            GlobalScreen.unregisterNativeHook();
            isRegistered = false;
            log.info("全局键盘钩子已注销");
        } catch (NativeHookException e) {
            log.error("注销全局键盘钩子失败：{}", e.getMessage());
            log.error("注销全局键盘钩子失败", e);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // 监听 F8 键（暂停）
        if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
            log.info("检测到 F8 按键，暂停游戏");

            // 只有在运行状态才能暂停
            if (gameStateManager.isRunning()) {
                gameCommonService.pauseGame();
            } else {
                log.warn("当前状态不能暂停：{}", gameStateManager.getCurrentStatus());
            }
        }
        // 监听 F9 键（恢复）
        else if (e.getKeyCode() == NativeKeyEvent.VC_F9) {
            log.info("检测到 F9 按键，恢复游戏");

            // 只有在暂停状态才能恢复
            if (gameStateManager.isPaused()) {
                gameCommonService.resumeGame();
            } else {
                log.warn("当前状态不能恢复：{}", gameStateManager.getCurrentStatus());
            }
        }
    }
}
