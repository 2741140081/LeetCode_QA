package com.marks.tools.kkplatform;

import com.marks.tools.CmdUtils;
import com.marks.tools.RobotUtils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class KKDzPlatformStartGame {

    private final double RANK = 0.667;

    // 匹配置信度, Match configuration reliability
    private final int MATCH_CONF_RELIABILITY = 80;

    // robot 操作工具类
    private RobotUtils robotUtils = new RobotUtils();

    // cmd 操作工具类
    private CmdUtils cmdUtils = new CmdUtils();

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void startGame(String gameImgPath) throws InterruptedException {
        Mat screenMat;


        Thread.sleep(10000);

        Mat gameMat = Imgcodecs.imread(gameImgPath);

        screenMat = robotUtils.captureCurrScreen();

        int[] gamePosition = robotUtils.getTargetImagePosition(gameMat, screenMat, RANK);

        if (gamePosition[0] >= MATCH_CONF_RELIABILITY) {
            robotUtils.smoothMove(gamePosition[1], gamePosition[2]);
            Thread.sleep(3000);

            robotUtils.onceLeftClick();

            Thread.sleep(2000);

            robotUtils.doubleLeftClick();
        }


    }
}
