package com.marks.tools;


import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.InputEvent;

import static com.marks.tools.Constants.*;

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
    private final double RANK = 0.67;

    // 匹配置信度, Match configuration reliability
    private final int MATCH_CONF_RELIABILITY = 80;

    private final int SYSTEM_EXIST_STATUE_CODE = 0;

    private final int DELAY_MS = 100; // 设置Robot 键入延迟毫秒数

    // robot 操作工具类
    private RobotUtils robotUtils = new RobotUtils();

    // cmd 操作工具类
    private CmdUtils cmdUtils = new CmdUtils();

    private String appName;

    private String programName;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public KKDzPlatformLauncher(String appName, String programName) {
        this.appName = appName;
        this.programName = programName;
    }


    /**
     * @Description:
     * 前置操作
     * 1. 关闭 program
     * 2. win + D 显示桌面
     * @return void
     * @author marks
     * @CreateDate: 2025/5/16 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void preOperation() {
        cmdUtils.killTargetProgramByCMD(programName);
        robotUtils.displayDesktopByRobot();
    }

    /**
     * @Description:
     * launch the game platform
     * @param platformPath
     * @return void
     * @author marks
     * @CreateDate: 2025/5/16 16:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void launcherPlatform(String platformPath, String loginBtnImagePath) throws Exception {
        Mat screenMat;
        // 1. launcher the KK Dz platform start

        // 1.1 display the desktop
        robotUtils.displayDesktopByRobot();

        // 1.2 load the KK Dz quick launcher icon from the hard disk resource.(example: D:\images\targetImage\XXX.png)
        Mat targetMat = Imgcodecs.imread(platformPath, Imgcodecs.IMREAD_COLOR);

        // 1.3 capture the current desktop, and don't save the png file, then load the png file
        screenMat = robotUtils.captureCurrScreen();

        // 1.4 find the targetMat from the screenMat, and return the kkLauncherPosition[],
        // kkLauncherPosition[0] is match configuration reliability(匹配置信度), kkLauncherPosition[1] and kkLauncherPosition[2] is the targetMats position
        int[] kkLauncherPosition = robotUtils.getTargetImagePosition(targetMat, screenMat, RANK);

        if (kkLauncherPosition[0] >= MATCH_CONF_RELIABILITY) {
            // move the mouse to the target position, the position is x = kkLauncherPosition[1], y = kkLauncherPosition[2]
            robotUtils.smoothMove(kkLauncherPosition[1], kkLauncherPosition[2]);
            Thread.sleep(2000); // wait a minute

            // double click to launcher the app
            robotUtils.doubleLeftClick();

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

        // 1. launcher the KK Dz platform end

        // 2. login KK Dz App (optional: because sometime user no need to click the login button)

        // 2.1 check whether the login button is exist.
        Mat loginButtonMat = Imgcodecs.imread(loginBtnImagePath, Imgcodecs.IMREAD_COLOR);

        // capture the whole screen
        screenMat = robotUtils.captureCurrScreen();

        int[] loginButtonResult = robotUtils.getTargetImagePosition(loginButtonMat, screenMat, RANK);

        if (loginButtonResult[0] >= MATCH_CONF_RELIABILITY) {
            // move the mouse to the login button position.
            robotUtils.smoothMove(loginButtonResult[1], loginButtonResult[2]);
            Thread.sleep(2000); // wait a minute

            // click the login button
            robotUtils.onceLeftClick();

            Thread.sleep(30000);
        }

        // 3. Close/Click the daily activity pop-up window

        // 3.1 By directly closing the entire kk.exe program at the system level, the pop-up window not display again.
        if (!cmdUtils.killTargetProgramByCMD(programName)) {
            // 或许有其他方法可以进行错误的检测, 待完善
            System.out.println("program was not successfully closed!");
        }
        // wait program close
        Thread.sleep(3000);

        // 3.2 restart the application
        if (kkLauncherPosition[0] >= MATCH_CONF_RELIABILITY) {
            // default the app is not started

            // move the mouse to the target position, the position is x = kkLauncherPosition[1], y = kkLauncherPosition[2]
            robotUtils.smoothMove(kkLauncherPosition[1], kkLauncherPosition[2]);
            Thread.sleep(2000); // wait a minute

            // double click to launcher the app
            robotUtils.doubleLeftClick();

            // wait 10s, wait the app launcher.
            Thread.sleep(10000);
        }

        // 4. click the target game, and start a new game, record the game start time


        // 5. select the easy difficulty options

        // 6. start game

        // 6.1 select target hero

        // 6.2 select target weapon, there are two weapons to select, according the weapons priority.

        // 6.3 click to activate Master Card

        // 6.4 find the Master building, click and select the Northern Europe Cards.

        // 6.5 loop click the update button, capture the screen, find the "Saul(索尔)" Card, and select the card

        // 6.6 update the weapon level, and select the specific event (execute every 3 minutes, and maxLoop is 10)

        // 6.7 check whether the game time is more than 40 minutes. if yes, find and click the game over button(or quick exit)

        // 7. play the game again. loop exec step 4 to step 6 before action.
    }


    /**
     * @Description:
     * 1. 通过将platform.exe 添加到系统变量 Path 中
     * 2. 使用CMD 命令进行启动
     * @param loginBtnPath
     * @return void
     * @author marks
     * @CreateDate: 2025/5/16 17:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void launcherPlatformByCMD(String loginBtnPath) throws Exception {
        cmdUtils.launchProgram(programName);

        // 休眠2min
        Thread.sleep(120000);

        // 防止弹窗影响
        cmdUtils.killTargetProgramByCMD(programName);

        // 休眠30s
        Thread.sleep(30000);


        cmdUtils.launchProgram(programName);

        // 休眠30s
        Thread.sleep(30000);
    }

}
