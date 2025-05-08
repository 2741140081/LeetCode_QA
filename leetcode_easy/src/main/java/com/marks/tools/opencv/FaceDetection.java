package com.marks.tools.opencv;


import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/28 16:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

public class FaceDetection {

    static {
        // 加载OpenCV本地库（路径需根据实际调整）
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static String resultFilePath = "D:\\images\\opencv\\result\\";
    // Haar级联分类器模型路径（需自行下载）
    private static final String HAAR_MODEL = "D:\\opencvProject\\opencv\\data\\haarcascades\\haarcascade_frontalface_default.xml";

    public static void main(String[] args) {
        // 方式1：检测图片中的人脸
        String inputPath = "D:\\images\\opencv\\lena.png";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String outputPath = resultFilePath + LocalDateTime.now().format(formatter) + "_lena.png";
        detectFaceInImage(inputPath, outputPath);

        // 方式2：实时摄像头检测
//        detectFaceFromCamera();
    }

    /**
     * 检测图片中的人脸并保存结果
     */
    public static void detectFaceInImage(String inputPath, String outputPath) {
        // 1. 加载图片
        Mat image = Imgcodecs.imread(inputPath);
        if (image.empty()) {
            System.err.println("Error: Could not load image!");
            return;
        }

        // 2. 人脸检测
        MatOfRect faces = detectFaces(image);

        // 3. 绘制绿色矩形框
        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(
                    image,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0),  // 绿色 (BGR格式)
                    2  // 线宽
            );
        }

        // 4. 保存结果
        Imgcodecs.imwrite(outputPath, image);
        System.out.println("Face detection saved to: " + outputPath);
    }

    /**
     * 从摄像头实时检测人脸
     */
    public static void detectFaceFromCamera() {
        VideoCapture camera = new VideoCapture(0); // 0=默认摄像头
        if (!camera.isOpened()) {
            System.err.println("Error: Camera not available!");
            return;
        }

        Mat frame = new Mat();
        while (true) {
            camera.read(frame);
            if (frame.empty()) break;

            // 检测人脸并绘制矩形
            MatOfRect faces = detectFaces(frame);
            for (Rect rect : faces.toArray()) {
                Imgproc.rectangle(
                        frame,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 0),
                        2
                );
            }

            // 显示实时画面
            HighGui.imshow("Real-time Face Detection", frame);
            if (HighGui.waitKey(30) == 27) break; // ESC键退出
        }

        camera.release();
        HighGui.destroyAllWindows();
    }

    /**
     * 核心检测方法
     */
    private static MatOfRect detectFaces(Mat image) {
        // 1. 转为灰度图（提升检测效率）
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // 2. 加载Haar级联分类器
        CascadeClassifier faceDetector = new CascadeClassifier(HAAR_MODEL);
        if (faceDetector.empty()) {
            System.err.println("Error: Could not load Haar model!");
            return new MatOfRect();
        }

        // 3. 检测人脸
        MatOfRect faces = new MatOfRect();
        faceDetector.detectMultiScale(
                grayImage,
                faces,
                1.1,  // 缩放因子
                3,    // 最小邻居数（过滤误检）
                0,    // 标志（默认0）
                new Size(30, 30),  // 最小人脸尺寸
                new Size()          // 最大人脸尺寸（空=不限制）
        );

        return faces;
    }
}

