package com.marks.tools.opencv;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/9 10:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class GraphicProcess {

    private String mountainImgPath = "D:\\images\\opencv\\mountain.jpeg";

    private String whiteBallImgPath = "D:\\images\\opencv\\white_ball.jpg";

    private boolean isHighGuiClosed = false;

    private int index = 0;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * @Description:
     * 图片平移
     * @return void
     * @author marks
     * @CreateDate: 2025/5/9 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void imageTranslate() {
        Mat curr_img = Imgcodecs.imread(mountainImgPath);

        if (curr_img.empty()) {
            System.err.printf("图片加载失败, 请检查!");
            return;
        }

        // x 方向移动 100px, y 方向移动 50px
        Mat translationMat = new Mat(2, 3, CvType.CV_32F);
        translationMat.put(0, 0, 1, 0, 100, 0, 1, 50);

        Mat translatedMat = new Mat();
        Imgproc.warpAffine(curr_img, translatedMat, translationMat, new Size(curr_img.cols(), curr_img.rows()));

        HighGui.imshow("平移效果", translatedMat);
        // 保存结果

        String outputPath = "D:\\images\\opencv\\translated\\translated_"+ LocalDateTime.now().format(formatter) + ".jpeg";
        Imgcodecs.imwrite(outputPath, translatedMat);

        System.out.println("截图保存在目录: " + outputPath);
    }

    public void ballRebound() {
        Mat whiteBall = Imgcodecs.imread(whiteBallImgPath);

        if (whiteBall.empty()) {
            System.err.printf("图片加载失败, 请检查!");
            return;
        }

        Mat blackBg = new Mat(1200, 1200, CvType.CV_8UC3, new Scalar(255, 255, 255)); // 创建1200x1200白色背景

        // 2. 初始参数设置
        int ballWidth = whiteBall.cols();
        int ballHeight = whiteBall.rows();
        int x = 0;
        int y = (blackBg.rows() - ballHeight) / 2;
        int dx = 15;  // 水平移动速度
        int dy = 10;  // 垂直移动速度（可选）

        // 3. 动画循环
        while (true) {
            // 创建当前帧的副本
            Mat frame = blackBg.clone();

            // 4. 处理透明通道合成
            if (whiteBall.channels() == 4) {
                // 分离透明通道
                Mat ballBGR = new Mat();
                Mat ballAlpha = new Mat();
                Core.split(whiteBall, new ArrayList<Mat>() {{
                    add(ballBGR);
                    add(ballAlpha);
                }});

                // 只在小球区域绘制
                Mat roi = frame.submat(new Rect(x, y, ballWidth, ballHeight));
                whiteBall.copyTo(roi, ballAlpha);
            } else {
                // 无透明通道直接绘制
                Mat roi = frame.submat(new Rect(x, y, ballWidth, ballHeight));
                whiteBall.copyTo(roi);
            }

            // 5. 显示当前帧
            HighGui.imshow("Bouncing Ball", frame);

            // 6. 更新小球位置（带边界碰撞检测）
            x += dx;
            y += dy;

            // 设置小球加速和减速, 通过q键加速, e键减速

            // 通过asdw来进行方向变化

            // 水平边界检测
            if (x <= 0) {
                x = 0;
                dx = -dx; // 反向
            } else if (x + ballWidth >= blackBg.cols()) {
                x = blackBg.cols() - ballWidth;
                dx = -dx;
            }

            // 垂直边界检测（可选）
            if (y <= 0) {
                y = 0;
                dy = -dy;
            } else if (y + ballHeight >= blackBg.rows()) {
                y = blackBg.rows() - ballHeight;
                dy = -dy;
            }

            // 将当前窗口截图
            String outputPath = "D:\\images\\opencv\\videos\\output\\output_"+ index + ".jpg";
            index++;
            Imgcodecs.imwrite(outputPath, frame);

            System.out.println("截图保存在目录: " + outputPath);

            // 7. 控制帧率并检测退出
            if (HighGui.waitKey(30) == 27) { // 按ESC退出
                isHighGuiClosed = true;
                break;
            }
            try {
                Thread.sleep(1000); // 加一点延迟, 要不然输出的图片太多了
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @Description:
     * 关闭显示的窗口
     * @return void
     * @author marks
     * @CreateDate: 2025/5/9 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void closeAllWindows() {
        if (!isHighGuiClosed) {
            HighGui.waitKey(0);
        }
        HighGui.destroyAllWindows();

//        Imgcodecs.imwrite("rotated.jpg", rotated);
//        Imgcodecs.imwrite("flipped.jpg", flipped);
    }
}
