package com.marks.auto_script.service;

import com.marks.auto_script.config.AppConfig;
import com.marks.auto_script.model.Script;
import com.marks.auto_script.model.ScriptCommand;
import com.marks.auto_script.util.RobotUtil;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ScriptExecutor </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */



public class ScriptExecutor {

    private static final Map<String, Integer> KEY_MAP = new HashMap<>();

    static {
        KEY_MAP.put("A", KeyEvent.VK_A);
        KEY_MAP.put("B", KeyEvent.VK_B);
        KEY_MAP.put("C", KeyEvent.VK_C);
        KEY_MAP.put("D", KeyEvent.VK_D);
        KEY_MAP.put("E", KeyEvent.VK_E);
        KEY_MAP.put("F", KeyEvent.VK_F);
        KEY_MAP.put("G", KeyEvent.VK_G);
        KEY_MAP.put("H", KeyEvent.VK_H);
        KEY_MAP.put("I", KeyEvent.VK_I);
        KEY_MAP.put("J", KeyEvent.VK_J);
        KEY_MAP.put("K", KeyEvent.VK_K);
        KEY_MAP.put("L", KeyEvent.VK_L);
        KEY_MAP.put("M", KeyEvent.VK_M);
        KEY_MAP.put("N", KeyEvent.VK_N);
        KEY_MAP.put("O", KeyEvent.VK_O);
        KEY_MAP.put("P", KeyEvent.VK_P);
        KEY_MAP.put("Q", KeyEvent.VK_Q);
        KEY_MAP.put("R", KeyEvent.VK_R);
        KEY_MAP.put("S", KeyEvent.VK_S);
        KEY_MAP.put("T", KeyEvent.VK_T);
        KEY_MAP.put("U", KeyEvent.VK_U);
        KEY_MAP.put("V", KeyEvent.VK_V);
        KEY_MAP.put("W", KeyEvent.VK_W);
        KEY_MAP.put("X", KeyEvent.VK_X);
        KEY_MAP.put("Y", KeyEvent.VK_Y);
        KEY_MAP.put("Z", KeyEvent.VK_Z);
        KEY_MAP.put("0", KeyEvent.VK_0);
        KEY_MAP.put("1", KeyEvent.VK_1);
        KEY_MAP.put("2", KeyEvent.VK_2);
        KEY_MAP.put("3", KeyEvent.VK_3);
        KEY_MAP.put("4", KeyEvent.VK_4);
        KEY_MAP.put("5", KeyEvent.VK_5);
        KEY_MAP.put("6", KeyEvent.VK_6);
        KEY_MAP.put("7", KeyEvent.VK_7);
        KEY_MAP.put("8", KeyEvent.VK_8);
        KEY_MAP.put("9", KeyEvent.VK_9);
        KEY_MAP.put("ENTER", KeyEvent.VK_ENTER);
        KEY_MAP.put("SPACE", KeyEvent.VK_SPACE);
        KEY_MAP.put("TAB", KeyEvent.VK_TAB);
        KEY_MAP.put("ESCAPE", KeyEvent.VK_ESCAPE);
        KEY_MAP.put("UP", KeyEvent.VK_UP);
        KEY_MAP.put("DOWN", KeyEvent.VK_DOWN);
        KEY_MAP.put("LEFT", KeyEvent.VK_LEFT);
        KEY_MAP.put("RIGHT", KeyEvent.VK_RIGHT);
        KEY_MAP.put("CONTROL", KeyEvent.VK_CONTROL);
        KEY_MAP.put("SHIFT", KeyEvent.VK_SHIFT);
        KEY_MAP.put("ALT", KeyEvent.VK_ALT);
    }

    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean paused = new AtomicBoolean(false);
    private Thread executorThread;

    public void execute(Script script) {
        if (running.get()) {
            return;
        }

        running.set(true);
        paused.set(false);

        executorThread = new Thread(() -> {
            try {
                int totalExecutions = script.getExecutionMode().ordinal() == 0
                        ? script.getExecutionCount() : Integer.MAX_VALUE;

                for (int i = 0; i < totalExecutions && running.get(); i++) {
                    script.setCurrentExecution(i + 1);

                    for (ScriptCommand command : script.getCommands()) {
                        if (!running.get() || paused.get()) {
                            break;
                        }

                        executeCommand(command);
                    }
                }
            } finally {
                running.set(false);
                script.reset();
            }
        });

        executorThread.start();
    }

    private void executeCommand(ScriptCommand command) {
        switch (command.getType()) {
            case KEY_PRESS:
                Integer keyCode = getKeyCode(command.getArgument());
                if (keyCode != null) {
                    RobotUtil.keyPress(keyCode);
                }
                break;
            case KEY_RELEASE:
                keyCode = getKeyCode(command.getArgument());
                if (keyCode != null) {
                    RobotUtil.keyRelease(keyCode);
                }
                break;
            case KEY_CLICK:
                keyCode = getKeyCode(command.getArgument());
                if (keyCode != null) {
                    RobotUtil.keyClick(keyCode);
                }
                break;
            case DELAY:
                RobotUtil.delay(command.getDelay());
                break;
            case MOUSE_PRESS:
                RobotUtil.leftClick();
                break;
            case MOUSE_RELEASE:
                break;
            case MOUSE_CLICK:
                RobotUtil.leftClick();
                break;
            case LEFT_CLICK:
                RobotUtil.leftClick();
                break;
            case RIGHT_CLICK:
                RobotUtil.rightClick();
                break;
        }
    }

    private Integer getKeyCode(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        return KEY_MAP.get(key.toUpperCase());
    }

    public void stop() {
        running.set(false);
        paused.set(false);
    }

    public void pause() {
        paused.set(true);
    }

    public void resume() {
        paused.set(false);
    }

    public boolean isRunning() {
        return running.get();
    }

    public boolean isPaused() {
        return paused.get();
    }
}

