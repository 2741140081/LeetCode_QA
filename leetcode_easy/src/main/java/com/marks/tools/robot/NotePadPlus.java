package com.marks.tools.robot;

import com.marks.tools.RobotUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称: notepad++ </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/15 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NotePadPlus {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private int initDisplay = 100;

    private Robot robot;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public NotePadPlus() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private RobotUtils robotUtils = new RobotUtils(); // 工具类

    public void openNotePadPlusExe(String imgPath) throws Exception {
        // Win + D 显示桌面, 试一下通过构建一个新的Robot对面来显示桌面看看有什么区别
        robotUtils.displayDesktopByRobot();

        // 读取 notepad++ icon img
        Mat src = Imgcodecs.imread(imgPath);

        // 截取当前屏幕
        Mat screenDesktopMat = robotUtils.captureCurrScreenAndSave();

        // 获取系统缩放系数, 计算rank值
        double initDisplay = 100;
        double rank = initDisplay / getDisplayScale(); // 假设缩放比例 = 150, 100 / 150 = 0.667

        int[] position = robotUtils.getTargetImagePosition(src, screenDesktopMat, rank);

        // 置信度需要大于等于80才算成功
        if (position[0] >= 80) {
            robotUtils.smoothMove(position[1], position[2]);
            Thread.sleep(3000);
            robotUtils.doubleLeftClick();
            Thread.sleep(10000);
        } else {
            System.out.println("找不到对应的图标进行启动");
            System.exit(0); // 0 表示正常退出，非0表示异常退出
        }
    }

    public void createNewFile(String iconImgPath) throws Exception {
        // debug 时, 让程序等待5s, 方便我切换到对应的页面
        Thread.sleep(1000);

        // 读取 notepad++ icon img
        Mat src = Imgcodecs.imread(iconImgPath);

        // 截取当前屏幕
        Mat screenDesktopMat = robotUtils.captureCurrScreen();

        // 获取系统缩放系数, 计算rank值
        double display = 100;
        double rank;
        if (initDisplay != 100) {
            rank = display / initDisplay;
        } else {
            rank = display / getDisplayScale(); // 假设缩放比例 = 150, 100 / 150 = 0.667
        }

        int[] position = robotUtils.getTargetImagePosition(src, screenDesktopMat, rank);

        // 置信度需要大于等于80才算成功
        if (position[0] >= 80) {
            robotUtils.smoothMove(position[1], position[2]);
            Thread.sleep(3000);
            robotUtils.onceLeftClick();
            Thread.sleep(3000);
        } else {
            System.out.println("找不到对应的图标进行启动");
            System.exit(0); // 0 表示正常退出，非0表示异常退出
        }

        // 通过判断搜狗状态栏中和英来切换输入法的中英文切换
        String chineseImgPath = "D:\\images\\targetImage\\SogouChinese.png";
        String englishImgPath = "D:\\images\\targetImage\\SogouEnglish.png";

        Mat chineseMat = Imgcodecs.imread(chineseImgPath);
        Mat englishMat = Imgcodecs.imread(englishImgPath);

        screenDesktopMat = robotUtils.captureCurrScreen();
        int[] chinesePosition = robotUtils.getTargetImagePosition(chineseMat, screenDesktopMat, rank);

        screenDesktopMat = robotUtils.captureCurrScreen();
        int[] englishPosition = robotUtils.getTargetImagePosition(englishMat, screenDesktopMat, rank);

        // 我只进行英文输入
        if (chinesePosition[0] > englishPosition[0]) {
            Thread.sleep(1000);
            robotUtils.anyKeyPressFromKeyboard(KeyEvent.VK_SHIFT);
            System.out.printf("按下 Shift 将中文输入切换为英文输入, chinese = %d, english = %d%n", chinesePosition[0], englishPosition[0]);
            Thread.sleep(1000);
        }

        screenDesktopMat = robotUtils.captureCurrScreen();
        englishPosition = robotUtils.getTargetImagePosition(englishMat, screenDesktopMat, rank);

        if (englishPosition[0] >= 80) {
            System.out.println("输入法切换成功!!!");
        }

        // input text
        String content = "In the digital realm, special characters like !@#$%^&*()_+{}|:\\\"<>?~ form "
                + "a unique language. The exclamation (!) shouts urgency, while @ connects us in emails "
                + "(user@domain.com). Hashtags (#) organize trends, and $ rules finance. "
                + "The caret (^) elevates exponents in math (2^3=8), and ampersands (&) link commands (A&B).";

        // 输入字符串
        robotUtils.typingSting(content, 50);

        // 保存文件
        // 点击保存文件按钮
        Thread.sleep(2000);
        String saveButtonImgPath = "D:\\images\\targetImage\\NotepadPlusSaveFile.png";
        Mat saveBtnMat = Imgcodecs.imread(saveButtonImgPath);

        screenDesktopMat = robotUtils.captureCurrScreen();
        int[] saveBtnPosition = robotUtils.getTargetImagePosition(saveBtnMat, screenDesktopMat, rank);

        if (saveBtnPosition[0] >= 80) {
            robotUtils.smoothMove(saveBtnPosition[1], saveBtnPosition[2]);
            Thread.sleep(3000);
            robotUtils.onceLeftClick();
            // 等待保存弹窗框
            Thread.sleep(5000);
        }

        // 文件保存路径
        String saveFilePath = "D:\\images\\result\\Notepad_Plus_Plus_"+ LocalDateTime.now().format(formatter) +".txt";
        // 删除默认名称
        robotUtils.anyKeyPressFromKeyboard(KeyEvent.VK_BACK_SPACE);
        Thread.sleep(1000);

        robotUtils.typingSting(saveFilePath, 50);

        String systemSaveBtnPath = "D:\\images\\targetImage\\SystemSaveBtn.png";
        Mat systemSaveBtnMat = Imgcodecs.imread(systemSaveBtnPath);

        screenDesktopMat = robotUtils.captureCurrScreen();
        int[] systemSaveBtnPosition = robotUtils.getTargetImagePosition(systemSaveBtnMat, screenDesktopMat, rank);

        if (systemSaveBtnPosition[0] >= 80) {
            robotUtils.smoothMove(systemSaveBtnPosition[1], systemSaveBtnPosition[2]);
            Thread.sleep(3000);
            robotUtils.onceLeftClick();
            // 等待保存弹窗框
            Thread.sleep(1000);
        }
    }

    public void exitNotePadPlus(String imgPath) throws Exception {
        Thread.sleep(5000);
        Mat src = Imgcodecs.imread(imgPath);

        Mat currScreen = robotUtils.captureCurrScreen();

        // 获取系统缩放系数, 计算rank值
        double display = 100;
        double rank;
        if (initDisplay != 100) {
            rank = display / initDisplay;
        } else {
            rank = display / getDisplayScale(); // 假设缩放比例 = 150, 100 / 150 = 0.667
        }

        int[] position = robotUtils.getTargetImagePosition(src, currScreen, rank);

        if (position[0] >= 80) {
            Thread.sleep(1000);
            robotUtils.smoothMove(position[1], position[2]);
            Thread.sleep(2000);
            robotUtils.onceLeftClick();
            Thread.sleep(2000);
        }

        String exitBtnOfNotePadPlusPath = "D:\\images\\targetImage\\NotepadPlusExitBtn.png";

        Mat exitMat = Imgcodecs.imread(exitBtnOfNotePadPlusPath);
        currScreen = robotUtils.captureCurrScreen();

        int[] exitPosition = robotUtils.getTargetImagePosition(exitMat, currScreen, rank);

        if (exitPosition[0] >= 80) {
            Thread.sleep(1000);
            robotUtils.smoothMove(exitPosition[1], exitPosition[2]);
            Thread.sleep(2000);
            robotUtils.onceLeftClick();
            Thread.sleep(2000);
        }

    }


    /**
     * @Description:
     * <p>获取显示器的缩放比例</p>
     * <p>通过JNA 库调用windows系统API获取缩放比例</p>
     * <p>仅支持windows 8.1版本及以上</p>
     * @return int
     * @author marks
     * @CreateDate: 2025/5/15 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getDisplayScale() {

        User32.INSTANCE.EnumDisplayMonitors(
                null, null,
                (hmonitor, hdc, rect, lparam) -> {
                    int[] scaleFactor = new int[1];
                    Shcore.INSTANCE.GetScaleFactorForMonitor(hmonitor, scaleFactor);
                    System.out.println("缩放比例: " + scaleFactor[0] + "%");
                    initDisplay = scaleFactor[0];
                    return 1;
                }, new WinDef.LPARAM(0)
        );

        return initDisplay;
    }
}
