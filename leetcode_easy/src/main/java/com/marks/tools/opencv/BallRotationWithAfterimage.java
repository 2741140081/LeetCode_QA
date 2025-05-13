package com.marks.tools.opencv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/13 10:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BallRotationWithAfterimage {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private String ballImgPath = "D:\\images\\opencv\\white_ball.jpg";

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private final int WIDTH = 900;
    private final int HEIGHT = 900;

    public void ballRotateChange() {
        // 1. 读取小球
        Mat ballMat = Imgcodecs.imread(ballImgPath);

        if (ballMat.empty()) {
            System.err.printf("图片加载失败, 请检查!");
        }

        Mat blackBg = new Mat(WIDTH, HEIGHT, CvType.CV_8UC3, new Scalar(0, 0, 0)); // 黑色背景板

        int ballRadius = 40; // 小球半径
        int ballDiameter = 80;
        int ballWidth = ballMat.cols(), ballHeight = ballMat.rows();
        Point center = new Point(450, 450); // 旋转中心

        int initX = 100, initY = (blackBg.rows() - ballHeight) / 2; // 小球的初始位置
        System.out.printf("initY = %d%n", initY);

        double radius = 350; // 旋转半径, 背景板中心点 - 小球中心点 = 450 - 100 = 350, 同一条直线上面

        double startAngle = Math.PI; // π 圆周率

        // 计算周长和最小步长
        double circumference = 2 * Math.PI * radius;
        // 最小转动角度为一个小球直径对应的角度
        double minAngleStep = (ballDiameter / circumference) * 2 * Math.PI;



        // 设置视频参数
        int fps = 30; // 30帧率, 即1s读取30张图片
//        int fps = 60; // 60帧率, 即1s读取60张图片
        int time = 60; // 60s, 即1分钟
        int totalFrames = fps * time; //  60 * 30 = 1800 张图片
        String outputVideoPath = "D:\\images\\opencv\\videos\\result\\outputVideo_"+ LocalDateTime.now().format(formatter) + ".avi";

        // 初始化 VideoWriter
        VideoWriter videoWriter = new VideoWriter(outputVideoPath,
                VideoWriter.fourcc('M', 'J', 'P', 'G'),
                fps,
                new Size(WIDTH, HEIGHT)
        );


        // 残影参数
        int trailLength = 6; // 尾焰宽度
        LinkedList<Point> trailPositions = new LinkedList<>();
        LinkedList<Double> trailWidths = new LinkedList<>();


        // 动画循环
        for (int frame = 0; frame < totalFrames; frame++) {
            Mat image = Mat.zeros(HEIGHT, WIDTH, CvType.CV_8UC3);

            // 计算当前角度(逆时针旋转)
            double angle = startAngle + (minAngleStep * frame);

            // 计算小球当前位置
            double x = center.x + radius * Math.cos(angle);
            double y = center.y + radius * Math.sin(angle);
            Point currentPos = new Point(x, y);

            // 更新尾焰位置
            trailPositions.add(0, currentPos);
            if (trailPositions.size() > trailLength) {
                trailPositions.remove(trailPositions.size() - 1);
            }

            // 更新尾焰宽度
            trailWidths.add(0, (double)ballRadius * 2);
            if (trailWidths.size() > trailLength) {
                trailWidths.remove(trailWidths.size() - 1);
            }

            // 绘制彩色尾焰
            if (trailPositions.size() > 1) {
                for (int i = 0; i < trailPositions.size() - 1; i++) {
                    // 计算颜色渐变(从红到黄)
                    double ratio = (double)i / trailLength;
                    int red = 255;
                    int green = (int)(255 * ratio);
                    int blue = 0;

                    // 计算宽度渐变
                    double width = ballRadius * 2 * (1 - 0.9 * ratio);

                    // 确保宽度不小于1
                    if (width < 1) width = 1;

                    // 绘制尾焰线段
                    Imgproc.line(image,
                            trailPositions.get(i),
                            trailPositions.get(i + 1),
                            new Scalar(blue, green, red),
                            (int)width);
                }
            }

            // 绘制当前小球(白色)
            Imgproc.circle(image, currentPos, ballRadius, new Scalar(255, 255, 255), -1);

            videoWriter.write(image);
        }


        videoWriter.release();
    }
}
