package com.marks.tools.opencv;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/28 9:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class OpenCvOperation {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private static String resultFilePath = "D:\\images\\opencv\\result\\";

    public static void main(String[] args) {
        String starSkyFilePath = "D:\\images\\opencv\\star_sky.jpg";
//        String landscapeFilePath = "D:\\images\\opencv\\landscape.jpg";
        String cartoonFilePath = "D:\\images\\opencv\\cartoon.jpg";

        addPictureByOpenCv(starSkyFilePath, cartoonFilePath);
        System.out.println("=====> test main case");
        System.exit(0);
    }

    private static void addPictureByOpenCv(String firstFilePath, String secondFilePath) {
        // 读取两张图片
        Mat firstMat = Imgcodecs.imread(firstFilePath);
        Mat secondMat = Imgcodecs.imread(secondFilePath);

        if (firstMat.empty() || secondMat.empty()) {
            System.out.println("picture is empty, please check!");
            System.exit(-1);
        }

        if ((firstMat.width() != secondMat.width()) || (firstMat.height() != secondMat.height())) {
            System.out.println("two picture don't have same size, please check!");
            System.out.println("first picture width is " + firstMat.width() + ", height is " + firstMat.height());
            System.out.println("second picture width is " + secondMat.width() + ", height is " + secondMat.height());
            System.exit(-1);
        }

        Mat resultMat = new Mat();

        // 设置权重值, 两种图片权重值相加和为1.0
        double alpha = 0.2;
        double beta = 1.0 - alpha;

        double gamma = -100; // 通常为0, 但是也有不通常的情况, 具体参数功能待测试

        // 将合并后的图片保存为 resultMat
        Core.addWeighted(firstMat, alpha, secondMat, beta, gamma, resultMat);

        // 创建掩膜
        Mat mask = Mat.zeros(secondMat.size(), CvType.CV_8UC1); // 全黑掩膜
        Point center = new Point(secondMat.cols() / 2, secondMat.rows() / 2);
        System.out.println("圆形中心点圆心坐标(" + secondMat.cols() / 2 + ", " + secondMat.rows() / 2 + ")" );
        int radius = 250; // 圆形半径

        Imgproc.circle(mask, center, radius, new Scalar(255), -1);

        // 通过掩膜还原第二张图片的圆形区域
        Mat roiOriginal = new Mat();
        secondMat.copyTo(roiOriginal, mask);
        roiOriginal.copyTo(resultMat, mask);

        // 保存图片到本地
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String outputPath = resultFilePath + LocalDateTime.now().format(formatter) + ".jpg";
        Imgcodecs.imwrite(outputPath, resultMat);

        // 显示混合后的图片
        HighGui.imshow("Under The Sky", resultMat);
        while (true) {
            int key = HighGui.waitKey(0);
            System.out.println("按键码: " + key);
            // 按下键盘ESC退出
            if (key == 27) {
                HighGui.destroyAllWindows();
                System.out.println("关闭HighGui 打开的窗口");
                break;
            }
        }
        System.out.println("=====> test case");

    }
}
