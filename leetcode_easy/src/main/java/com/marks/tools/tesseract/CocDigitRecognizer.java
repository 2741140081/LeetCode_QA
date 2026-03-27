package com.marks.tools.tesseract;

import com.marks.utils.LogUtil;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.*;
import java.util.List;

/**
 * 1. 先找到相关 x_tmplate 的坐标中心点, 根据中心点以及模板的size 得到截图区域的起始坐标和宽度, 长度自行定义, 当前使用的长度是: 310 px
 * 2. 然后根据[0~9] 的数字模板进行匹配, 返回模板数字的匹配度满足要求的所有点的集合, 然后根据坐标位置排序, 就可以得到模板图片 -> 坐标位置 的一种一对多映射关系.
 * 例如 模板数字[6], 有p1, p2, p3 这3个坐标点返回, itme{tmplateName, List<Point> tmplatePoints} 返回这种类型的实体.
 * 3. 有了这样一个实体之后, 然后可以通过插入排序的方式, 按照point 进行升序排序(根据point.y), 截取的是一行数据, 所以这些坐标点的 x 值是相同的,
 * 需要根据 y 值进行升序排序.然后对已经排序的item进行遍历, 并且可以构建一个 Map<String, Integer> map;
 * 存储tmplateName 与 number 直接的关系. 根据这个关系 可以得到 StringBuilder sb = value;
 * 4. value 就是数字的拼接. 然后将数字转成 int, 这就得到最终结果, 输出金币数量
 * 5. 相关模板的信息: 所有截图模板都在目录: D:\images\automation\coc 目录下
 * 6. 金币, 圣水, 黑油的模板图片名称分别是: gold_template.png, water_template.png, oil_template.png.
 * 数字模板的名称是: number_{数字}.png, 例如 number_0.png, number_1.png等
 * 7. 由于当前游戏没有在运行, 当前是使用Notepad++ 来替代游戏界面, 需要切换到notepad++ 页面, 并且需要改界面设置为全屏状态
 * 8. 缩放比例还是 0.6667.
 * @author marks
 * @version v1.0
 * @date 2026/3/26
 */
public class CocDigitRecognizer {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static final String TEMPLATE_DIR = "D:/images/automation/coc";
    private static final String RESULT_DIR = "D:/images/automation/coc/result";
    private static final double SCALE = 0.6667;
    private static final double MATCH_THRESHOLD = 0.75;
    private static final int SCREENSHOT_WIDTH = 310;

    private Robot robot;

    public CocDigitRecognizer() throws AWTException {
        this.robot = new Robot();
    }

    /**
     * 识别资源数量（金币、圣水、黑油）
     * @param resourceType 资源类型："gold", "water", "oil"
     * @return 资源数量
     */
    public int recognizeResource(String resourceType) {
        try {
            String templateName = resourceType + "_template";

            String templatePath = TEMPLATE_DIR + "/" + templateName + ".png";
            Mat template = Imgcodecs.imread(templatePath);

            if (template.empty()) {
                LogUtil.error("无法读取模板图片：" + templatePath);
                return -1;
            }

            Point centerPoint = findTemplateCenter(templateName, template);

            if (centerPoint == null) {
                LogUtil.error("未找到资源模板：" + templateName);
                return -1;
            }

            Rect screenshotRegion = calculateScreenshotRegion(centerPoint, template);
            Mat screenshotMat = captureScreenRegion(screenshotRegion,resourceType);

            List<DigitMatchResult> digitResults = matchAllDigits(screenshotMat);

            List<DigitPointWithNumber> sortedResults = sortByXCoordinate(digitResults);

            String numberStr = buildNumberString(sortedResults);

            LogUtil.info("识别结果：{} = {}", resourceType, numberStr);

            screenshotMat.release();
            template.release();

            return 0;

        } catch (Exception e) {
            LogUtil.error("识别资源数量失败：" + resourceType, e);
            return -1;
        }
    }

    /**
     * 查找模板中心点
     */
    private Point findTemplateCenter(String templateName, Mat templateMat) {

        Mat screenMat = captureScreenToMat();
        Mat screenGray = new Mat();
        Mat templateGray = new Mat();

        Imgproc.cvtColor(screenMat, screenGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(templateMat, templateGray, Imgproc.COLOR_BGR2GRAY);

        Mat scaledTemplate = new Mat();
        Size newSize = new Size(templateGray.cols() * SCALE, templateGray.rows() * SCALE);
        Imgproc.resize(templateGray, scaledTemplate, newSize);

        Mat result = new Mat();
        Imgproc.matchTemplate(screenGray, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        Point centerPoint = null;
        if (minMaxResult.maxVal >= MATCH_THRESHOLD) {
            centerPoint = new Point(
                    (int) (minMaxResult.maxLoc.x + scaledTemplate.cols() / 2),
                    (int) (minMaxResult.maxLoc.y + scaledTemplate.rows() / 2)
            );
            LogUtil.info("找到模板 {}，中心点：({}, {})", templateName, centerPoint.x, centerPoint.y);
        } else {
            LogUtil.error("未找到模板 {}，匹配度：{}", templateName, minMaxResult.maxVal);
        }

        screenMat.release();
        screenGray.release();
        templateGray.release();
        scaledTemplate.release();
        result.release();

        return centerPoint;
    }

    /**
     * 计算截图区域
     */
    private Rect calculateScreenshotRegion(Point centerPoint, Mat templateMat) {
        // 计算模板缩放后的尺寸
        int templateWidth = (int) (templateMat.cols() * SCALE);
        int templateHeight = (int) (templateMat.rows() * SCALE);

        // 计算模板的右边界（中心点 + 模板宽度的一半）
        int templateRightEdge = centerPoint.x + templateWidth / 2;

        // 计算截图区域的起始 Y 坐标（中心点 - 模板高度的一半 - 5px 上边距）
        int startY = centerPoint.y - templateHeight / 2 - 5;

        // 截图高度 = 模板高度 + 上下各 5px
        int screenshotHeight = templateHeight + 10;

        int startX = templateRightEdge;
        startY = Math.max(0, startY);
        startX = Math.max(0, startX);

        LogUtil.debug("截图区域：startX={}, startY={}, width={}, height={}",
                startX, startY, SCREENSHOT_WIDTH, screenshotHeight);

        // 宽度 200px，高度 = 模板高度 + 10px（上下各 5px）
        return new Rect(startX, startY, SCREENSHOT_WIDTH, screenshotHeight);
    }

    /**
     * 截取屏幕区域并保存
     */
    private Mat captureScreenRegion(Rect region, String resourceType) {
        Rectangle screenRect = new Rectangle(region.x, region.y, region.width, region.height);
        BufferedImage image = robot.createScreenCapture(screenRect);

        // 保存截图到 result 目录
        saveScreenshot(image, resourceType);

        return bufferedImageToMat(image);
    }

    /**
     * 保存截图到 result 目录
     */
    private void saveScreenshot(BufferedImage image, String resourceType) {
        try {
            String timestamp = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = resourceType + "_screenshot_" + timestamp + ".png";
            String outputPath = java.nio.file.Paths.get(RESULT_DIR, fileName).toString();

            // 确保目录存在
            java.io.File outputDir = new java.io.File(RESULT_DIR);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            javax.imageio.ImageIO.write(image, "png", new java.io.File(outputPath));
            LogUtil.info("截图已保存到：{}", outputPath);

        } catch (Exception e) {
            LogUtil.error("保存截图失败：{}", e.getMessage());
        }
    }

    /**
     * 捕获整个屏幕并转换为 Mat
     */
    private Mat captureScreenToMat() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenBounds = new Rectangle(screenSize);
        BufferedImage screenCapture = robot.createScreenCapture(screenBounds);
        return bufferedImageToMat(screenCapture);
    }

    /**
     * BufferedImage 转换为 Mat
     */
    private Mat bufferedImageToMat(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

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

        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }

    /**
     * 匹配所有数字模板
     */
    private List<DigitMatchResult> matchAllDigits(Mat screenshotMat) {
        List<DigitMatchResult> results = new ArrayList<>();

        Mat screenshotGray = new Mat();
        Imgproc.cvtColor(screenshotMat, screenshotGray, Imgproc.COLOR_BGR2GRAY);

        int screenshotWidth = screenshotGray.cols();
        int screenshotHeight = screenshotGray.rows();

        LogUtil.info("=== 截图区域信息 ===");
        LogUtil.info("截图区域尺寸：{}x{} (宽 x 高)", screenshotWidth, screenshotHeight);

        for (int i = 0; i <= 9; i++) {
            String numberTemplateName = "number_" + i;
            String numberTemplatePath = TEMPLATE_DIR + "/" + numberTemplateName + ".png";

            Mat numberTemplate = Imgcodecs.imread(numberTemplatePath);
            if (numberTemplate.empty()) {
                LogUtil.warn("无法读取数字模板：" + numberTemplatePath);
                continue;
            }

            Mat numberTemplateGray = new Mat();
            Imgproc.cvtColor(numberTemplate, numberTemplateGray, Imgproc.COLOR_BGR2GRAY);

            Mat scaledTemplate = new Mat();
            Size newSize = new Size(
                    numberTemplateGray.cols() * SCALE,
                    numberTemplateGray.rows() * SCALE
            );
            Imgproc.resize(numberTemplateGray, scaledTemplate, newSize);

            int scaledTemplateWidth = scaledTemplate.cols();
            int scaledTemplateHeight = scaledTemplate.rows();

            Mat result = new Mat();
            try {
                // 正确的匹配方向：在截图区域中查找数字模板
                // screenshotGray: 待匹配的图像（source）
                // scaledTemplate: 模板（template）
                Imgproc.matchTemplate(screenshotGray, scaledTemplate, result, Imgproc.TM_CCOEFF_NORMED);

                // 遍历 result 矩阵，收集所有匹配度 >= 阈值的点
                List<Point> matchPoints = new ArrayList<>();
                for (int y = 0; y < result.rows(); y++) {
                    for (int x = 0; x < result.cols(); x++) {
                        double matchValue = result.get(y, x)[0];
                        if (matchValue >= MATCH_THRESHOLD) {
                            Point matchPoint = new Point(x, y);
                            matchPoints.add(matchPoint);
                        }
                    }
                }

                // 去重：过滤掉距离太近的重复点
                List<Point> filteredPoints = removeDuplicatePoints(matchPoints, scaledTemplateWidth, scaledTemplateHeight);

                // 如果有匹配点，创建结果对象
                if (!filteredPoints.isEmpty()) {
                    DigitMatchResult digitResult = new DigitMatchResult();
                    digitResult.templateName = numberTemplateName;
                    digitResult.number = i;
                    digitResult.points = filteredPoints;
                    results.add(digitResult);
                    LogUtil.info("✓ 数字 {} 匹配到 {} 个点", numberTemplateName, filteredPoints.size());
                }

            } catch (Exception e) {
                LogUtil.error("模板匹配失败：{}", numberTemplateName, e);
            } finally {
                result.release();
            }

            numberTemplateGray.release();
            scaledTemplate.release();
            numberTemplate.release();
        }

        screenshotGray.release();
        LogUtil.info("====================");

        return results;
    }

    /**
     * 去除重复的匹配点（距离太近的点只保留一个）
     * @param points 原始匹配点列表
     * @param templateWidth 模板宽度
     * @param templateHeight 模板高度
     * @return 去重后的点列表
     */
    private List<Point> removeDuplicatePoints(List<Point> points, int templateWidth, int templateHeight) {
        if (points.isEmpty()) {
            return points;
        }

        List<Point> filteredPoints = new ArrayList<>();
        // 使用模板宽度的一半作为最小距离阈值
        int minDistance = Math.max(templateWidth / 2, 5);

        for (Point point : points) {
            boolean isDuplicate = false;

            // 检查是否已存在距离很近的点
            for (Point existingPoint : filteredPoints) {
                double distance = Math.sqrt(
                        Math.pow(point.x - existingPoint.x, 2) +
                                Math.pow(point.y - existingPoint.y, 2)
                );

                if (distance < minDistance) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                filteredPoints.add(point);
            }
        }

        LogUtil.debug("  去重前：{} 个点，去重后：{} 个点，最小距离阈值：{} px",
                points.size(), filteredPoints.size(), minDistance);

        return filteredPoints;
    }

    /**
     * 查找非零点
     */
    private List<Point> findNonZeroPoints(Mat matches) {
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < matches.rows(); y++) {
            for (int x = 0; x < matches.cols(); x++) {
                double[] data = matches.get(y, x);
                if (data[0] > 0) {
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    /**
     * 按 X 坐标排序（从左到右，升序）
     */
    private List<DigitPointWithNumber> sortByXCoordinate(List<DigitMatchResult> results) {
        // 将所有坐标点展开，每个点都包含对应的数字信息
        List<DigitPointWithNumber> allPoints = new ArrayList<>();

        for (DigitMatchResult result : results) {
            for (Point point : result.points) {
                DigitPointWithNumber pointWithNumber = new DigitPointWithNumber();
                pointWithNumber.x = point.x;
                pointWithNumber.y = point.y;
                pointWithNumber.number = result.number;
                pointWithNumber.templateName = result.templateName;
                allPoints.add(pointWithNumber);
            }
        }

        LogUtil.info("展开后总点数：{}", allPoints.size());

        // 按 X 坐标升序排序（从左到右）
        allPoints.sort(Comparator.comparingInt(p -> p.x));
        return allPoints;
    }

    /**
     * 按 Y 坐标排序（插入排序）
     */
    private List<DigitPointWithNumber> sortByYCoordinate(List<DigitMatchResult> results) {
        // 将所有坐标点展开，每个点都包含对应的数字信息
        List<DigitPointWithNumber> allPoints = new ArrayList<>();

        for (DigitMatchResult result : results) {
            for (Point point : result.points) {
                DigitPointWithNumber pointWithNumber = new DigitPointWithNumber();
                pointWithNumber.x = point.x;
                pointWithNumber.y = point.y;
                pointWithNumber.number = result.number;
                pointWithNumber.templateName = result.templateName;
                allPoints.add(pointWithNumber);
            }
        }

        // 按 Y 坐标升序排序（从上到下）
        allPoints.sort(Comparator.comparingInt(p -> p.y));

        return allPoints;
    }

    /**
     * 内部类：带数字的坐标点
     */
    private static class DigitPointWithNumber {
        int x;
        int y;
        int number;
        String templateName;
    }

    /**
     * 插入排序（按 Y 坐标升序）
     */
    private void insertionSortByY(List<Point> points) {
        for (int i = 1; i < points.size(); i++) {
            Point current = points.get(i);
            int j = i - 1;

            while (j >= 0 && points.get(j).y > current.y) {
                points.set(j + 1, points.get(j));
                j--;
            }
            points.set(j + 1, current);
        }
    }

    /**
     * 构建数字字符串
     */
    private String buildNumberString(List<DigitPointWithNumber> sortedResults) {
        Map<String, Integer> templateToNumber = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (DigitPointWithNumber sortedResult : sortedResults) {
            sb.append(sortedResult.number);
        }
        return sb.toString();
    }

    /**
     * 内部类：数字匹配结果
     */
    private static class DigitMatchResult {
        String templateName;
        int number;
        List<Point> points;
    }

    public static void main(String[] args) {
        try {
            // 休眠 10s, 等我切换到notepad++
            Thread.sleep(10000);
            CocDigitRecognizer recognizer = new CocDigitRecognizer();

            int gold = recognizer.recognizeResource("gold");
            LogUtil.info("金币数量：{}", gold);

            int water = recognizer.recognizeResource("water");
            LogUtil.info("圣水数量：{}", water);

            int oil = recognizer.recognizeResource("oil");
            LogUtil.info("黑油数量：{}", oil);

            System.out.println("识别完成");

        } catch (AWTException e) {
            LogUtil.error("初始化失败", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
