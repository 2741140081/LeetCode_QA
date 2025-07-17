package com.marks.tools.video;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class AutoPlayController implements NativeKeyListener {
    private Runnable playAction;
    private Runnable stopAction;

    public AutoPlayController(Runnable playAction, Runnable stopAction) {
        this.playAction = playAction;
        this.stopAction = stopAction;
        try {
            java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(java.util.logging.Level.OFF);
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Failed to register native hook: " + ex.getMessage());
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_F8) {
            playAction.run();
        } else if (e.getKeyCode() == NativeKeyEvent.VC_F9) {
            stopAction.run();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}
}

