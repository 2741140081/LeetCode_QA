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
import java.util.concurrent.*;

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
    // 图片尺寸（从第一张图片动态获取）
    private static int IMAGE_WIDTH = 0;
    private static int IMAGE_HEIGHT = 0;
    // PDF页面最大尺寸限制
    private static final int MAX_PDF_PAGE_SIZE = 14400;

    private static final int MAX_NUM_OF_IMAGE = 360;

    // 输入目录
    private static final String INPUT_DIR = "D:\\spider\\data\\test\\result";
    // 输出目录
    private static final String OUTPUT_DIR = "D:\\spider\\pdf\\result";
    // 每页PDF包含的图片数量
    private static final int IMAGES_PER_PDF = 200;
    // 线程池大小
    private static final int THREAD_POOL_SIZE = 6;
    // 是否需要将图片顺时针旋转90度
    private static final boolean NEED_ROTATE = false;
    public static void main(String[] args) {
        ImageToPdfConverterAdvanced converter = new ImageToPdfConverterAdvanced();
        long startTime = System.currentTimeMillis();
        converter.convertImagesToPdf();
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        // 耗时多少分钟多少秒
        System.out.println("耗时: " + spendTime / 60000 + " 分钟 " + spendTime % 60000 / 1000 + " 秒");
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

            // 读取第一张图片获取尺寸
            readImageDimensions(imageFiles.get(0));

            // 按文件名排序
            imageFiles.sort((f1, f2) -> {
                // 由于图片名称 类似于 img154.png/jpg/webp 这种格式, 需要提取其中的数字进行排序比较, 而不是直接比较string
                int id1 = getFileOrder(f1.getName());
                int id2 = getFileOrder(f2.getName());
                return Integer.compare(id1, id2);
            });

            // 创建递增编号的子目录
            String subDirName = createNextSubDirectory(outputDir);
            File targetDir = new File(outputDir, subDirName);
            
            System.out.println("PDF文件将保存到子目录: " + targetDir.getAbsolutePath());

            // 分页处理，每100张图片创建一个PDF
            createPagedPdfs(imageFiles, targetDir, subDirName);

        } catch (Exception e) {
            System.err.println("转换过程中发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建下一个递增编号的子目录
     */
    private String createNextSubDirectory(File parentDir) {
        // 获取当前目录下的所有子目录
        File[] subDirs = parentDir.listFiles(File::isDirectory);
        int maxDirNum = 0;
        
        if (subDirs != null) {
            for (File subDir : subDirs) {
                String dirName = subDir.getName();
                // 尝试解析目录名为数字
                try {
                    int dirNum = Integer.parseInt(dirName);
                    if (dirNum > maxDirNum) {
                        maxDirNum = dirNum;
                    }
                } catch (NumberFormatException e) {
                    // 忽略非数字命名的目录
                }
            }
        }
        
        // 生成下一个编号（三位数格式）
        int nextDirNum = maxDirNum + 1;
        String nextDirName = String.format("%03d", nextDirNum);
        
        // 创建子目录
        File newDir = new File(parentDir, nextDirName);
        if (!newDir.exists()) {
            newDir.mkdirs();
        }
        
        System.out.println("创建子目录: " + nextDirName + " (当前已有 " + maxDirNum + " 个目录)");
        return nextDirName;
    }

    /**
     * 分页创建多个PDF文件
     */
    private void createPagedPdfs(List<File> imageFiles, File targetDir, String subDirName) {
        int totalImages = imageFiles.size();
        int totalPages = (int) Math.ceil((double) totalImages / IMAGES_PER_PDF);

        System.out.println("总共 " + totalImages + " 张图片，将分为 " + totalPages + " 个PDF文件");

        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<?>> futures = new ArrayList<>();

        for (int page = 0; page < totalPages; page++) {
            int startIndex = page * IMAGES_PER_PDF;
            int endIndex = Math.min(startIndex + IMAGES_PER_PDF, totalImages);
            List<File> pageImages = imageFiles.subList(startIndex, endIndex);

            // 生成页号（两位数格式）
            String pageNum = String.format("%02d", page + 1);
            String outputFileName = subDirName + "_" + pageNum + ".pdf";
            String outputPath = targetDir.getAbsolutePath() + File.separator + outputFileName;

            final int currentPage = page + 1;
            Future<?> future = executor.submit(() -> {
                try {
                    System.out.println("[线程-" + Thread.currentThread().getName() + "] 开始处理第 " + currentPage + " 页，图片范围: " + (startIndex + 1) + "-" + endIndex);
                    createContinuousPdf(pageImages, outputPath);
                    System.out.println("[线程-" + Thread.currentThread().getName() + "] 第 " + currentPage + " 页完成: " + outputFileName);
                } catch (Exception e) {
                    System.err.println("[线程-" + Thread.currentThread().getName() + "] 第 " + currentPage + " 页处理失败: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            futures.add(future);
        }

        // 等待所有任务完成
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("等待任务完成时出错: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 关闭线程池
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("所有PDF文件创建完成！共 " + totalPages + " 个文件");
    }

    private int getFileOrder(String name) {
        int sIdx = name.indexOf("img") + 3;
        int eIdx = name.lastIndexOf(".");
        // 判定合法性
        if (sIdx < 0 || eIdx < 0 || sIdx >= eIdx) {
            return -1;
        }
        String substring = name.substring(sIdx, eIdx);
        return Integer.parseInt(substring);
    }

    /**
     * 读取图片尺寸
     */
    private void readImageDimensions(File imageFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            if (bufferedImage != null) {
                IMAGE_WIDTH = bufferedImage.getWidth();
                IMAGE_HEIGHT = bufferedImage.getHeight();
                System.out.println("检测到图片尺寸: " + IMAGE_WIDTH + " x " + IMAGE_HEIGHT);
            } else {
                throw new IOException("无法读取图片: " + imageFile.getName());
            }
        } catch (IOException e) {
            System.err.println("读取图片尺寸失败: " + e.getMessage());
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
                name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".jpg")
                        || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".webp"));

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
            ImageIO.write(bufferedImage, "WEBP", baos);
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
        int size = Math.min(imageFiles.size(), MAX_NUM_OF_IMAGE);

        // 计算总高度（图片高度 + 很小的间隔）
        int imageSpacing = 1; // 2像素间隔
        float totalHeight = size * IMAGE_HEIGHT + (size - 1) * imageSpacing;
        float pageWidth = IMAGE_WIDTH;

        // 检查是否超过PDF页面尺寸限制,如果超过则压缩图片
        if (totalHeight > MAX_PDF_PAGE_SIZE || pageWidth > MAX_PDF_PAGE_SIZE) {
            float scale = Math.min(
                (float) MAX_PDF_PAGE_SIZE / pageWidth,
                (float) MAX_PDF_PAGE_SIZE / totalHeight
            );
            // 使用略小的缩放比例以确保不超过限制(避免浮点数精度问题)
            scale *= 0.9999f;
            pageWidth *= scale;
            totalHeight *= scale;
            System.out.println("检测到超出PDF页面限制,按比例 " + String.format("%.4f", scale) + " 压缩图片");
        }
                
        // 再次确保不超过限制(向下取整)
        pageWidth = Math.min(pageWidth, MAX_PDF_PAGE_SIZE);
        totalHeight = Math.min(totalHeight, MAX_PDF_PAGE_SIZE);

        // 创建足够高的页面
        Rectangle pageSize = new Rectangle(pageWidth, totalHeight);
        Document document = new Document(pageSize, 0, 0, 0, 0);

        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // 连续添加所有图片
        float currentY = totalHeight - (IMAGE_HEIGHT * (totalHeight / (size * IMAGE_HEIGHT + (size - 1) * imageSpacing))); // 从顶部开始
        float scaledImageHeight = IMAGE_HEIGHT * (totalHeight / (size * IMAGE_HEIGHT + (size - 1) * imageSpacing));
        float scaledImageWidth = pageWidth;
        float scaledSpacing = imageSpacing * (totalHeight / (size * IMAGE_HEIGHT + (size - 1) * imageSpacing));

        for (int i = 0; i < size; i++) {
            File imageFile = imageFiles.get(i);
            System.out.println("正在处理图片 " + (i + 1) + ": " + imageFile.getName());

            // 读取图片
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            // 如果需要旋转，先顺时针旋转90度
            if (NEED_ROTATE) {
                bufferedImage = rotateImage90Degrees(bufferedImage);
            }

            // 调整图片大小
            if (bufferedImage.getWidth() != IMAGE_WIDTH || bufferedImage.getHeight() != IMAGE_HEIGHT) {
                bufferedImage = resizeImage(bufferedImage, IMAGE_WIDTH, IMAGE_HEIGHT);
            }

            bufferedImage = addIndexNumber(bufferedImage, i);

            // 转换为PDF Image对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "JPEG", baos);
            Image pdfImage = Image.getInstance(baos.toByteArray());

            // 设置图片位置
            pdfImage.scaleAbsolute(scaledImageWidth, scaledImageHeight);
            pdfImage.setAbsolutePosition(0, currentY);

            // 添加到PDF
            document.add(pdfImage);

            // 更新Y坐标（向下移动）
            currentY -= (scaledImageHeight + scaledSpacing);

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

    /**
     * 为图片添加索引编号
     */
    private BufferedImage addIndexNumber(BufferedImage image, int index) {
        BufferedImage imageWithText = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imageWithText.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, null);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));

        String text = String.valueOf(index);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int x = image.getWidth() - textWidth - 10;
        int y = image.getHeight() - textHeight + fm.getAscent() - 10;

        g2d.drawString(text, x, y);
        g2d.dispose();

        return imageWithText;
    }

    /**
     * 将图片顺时针旋转90度
     */
    private BufferedImage rotateImage90Degrees(BufferedImage original) {
        int width = original.getWidth();
        int height = original.getHeight();
        
        // 创建新的图片，宽高互换
        BufferedImage rotated = new BufferedImage(height, width, original.getType());
        Graphics2D g2d = rotated.createGraphics();
        
        // 设置旋转中心点为新图片的中心
        g2d.translate((height - width) / 2, (height - width) / 2);
        // 顺时针旋转90度
        g2d.rotate(Math.toRadians(90), width / 2.0, height / 2.0);
        
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, null);
        g2d.dispose();
        
        return rotated;
    }
}
