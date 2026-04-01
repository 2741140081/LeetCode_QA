package com.marks.auto_script.service;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: HotkeyService </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

import com.marks.auto_script.model.Script;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class HotkeyService implements NativeKeyListener {

    private static final Logger logger = Logger.getLogger(HotkeyService.class.getName());

    private Map<String, Runnable> hotkeyHandlers = new HashMap<>();
    private Script currentScript;
    private ScriptExecutor executor;

    private enum State {
        IDLE,
        RUNNING,
        RECORDING
    }

    private State currentState = State.IDLE;

    public HotkeyService(ScriptExecutor executor) {
        this.executor = executor;

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException("Failed to register native hook", e);
        }

        GlobalScreen.addNativeKeyListener(this);
        // 注册默认快捷键
        registerDefaultHotkeys();
    }

    private void registerDefaultHotkeys() {
        logger.info("开始注册默认快捷键");

        registerHotkey("F8", () -> {
            logger.info("F8 快捷键被触发，当前状态：" + currentState);
            if (currentState == State.IDLE || currentState == State.RUNNING) {
                startScriptExecution();
            } else {
                logger.warning("F8 快捷键被触发但状态不允许执行，当前状态：" + currentState);
            }
        });

        registerHotkey("F10", () -> {
            logger.info("F10 快捷键被触发，当前状态：" + currentState);
            if (currentState == State.IDLE || currentState == State.RECORDING) {
                toggleRecording();
            } else {
                logger.warning("F10 快捷键被触发但状态不允许执行，当前状态：" + currentState);
            }
        });

        registerHotkey("F9", () -> {
            logger.info("F9 快捷键被触发，当前状态：" + currentState);
            stopCurrentOperation();
        });

        logger.info("默认快捷键注册完成，共注册 " + hotkeyHandlers.size() + " 个快捷键");
    }

    private void startScriptExecution() {
        logger.info("startScriptExecution 方法被调用");
        if (executor.isRunning()) {
            logger.info("脚本正在运行中");
            if (executor.isPaused()) {
                logger.info("恢复暂停的脚本");
                executor.resume();
            } else {
                logger.info("暂停脚本");
                executor.pause();
            }
        } else {
            logger.info("脚本未运行，尝试启动");
            if (currentScript != null) {
                logger.info("执行脚本：" + currentScript.getName());
                executor.execute(currentScript);
                currentState = State.RUNNING;
                logger.info("脚本开始执行，状态已更新为 RUNNING");
            } else {
                logger.severe("currentScript 为 null，无法执行");
            }
        }
    }

    private void toggleRecording() {
        logger.info("toggleRecording 方法被调用");
        if (executor.isRecording()) {
            logger.info("正在录制，执行停止");
            executor.stopRecording();
            currentState = State.IDLE;
            logger.info("录制已停止，状态更新为 IDLE");
        } else {
            logger.info("开始录制");
            executor.startRecording();
            currentState = State.RECORDING;
            logger.info("录制已开始，状态更新为 RECORDING");
        }
    }

    private void stopCurrentOperation() {
        logger.info("stopCurrentOperation 方法被调用");
        if (executor.isRunning()) {
            logger.info("停止运行中的脚本");
            executor.stop();
            currentState = State.IDLE;
            logger.info("脚本执行已停止，状态更新为 IDLE");
        } else if (executor.isRecording()) {
            logger.info("停止录制");
            executor.stopRecording();
            currentState = State.IDLE;
            logger.info("录制已停止，状态更新为 IDLE");
        } else {
            logger.info("当前没有正在执行的操作");
        }
    }

    public void setCurrentScript(Script script) {
        this.currentScript = script;
    }

    public void registerHotkey(String key, Runnable handler) {
        hotkeyHandlers.put(key.toUpperCase(), handler);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        String keyText = NativeKeyEvent.getKeyText(nke.getKeyCode()).toUpperCase();

        Runnable handler = hotkeyHandlers.get(keyText);
        if (handler != null) {
            handler.run();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
    }

    public void unregisterAll() {
        hotkeyHandlers.clear();
    }
}

