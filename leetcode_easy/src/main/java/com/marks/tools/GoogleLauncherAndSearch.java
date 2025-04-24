package com.marks.tools;


import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * <p> Rectangle screenRect = new Rectangle(0, 0, screenSize.width, screenSize.height);</p>
 * <p> 1. 需要注意, 由于我使用的是外置显示器, 并且将外置显示器设置为主显示器, 所以这个方法获取的是我当前外置显示器的分辨率。</p>
 *
 * <p> 2. 并且我的外置显示器分辨率 3840 * 2160, 但是我使用了 150% 的缩放, 所以实际的屏幕截图是(3840/1.5, 2160/1.5) = 2560 * 1440,
 *    这个分辨率不需要我们去进行计算, 通过上面这个方法获取到的截图的分辨率就是(2560 * 1440), 而不是 3840 * 2160</p>
 *
 * <p> 3. 我们实际截图, 例如我们在桌面截取 "Google Chrome" 快捷启动, 这个是基于我们的显示器的物理分辨率截取的, 也就是基于3840 * 2160
 *     因此, 我们截图的图片"google_sign.png", 如果需要再desktop.png(通过Robot截图, 大小是 2560 * 1440), 我们需要将我们截取的
 *     "google_sign.png" 进行相应的缩小1.5倍, 即 (长,宽) / 1.5 = "google_sign_handle.png", 这个handle.png 才能进行匹配</p>
 *
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/22 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class GoogleLauncherAndSearch {
//    private static final Logger logger = LoggerFactory.getLogger(GoogleLauncherAndSearch.class);

    private static final double rank = 0.67; // 显示器的缩放比例, 例如缩放比例150%, 对应大小所缩小为原本的 2 / 3 = 0.67

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        RobotUtils robotUtils = new RobotUtils(); // 工具类

        // 1. 启动 Google
        robotUtils.displayDesktopByRobot(robot);

        // 加载图标模板, 转为Mat对象
        String targetImagePath = "D:\\images\\google_sign.png";
        Mat targetMat = Imgcodecs.imread(targetImagePath, Imgcodecs.IMREAD_COLOR);

        // 截取当前屏幕并且保存
        Mat screenMat = robotUtils.captureCurrScreenAndSave(robot);

        // 找到 targetMat 在 screenMat中的位置, 并且使用绿色边框标记后另存find_image_XXX.png
        int[] position = robotUtils.getTargetImagePosition(targetMat, screenMat, rank);

        if (position[0] >= 80) {

            // 双击启动应用
            robotUtils.smoothMove(robot, position[1], position[2]); // 1000ms完成移动
            robot.delay(3000); // 让我更好看看效果

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(100);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(100);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(100);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(10000); // 让我更好看看效果, 10s是我最后的等待, 具体情况具体设置吧, 万一它启动需要半个小时?
        } else {
            System.out.println("找不到对应的图标进行启动");
            System.exit(0); // 0 表示正常退出，非0表示异常退出
        }


        // 2. 使用百度导航栏(搜索栏), 输入网址进行搜索, 直接进行网址输入, 不找搜索栏了, 太费事了

        robot.delay(3000); // 让我更好看看效果

        String webAddress = "https://leetcode.cn/discuss/post/3142882/fen-xiang-gun-ti-dan-lian-biao-er-cha-sh-6srp/";
        // 进行网址输入
        robotUtils.inputWebAddressByRobot(robot, webAddress, 100);

        // 默认输入法是中文输入法, 需要多一次enter, 第一个enter 用于填入, 第二个用于搜索
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(100);

        // 按下 Enter 进行搜索
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(100);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(100);

        robot.delay(10000); // 让我更好看看效果, 10s是我最后的等待, 具体情况具体设置吧, 万一它启动需要半个小时?

    }
}
