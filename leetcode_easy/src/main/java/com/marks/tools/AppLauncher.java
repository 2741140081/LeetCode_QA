package com.marks.tools;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称: win10 桌面应用启动 </p>
 * <p>文件名称:  </p>
 * <p>描述:
 * 语言: java
 * 工具/类库: opencv + robot
 * 资源及其配置说明
 * 1. 已经截图应用图标 AAA.png, 放在/resources/images 目录下。
 * 2. 程序通过idea 启动, 需要显示桌面, 然后移动鼠标到应用图标中心点, 双击启动应用。
 * 3. 系统为win10， 显示器分辨率为 3840 * 2160， 缩放和布局设置为 200%。
 * </p>
 *
 * <p>
 * 运行说明
 * 1. 需要先去官网下载 opencv_java4100.dll 文件, 官网地址: https://opencv.org/releases/ 点击下载对应版本的windows 按钮
 * 2. 执行 opencv-4.10.0-windows.exe 文件, 解压后得到 opencv 文件夹, copy opencv\build\java\x64 下的 opencv_java4100.dll
 * 3. 将 opencv_java4100.dll 文件复制到 项目java jdk 的bin 目录下
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/21 17:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class AppLauncher {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws Exception {
        // 显示桌面
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_D);
        robot.delay(100); // 带点缓冲吧
        robot.keyRelease(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.delay(3000);

        // 加载图标模板
        String templatePath = "D:\\images\\KK_sign.png";
        Mat template = Imgcodecs.imread(templatePath, Imgcodecs.IMREAD_COLOR);

        // 截取当前屏幕
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRect = new Rectangle(0, 0,
                (int)(screenSize.width),  // 3840/2=1920
                (int)(screenSize.height)); // 2160/2=1080

        BufferedImage screenshot = robot.createScreenCapture(screenRect);

        // 5. 生成带时间戳的文件名, 将当前桌面截图进行保存
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String outputPath = "D:\\images\\image_"+ LocalDateTime.now().format(formatter) + "desktop.png";

        // 6. 保存图像
        ImageIO.write(screenshot, "png", new File(outputPath));
        System.out.println("截图保存在目录: " + outputPath);


        // 定义四个角落坐标
        Point[] corners = {
                new Point(0, 0),           // 左上角
                new Point(screenSize.width - 100, 0),    // 右上角
                new Point(screenSize.width - 100, screenSize.height - 100), // 右下角
                new Point(0, screenSize.height - 100)    // 左下角
        };

        // 移动到每个角落并停留
        for (Point corner : corners) {
            // 平滑移动（可选）
            smoothMove(robot, (int) corner.x, (int) corner.y, 1000); // 500ms完成移动

            // 停留3秒
            robot.delay(1000);
        }

        // 转换为OpenCV格式
        Mat screenMat = bufferedImageToMat(screenshot);

        int[] position = getTargetImagePosition(template, screenMat);

        // 成功找到对应的图标, 需要移动到图标的中心处
        position[0] += template.width() / 2;
        position[1] += template.height() / 2;

        // 双击启动应用
        smoothMove(robot, position[0], position[1], 1000); // 500ms完成移动
        robot.delay(3000); // 让我更好看看效果

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
    }

    /**
     * @Description: [功能描述]
     * @param targetImage
     * @param screenMat
     * @return int[]
     * @author marks
     * @CreateDate: 2025/4/22 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private static int[] getTargetImagePosition(Mat targetImage, Mat screenMat) {

        // 1. 转换为灰度图（提升匹配速度）
        Mat grayTemplate = new Mat();
        Mat graySource = new Mat();
        Imgproc.cvtColor(targetImage, grayTemplate, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(screenMat, graySource, Imgproc.COLOR_BGR2GRAY);

        // 多尺度匹配参数设置
        double[] scales = {0.5, 0.55, 0.6, 0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95, 1.0, 1.05, 1.1, 1.15, 1.2, 1.25, 1.3, 1.35, 1.4, 1.45, 1.5};  // 缩放比例范围:ml-citation{ref="2" data="citationList"}
        double maxVal = 0;
        Point maxLoc = new Point();
        Mat result = new Mat();

        // 多尺度循环匹配
        double currentScale = 1.0;
        Point matchLoc = null;
        for (double scale : scales) {
            // 缩放模板图像
            Mat resizedTemplate = new Mat();
            Imgproc.resize(targetImage, resizedTemplate,
                    new Size(targetImage.cols()*scale, targetImage.rows()*scale));

            // 执行模板匹配:ml-citation{ref="1" data="citationList"}
            Imgproc.matchTemplate(screenMat, resizedTemplate, result,
                    Imgproc.TM_CCOEFF_NORMED);

            // 4. 获取最佳匹配位置
            Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
            if (mmr.maxVal > maxVal) {
                maxVal = mmr.maxVal;
                maxLoc = mmr.maxLoc;
                // 记录当前最佳缩放比例
                currentScale = scale;
                matchLoc = mmr.maxLoc;
            }
        }

        int[] ans = new int[2];
        // 5. 绘制绿色矩形框
        if (maxVal >= 0.8) {
            System.out.println("匹配位置: X=" + (int)matchLoc.x + " Y=" + (int)matchLoc.y);
            System.out.println("匹配置信度: " + maxVal);
            System.out.println("当前目标截图的缩放系数: " + currentScale);
            ans[0] = (int) matchLoc.x;
            ans[1] = (int) matchLoc.y;
        }

        return ans;
    }

    /**
     * 将BufferedImage转换为OpenCV Mat格式
     * @param bi 输入的BufferedImage（支持TYPE_3BYTE_BGR/TYPE_INT_RGB等格式）
     * @return 转换后的Mat对象（CV_8UC3类型）
     */
    public static Mat bufferedImageToMat(BufferedImage bi) {
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


    // 平滑移动函数（可选）
    private static void smoothMove(Robot robot, int targetX, int targetY, int durationMs) {
        try {
            java.awt.Point currentPos = MouseInfo.getPointerInfo().getLocation();
            int startX = currentPos.x;
            int startY = currentPos.y;

            int steps = durationMs / 20; // 每20ms移动一步
            for (int i = 0; i <= steps; i++) {
                double ratio = (double) i / steps;
                int x = (int) (startX + (targetX - startX) * ratio);
                int y = (int) (startY + (targetY - startY) * ratio);
                robot.mouseMove(x, y);
                robot.delay(20);
            }
        } catch (Exception e) {
            robot.mouseMove(targetX, targetY); // 出错时直接跳转
        }
    }
}
