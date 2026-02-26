package com.marks.tools.download_cartoon.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: PdfConverterUtils </p>
 * <p>描述: PDF转换工具类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 15:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class PdfConverterUtils {

    /**
     * 将章节图片合并为PDF
     * @param imageFiles 图片文件列表
     * @param outputPath 输出PDF路径
     * @param chapterTitle 章节标题
     */
    public static void convertImagesToPdf(List<File> imageFiles, String outputPath, String chapterTitle) {
        try {
            if (imageFiles == null || imageFiles.isEmpty()) {
                System.out.println("章节 " + chapterTitle + " 没有图片文件");
                return;
            }

            // 按文件名排序确保顺序正确
            imageFiles.sort((f1, f2) -> {
                String name1 = extractNumberFromFilename(f1.getName());
                String name2 = extractNumberFromFilename(f2.getName());
                return name1.compareTo(name2);
            });

            System.out.println("开始合并章节 " + chapterTitle + " 的 " + imageFiles.size() + " 张图片");

            // 合并图片
            BufferedImage mergedImage = mergeImagesVertically(imageFiles);

            // 保存为图片格式（实际项目中应使用专业PDF库）
            saveMergedImage(mergedImage, outputPath);

            System.out.println("章节 " + chapterTitle + " PDF文件已保存到: " + outputPath);

        } catch (Exception e) {
            System.err.println("转换章节 " + chapterTitle + " 为PDF时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 竖向合并多张图片
     */
    private static BufferedImage mergeImagesVertically(List<File> imageFiles) throws IOException {
        // 计算第一张图片的尺寸作为基准
        BufferedImage firstImage = ImageIO.read(imageFiles.get(0));
        int imageWidth = firstImage.getWidth();
        int imageHeight = firstImage.getHeight();
        int totalHeight = imageFiles.size() * imageHeight;

        // 创建合并后的图片
        BufferedImage mergedImage = new BufferedImage(imageWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = mergedImage.createGraphics();

        // 设置背景为白色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imageWidth, totalHeight);

        // 逐个绘制图片
        int currentY = 0;
        for (int i = 0; i < imageFiles.size(); i++) {
            File imageFile = imageFiles.get(i);
            System.out.println("正在处理图片: " + imageFile.getName());

            BufferedImage image = ImageIO.read(imageFile);

            // 如果尺寸不匹配，调整图片大小
            if (image.getWidth() != imageWidth || image.getHeight() != imageHeight) {
                image = resizeImage(image, imageWidth, imageHeight);
            }

            // 绘制到合并图片上
            g2d.drawImage(image, 0, currentY, null);
            currentY += imageHeight;

            // 释放资源
            image.flush();
        }

        g2d.dispose();
        return mergedImage;
    }

    /**
     * 调整图片大小
     */
    private static BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resized;
    }

    /**
     * 保存合并后的图片
     */
    private static void saveMergedImage(BufferedImage image, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        // 确保输出目录存在
        File parentDir = outputFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        // 保存为JPEG格式
        String formatName = outputPath.toLowerCase().endsWith(".png") ? "PNG" : "JPEG";
        ImageIO.write(image, formatName, outputFile);
    }

    /**
     * 从文件名中提取数字部分
     */
    private static String extractNumberFromFilename(String filename) {
        return filename.replaceAll("[^0-9]", "");
    }
}
