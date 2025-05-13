package com.marks.tools.opencv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * <p>项目名称: 数独图像边框检测 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/13 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Sudoku {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * @Description:
     * 没想好具体返回值是什么, 大概率是进行边框检测后保存的图像存储路径
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/5/13 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String imgFrameDetection(String srcImgPath) {
        // 1. 图像采集
        Mat imgMat = Imgcodecs.imread(srcImgPath);

        // 2. 灰度转换
        Mat gray = new Mat();
        Imgproc.cvtColor(imgMat, gray, Imgproc.COLOR_BGR2GRAY);

        // 我想测试的时候看看图片,
        // 存在一个问题Core.hconcat() 进行Mat对象合并时, 如果通道数不同, 会报CvException,imgMat 为 CV_8UC3, gray 为CV_8UC1
//        Mat combined = new Mat();
//        Mat grayBGR = new Mat();
//        Imgproc.cvtColor(gray, grayBGR, Imgproc.COLOR_GRAY2BGR);
//        Core.hconcat(Arrays.asList(imgMat, grayBGR), combined);
//
//        HighGui.imshow("灰度化效果", gray);
//        HighGui.waitKey(0);

        // 3. 噪声去除, 高斯模糊去噪, 听着很高大上, 实际谁也不知道有什么作用
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 0);
//        HighGui.imshow("高斯去噪效果", blurred);
//        HighGui.waitKey(0);

        // 4. 二值化处理, 自适应阈值二值化
        Mat binary = new Mat();
        Imgproc.adaptiveThreshold(
                blurred,
                binary,
                255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY_INV,
                11,
                2
        );
        /*
        参数解析
        Imgproc.adaptiveThreshold(
            Mat src,                // 输入图像(必须为单通道灰度图，CV_8UC1)
            Mat dst,                // 输出二值化图像（与src同尺寸）
            double maxValue,        // 满足条件的像素赋予的最大值（通常为255）
            int adaptiveMethod,     // 自适应阈值算法：
                                    // - ADAPTIVE_THRESH_MEAN_C（邻域均值）
                                    // - ADAPTIVE_THRESH_GAUSSIAN_C（高斯加权均值）
            int thresholdType,      // 二值化类型：
                                    // - THRESH_BINARY（正向二值化）
                                    // - THRESH_BINARY_INV（反向二值化）
            int blockSize,          // 邻域大小（奇数，如3、5、21等）
            double C                // 从均值或加权均值中减去的常数（调节阈值敏感度）
        );
         */
//        HighGui.imshow("反向二值化处理后效果", binary);
//        HighGui.waitKey(0);

        // 5. 边缘检测
        Mat edges = new Mat();
        Imgproc.Canny(binary, edges, 50, 150);

//        HighGui.imshow("边缘检测效果", binary);
//        HighGui.waitKey(0);

        // 形态学操作强化边缘
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.dilate(edges, edges, kernel);

        // 6. 轮廓查找
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(
                edges,
                contours,
                hierarchy,
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE
        );

//        HighGui.imshow("边缘检测效果", hierarchy);
//        HighGui.waitKey(0);

        // 7. 绘制所有矩形边框
        Scalar redColor = new Scalar(0, 0, 255); // BGR格式的红色
        for (MatOfPoint contour : contours) {
            // 计算轮廓的边界矩形
            Rect rect = Imgproc.boundingRect(contour);

            // 绘制红色矩形边框
            Imgproc.rectangle(imgMat,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    redColor, 2);
        }

        // 5. 保存结果图像
        String outputPath = "D:\\images\\tesseract\\result\\sudoku_with_borders_"+ LocalDateTime.now().format(formatter) +".png";
        Imgcodecs.imwrite(outputPath, imgMat);
        System.out.println("结果已保存到: " + outputPath);

        return outputPath;
    }


    public String imgLineDrawing(String srcImgPath) {
        // 1. 图像采集
        Mat imgMat = Imgcodecs.imread(srcImgPath);

        // 2. 灰度转换
        Mat gray = new Mat();
        Imgproc.cvtColor(imgMat, gray, Imgproc.COLOR_BGR2GRAY);

//        HighGui.imshow("灰度化效果", gray);
//        HighGui.waitKey(0);

        // 3. 噪声去除, 高斯模糊去噪, 听着很高大上, 实际谁也不知道有什么作用
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new Size(5, 5), 0);
//        HighGui.imshow("高斯去噪效果", blurred);
//        HighGui.waitKey(0);

        // 4. 二值化处理, 自适应阈值二值化
        Mat binary = new Mat();
        Imgproc.adaptiveThreshold(
                blurred,
                binary,
                255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY_INV,
                11,
                2
        );
        /*
        参数解析
        Imgproc.adaptiveThreshold(
            Mat src,                // 输入图像(必须为单通道灰度图，CV_8UC1)
            Mat dst,                // 输出二值化图像（与src同尺寸）
            double maxValue,        // 满足条件的像素赋予的最大值（通常为255）
            int adaptiveMethod,     // 自适应阈值算法：
                                    // - ADAPTIVE_THRESH_MEAN_C（邻域均值）
                                    // - ADAPTIVE_THRESH_GAUSSIAN_C（高斯加权均值）
            int thresholdType,      // 二值化类型：
                                    // - THRESH_BINARY（正向二值化）
                                    // - THRESH_BINARY_INV（反向二值化）
            int blockSize,          // 邻域大小（奇数，如3、5、21等）
            double C                // 从均值或加权均值中减去的常数（调节阈值敏感度）
        );
         */
//        HighGui.imshow("反向二值化处理后效果", binary);
//        HighGui.waitKey(0);

        // 5. 边缘检测
        Mat edges = new Mat();
        Imgproc.Canny(binary, edges, 50, 150);

//        HighGui.imshow("边缘检测效果", binary);
//        HighGui.waitKey(0);

        // 6. 形态学操作（去除数字部分）
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));

        // 先膨胀（连接断开的网格线）
        Imgproc.dilate(binary, binary, kernel);

        // 再腐蚀（去除数字部分）
        Imgproc.erode(binary, binary, kernel);

        // 7. 霍夫直线检测
        Mat lines = new Mat();
        Imgproc.HoughLinesP(binary, lines, 1, Math.PI/180, 50, 30, 10);

        // 8. 绘制检测到的直线（红色）
        Scalar redColor = new Scalar(0, 0, 255); // BGR格式的红色
        for (int i = 0; i < lines.rows(); i++) {
            double[] val = lines.get(i, 0);
            Imgproc.line(imgMat,
                    new Point(val[0], val[1]),
                    new Point(val[2], val[3]),
                    redColor, 2, Imgproc.LINE_AA);
        }

        // 5. 保存结果图像
        String outputPath = "D:\\images\\tesseract\\result\\sudoku_with_lines_"+ LocalDateTime.now().format(formatter) +".png";
        Imgcodecs.imwrite(outputPath, imgMat);
        System.out.println("结果已保存到: " + outputPath);

        return outputPath;
    }

}
