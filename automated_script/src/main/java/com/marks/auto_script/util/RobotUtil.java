package com.marks.auto_script.util;


import com.marks.auto_script.config.AppConfig;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: RobotUtil </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class RobotUtil {

    private static Robot robot;
    private static final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();

    static {
        try {
            robot = new Robot();
            robot.setAutoWaitForIdle(true);
        } catch (AWTException e) {
            throw new RuntimeException("Failed to create Robot instance", e);
        }
    }

    public static void delay(int milliseconds) {
        if (milliseconds <= 0) {
            return;
        } else if (milliseconds < 60000) {
            robot.delay(milliseconds);
        } else {
            // > 60s
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void keyPress(int keyCode) {
        robot.keyPress(keyCode);
        pressedKeys.add(keyCode);
    }

    public static void keyRelease(int keyCode) {
        robot.keyRelease(keyCode);
        pressedKeys.remove(keyCode);
    }

    public static void keyClick(int keyCode) {
        keyPress(keyCode);
        delay(AppConfig.DEFAULT_CLICK_INTERVAL);
        keyRelease(keyCode);
    }

    public static void mousePress(int buttons) {
        robot.mousePress(buttons);
    }

    public static void mouseRelease(int buttons) {
        robot.mouseRelease(buttons);
    }

    public static void leftClick() {
        mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delay(AppConfig.DEFAULT_CLICK_INTERVAL);
        mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void leftClick(int x, int y) {
        if (x != -1 || y != -1) {
            moveMouse(x, y);
            delay(50);
        }
        mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delay(AppConfig.DEFAULT_CLICK_INTERVAL);
        mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public static void rightClick() {
        mousePress(InputEvent.BUTTON3_DOWN_MASK);
        delay(AppConfig.DEFAULT_CLICK_INTERVAL);
        mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public static void rightClick(int x, int y) {
        if (x != -1 || y != -1) {
            moveMouse(x, y);
            delay(50);
        }
        mousePress(InputEvent.BUTTON3_DOWN_MASK);
        delay(AppConfig.DEFAULT_CLICK_INTERVAL);
        mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public static void moveMouse(int x, int y) {
        robot.mouseMove(x, y);
    }

    public static void waitForIdle() {
        robot.waitForIdle();
    }

    public static void resetAllKeys() {
        for (Integer keyCode : pressedKeys) {
            try {
                robot.keyRelease(keyCode);
            } catch (Exception e) {
                System.err.println("释放按键失败，keyCode: " + keyCode + ", 错误: " + e.getMessage());
            }
        }
        pressedKeys.clear();
    }

    public static boolean hasPressedKeys() {
        return !pressedKeys.isEmpty();
    }

    public static int getPressedKeysCount() {
        return pressedKeys.size();
    }
}

