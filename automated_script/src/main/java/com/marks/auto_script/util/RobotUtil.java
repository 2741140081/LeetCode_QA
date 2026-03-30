package com.marks.auto_script.util;


import com.marks.auto_script.config.AppConfig;

import java.awt.*;
import java.awt.event.InputEvent;
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

    static {
        try {
            robot = new Robot();
            robot.setAutoWaitForIdle(true);
        } catch (AWTException e) {
            throw new RuntimeException("Failed to create Robot instance", e);
        }
    }

    public static void delay(int milliseconds) {
        robot.delay(milliseconds);
    }

    public static void keyPress(int keyCode) {
        robot.keyPress(keyCode);
    }

    public static void keyRelease(int keyCode) {
        robot.keyRelease(keyCode);
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

    public static void rightClick() {
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
}

