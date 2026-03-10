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

import static com.marks.tools.kkplatform.common.KingOfBeastsConstants.*;
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
    // 添加静态代码块
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public Robot robot;
    private Dimension screenSize;

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
     * @param isNecessary 是否为必需的图片，true 时识别失败会报错并保存截图，false 时仅返回 null
     * @return 匹配到的坐标，未找到返回 null
     */
    public Point findImage(String templateName, boolean isNecessary) {
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

            Point result = recognizeAndMarkElement(screenMat, template, templateName, isNecessary);

            saveResult(screenMat, "result_" + templateName);
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
     * 在屏幕上查找匹配的图片（使用最佳缩放比例 + 二分法），默认是必需的图片
     * @param templateName 模板图片名称（不含扩展名）
     * @return 匹配到的坐标，未找到返回 null
     */
    public Point findImage(String templateName) {
        return findImage(templateName, true);
    }

    /**
     * 使用最佳缩放比例 + 二分法查找图片
     * @param screenMat 屏幕截图 Mat 对象
     * @param template 模板图片 Mat 对象
     * @param templatePath 模板图片路径
     * @param isNecessary 是否为必需的图片，true 时识别失败会报错并保存截图，false 时不处理
     * @return 匹配到的坐标，未找到返回 null
     */
    private Point recognizeAndMarkElement(Mat screenMat, Mat template, String templatePath, boolean isNecessary) {
        LogUtil.info("\n=== 查找元素：" + templatePath + " ===");

        String templateName = Paths.get(templatePath).getFileName().toString();
        System.out.println("\n=== 识别元素：" + templateName + " ===");

        Mat screenGray = new Mat();
        Mat templateGray = new Mat();
        Imgproc.cvtColor(screenMat, screenGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(template, templateGray, Imgproc.COLOR_BGR2GRAY);

        ScaleMatchResult result;

        // 首先尝试最佳缩放比例
        LogUtil.info("尝试使用最佳缩放比例：" + BEST_SCALE);
        result = matchWithScaleGray(screenGray, templateGray, BEST_SCALE);

        if (result.bestMatchValue >= MATCH_THRESHOLD) {
            Imgproc.rectangle(screenMat, result.bestRect, RED_COLOR, RECT_THICKNESS);
            LogUtil.info("✓ 使用最佳缩放比例匹配成功！");
            LogUtil.info("  匹配度：" + String.format("%.4f", result.bestMatchValue));
            LogUtil.info("  位置：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");
        } else if (isNecessary) {
            // isNecessary = true, 则需要进行遍历查找, 如果未找到则报错, isNecessary = false, 则不进行后续查找
            LogUtil.info("最佳缩放比例匹配失败（匹配度：" + String.format("%.4f", result.bestMatchValue) +
                    "），开始二分法查找...");
            result = findBestScaleBinarySearchGray(screenGray, templateGray);

            if (result.bestMatchValue >= MATCH_THRESHOLD) {
                Imgproc.rectangle(screenMat, result.bestRect, RED_COLOR, RECT_THICKNESS);
                LogUtil.info("✓ 二分法查找成功！");
                LogUtil.info("  最佳缩放比例：" + String.format("%.3f", result.bestScale));
                LogUtil.info("  匹配度：" + String.format("%.4f", result.bestMatchValue));
                LogUtil.info("  位置：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");
            } else {
                LogUtil.info("✗ 未找到匹配元素：" + templateName);
                LogUtil.info("  最佳匹配度：" + String.format("%.4f", result.bestMatchValue));
                LogUtil.info("  阈值：" + MATCH_THRESHOLD);
                handleNotFound(screenMat, templateName, result);

            }
        }

        // 释放资源
        templateGray.release();
        screenGray.release();
        // 返回结果
        return result.bestMatchValue >= MATCH_THRESHOLD ? convertToJavaPoint(result.bestLoc, template.cols(), template.rows()) : null;
    }


    private ScaleMatchResult matchWithScaleGray(Mat screenGray, Mat templateGray, double scale) {
        Mat scaledTemplate = new Mat();
        Size newSize = new Size(templateGray.cols() * scale, templateGray.rows() * scale);
        Imgproc.resize(templateGray, scaledTemplate, newSize);

        System.out.println("模板尺寸：" + templateGray.cols() + "x" + templateGray.rows() +
                " -> 缩放后：" + scaledTemplate.cols() + "x" + scaledTemplate.rows());

        if (scaledTemplate.cols() > screenGray.cols() || scaledTemplate.rows() > screenGray.rows()) {
            System.out.println("缩放后的模板超出屏幕范围");
            scaledTemplate.release();
            return new ScaleMatchResult(scale, 0.0, new org.opencv.core.Point(0, 0));
        }

        Mat result = new Mat();
        Imgproc.matchTemplate(screenGray, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        org.opencv.core.Point bestLoc = minMaxResult.maxLoc;
        double bestMatchValue = minMaxResult.maxVal;

        Rect bestRect = new Rect(bestLoc, new org.opencv.core.Point(
                bestLoc.x + scaledTemplate.cols(),
                bestLoc.y + scaledTemplate.rows()
        ));

        result.release();
        scaledTemplate.release();

        ScaleMatchResult scaleMatchResult = new ScaleMatchResult(scale, bestMatchValue, bestLoc);
        scaleMatchResult.bestRect = bestRect;
        return scaleMatchResult;
    }

    private ScaleMatchResult findBestScaleBinarySearchGray(Mat screenGray, Mat originalTemplate) {
        double left = MIN_SCALE;
        double right = MAX_SCALE;

        double globalBestScale = 1.0;
        double globalBestMatchValue = 0.0;
        org.opencv.core.Point globalBestLoc;

        double bestScale = left;
        double bestMatchValue = 0.0;

        for (double scale = left; scale <= right; scale += 0.1) {
            double matchValue = testScaleGray(screenGray, originalTemplate, scale);

            System.out.println(String.format("测试缩放比例：%.3f, 匹配度：%.4f", scale, matchValue));

            if (matchValue > bestMatchValue) {
                bestMatchValue = matchValue;
                bestScale = scale;
            }

            if (bestMatchValue > globalBestMatchValue) {
                globalBestMatchValue = bestMatchValue;
                globalBestScale = bestScale;
            }
        }

        System.out.println("遍历查找结束，最佳缩放比例：" + String.format("%.3f", globalBestScale) +
                "，匹配度：" + String.format("%.4f", globalBestMatchValue));

        Mat bestScaledTemplate = new Mat();
        Size bestSize = new Size(originalTemplate.cols() * globalBestScale, originalTemplate.rows() * globalBestScale);
        Imgproc.resize(originalTemplate, bestScaledTemplate, bestSize);

        Mat result = new Mat();
        Imgproc.matchTemplate(screenGray, bestScaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);
        globalBestLoc = minMaxResult.maxLoc;

        Rect bestRect = new Rect(globalBestLoc, new org.opencv.core.Point(
                globalBestLoc.x + bestScaledTemplate.cols(),
                globalBestLoc.y + bestScaledTemplate.rows()
        ));

        result.release();
        bestScaledTemplate.release();

        ScaleMatchResult scaleMatchResult = new ScaleMatchResult(globalBestScale, globalBestMatchValue, globalBestLoc);
        scaleMatchResult.bestRect = bestRect;
        return scaleMatchResult;
    }


    private double testScaleGray(Mat screenGray, Mat template, double scale) {
        Mat scaledTemplate = new Mat();
        Size newSize = new Size(template.cols() * scale, template.rows() * scale);
        Imgproc.resize(template, scaledTemplate, newSize);

        if (scaledTemplate.cols() > screenGray.cols() || scaledTemplate.rows() > screenGray.rows()) {
            scaledTemplate.release();
            return 0.0;
        }

        Mat result = new Mat();
        Imgproc.matchTemplate(screenGray, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        double maxVal = minMaxResult.maxVal;

        scaledTemplate.release();
        result.release();

        return maxVal;
    }

    /**
     * 处理未找到匹配的情况，保存截图
     */
    private void handleNotFound(Mat screenMat, String templateName, ScaleMatchResult result) {
        String errorFileName = "error_" + templateName;

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
        robot.delay(100);
    }

    /**
     * 双击指定坐标
     */
    public void oneClick(int x, int y) {
        click(x, y);
        robot.delay(100);
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
        //
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        fileName = fileName.replace(".png", "") + "_" + timestamp + ".png";
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
