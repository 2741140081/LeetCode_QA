package com.marks.tools.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.xfeatures2d.SURF;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/23 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SurfFeatureExtractor {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public Mat readImage(String imagePath) {
        Mat mat = Imgcodecs.imread(imagePath);
        return mat;
    }

    public MatOfKeyPoint extract(Mat image) {
        // 创建SURF检测器(设置hessian阈值400)
        SURF surf = SURF.create(400);

        // 检测关键点
        MatOfKeyPoint keyPoints = new MatOfKeyPoint();
        surf.detect(image, keyPoints);

        // 计算描述子
        Mat descriptors = new Mat();
        surf.compute(image, keyPoints, descriptors);

        return keyPoints;
    }

    public Mat captureDesktopRegion(int x, int y, int width, int height) {
        try {
            // 创建屏幕截图对象
            Robot robot = new Robot();
            Rectangle captureRect = new Rectangle(x, y, width, height);
            BufferedImage screenCapture = robot.createScreenCapture(captureRect);

            // 转换BufferedImage为OpenCV Mat格式
            BufferedImage convertedImage = new BufferedImage(
                    width, height, BufferedImage.TYPE_3BYTE_BGR);
            convertedImage.getGraphics().drawImage(screenCapture, 0, 0, null);

            byte[] pixels = ((DataBufferByte) convertedImage.getRaster()
                    .getDataBuffer()).getData();
            Mat mat = new Mat(height, width, CvType.CV_8UC3);
            mat.put(0, 0, pixels);

            return mat;
        } catch (AWTException e) {
            e.printStackTrace();
            return new Mat();
        }
    }
}
