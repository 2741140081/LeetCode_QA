package com.marks.tools.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述:
 * 后续进阶:
 * 1. 将整体项目改成spring boot项目
 * 2. 可以实现网络的在线观看
 * 3. 优化视频的传输(如果有可能性的话)
 * 4. 增加音频方面
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/7 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CameraRecorder {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        VideoCapture camera = new VideoCapture(0); // 系统默认摄像头
        if (!camera.isOpened()) {
            System.out.println("系统摄像头打开失败, 请检查!");
            System.exit(-1);
        }

        // 设置视频参数
        int frameWidth = 640, frameHeight = 480;
        double fps = 30.0;

        // 创建视频写入器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        String outputPath = "D:\\images\\opencv\\videos\\output\\output_" + LocalDateTime.now().format(formatter) + ".avi";
        VideoWriter writer = new VideoWriter(
                outputPath,
                VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps,
                new Size(frameWidth, frameHeight)
        );

        String winName = "real time image";
        Mat frame = new Mat();
        while (true) {
            camera.read(frame);
            if (frame.empty()) break;

            // 写入视频文件
            writer.write(frame);

            // 实时预览（可选）
            HighGui.imshow(winName, frame);
            if (HighGui.waitKey(30) >= 0) break; // 按任意键停止
        }

        System.out.println("=====> exit start");
        // 释放资源
//        camera.release();
//        System.out.println("=====> exit 1");
//        writer.release();
//        System.out.println("=====> exit 2");
//        HighGui.destroyAllWindows();

        try {
            // 优先释放依赖摄像头的资源
            if (writer != null) {
                writer.release();  // 先停止写入
                writer = null;     // 显式置空防止重复释放
                System.out.println("=====> exit 1");
            }

            // 然后释放摄像头
            if (camera != null && camera.isOpened()) {
                camera.release();  // 断开硬件连接
                camera = null;
                System.out.println("=====> exit 2");
            }

            // 最后关闭UI
            if (winName != null) {
                HighGui.destroyWindow(winName);
                System.out.println("=====> exit 3");
//                HighGui.waitKey(1);  // 清理事件队列
            }
        } catch (Exception e) {
            System.err.println("资源释放异常: " + e.getMessage());
        }
//        System.exit(0);
        // 打印线程状态（调试用）
        Thread.getAllStackTraces().keySet().forEach(t ->
                System.out.println("存活线程: " + t.getName())
        );
        System.exit(0);
    }
}
