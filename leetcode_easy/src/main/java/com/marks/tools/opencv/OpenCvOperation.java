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
    private  String resultFilePath = "D:\\images\\opencv\\result\\";


    public void simpleMat() {
        //创建简单的矩阵
        Mat m0 = Mat.zeros( 2, 3, CvType.CV_8UC1 );
        Mat m1 = Mat.ones( 3, 2, CvType.CV_8UC1 );
        Mat m2  = Mat.eye( 3, 3, CvType.CV_8UC1 );
        Mat m3  = Mat.ones( 2, 3, CvType.CV_8UC3 );

        //在控制台输出矩阵m0数据
        System.out.println("m0:");
        System.out.println(m0.dump());
        System.out.println();

        //在控制台输出矩阵m1数据
        System.out.println("m1:");
        System.out.println(m1.dump());
        System.out.println();

        //在控制台输出矩阵m2数据
        System.out.println("m2:");
        System.out.println(m2.dump());
        System.out.println();

        //在控制台输出矩阵m3数据
        System.out.println("m3:");
        System.out.println(m3.dump());
        System.out.println();
    }


    /**
     * @Description: [功能描述]
     *
     * @return void
     * @author marks
     * @CreateDate: 2025/5/14 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void matBatch() {
        // 创建矩阵mat
        Mat mat = new Mat(2, 2, CvType.CV_32SC3);

        // 设置矩阵数据
        int[] data = {1,2,3,4,5,6,7,8,9,128,200,255};
        mat.put(0, 0, data);

        // 查看mat矩阵的数据
        System.out.println(mat.dump());

        // 转换矩阵类型
        Mat m = new Mat();
        mat.convertTo(m, CvType.CV_8UC3);

        //获取矩阵所有数据
        int[] i= new int[12];  //创建数组
        mat.get(0,0,i);  //获取所有数据

        //查看m的矩阵头
        System.out.println(m);
        System.out.println();

        //查看矩阵数据
        System.out.println(m.dump());
        System.out.println();

        //查看数组i的数据
        for (int n=0; n<12; n++)
            System.out.print(i[n]+",");

    }


    public void drawChessBoard() {
        // 创建一个800 * 800, 也就是说这是一个BGR 3通道, 整体棋盘颜色为淡黄色
        Mat img = new Mat(800, 800, CvType.CV_8UC3, new Scalar(220, 248, 255));

        int size = 40; // 网格宽度 40 px
        Scalar black = new Scalar(0, 0, 0); // 黑色线
        /*
        棋盘由横竖19条线构成, 每条线宽度为 1 px, 总计宽度 19 px
        每行每列都是18个宽度为 40 px 的网格, 共计 720 px
        整体大小是 800 * 800 px, 除去棋盘的区域剩余大小为 61 px
         */
        //画围棋棋盘格子
        for (int i=0; i<19; i++) {
            Imgproc.line(img, new Point(40, 40 + size*i), new Point(760, 40 + size*i), black, 1);
            Imgproc.line(img, new Point(40 + size*i, 40), new Point(40 + size*i, 760), black, 1);
        }

        //画棋盘的外框
        Imgproc.rectangle(img, new Point(30, 30), new Point(770, 770), black, 2);

        //画星位
        for (int i=3; i<19; i=i+6) {
            for (int j=3; j<19; j=j+6) {
                Imgproc.circle(img, new Point(40+ size*i, 40 + size*j), 4, black, 1);
            }
        }

        //在屏幕上显示画好的棋盘
        HighGui.imshow("img", img);
        HighGui.waitKey(0);
        System.exit(0);
    }

    /**
     * @Description: [功能描述]
     * 绘制弯曲的线
     * @return void
     * @author marks
     * @CreateDate: 2025/5/14 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void drawCurve() {
        // 1. 创建画布 (淡黄色背景)
        Mat img = new Mat(800, 800, CvType.CV_8UC3, new Scalar(220, 248, 255));

        // 2. 定义正弦曲线参数
        double amplitude = 100;  // 振幅
        double frequency = 0.02; // 频率
        int thickness = 2;       // 线宽

        // 3. 绘制正弦曲线
        for (int x = 0; x < img.cols(); x++) {
            int y = (int) (img.rows()/2 + amplitude * Math.sin(frequency * x));
            Point pt = new Point(x, y);

            // 使用line连接相邻点（更高效）
            if (x > 0) {
                Point prevPt = new Point(x-1,
                        (int)(img.rows()/2 + amplitude * Math.sin(frequency * (x-1))));
                Imgproc.line(img, prevPt, pt, new Scalar(0, 0, 0), thickness, Imgproc.LINE_AA); // 启用抗锯齿效果: Imgproc.LINE_AA
            }
        }

        // 4. 显示结果
        HighGui.imshow("Math Curve", img);
        HighGui.waitKey(0);
    }

    public void drawRainbow() {
        // 1. 创建画布 (白色背景)
        Mat img = new Mat(300, 300, CvType.CV_8UC3, new Scalar(255, 255, 255));

        // 2. 彩虹参数设置
        int rainbowWidth = 70;   // 彩虹带总高度
        int rainbowLength = 100; // 彩虹带长度
        int colorWidth = 10;     // 每种颜色高度
        int centerX = img.cols() / 2;
        int centerY = img.rows() / 2;

        // 3. 定义彩虹颜色 (BGR格式)
        Scalar[] rainbowColors = {
                new Scalar(0, 0, 255),     // 红
                new Scalar(0, 165, 255),   // 橙
                new Scalar(0, 255, 255),   // 黄
                new Scalar(0, 255, 0),     // 绿
                new Scalar(255, 0, 0),     // 蓝
                new Scalar(130, 0, 75),    // 靛
                new Scalar(238, 130, 238)  // 紫
        };

        // 3. 绘制弯曲的彩虹带
        int radius = 270; // 半圆半径

        for (int i = 0; i < 7; i++) {
            int currentRadius = radius - i * colorWidth;

            for (int x = 0; x < img.cols(); x++) {
                // 计算半圆上的y坐标 (上半圆)
                if (Math.abs(x - centerX) <= currentRadius) {
                    int yOffset = (int) Math.sqrt(currentRadius * currentRadius -
                            (x - centerX) * (x - centerX));
                    int y = img.rows() - yOffset - 1; // 从底部开始绘制

                    // 绘制当前颜色的线段
                    if (y >= 0 && y < img.rows()) {
                        Imgproc.line(
                                img,
                                new Point(x, y),
                                new Point(x, Math.min(y + colorWidth - 1, img.rows() - 1)),
                                rainbowColors[i],
                                1,
                                Imgproc.LINE_AA
                        );
                    }
                }
            }
        }

        Mat scaledImg = new Mat();
        Imgproc.resize(img, scaledImg,
                new Size(img.cols() * 2.0, img.rows() * 2.0),
                0, 0, Imgproc.INTER_LINEAR);

        // 5. 显示和保存结果
        HighGui.imshow("Rainbow", scaledImg);
        HighGui.waitKey(0);
        System.exit(0);
    }

    public void addPictureByOpenCv(String firstFilePath, String secondFilePath) {
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
        System.exit(0);
    }

    /**
     * @Description:
     * 霍夫线检测
     * @param imgPath
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/5/14 9:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String findHoughLines(String imgPath) {

        // 读取图片
        Mat src = Imgcodecs.imread(imgPath);

        // 转为灰度图
        Mat gary = new Mat();
        Imgproc.cvtColor(src, gary, Imgproc.COLOR_BGR2GRAY);

        HighGui.imshow("灰度转换效果图", gary);
        HighGui.waitKey(0); // 按任意键关闭

        // 边缘检测 Canny算法
        Mat edges = new Mat();
        Mat dst = new Mat();  //用于画线

        Imgproc.Canny(gary, edges, 50, 150);
        Imgproc.cvtColor(edges, dst, Imgproc.COLOR_GRAY2BGR);

        HighGui.imshow("边缘检测转换效果图", edges);
        HighGui.waitKey(0); // 按任意键关闭

        // 霍夫线检测
        Mat lines = new Mat();

        Imgproc.HoughLines(edges, lines, 1, Math.PI/180, 150);

        // 好像最外侧的边框线没有检测到

//        for (int i = 0; i < lines.rows(); i++) {
//            double rho = lines.get(i, 0)[0]; //极坐标中的ρ
//            double theta = lines.get(i, 0)[1];  //极坐标中的θ
//            double cos = Math.cos(theta);
//            double sin = Math.sin(theta);
//
//            // 将极坐标转为直角坐标系坐标
//            double x0 = rho * Math.cos(theta);
//            double y0 = rho * Math.sin(theta);
//
//            double len = 800; //画的直线的长度
//            Point pt1 = new Point(Math.round(x0 + len*(-sin)), Math.round(y0 + len*(cos)));
//            Point pt2 = new Point(Math.round(x0 - len*(-sin)), Math.round(y0 - len*(cos)));
//            Imgproc.line(dst, pt1, pt2, new Scalar(0, 0, 255), 3);
//        }

        // 概率霍夫直线检测
        Mat linesP = new Mat();
        Imgproc.HoughLinesP(edges, linesP, 1, Math.PI/180, 50, 10, 100); // 参数：最小线段长度、最大线段间隔

        Scalar redColor = new Scalar(0, 0, 255); // BGR格式的红色
        for (int i = 0; i < linesP.rows(); i++) {
            double[] val = linesP.get(i, 0);
            Imgproc.line(src,
                    new Point(val[0], val[1]),
                    new Point(val[2], val[3]),
                    redColor, 2, Imgproc.LINE_AA);
        }
        /*
            选择建议‌
            ‌优先 HoughLinesP()‌：大多数实际场景（如物体检测）更关注线段位置而非全局直线。
            ‌慎用 HoughLines()‌：仅在需要极坐标参数或处理高对比度连续直线时使用
         */
        HighGui.imshow("Detected Lines", src);
        HighGui.waitKey(0);
        System.exit(0);

        return "";
    }
    

    /**
     * @Description:
     * 霍夫圆检测
     * @param srcPath
     * @return void
     * @author marks
     * @CreateDate: 2025/5/14 16:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void houghCircle(String srcPath) {
        //读取图像并在屏幕上显示
        Mat src = Imgcodecs.imread(srcPath);
        HighGui.imshow("Chess", src);
        HighGui.waitKey(0);

        //预处理
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gray, gray, new Size(9,9), 2);

        //霍夫圆检测
        Mat circles = new Mat();
        Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, 1, 10, 100, 30, 5, 30);

        //将检测出的圆画出
        for (int n = 0; n < circles.cols(); n++) {
            double[] c = circles.get(0, n);
            Point center = new Point(Math.round(c[0]), Math.round(c[1])); //圆心
            int radius = (int) Math.round(c[2]);  //半径
            Imgproc.circle(src, center, radius, new Scalar(0,0,255), 5);
        }

        //在屏幕上显示检测结果
        HighGui.imshow("Circles", src);
        HighGui.waitKey();
        System.exit(0);
    }


    public void getCartoon(String imgPath) {
        // read img path
        Mat src = Imgcodecs.imread(imgPath);

        // change the src to gray mat
        Mat gary = new Mat();
        Imgproc.cvtColor(src, gary, Imgproc.COLOR_BGR2GRAY);

        HighGui.namedWindow("Gary Img", HighGui.WINDOW_NORMAL);
        HighGui.resizeWindow("Gary Img", 640,  360);

        // imshow() this method change the gary mat size to 640 * 360, and this change is forever
        HighGui.imshow("Gary Img", gary);
        HighGui.waitKey(0); // Wait for any key pressed by the keyboard


        // platform GaussianBlur
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gary, blurred, new Size(5, 5), 0); // 这里的Size大小只能是奇数, 3*3, 5*5

        HighGui.imshow("GaussianBlur Img", blurred);
        HighGui.waitKey(0);

        // change gary mat to binary mat
        Mat binary = new Mat();
        Imgproc.adaptiveThreshold(blurred, binary, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY, 11, 2);


    }

}
