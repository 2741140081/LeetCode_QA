package com.marks.tools.kkplatform;


import com.marks.utils.LogUtil;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core.MinMaxLocResult;

import java.awt.*;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SpecialHandel </p>
 * <p>描述: 特殊处理九宫格中左上角的标识 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 14:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */


/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：SpecialHandel </p>
 * <p>描述：特殊处理九宫格中左上角的标识，通过模板匹配识别字母'A'并返回坐标</p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 14:36
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SpecialHandel {
    private static final String TEMPLATE_DIR = "D:/images/automation/templates";
    private static final String OUTPUT_DIR = "D:/images/automation/results";
    private static final double MATCH_THRESHOLD = 0.75;

    private static final double MIN_SCALE = 0.3;
    private static final double MAX_SCALE = 2.0;
    private static final double BEST_SCALE = 0.6667;

    private static final int RECT_THICKNESS = 2;
    private static final Scalar RED_COLOR = new Scalar(0, 0, 255);
    private static final Scalar GREEN_COLOR = new Scalar(0, 255, 0);

    // 操作延迟（毫秒）
    private static final int DELAY_SHORT = 100;

    public Robot robot;
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public SpecialHandel() throws Exception {
        this.robot = new Robot();
    }

    /**
     * BufferedImage 转换为 Mat
     */
    private Mat bufferedImageToMat(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // 确保图像类型为 BGR
        if (image.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            BufferedImage converted = new BufferedImage(
                    width, height, BufferedImage.TYPE_3BYTE_BGR
            );
            Graphics2D g = converted.createGraphics();
            try {
                g.drawImage(image, 0, 0, null);
            } finally {
                g.dispose();
            }
            image = converted;
        }

        // 获取像素数据
        byte[] pixels;
        pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }







    public Point findImage(String templateName) {
        try {
            String templatePath = Paths.get(TEMPLATE_DIR, templateName + ".png").toString();
            File templateFile = new File(templatePath);

            if (!templateFile.exists()) {
                LogUtil.info("模板文件不存在：" + templatePath);
                return null;
            }

            Mat screenMat = captureScreenToMat();
            Mat template = Imgcodecs.imread(templatePath);

            if (template.empty()) {
                LogUtil.info("无法读取模板图片：" + templatePath);
                return null;
            }

            List<ScaleMatchResult> result = recognizeAndMarkElement(screenMat, template, templateName);
            // 提取左上角的匹配
            int x = Integer.MAX_VALUE, y = Integer.MAX_VALUE;
            // 构建一个小根堆, 用于存储最佳的3个匹配度最高的结果
            PriorityQueue<ScaleMatchResult> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a.bestMatchValue)); // 应该用大根堆?
            for (ScaleMatchResult match : result) {
                if (pq.size() < 3) {
                    pq.add(match);
                } else {
                    if (match.bestMatchValue > pq.peek().bestMatchValue) {
                        pq.poll();
                        pq.add(match);
                    }
                }
            }
            ScaleMatchResult leftTopMatch = null;
            while (!pq.isEmpty()) {
                ScaleMatchResult match = pq.poll();
                if (match.bestLoc.x <= x && match.bestLoc.y <= y) {
                    x = (int) match.bestLoc.x;
                    y = (int) match.bestLoc.y;
                    leftTopMatch = match;
                }
                // 绘制矩阵
                Imgproc.rectangle(screenMat, match.bestRect, RED_COLOR, RECT_THICKNESS);
            }
            // 绘制矩阵
            Imgproc.rectangle(screenMat, leftTopMatch.bestRect, GREEN_COLOR, RECT_THICKNESS);
            // 保存屏幕图片
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "result_" + templateName.replace(".png", "") + "_" + timestamp + ".png";
            saveResult(screenMat, fileName);
            screenMat.release();
            template.release();

            return new Point(x, y);

        } catch (Exception e) {
            LogUtil.info("查找图片失败：" + templateName);
            e.printStackTrace();
            return null;
        }
    }

    private List<ScaleMatchResult> recognizeAndMarkElement(Mat screenMat, Mat template, String templatePath) {
        LogUtil.info("\n=== 查找元素：" + templatePath + " ===");

        String templateName = Paths.get(templatePath).getFileName().toString();
        System.out.println("\n=== 识别元素：" + templateName + " ===");

        Mat screenGray = new Mat();
        Mat templateGray = new Mat();
        Imgproc.cvtColor(screenMat, screenGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(template, templateGray, Imgproc.COLOR_BGR2GRAY);

        // 首先尝试最佳缩放比例
        LogUtil.info("尝试使用最佳缩放比例：" + BEST_SCALE);
        List<ScaleMatchResult> result = matchWithScaleGray(screenGray, templateGray, BEST_SCALE);
        screenGray.release();
        templateGray.release();
        return result;
    }

    private List<ScaleMatchResult> matchWithScaleGray(Mat screenGray, Mat templateGray, double scale) {
        Mat scaledTemplate = new Mat();
        Size newSize = new Size(templateGray.cols() * scale, templateGray.rows() * scale);
        Imgproc.resize(templateGray, scaledTemplate, newSize);

        System.out.println("模板尺寸：" + templateGray.cols() + "x" + templateGray.rows() +
                " -> 缩放后：" + scaledTemplate.cols() + "x" + scaledTemplate.rows());

        Mat result = new Mat();
        Imgproc.matchTemplate(screenGray, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        // 遍历result, 使用List存储匹配结果
        List<ScaleMatchResult> ans = new ArrayList<>();
        for (int i = 0; i < result.rows(); i++) {
            for (int j = 0; j < result.cols(); j++) {
                double matchValue = result.get(i, j)[0];
                if (matchValue >= MATCH_THRESHOLD) {
                    org.opencv.core.Point bestLoc = new org.opencv.core.Point(j, i);
                    Rect bestRect = new Rect(bestLoc, new org.opencv.core.Point(
                            bestLoc.x + scaledTemplate.cols(),
                            bestLoc.y + scaledTemplate.rows()
                    ));
                    ScaleMatchResult matchResult = new ScaleMatchResult(scale, matchValue, bestLoc);
                    matchResult.bestRect = bestRect;
                    ans.add(matchResult);
                }
            }
        }

        result.release();
        scaledTemplate.release();

        return ans;
    }


    /**
     * 捕获屏幕并转换为 Mat
     */
    private Mat captureScreenToMat() {
        Rectangle screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenCapture = robot.createScreenCapture(screenBounds);
        return bufferedImageToMat(screenCapture);
    }

    /**
     * 保存结果图片
     */
    private void saveResult(Mat resultImage, String fileName) {
        String outputPath = Paths.get(OUTPUT_DIR, fileName).toString();
        Imgcodecs.imwrite(outputPath, resultImage);
        LogUtil.info("\n结果已保存到：" + outputPath);
    }

    /**
     * 内部类：缩放匹配结果
     */
    private static class ScaleMatchResult {
        double bestScale;
        double bestMatchValue;
        org.opencv.core.Point bestLoc;
        Rect bestRect;

        ScaleMatchResult(double bestScale, double bestMatchValue, org.opencv.core.Point bestLoc) {
            this.bestScale = bestScale;
            this.bestMatchValue = bestMatchValue;
            this.bestLoc = bestLoc;
        }
    }
}

