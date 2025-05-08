package com.marks.tools.robot;

import com.marks.tools.RobotUtils;
import org.opencv.core.Core;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/7 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class robotMove {
    private final int DELAY_MS = 100; // 设置Robot 键入延迟毫秒数

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void moveTwiceAndClick() {
        try {
            Robot robot = new Robot();
            int[] info1 = {640, 612};
            int[] info2 = {154, 134};

            RobotUtils utils = new RobotUtils();
            utils.displayDesktopByRobot(robot);

            Thread.sleep(5000);
            utils.smoothMove(info1[0], info1[1]);
            // 获取当前鼠标位置
            Point mousePosition1 = MouseInfo.getPointerInfo().getLocation();
            int x1 = (int) mousePosition1.getX();
            int y1 = (int) mousePosition1.getY();

            System.out.println("当前鼠标位置: (" + x1 + ", " + y1 + ")");

            Thread.sleep(5000);
            // 双击
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(DELAY_MS);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(DELAY_MS);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(DELAY_MS);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(15000);

            utils.smoothMove(info2[0], info2[1]);
            // 获取当前鼠标位置
            Point mousePosition2 = MouseInfo.getPointerInfo().getLocation();
            int x2 = (int) mousePosition2.getX();
            int y2 = (int) mousePosition2.getY();

            System.out.println("当前鼠标位置: (" + x2 + ", " + y2 + ")");
            Thread.sleep(5000);
        } catch (Exception e) {
            System.err.printf(e.getMessage());
        }

    }
}
