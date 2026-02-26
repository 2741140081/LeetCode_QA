package com.marks.tools.img2pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageToPdfConverterAdvanced </p>
 * <p>描述:
 * 高级图片转PDF转换器（需要iText依赖）
 * 将指定目录下的多张图片(1270*720)竖向合并后转换为PDF文件
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 11:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ImageToPdfConverterAdvanced {
    // 图片尺寸
    private static final int IMAGE_WIDTH = 1270;
    private static final int IMAGE_HEIGHT = 720;

    // 输入目录
    private static final String INPUT_DIR = "D:\\images\\pdf";
    // 输出目录
    private static final String OUTPUT_DIR = "D:\\images\\pdf\\result";

    public static void main(String[] args) {
        ImageToPdfConverterAdvanced converter = new ImageToPdfConverterAdvanced();
        converter.convertImagesToPdf();
    }

    /**
     * 转换图片为PDF
     */
    public void convertImagesToPdf() {
        try {
            // 创建输出目录
            File outputDir = new File(OUTPUT_DIR);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            // 获取输入目录下的所有JPEG图片
            List<File> imageFiles = getImageFiles(INPUT_DIR);

            if (imageFiles.isEmpty()) {
                System.out.println("未找到任何图片文件");
                return;
            }

            System.out.println("找到 " + imageFiles.size() + " 张图片");

            // 按文件名排序
            imageFiles.sort((f1, f2) -> {
                String name1 = f1.getName().toLowerCase();
                String name2 = f2.getName().toLowerCase();
                return name1.compareTo(name2);
            });

            // 创建PDF文档
            String outputFileName = "merged_images_" + System.currentTimeMillis() + ".pdf";
            String outputPath = OUTPUT_DIR + File.separator + outputFileName;

            // createPdfFromImages(imageFiles, outputPath);
            createContinuousPdf(imageFiles, outputPath);
            System.out.println("PDF文件已保存到: " + outputPath);

        } catch (Exception e) {
            System.err.println("转换过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取指定目录下的所有JPEG图片文件
     */
    private List<File> getImageFiles(String directoryPath) {
        List<File> imageFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("目录不存在: " + directoryPath);
            return imageFiles;
        }

        File[] files = directory.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".jpg"));

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    imageFiles.add(file);
                }
            }
        }

        return imageFiles;
    }

    /**
     * 直接从图片创建PDF（每张图片一页）
     */
    private void createPdfFromImages(List<File> imageFiles, String outputPath)
            throws DocumentException, IOException {

        // 创建PDF文档，使用第一个图片的尺寸作为页面尺寸
        Rectangle pageSize = new Rectangle(IMAGE_WIDTH, IMAGE_HEIGHT);
        Document document = new Document(pageSize);

        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // 逐个添加图片到PDF
        for (int i = 0; i < imageFiles.size(); i++) {
            File imageFile = imageFiles.get(i);
            System.out.println("正在处理图片: " + imageFile.getName());

            // 读取图片
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            // 调整图片大小（如果需要）
            if (bufferedImage.getWidth() != IMAGE_WIDTH || bufferedImage.getHeight() != IMAGE_HEIGHT) {
                bufferedImage = resizeImage(bufferedImage, IMAGE_WIDTH, IMAGE_HEIGHT);
            }

            // 转换为PDF Image对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "JPEG", baos);
            Image pdfImage = Image.getInstance(baos.toByteArray());

            // 设置图片尺寸和位置
            pdfImage.scaleToFit(IMAGE_WIDTH, IMAGE_HEIGHT);
            pdfImage.setAbsolutePosition(0, 0);

            // 添加到PDF
            document.add(pdfImage);

            // 如果不是最后一张图片，添加新页面
            if (i < imageFiles.size() - 1) {
                document.newPage();
            }

            // 释放资源
            bufferedImage.flush();
        }

        document.close();
    }

    /**
     * 创建连续长图PDF - 最紧凑的方式
     */
    private void createContinuousPdf(List<File> imageFiles, String outputPath)
            throws DocumentException, IOException {

        // 计算总高度（图片高度 + 很小的间隔）
        int imageSpacing = 2; // 2像素间隔
        float totalHeight = imageFiles.size() * IMAGE_HEIGHT + (imageFiles.size() - 1) * imageSpacing;
        float pageWidth = IMAGE_WIDTH;

        // 创建足够高的页面
        Rectangle pageSize = new Rectangle(pageWidth, totalHeight);
        Document document = new Document(pageSize, 0, 0, 0, 0);

        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // 连续添加所有图片
        float currentY = totalHeight - IMAGE_HEIGHT; // 从顶部开始

        for (int i = 0; i < imageFiles.size(); i++) {
            File imageFile = imageFiles.get(i);
            System.out.println("正在处理图片 " + (i + 1) + ": " + imageFile.getName());

            // 读取图片
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            // 调整图片大小
            if (bufferedImage.getWidth() != IMAGE_WIDTH || bufferedImage.getHeight() != IMAGE_HEIGHT) {
                bufferedImage = resizeImage(bufferedImage, IMAGE_WIDTH, IMAGE_HEIGHT);
            }

            // 转换为PDF Image对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "JPEG", baos);
            Image pdfImage = Image.getInstance(baos.toByteArray());

            // 设置图片位置
            pdfImage.scaleAbsolute(IMAGE_WIDTH, IMAGE_HEIGHT);
            pdfImage.setAbsolutePosition(0, currentY);

            // 添加到PDF
            document.add(pdfImage);

            // 更新Y坐标（向下移动）
            currentY -= (IMAGE_HEIGHT + imageSpacing);

            // 释放资源
            bufferedImage.flush();
        }

        document.close();
        System.out.println("创建了连续长图PDF，总高度: " + totalHeight + " 像素");
    }

    /**
     * 调整图片大小
     */
    private BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resized;
    }
}
