package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.cache.ImageCache;
import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import com.marks.kkPlatformGameAuto.service.ImageRecognitionService;
import com.marks.kkPlatformGameAuto.util.FileUtils;
import com.marks.kkPlatformGameAuto.util.InputController;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageRecognitionServiceImpl </p>
 * <p>描述: 图片识别的实现类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
public class ImageRecognitionServiceImpl implements ImageRecognitionService {
    // 添加静态代码块
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Autowired
    private GameAutoProperties gameAutoProperties;

    // 注入输入统一门面
    @Autowired
    private InputController input;

    // 注入文件处理服务
    @Autowired
    private FileUtils fileService;

    // 注入图片缓存
    @Autowired
    private ImageCache imageCache;

    @Override
    public java.awt.Point findImageCenter(String imagePath, int timeout, int delayTime) {
        long startTime = System.currentTimeMillis();
        // 从缓存中获取
        String cacheKey = fileService.extractImageName(imagePath);

        Point cachedPoint = imageCache.get(cacheKey);
        if (cachedPoint != null) {
            log.info("缓存命中：{}, 坐标：({}, {})", imagePath, cachedPoint.x, cachedPoint.y);
            return new java.awt.Point((int) cachedPoint.x, (int) cachedPoint.y);
        }

        // 加载目标图片
        Mat templateMat = Imgcodecs.imread(imagePath);
        if (templateMat.empty()) {
            log.error("无法加载目标图片：{}", imagePath);
            return null;
        }
        // 创建返回结果
        java.awt.Point resultPoint = null;
        try {
            Robot robot = new Robot();
            // 缩放模板图片
            Mat resizedTemplateMat = resizeTemplateImage(templateMat);
            // 循环查找图片直到超时
            while (System.currentTimeMillis() - startTime < timeout) {
                // 获取屏幕截图
                Mat screenMat = captureScreenToMat(robot);
                // 识别元素并标记
                Point matchPoint = recognizeAndMarkElement(resizedTemplateMat, screenMat);
                if (matchPoint != null) {
                    // 转换为 java.awt.Point 并返回中心点坐标
                    resultPoint = new java.awt.Point(
                            (int) matchPoint.x,
                            (int) matchPoint.y
                    );
                    log.info("找到图片，中心点坐标：({}, {})", resultPoint.x, resultPoint.y);
                    // 更新缓存
                    imageCache.put(cacheKey, matchPoint);
                    // 将矩形标记后的屏幕截图保存到临时文件中, 配置文件获取临时文件路径
                    fileService.saveMatToTempDir(screenMat, imagePath);
                    // 释放资源
                    screenMat.release();
                    // 跳出循环
                    break;
                }
                // 未找到, 释放资源
                screenMat.release();
                robot.delay(delayTime);
            }
            // 释放资源
            resizedTemplateMat.release();
            templateMat.release();
        } catch (AWTException e) {
            log.error("创建 Robot 对象失败：{}", e.getMessage());
            return resultPoint;
        }
        return resultPoint;
    }

    @Override
    public boolean findAndClickImage(String imagePath, int timeout) {
        // 获取配置文件中find image 的默认延迟
        int delayTime = gameAutoProperties.getImageRecognition().getInterval();
        // 调用 findImageCenter 方法
        java.awt.Point imageCenter = findImageCenter(imagePath, timeout, delayTime);
        if (imageCenter == null) {
            return false;
        }
        // 执行点击事件
        input.moveAndClick(imageCenter.x, imageCenter.y);
        return true;
    }

    @Override
    public boolean findImage(String imagePath) {
        int timeout = gameAutoProperties.getImageRecognition().getTimeout();
        int delayTime = gameAutoProperties.getImageRecognition().getInterval();
        return findImage(imagePath, timeout, delayTime);
    }

    @Override
    public boolean findImage(String imagePath, int timeout) {
        int delayTime = gameAutoProperties.getImageRecognition().getInterval();
        return findImage(imagePath, timeout, delayTime);
    }

    @Override
    public boolean findImage(String imagePath, int timeout, int delayTime) {
        java.awt.Point foundPoint = findImageCenter(imagePath, timeout, delayTime);
        return foundPoint != null;
    }


    private Point recognizeAndMarkElement(Mat templateMat, Mat screenMat) {
        // 将 Mat 转为灰度处理
        Mat screenGray = new Mat();
        Mat templateGray = new Mat();
        Imgproc.cvtColor(screenMat, screenGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(templateMat, templateGray, Imgproc.COLOR_BGR2GRAY);

        // 使用opencv的匹配算法
        Mat result = new Mat();
        Imgproc.matchTemplate(screenGray, templateGray, result, Imgproc.TM_CCOEFF_NORMED);
        Core.MinMaxLocResult minMaxResult = Core.minMaxLoc(result);

        // 左上角坐标
        Point bestLoc = minMaxResult.maxLoc;
        double bestMatchValue = minMaxResult.maxVal;
        // 返回结果
        Point resultPoint = null;
        // 判断匹配
        if (bestMatchValue >= gameAutoProperties.getSimilarityThreshold()) {
            log.info("匹配成功，匹配值：{}", bestMatchValue);
            // 绘制匹配的矩形在屏幕中
            markMatchPosition(screenMat, bestLoc, templateMat.cols(), templateMat.rows());
            // 获取匹配位置的中心点坐标
            resultPoint = new Point(bestLoc.x + (double) templateMat.cols() / 2, bestLoc.y + (double) templateMat.rows() / 2);
        }
        // 释放资源
        templateGray.release();
        screenGray.release();
        result.release();
        return resultPoint;
    }

    /**
     * 缩放模板图片
     *
     * @param templateMat 原始模板图片
     * @return 缩放后的模板图片
     */
    private Mat resizeTemplateImage(Mat templateMat) {
        // 获取模板图片的尺寸
        Size templateSize = templateMat.size();
        // 获取配置文件中的缩放比例
        double scale = gameAutoProperties.getDefaultScale();
        // 计算缩放后的尺寸
        Size newSize = new Size(templateSize.width * scale, templateSize.height * scale);
        // 创建一个空的 Mat 对象，用于存储缩放后的图片
        Mat resizedTemplateMat = new Mat();
        // 使用 OpenCV 的 resize 函数进行缩放
        Imgproc.resize(templateMat, resizedTemplateMat, newSize);
        return resizedTemplateMat;
    }

    /**
     * 捕获屏幕并转换为 Mat
     */
    private Mat captureScreenToMat(Robot robot) {
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
            return null;
        }

        Mat mat = new Mat(height, width, CvType.CV_8UC3);
        mat.put(0, 0, pixels);
        return mat;
    }

    /**
     * 在屏幕上标记匹配位置
     * @param screenMat 屏幕截图
     * @param topLeftLoc 匹配位置的左上角坐标
     * @param width 模板图片宽度
     * @param height 模板图片高度
     */
    private void markMatchPosition(Mat screenMat, Point topLeftLoc, int width, int height) {
        // 获取配置文件中矩形颜色
        Scalar rectColor = gameAutoProperties.getRectColor();
        // 获取配置文件中矩形宽度
        int rectThickness = gameAutoProperties.getImageRecognition().getRectThickness();
        // 计算右下角坐标
        Point bottomRightLoc = new Point(topLeftLoc.x +width, topLeftLoc.y + height);
        // 绘制矩形在屏幕上
        Imgproc.rectangle(screenMat, topLeftLoc, bottomRightLoc, rectColor, rectThickness);

    }

}
