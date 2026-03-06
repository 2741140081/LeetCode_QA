package com.marks.tools.kkplatform;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageRecognitionAutomationTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 16:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class ImageRecognitionAutomationTest {
    private static final String TEMPLATE_DIR = "D:/images/automation/templates";
    private static final String OUTPUT_DIR = "D:/images/automation/results";
    private static final Scalar RED_COLOR = new Scalar(0, 0, 255);
    private static final int RECT_THICKNESS = 2;
    private static final double MATCH_THRESHOLD = 0.75;

    private static final double MIN_SCALE = 0.3;
    private static final double MAX_SCALE = 2.0;
    private static final double BEST_SCALE = 0.6667;
    private static final boolean USE_BEST_SCALE_FIRST = true;
    private WindowSwitcherUtils windowSwitcherUtils;

    @BeforeEach
    void setUp() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));
            windowSwitcherUtils = WindowSwitcherUtils.getInstance();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create output directory", e);
        }
    }

    @Test
    @DisplayName("屏幕截图并识别所有模板元素")
    void testScreenCaptureAndElementRecognition() {
        try {
            BufferedImage screenCapture = captureFullScreen();
            Mat screenMat = bufferedImageToMat(screenCapture);
            List<String> templateFiles = getTemplateFiles();
            Mat resultScreen = screenMat.clone();

            for (String templatePath : templateFiles) {
                recognizeAndMarkElement(screenMat, resultScreen, templatePath);
            }

            saveResult(resultScreen, "screen_recognition_result.png");
            screenMat.release();
            resultScreen.release();

        } catch (Exception e) {
            fail("图像识别失败：" + e.getMessage());
        }
    }

    @Test
    @DisplayName("识别特定模板图片 - 开始游戏按钮")
    void testRecognizeStartGameButton() {
        String templateName = "start_game_btn.png";
        recognizeSingleTemplate(templateName, "start_game_btn_result.png");
    }

    @Test
    @DisplayName("识别特定模板图片 - 开始游戏按钮2")
    void testRecognizeStartGameButton2() {
        // 切换到 chrome 浏览器 界面
        windowSwitcherUtils.switchToWindow("百度一下，你就知道 - Google Chrome");
        String templateName = "start_game_btn_2.png";
        recognizeSingleTemplate(templateName, "start_game_btn_2_result.png");
    }

    @Test
    @DisplayName("识别特定模板图片 - 更新按钮")
    void testRecognizeUpdateButton() {
        String templateName = "update_btn.png";
        recognizeSingleTemplate(templateName, "update_btn_result.png");
    }

    @Test
    @DisplayName("识别特定模板图片 - 人物属性标签")
    void testRecognizePeopleAttributeTab() {
        String templateName = "people_att_tab.png";
        recognizeSingleTemplate(templateName, "people_att_tab_result.png");
    }

    @Test
    @DisplayName("识别特定模板图片 - 经验输入框")
    void testRecognizeEXPInput() {
        String templateName = "EXP_Input.png";
        recognizeSingleTemplate(templateName, "EXP_Input_result.png");
    }

    @Test
    @DisplayName("查找经验值输入框位置")
    void testFindEXPInputLocation() {
        try {
            BufferedImage screenCapture = captureFullScreen();
            Mat screenMat = bufferedImageToMat(screenCapture);

            String expInputTemplate = Paths.get(TEMPLATE_DIR, "EXP_Input.png").toString();
            Mat template = Imgcodecs.imread(expInputTemplate);

            if (template.empty()) {
                System.out.println("无法读取模板图片：" + expInputTemplate);
                return;
            }

            System.out.println("\n=== 查找经验值输入框 ===");

            ScaleMatchResult result = matchWithScale(screenMat, template, BEST_SCALE);

            if (result.bestMatchValue < MATCH_THRESHOLD) {
                result = findBestScaleBinarySearch(screenMat, template);
            }

            if (result.bestMatchValue >= MATCH_THRESHOLD) {
                System.out.println("✓ 找到经验值输入框");
                System.out.println("  缩放比例：" + String.format("%.3f", result.bestScale));
                System.out.println("  匹配度：" + String.format("%.4f", result.bestMatchValue));
                System.out.println("  左上角坐标：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");
                System.out.println("  右下角坐标：(" + (result.bestLoc.x + result.bestRect.width) +
                        ", " + (result.bestLoc.y + result.bestRect.height) + ")");
                System.out.println("  宽度：" + result.bestRect.width + ", 高度：" + result.bestRect.height);

                int centerX = (int) result.bestLoc.x + result.bestRect.width / 2;
                int centerY = (int) result.bestLoc.y + result.bestRect.height / 2;
                System.out.println("  中心点击位置：(" + centerX + ", " + centerY + ")");

                Mat resultScreen = screenMat.clone();
                Imgproc.rectangle(resultScreen, result.bestRect, RED_COLOR, RECT_THICKNESS);
                saveResult(resultScreen, "exp_input_location.png");
                resultScreen.release();

            } else {
                System.out.println("✗ 未找到经验值输入框");
                System.out.println("  最佳匹配度：" + String.format("%.4f", result.bestMatchValue));
            }

            template.release();
            screenMat.release();

        } catch (Exception e) {
            fail("查找失败：" + e.getMessage());
        }
    }


    @Test
    @DisplayName("修改经验值 - 定位并修改为 45000000")
    void testModifyEXPValue() {
        try {
            BufferedImage screenCapture = captureFullScreen();
            Mat screenMat = bufferedImageToMat(screenCapture);

            String expInputTemplate = Paths.get(TEMPLATE_DIR, "EXP_Input.png").toString();
            Mat template = Imgcodecs.imread(expInputTemplate);

            if (template.empty()) {
                System.out.println("无法读取模板图片：" + expInputTemplate);
                return;
            }

            System.out.println("\n=== 修改经验值任务 ===");

            ScaleMatchResult result = matchWithScale(screenMat, template, BEST_SCALE);

            if (result.bestMatchValue < MATCH_THRESHOLD) {
                System.out.println("未找到经验值输入框，尝试二分法查找...");
                result = findBestScaleBinarySearch(screenMat, template);
            }

            if (result.bestMatchValue >= MATCH_THRESHOLD) {
                System.out.println("✓ 找到经验值输入框位置");
                System.out.println("  位置：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");
                System.out.println("  大小：" + result.bestRect.width + "x" + result.bestRect.height);

                int inputX = (int) result.bestLoc.x;
                int inputY = (int) result.bestLoc.y;
                int inputWidth = result.bestRect.width;
                int inputHeight = result.bestRect.height;

                int clickX = inputX + (inputWidth * 2 / 3);
                int clickY = inputY + inputHeight / 2;

                System.out.println("\n开始修改经验值...");
                System.out.println("1. 双击输入框位置：(" + clickX + ", " + clickY + ") (宽度 2/3 处)");

                Robot robot = new Robot();
                robot.setAutoDelay(100);

                robot.mouseMove(clickX, clickY);
                robot.delay(50);

                System.out.println("2. 第一次点击");
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(100);

                System.out.println("3. 第二次点击 (双击)");
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                robot.delay(300);

                System.out.println("4. 全选原有内容 (Ctrl+A)");
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_A);
                robot.keyRelease(KeyEvent.VK_A);
                robot.keyRelease(KeyEvent.VK_CONTROL);

                robot.delay(100);

                System.out.println("5. 输入新值：45000000");
                typeString(robot, "45000000");

                robot.delay(200);

                System.out.println("6. 按 Enter 确认");
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);

                System.out.println("\n✓ 经验值修改完成！");

                Mat resultScreen = screenMat.clone();
                Imgproc.rectangle(resultScreen, result.bestRect, RED_COLOR, RECT_THICKNESS);

                Point clickPoint = new Point(clickX, clickY);
                Imgproc.circle(resultScreen, clickPoint, 5, new Scalar(0, 255, 0), -1);

                saveResult(resultScreen, "exp_input_modify_result.png");
                resultScreen.release();

            } else {
                System.out.println("✗ 未找到经验值输入框");
                System.out.println("  最佳匹配度：" + String.format("%.4f", result.bestMatchValue));
            }

            template.release();
            screenMat.release();

        } catch (Exception e) {
            fail("修改经验值失败：" + e.getMessage());
        }
    }

    @Test
    @DisplayName("使用二分法查找最佳缩放比例")
    void testFindBestScaleWithBinarySearch() {
        String templateName = "start_game_btn.png";
        String templatePath = Paths.get(TEMPLATE_DIR, templateName).toString();

        if (!new File(templatePath).exists()) {
            System.out.println("模板文件不存在：" + templatePath);
            return;
        }

        try {
            BufferedImage screenCapture = captureFullScreen();
            Mat screenMat = bufferedImageToMat(screenCapture);
            Mat template = Imgcodecs.imread(templatePath);

            System.out.println("\n=== 二分法查找最佳缩放比例 ===");
            System.out.println("模板名称：" + templateName);
            System.out.println("屏幕尺寸：" + screenMat.cols() + "x" + screenMat.rows());
            System.out.println("原始模板尺寸：" + template.cols() + "x" + template.rows());

            ScaleMatchResult result = findBestScaleBinarySearch(screenMat, template);

            System.out.println("\n最佳缩放比例：" + result.bestScale);
            System.out.println("最佳匹配度：" + String.format("%.4f", result.bestMatchValue));
            System.out.println("匹配位置：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");

            template.release();
            screenMat.release();

        } catch (Exception e) {
            fail("测试失败：" + e.getMessage());
        }
    }

    private void recognizeSingleTemplate(String templateName, String outputName) {
        try {
            String templatePath = Paths.get(TEMPLATE_DIR, templateName).toString();
            File templateFile = new File(templatePath);

            if (!templateFile.exists()) {
                System.out.println("模板文件不存在：" + templatePath);
                return;
            }

            BufferedImage screenCapture = captureFullScreen();
            Mat screenMat = bufferedImageToMat(screenCapture);
            Mat resultScreen = screenMat.clone();

            recognizeAndMarkElement(screenMat, resultScreen, templatePath);

            saveResult(resultScreen, outputName);

            screenMat.release();
            resultScreen.release();

        } catch (Exception e) {
            fail("识别 " + templateName + " 失败：" + e.getMessage());
        }
    }

    private BufferedImage captureFullScreen() throws AWTException {
        Robot robot = new Robot();
        Rectangle screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        return robot.createScreenCapture(screenBounds);
    }

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

    private List<String> getTemplateFiles() {
        List<String> templateFiles = new ArrayList<>();
        File dir = new File(TEMPLATE_DIR);
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".png"));
            if (files != null) {
                for (File file : files) {
                    templateFiles.add(file.getAbsolutePath());
                }
            }
        }
        return templateFiles;
    }

    private static class ScaleMatchResult {
        double bestScale;
        double bestMatchValue;
        Point bestLoc;
        Rect bestRect;

        ScaleMatchResult(double bestScale, double bestMatchValue, Point bestLoc) {
            this.bestScale = bestScale;
            this.bestMatchValue = bestMatchValue;
            this.bestLoc = bestLoc;
        }
    }

    private ScaleMatchResult findBestScaleBinarySearch(Mat screenMat, Mat originalTemplate) {
        double left = MIN_SCALE;
        double right = MAX_SCALE;

        double globalBestScale = 1.0;
        double globalBestMatchValue = 0.0;
        Point globalBestLoc;

        double bestScale = left;
        double bestMatchValue = 0.0;

        for (double scale = left; scale <= right; scale += 0.1) {
            double matchValue = testScale(screenMat, originalTemplate, scale);

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
        Imgproc.matchTemplate(screenMat, bestScaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);
        globalBestLoc = minMaxResult.maxLoc;

        Rect bestRect = new Rect(globalBestLoc, new Point(
                globalBestLoc.x + bestScaledTemplate.cols(),
                globalBestLoc.y + bestScaledTemplate.rows()
        ));

        result.release();
        bestScaledTemplate.release();

        ScaleMatchResult scaleMatchResult = new ScaleMatchResult(globalBestScale, globalBestMatchValue, globalBestLoc);
        scaleMatchResult.bestRect = bestRect;
        return scaleMatchResult;
    }

    private double testScale(Mat screenMat, Mat template, double scale) {
        Mat scaledTemplate = new Mat();
        Size newSize = new Size(template.cols() * scale, template.rows() * scale);
        Imgproc.resize(template, scaledTemplate, newSize);

        if (scaledTemplate.cols() > screenMat.cols() || scaledTemplate.rows() > screenMat.rows()) {
            scaledTemplate.release();
            return 0.0;
        }

        Mat result = new Mat();
        Imgproc.matchTemplate(screenMat, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        double maxVal = minMaxResult.maxVal;

        scaledTemplate.release();
        result.release();

        return maxVal;
    }

    private void recognizeAndMarkElement(Mat screenMat, Mat resultScreen, String templatePath) {
        Mat template = Imgcodecs.imread(templatePath);

        if (template.empty()) {
            System.out.println("无法读取模板图片：" + templatePath);
            return;
        }

        String templateName = Paths.get(templatePath).getFileName().toString();
        System.out.println("\n=== 识别元素：" + templateName + " ===");

        Mat screenGray = new Mat();
        Mat templateGray = new Mat();
        Imgproc.cvtColor(screenMat, screenGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(template, templateGray, Imgproc.COLOR_BGR2GRAY);

        ScaleMatchResult result;

        if (USE_BEST_SCALE_FIRST) {
            System.out.println("尝试使用最佳缩放比例：" + BEST_SCALE);
            result = matchWithScaleGray(screenGray, templateGray, BEST_SCALE);

            if (result.bestMatchValue >= MATCH_THRESHOLD) {
                System.out.println("✓ 使用最佳缩放比例匹配成功！");
            } else {
                System.out.println("最佳缩放比例匹配失败，开始二分法查找...");
                result = findBestScaleBinarySearchGray(screenGray, templateGray);
            }
        } else {
            result = findBestScaleBinarySearchGray(screenGray, templateGray);
        }

        if (result.bestMatchValue >= MATCH_THRESHOLD) {
            Imgproc.rectangle(resultScreen, result.bestRect, RED_COLOR, RECT_THICKNESS);
            System.out.println("✓ 找到匹配元素：" + templateName);
            System.out.println("  缩放比例：" + String.format("%.3f", result.bestScale));
            System.out.println("  匹配度：" + String.format("%.4f", result.bestMatchValue));
            System.out.println("  位置：(" + result.bestLoc.x + ", " + result.bestLoc.y + ")");
            System.out.println("  方框大小：" + result.bestRect.width + "x" + result.bestRect.height);
        } else {
            System.out.println("✗ 未找到匹配元素：" + templateName);
            System.out.println("  最佳匹配度：" + String.format("%.4f", result.bestMatchValue));
            System.out.println("  阈值：" + MATCH_THRESHOLD);
        }

        template.release();
        templateGray.release();
        screenGray.release();
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
            return new ScaleMatchResult(scale, 0.0, new Point(0, 0));
        }

        Mat result = new Mat();
        Imgproc.matchTemplate(screenGray, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        Point bestLoc = minMaxResult.maxLoc;
        double bestMatchValue = minMaxResult.maxVal;

        Rect bestRect = new Rect(bestLoc, new Point(
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
        Point globalBestLoc;

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

        Rect bestRect = new Rect(globalBestLoc, new Point(
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

    private ScaleMatchResult matchWithScale(Mat screenMat, Mat template, double scale) {
        Mat scaledTemplate = new Mat();
        Size newSize = new Size(template.cols() * scale, template.rows() * scale);
        Imgproc.resize(template, scaledTemplate, newSize);

        System.out.println("模板尺寸：" + template.cols() + "x" + template.rows() +
                " -> 缩放后：" + scaledTemplate.cols() + "x" + scaledTemplate.rows());

        if (scaledTemplate.cols() > screenMat.cols() || scaledTemplate.rows() > screenMat.rows()) {
            System.out.println("缩放后的模板超出屏幕范围");
            scaledTemplate.release();
            return new ScaleMatchResult(scale, 0.0, new Point(0, 0));
        }

        Mat result = new Mat();
        Imgproc.matchTemplate(screenMat, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        Point bestLoc = minMaxResult.maxLoc;
        double bestMatchValue = minMaxResult.maxVal;

        Rect bestRect = new Rect(bestLoc, new Point(
                bestLoc.x + scaledTemplate.cols(),
                bestLoc.y + scaledTemplate.rows()
        ));

        result.release();
        scaledTemplate.release();

        ScaleMatchResult scaleMatchResult = new ScaleMatchResult(scale, bestMatchValue, bestLoc);
        scaleMatchResult.bestRect = bestRect;
        return scaleMatchResult;
    }

    private void saveResult(Mat resultImage, String fileName) {
        String outputPath = Paths.get(OUTPUT_DIR, fileName).toString();
        Imgcodecs.imwrite(outputPath, resultImage);
        System.out.println("\n结果已保存到：" + outputPath);
    }


    private void typeString(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = getVirtualKeyCode(c);
            if (keyCode != 0) {
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                robot.delay(50);
            }
        }
    }

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
            case '-': return KeyEvent.VK_MINUS;
            case '=': return KeyEvent.VK_EQUALS;
            default: return 0;
        }
    }
}