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

public class HotkeyService implements NativeKeyListener {

    private Map<String, Runnable> hotkeyHandlers = new HashMap<>();
    private Script currentScript;
    private ScriptExecutor executor;

    public HotkeyService(ScriptExecutor executor) {
        this.executor = executor;

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException("Failed to register native hook", e);
        }

        GlobalScreen.addNativeKeyListener(this);
    }

    public void setCurrentScript(Script script) {
        this.currentScript = script;
        if (script != null) {
            registerHotkey(script.getStartKey(), () -> {
                if (!executor.isRunning()) {
                    executor.execute(script);
                }
            });

            registerHotkey(script.getPauseKey(), () -> {
                if (executor.isRunning()) {
                    if (executor.isPaused()) {
                        executor.resume();
                    } else {
                        executor.pause();
                    }
                }
            });
        }
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

