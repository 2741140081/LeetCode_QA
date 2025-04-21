package com.marks.tools;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

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
        String templatePath = "/images/Pulse_Sign.png";
        Mat template = Imgcodecs.imread(templatePath, Imgcodecs.IMREAD_COLOR);

        // 截取当前屏幕（考虑200%缩放）
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRect = new Rectangle(0, 0,
                (int)(screenSize.width/2),  // 3840/2=1920
                (int)(screenSize.height/2)); // 2160/2=1080
        BufferedImage screenshot = robot.createScreenCapture(screenRect);

        // 转换为OpenCV格式
        Mat screenMat = bufferedImageToMat(screenshot);

        // 模板匹配
        Mat result = new Mat();
        Imgproc.matchTemplate(screenMat, template, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);

        // 计算图标中心点（需转换回实际屏幕坐标）
        Point iconCenter = new Point(
                (mmr.maxLoc.x + template.cols()/2) * 2,  // 乘以2补偿200%缩放
                (mmr.maxLoc.y + template.rows()/2) * 2
        );

        // 双击启动应用
        robot.mouseMove((int)iconCenter.x, (int)iconCenter.y);
        robot.delay(5000); // 让我更好看看效果

        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(100);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
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
}
