package com.marks.tools;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/22 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class RobotUtils {
    private Robot currRobot;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public RobotUtils() {
        try {
            currRobot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    //    private static final Logger logger = LoggerFactory.getLogger(RobotUtils.class);
    /**
     * @Description: 显示桌面, 方便查找桌面的应用
     * @param robot
     * @return void
     * @author marks
     * @CreateDate: 2025/4/22 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void displayDesktopByRobot(Robot robot) {
        // 显示桌面
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_D);
        robot.delay(100); // 带点缓冲吧
        robot.keyRelease(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.delay(1500);
    }

    public void displayDesktopByRobot() {
        // 显示桌面
        currRobot.keyPress(KeyEvent.VK_WINDOWS);
        currRobot.keyPress(KeyEvent.VK_D);
        currRobot.delay(100); // 带点缓冲吧
        currRobot.keyRelease(KeyEvent.VK_D);
        currRobot.keyRelease(KeyEvent.VK_WINDOWS);
        currRobot.delay(1500);
    }



    /**
     * @Description: 鼠标平滑移动函数, 每20ms移动一步
     * 跟新平滑移动方法, 使其进行非线性加速移动, 相距越长, 移动速度越快
     * @param targetX
     * @param targetY
     * @return void
     * @author marks
     * @CreateDate: 2025/4/22 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void smoothMove(int targetX, int targetY) {
        try {
            Point start = MouseInfo.getPointerInfo().getLocation();
            int steps = 50;
            int duration = 800;

            for (int i = 1; i <= steps; i++) {
                double ratio = Math.sin((i * Math.PI) / (2 * steps)); // 缓动函数
                int x = (int) (start.getX() + (targetX - start.getX()) * ratio);
                int y = (int) (start.getY() + (targetY - start.getY()) * ratio);

                currRobot.mouseMove(x, y);
                currRobot.delay(duration / steps);
            }
        } catch (SecurityException e) {
            System.err.println("权限不足: " + e.getMessage());
        }
    }

    /**
     * @Description:
     *
     * 将BufferedImage转换为OpenCV Mat格式
     *
     * @param bi
     * @return org.opencv.core.Mat
     * @author marks
     * @CreateDate: 2025/4/22 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Mat bufferedImageToMat(BufferedImage bi) {
        // 统一转换为3字节BGR格式（兼容OpenCV默认通道顺序）
        if (bi.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            BufferedImage convertedImage = new BufferedImage(
                    bi.getWidth(),
                    bi.getHeight(),
                    BufferedImage.TYPE_3BYTE_BGR
            );
            convertedImage.getGraphics().drawImage(bi, 0, 0, null);
            bi = convertedImage;
        }

        // 获取图像字节数据
        byte[] pixels = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();

        // 创建相同尺寸的Mat对象
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);

        // 填充数据（自动处理BGR通道顺序）
        mat.put(0, 0, pixels);
        return mat;
    }



    /**
     * @Description:
     * 1. 将目标图片按照显示器的缩放比例 rank 等比例缩小
     * 2. 找到目标图片在桌面截图的位置, 返回position[] = {x, y};
     * @param targetImage
     * @param screenMat
     * @param rank
     * @return int[]
     * @author marks
     * @CreateDate: 2025/4/22 16:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] getTargetImagePosition(Mat targetImage, Mat screenMat, double rank) {
        // 1. 转换为灰度图（提升匹配速度）
        Mat srcGary = new Mat();
        Mat desktopGary = new Mat();
        Imgproc.cvtColor(targetImage, srcGary, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(screenMat, desktopGary, Imgproc.COLOR_BGR2GRAY);

        double maxVal = 0;
        org.opencv.core.Point maxLoc = new org.opencv.core.Point();
        Mat result = new Mat();

        // 多尺度循环匹配
        double currentScale = rank;
        org.opencv.core.Point matchLoc = null;

        // 缩放模板图像
        Mat resizedTemplate = new Mat();
        Imgproc.resize(targetImage, resizedTemplate,
                new Size(targetImage.cols()*currentScale, targetImage.rows()*currentScale));

        // 执行模板匹配:ml-citation{ref="1" data="citationList"}
        Imgproc.matchTemplate(screenMat, resizedTemplate, result,
                Imgproc.TM_CCOEFF_NORMED);

        // 4. 获取最佳匹配位置
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        if (mmr.maxVal > maxVal) {
            maxVal = mmr.maxVal;
            maxLoc = mmr.maxLoc;
            // 记录当前最佳缩放比例
            matchLoc = mmr.maxLoc;
        }


        int[] ans = new int[3];
        ans[0] = (int) (maxVal * 100);
        // 5. 绘制绿色矩形框
        if (maxVal >= 0.8) {
            // 计算实际匹配区域尺寸
            Size matchedSize = new Size(
                    targetImage.cols() * currentScale,
                    targetImage.rows() * currentScale
            );

            // 绘制绿色矩形框（线宽3px）:ml-citation{ref="1" data="citationList"}
            Imgproc.rectangle(
                    screenMat,
                    maxLoc,
                    new org.opencv.core.Point(maxLoc.x + matchedSize.width, maxLoc.y + matchedSize.height),
                    new Scalar(0, 255, 0),
                    2
            );

            // 6. 保存结果
            String outputPath = "D:\\images\\result\\find_image_"+ LocalDateTime.now().format(formatter) + ".png";
            boolean success = Imgcodecs.imwrite(outputPath, screenMat);
            if (success) {
                System.out.println("处理成功！结果已保存至: " + outputPath);
                System.out.println("匹配位置: X=" + (int)matchLoc.x + " Y=" + (int)matchLoc.y);
                System.out.println("匹配置信度: " + maxVal);
                System.out.println("当前目标截图的缩放系数: " + currentScale);
            }

            ans[1] = (int) matchLoc.x;
            ans[2] = (int) matchLoc.y;

            /*
            // debug 的时候在用吧, 实在是显示的太小了, 根本看不清
            HighGui.namedWindow("screen desktop", HighGui.WINDOW_NORMAL);
            HighGui.resizeWindow("screen desktop", 1000, 1000);

            // 添加提示信息
            Imgproc.putText(screenMat, "请按任意键继续",
                    new org.opencv.core.Point(50, 150),
                    Imgproc.FONT_HERSHEY_SIMPLEX,
                    1.0,
                    new Scalar(0, 0, 255),
                    2
            );
            HighGui.imshow("screen desktop", screenMat);
            HighGui.waitKey(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
             */
        }

        // set the targetMat center position
        ans[1] += targetImage.width() / 2;
        ans[2] += targetImage.height() / 2;

        return ans;
    }


    /**
     * @Description:
     * 截取当前屏幕并且进行保存
     * @param robot
     * @return org.opencv.core.Mat
     * @author marks
     * @CreateDate: 2025/4/22 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Mat captureCurrScreenAndSave(Robot robot) throws Exception {
        // 截取当前屏幕
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
        BufferedImage screenshot = robot.createScreenCapture(screenRect);

        // 5. 生成带时间戳的文件名, 将当前桌面截图进行保存
        String outputPath = "D:\\images\\image_"+ LocalDateTime.now().format(formatter) + "desktop.png";

        // 6. 保存图像
        ImageIO.write(screenshot, "png", new File(outputPath));
        System.out.println("截图保存在目录: " + outputPath);

        // 转换为OpenCV格式, 即Mat对象
        Mat screenMat = bufferedImageToMat(screenshot);
        return screenMat;
    }

    /**
     * @Description:
     * overload captureCurrScreenAndSave() method, change the parameter different
     * @return org.opencv.core.Mat
     * @author marks
     * @CreateDate: 2025/5/15 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Mat captureCurrScreenAndSave() {
        // 截取当前屏幕
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
        BufferedImage screenshot = currRobot.createScreenCapture(screenRect);

        // 5. 生成带时间戳的文件名, 将当前桌面截图进行保存
        String outputPath = "D:\\images\\result\\image_"+ LocalDateTime.now().format(formatter) + "desktop.png";

        // 6. 保存图像
        try {
            ImageIO.write(screenshot, "png", new File(outputPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("截图保存在目录: " + outputPath);

        // 转换为OpenCV格式, 即Mat对象
        Mat screenMat = bufferedImageToMat(screenshot);
        return screenMat;
    }

    /**
     * @Description:
     * 只截取当前屏幕, 不进行保存
     * @return org.opencv.core.Mat
     * @author marks
     * @CreateDate: 2025/5/15 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Mat captureCurrScreen() {
        // 截取当前屏幕
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
        BufferedImage screenshot = currRobot.createScreenCapture(screenRect);

        // 转换为OpenCV格式, 即Mat对象
        Mat screenMat = bufferedImageToMat(screenshot);
        return screenMat;
    }

    /**
     * @param robot
     * @param webAddress
     * @param delayMs
     * @return void
     * @Description: [功能描述]
     * @author marks
     * @CreateDate: 2025/4/22 17:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void inputWebAddressByRobot(Robot robot, String webAddress, int delayMs) {
        for (char c : webAddress.toCharArray()) {
            // 处理特殊符号

            int keyCode = getKeyCodeForChar(c);

            // 处理需要Shift的特殊字符
            if ("!@#$%^&*()_+{}|:\"<>?~".indexOf(c) != -1) {
                robot.keyPress(KeyEvent.VK_SHIFT); // 按下Shift
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.keyRelease(KeyEvent.VK_SHIFT); // 释放Shift
            }
            // 处理大写字母
            else if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT); // 按下Shift
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
            // 处理空格/逗号/小写字母（直接输入）
            else {
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode); // 无Shift
            }
            // 控制输入速度
            robot.delay(delayMs);
        }
    }

    // 获取字符对应的KeyEvent（支持常见符号）
    private static int getKeyCodeForChar(char c) {
        switch (c) {
            case '!': return KeyEvent.VK_1;
            case '@': return KeyEvent.VK_2;
            case '#': return KeyEvent.VK_3;
            case '$': return KeyEvent.VK_4;
            case '%': return KeyEvent.VK_5;
            case '^': return KeyEvent.VK_6;
            case '&': return KeyEvent.VK_7;
            case '*': return KeyEvent.VK_8;
            case '(': return KeyEvent.VK_9;
            case ')': return KeyEvent.VK_0;
            case '_': return KeyEvent.VK_MINUS;
            case '+': return KeyEvent.VK_EQUALS;
            case '{': return KeyEvent.VK_OPEN_BRACKET;
            case '}': return KeyEvent.VK_CLOSE_BRACKET;
            case ':': return KeyEvent.VK_SEMICOLON;
            case '<': return KeyEvent.VK_COMMA;
            case '>': return KeyEvent.VK_PERIOD;
            case '\"': return KeyEvent.VK_QUOTE;
            case '?': return KeyEvent.VK_SLASH;
            case '|': return KeyEvent.VK_BACK_SLASH;
            case '~': return KeyEvent.VK_BACK_QUOTE;
            default: return KeyEvent.getExtendedKeyCodeForChar(c);
        }
    }

    public void doubleLeftClick() {
        currRobot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        currRobot.delay(100);
        currRobot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        currRobot.delay(100);
        currRobot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        currRobot.delay(100);
        currRobot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void onceLeftClick() {
        currRobot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        currRobot.delay(100);
        currRobot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    public void anyKeyPressFromKeyboard(int keyValue) {
        currRobot.keyPress(keyValue);
        currRobot.delay(100);
        currRobot.keyRelease(keyValue);
    }

    public void typingSting(String content, int delay) {
        for (char c : content.toCharArray()) {
            // 处理特殊符号

            int keyCode = getKeyCodeForChar(c);

            // 处理需要Shift的特殊字符
            if ("!@#$%^&*()_+{}|:\"<>?~".indexOf(c) != -1) {
                currRobot.keyPress(KeyEvent.VK_SHIFT); // 按下Shift
                currRobot.keyPress(keyCode);
                currRobot.keyRelease(keyCode);
                currRobot.keyRelease(KeyEvent.VK_SHIFT); // 释放Shift
            }
            // 处理大写字母
            else if (Character.isUpperCase(c)) {
                currRobot.keyPress(KeyEvent.VK_SHIFT); // 按下Shift
                currRobot.keyPress(keyCode);
                currRobot.keyRelease(keyCode);
                currRobot.keyRelease(KeyEvent.VK_SHIFT);
            }
            // 处理空格/逗号/小写字母（直接输入）
            else {
                currRobot.keyPress(keyCode);
                currRobot.keyRelease(keyCode); // 无Shift
            }
            // 控制输入速度
            currRobot.delay(delay);
        }
    }
}
