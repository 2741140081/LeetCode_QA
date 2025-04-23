package com.marks.tools;


import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/23 10:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class KKDzPlatformLauncher {

    // 屏幕缩放比例, 0.67 = 1 / (150%), 150%是显示器的缩放比例
    private static final double RANK = 0.67;

    // 匹配置信度, Match configuration reliability
    private static final int MATCH_CONF_RELIABILITY = 80;

    private static final int SYSTEM_EXIST_STATUE_CODE = 0;

    private static final int DELAY_MS = 100; // 设置Robot 键入延迟毫秒数

    // robot 操作工具类
    private static RobotUtils robotUtils = new RobotUtils();

    // cmd 操作工具类
    private static CmdUtils cmdUtils = new CmdUtils();

    private static String appName = "KK.exe";

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {
        // 1. launcher the KK Dz platform
        Robot robot = new Robot();

        // 1.1 display the desktop
        robotUtils.displayDesktopByRobot(robot);

        // 1.2 load the KK Dz quick launcher icon from the hard disk resource.(example: D:\images\targetImage\XXX.png)
        String targetImagePath = "D:\\images\\targetImage\\kk_sign.png";
        Mat targetMat = Imgcodecs.imread(targetImagePath, Imgcodecs.IMREAD_COLOR);

        // 1.3 capture the current desktop, and save the png file, then load the png file
        Mat screenMat = robotUtils.captureCurrScreenAndSave(robot);

        // 1.4 find the targetMat from the screenMat, and return the result[],
        // result[0] is match configuration reliability(匹配置信度), result[1] and result[2] is the targetMats position
        int[] result = robotUtils.getTargetImagePosition(targetMat, screenMat, RANK);

        if (result[0] >= MATCH_CONF_RELIABILITY) {
            // default the app is not started

            // move the mouse to the target position, the position is x = result[1], y = result[2]
            robotUtils.smoothMove(robot, result[1], result[2]);
            Thread.sleep(2000); // wait a minute

            // double click to launcher the app
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(DELAY_MS);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(DELAY_MS);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(DELAY_MS);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            /*
            * wait 2 minutes, wait the app launcher.
            * because maybe the App will automatically update the client when it starts
            * */
            Thread.sleep(120000);
        } else {
            // exist the tool
            System.out.println("Can't find the correct icon to launcher the app, please check it!");
            System.exit(SYSTEM_EXIST_STATUE_CODE);
        }

        // 1.5 check whether the KK Dz starts successfully
        if (!cmdUtils.isProcessRunning(appName)) {
            System.out.println("Can't find the app by use the tasklist command line, please check it!");
            System.exit(SYSTEM_EXIST_STATUE_CODE);
        }


        // 2. login KK Dz App (optional: because sometime user no need to click the login button)

        // 2.1 check whether the login button is exist.
        String loginBtnImagePath = "D:\\images\\targetImage\\kk_login_button_sign.png";
        Mat loginButtonMat = Imgcodecs.imread(loginBtnImagePath, Imgcodecs.IMREAD_COLOR);

        // capture the whole screen
        screenMat = robotUtils.captureCurrScreenAndSave(robot);

        int[] loginButtonResult = robotUtils.getTargetImagePosition(loginButtonMat, screenMat, RANK);

        if (loginButtonResult[0] >= MATCH_CONF_RELIABILITY) {
            // move the mouse to the login button position.
            robotUtils.smoothMove(robot, loginButtonResult[1], loginButtonResult[2]);
            Thread.sleep(2000); // wait a minute

            // click the login button
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(DELAY_MS);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            Thread.sleep(30000);
        }

        // 3. Close/Click the daily activity pop-up window

        // need todo


        // 4. click the target game, and start a new game

        // 5. select the easy difficulty options

        // 6. start game

        // 6.1 select target hero

        // 6.2 select target weapon, there are two weapons to select, according the weapons priority.

        // 6.3 click to activate Master Card

        // 6.4 find the Master building, click and select the Northern Europe Cards.

        //
    }
}
