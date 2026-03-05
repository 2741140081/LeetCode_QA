package com.marks.tools.kkplatform;

import com.marks.utils.LogUtil;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageRecognitionAutomation </p>
 * <p>描述:
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 16:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ImageRecognitionAutomation {
    public Robot robot;
    private Dimension screenSize;

    // 模板图片路径
    private static final String TEMPLATE_DIR = "D:/images/automation/templates";
    private static final String OUTPUT_DIR = "D:/images/automation/results";
    private static final double MATCH_THRESHOLD = 0.75;

    private static final double MIN_SCALE = 0.3;
    private static final double MAX_SCALE = 2.0;
    private static final double BEST_SCALE = 0.6667;
    private static final int BINARY_SEARCH_ITERATIONS = 10;

    // 操作延迟（毫秒）
    private static final int DELAY_SHORT = 100;

    public ImageRecognitionAutomation() throws AWTException {
        robot = new Robot();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        robot.setAutoDelay(DELAY_SHORT);
    }

    /**
     * 在屏幕上查找匹配的图片（使用最佳缩放比例 + 二分法）
     * @param templateName 模板图片名称（不含扩展名）
     * @return 匹配到的坐标，未找到返回 null
     */
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

            Point result = findImageWithScale(screenMat, template, templateName);

            screenMat.release();
            template.release();

            return result;

        } catch (Exception e) {
            LogUtil.info("查找图片失败：" + templateName);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用最佳缩放比例 + 二分法查找图片
     */
    private Point findImageWithScale(Mat screenMat, Mat template, String templateName) {
        LogUtil.info("\n=== 查找元素：" + templateName + " ===");

        ScaleMatchResult result;

        // 首先尝试最佳缩放比例
        LogUtil.info("尝试使用最佳缩放比例：" + BEST_SCALE);
        result = matchWithScale(screenMat, template, BEST_SCALE);

        if (result.bestMatchValue >= MATCH_THRESHOLD) {
            LogUtil.info("✓ 使用最佳缩放比例匹配成功！");
            LogUtil.info("  匹配度：" + String.format("%.4f", result.bestMatchValue));
            LogUtil.info("  位置：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");
            return convertToJavaPoint(result.bestLoc, template.cols(), template.rows());
        }

        LogUtil.info("最佳缩放比例匹配失败（匹配度：" + String.format("%.4f", result.bestMatchValue) +
                "），开始二分法查找...");

        // 使用二分法查找最佳缩放比例
        result = findBestScaleBinarySearch(screenMat, template);

        if (result.bestMatchValue >= MATCH_THRESHOLD) {
            LogUtil.info("✓ 二分法查找成功！");
            LogUtil.info("  最佳缩放比例：" + String.format("%.3f", result.bestScale));
            LogUtil.info("  匹配度：" + String.format("%.4f", result.bestMatchValue));
            LogUtil.info("  位置：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");
            return convertToJavaPoint(result.bestLoc, result.bestRect.width, result.bestRect.height);
        }

        // 二分法也未找到，保存截图并报错
        handleNotFound(screenMat, templateName, result);
        return null;
    }

    /**
     * 处理未找到匹配的情况，保存截图
     */
    private void handleNotFound(Mat screenMat, String templateName, ScaleMatchResult result) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String errorFileName = "error_" + templateName.replace(".png", "") + "_" + timestamp + ".png";

        try {
            saveResult(screenMat, errorFileName);
        } catch (Exception e) {
            LogUtil.info("保存错误截图失败：" + e.getMessage());
        }

        String errorMessage = String.format(
                "✗ 未找到匹配元素：%s\n" +
                        "  最佳匹配度：%.4f\n" +
                        "  阈值：%.2f\n" +
                        "  错误截图已保存：%s",
                templateName,
                result.bestMatchValue,
                MATCH_THRESHOLD,
                Paths.get(OUTPUT_DIR, errorFileName)
        );

        LogUtil.info(errorMessage);
        fail(errorMessage);
    }

    /**
     * 将 OpenCV 的 Point 转换为 Java 的 Point
     */
    private Point convertToJavaPoint(org.opencv.core.Point cvPoint, int width, int height) {
        return new Point((int) cvPoint.x + width / 2, (int) cvPoint.y + height / 2);
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
        try {
            pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        } catch (ClassCastException e) {
            // 回退到原始方法
            return bufferedImageToMatFallback(image, width, height);
        }

        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }

    // 回退方法（原始实现）
    private Mat bufferedImageToMatFallback(BufferedImage image, int width, int height) {
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        byte[] data = new byte[width * height * 3];
        for (int i = 0; i < width * height; i++) {
            int argb = pixels[i];
            data[i * 3] = (byte) ((argb >> 16) & 0xFF);
            data[i * 3 + 1] = (byte) ((argb >> 8) & 0xFF);
            data[i * 3 + 2] = (byte) (argb & 0xFF);
        }

        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    /**
     * 二分查找最佳缩放比例
     */
    private ScaleMatchResult findBestScaleBinarySearch(Mat screenMat, Mat originalTemplate) {
        double left = MIN_SCALE;
        double right = MAX_SCALE;

        double globalBestScale = 1.0;
        double globalBestMatchValue = 0.0;
        org.opencv.core.Point globalBestLoc = new org.opencv.core.Point(0, 0);
        Rect globalBestRect = null;

        LogUtil.info("二分查找范围：[" + left + ", " + right + "]，迭代次数：" + BINARY_SEARCH_ITERATIONS);

        for (int iteration = 0; iteration < BINARY_SEARCH_ITERATIONS; iteration++) {
            double mid1 = left + (right - left) / 3;
            double mid2 = right - (right - left) / 3;

            ScaleMatchResult result1 = matchWithScale(screenMat, originalTemplate, mid1);
            ScaleMatchResult result2 = matchWithScale(screenMat, originalTemplate, mid2);

            LogUtil.info(String.format("迭代 %d: scale=%.3f(%.4f), scale=%.3f(%.4f)",
                    iteration + 1, mid1, result1.bestMatchValue, mid2, result2.bestMatchValue));

            if (result1.bestMatchValue > globalBestMatchValue) {
                globalBestMatchValue = result1.bestMatchValue;
                globalBestScale = mid1;
                globalBestLoc = result1.bestLoc;
                globalBestRect = result1.bestRect;
            }
            if (result2.bestMatchValue > globalBestMatchValue) {
                globalBestMatchValue = result2.bestMatchValue;
                globalBestScale = mid2;
                globalBestLoc = result2.bestLoc;
                globalBestRect = result2.bestRect;
            }

            if (result1.bestMatchValue < result2.bestMatchValue) {
                left = mid1;
            } else {
                right = mid2;
            }
        }

        LogUtil.info("二分查找完成，最佳缩放比例：" + String.format("%.3f", globalBestScale) +
                "，匹配度：" + String.format("%.4f", globalBestMatchValue));

        return new ScaleMatchResult(globalBestScale, globalBestMatchValue, globalBestLoc, globalBestRect);
    }

    /**
     * 使用指定缩放比例进行模板匹配
     */
    private ScaleMatchResult matchWithScale(Mat screenMat, Mat template, double scale) {
        Mat scaledTemplate = new Mat();
        Size newSize = new Size(template.cols() * scale, template.rows() * scale);
        Imgproc.resize(template, scaledTemplate, newSize);

        LogUtil.info("测试缩放比例：" + String.format("%.3f", scale) +
                " (模板：" + template.cols() + "x" + template.rows() +
                " -> 缩放后：" + scaledTemplate.cols() + "x" + scaledTemplate.rows() + ")");

        if (scaledTemplate.cols() > screenMat.cols() || scaledTemplate.rows() > screenMat.rows()) {
            LogUtil.info("缩放后的模板超出屏幕范围");
            scaledTemplate.release();
            return new ScaleMatchResult(scale, 0.0, new org.opencv.core.Point(0, 0), null);
        }

        Mat result = new Mat();
        Imgproc.matchTemplate(screenMat, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        org.opencv.core.Point bestLoc = minMaxResult.maxLoc;
        double bestMatchValue = minMaxResult.maxVal;

        Rect bestRect = new Rect(bestLoc, new org.opencv.core.Point(
                bestLoc.x + scaledTemplate.cols(),
                bestLoc.y + scaledTemplate.rows()
        ));

        result.release();
        scaledTemplate.release();

        return new ScaleMatchResult(scale, bestMatchValue, bestLoc, bestRect);
    }

    /**
     * 点击指定坐标
     */
    public void click(int x, int y) {
        robot.mouseMove(x, y);
        robot.delay(DELAY_SHORT);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        LogUtil.info("点击坐标：(" + x + ", " + y + ")");
    }

    /**
     * 双击指定坐标
     */
    public void doubleClick(int x, int y) {
        click(x, y);
        robot.delay(100);
        click(x, y);
    }

    /**
     * 输入文本
     */
    public void typeText(String text) {
        LogUtil.info("输入文本：" + text);
        for (char c : text.toCharArray()) {
            typeCharacter(c);
        }
    }

    /**
     * 输入单个字符
     */
    private void typeCharacter(char c) {
        int keyCode = getVirtualKeyCode(c);
        if (keyCode == 0) {
            LogUtil.info("不支持的字符：" + c);
            return;
        }
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
        robot.delay(50);
    }

    /**
     * 获取虚拟键码
     */
    private int getVirtualKeyCode(char c) {
        switch (c) {
            case '0': return KeyEvent.VK_0;
            case '1': return KeyEvent.VK_1;
            case '2': return KeyEvent.VK_2;
            case '3': return KeyEvent.VK_3;
            case '4': return KeyEvent.VK_4;
            case '5': return KeyEvent.VK_5;
            case '6': return KeyEvent.VK_6;
            case '7': return KeyEvent.VK_7;
            case '8': return KeyEvent.VK_8;
            case '9': return KeyEvent.VK_9;
            case '.': return KeyEvent.VK_PERIOD;
            case '-': return KeyEvent.VK_MINUS;
            case '=': return KeyEvent.VK_EQUALS;
            case '_': return KeyEvent.VK_UNDERSCORE;
            default:
                int extendedKeyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                return extendedKeyCode != KeyEvent.CHAR_UNDEFINED ? extendedKeyCode : 0;
        }
    }

    /**
     * 按下回车键
     */
    public void pressEnter() {
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_ENTER);
        LogUtil.info("按下回车键");
    }

    /**
     * 按下 Tab 键
     */
    public void pressTab() {
        robot.keyPress(KeyEvent.VK_TAB);
        robot.delay(50);
        robot.keyRelease(KeyEvent.VK_TAB);
        LogUtil.info("按下 Tab 键");
    }

    /**
     * 按下方向键
     */
    public void pressArrowKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                robot.keyPress(keyCode);
                robot.delay(50);
                robot.keyRelease(keyCode);
                LogUtil.info("按下方向键：" + KeyEvent.getKeyText(keyCode));
                break;
            default:
                LogUtil.info("无效的方向键：" + keyCode);
        }
    }

    /**
     * 快捷键操作（如 Ctrl+C）
     */
    public void pressShortcut(int controlKey, int keyCode) {
        robot.keyPress(controlKey);
        robot.keyPress(keyCode);
        robot.delay(50);
        robot.keyRelease(keyCode);
        robot.keyRelease(controlKey);
        LogUtil.info("按下快捷键：" + KeyEvent.getKeyText(controlKey) + "+" + KeyEvent.getKeyText(keyCode));
    }

    /**
     * 截取屏幕并保存
     */
    public void captureScreen(String outputPath) {
        try {
            Rectangle screenRect = new Rectangle(screenSize);
            BufferedImage screenshot = robot.createScreenCapture(screenRect);
            File outputFile = new File(outputPath);
            ImageIO.write(screenshot, "png", outputFile);
            LogUtil.info("屏幕截图已保存到：" + outputPath);
        } catch (IOException e) {
            LogUtil.info("截图失败");
            e.printStackTrace();
        }
    }

    /**
     * 截取指定区域并保存
     */
    public void captureRegion(int x, int y, int width, int height, String outputPath) {
        try {
            Rectangle region = new Rectangle(x, y, width, height);
            BufferedImage screenshot = robot.createScreenCapture(region);
            File outputFile = new File(outputPath);
            ImageIO.write(screenshot, "png", outputFile);
            LogUtil.info("区域截图已保存到：" + outputPath);
        } catch (IOException e) {
            LogUtil.info("截图失败");
            e.printStackTrace();
        }
    }

    /**
     * 延迟
     */
    public void delay(int milliseconds) {
        robot.delay(milliseconds);
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

        ScaleMatchResult(double bestScale, double bestMatchValue, org.opencv.core.Point bestLoc, Rect bestRect) {
            this.bestScale = bestScale;
            this.bestMatchValue = bestMatchValue;
            this.bestLoc = bestLoc;
            this.bestRect = bestRect;
        }
    }
}
