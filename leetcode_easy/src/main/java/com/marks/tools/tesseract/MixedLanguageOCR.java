package com.marks.tools.tesseract;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称: 中英混合的文字识别 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/6 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MixedLanguageOCR {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    static {
        // 加载 OpenCV 库
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public String TesseractOCRImage(String imagePath) {
        // 1. OpenCV 预处理
        Mat processedImage = preprocessImage(imagePath);

        // 保存预处理后的图片（可选，用于调试）
        Imgcodecs.imwrite("D:\\images\\tesseract\\temp\\preprocessed_" + LocalDateTime.now().format(formatter) + ".png", processedImage);

        // 2. Tesseract 识别
        String result = recognizeText(processedImage);

        return result;
    }

    /**
     * OpenCV 预处理（灰度化 + 二值化）
     */
    private Mat preprocessImage(String imagePath) {
        // 读取图片
        Mat src = Imgcodecs.imread(imagePath);
        if (src.empty()) {
            throw new IllegalArgumentException("图片加载失败！");
        }

        // 灰度化
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        // 二值化（自适应阈值）
        Mat binary = new Mat();
        Imgproc.adaptiveThreshold(
                gray,
                binary,
                255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY,
                11,
                2
        );

        return binary;
    }

    /**
     * Tesseract 文字识别（中英文混合）
     */
    private String recognizeText(Mat image) {
        // 保存 Mat 为临时文件（Tesseract 需要文件路径）
        String tempPath = "D:\\images\\tesseract\\temp\\temp.png";
        Imgcodecs.imwrite(tempPath, image);

        File outputFile = new File("D:\\images\\tesseract\\result\\output_" + LocalDateTime.now().format(formatter) + ".txt");
        try(FileWriter writer = new FileWriter(outputFile)) {
            Tesseract tesseract = new Tesseract();

            // 设置语言包路径（需提前下载 chi_sim.traineddata 和 eng.traineddata）
            tesseract.setDatapath("D:\\Tesseract_OCR\\tessdata");

            // 中英文混合识别
            tesseract.setLanguage("chi_sim+eng");

            // 参数优化
            tesseract.setPageSegMode(6);  // PSM_SINGLE_BLOCK（适合单栏文本）
            tesseract.setOcrEngineMode(1); // OEM_LSTM_ONLY（LSTM 引擎）

            // 执行识别
            String result = tesseract.doOCR(new File(tempPath));

            // 保存结果到文本文件
            writer.write(result);
            System.out.println("识别结果已保存至: " + outputFile.getAbsolutePath());
            return result;
        } catch (Exception e) {
            throw new RuntimeException("OCR 识别失败: " + e.getMessage());
        } finally {
            // 删除临时文件
            new File(tempPath).delete();
        }
    }
}
