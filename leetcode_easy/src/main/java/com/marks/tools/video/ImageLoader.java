package com.marks.tools.video;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    // 保留原有方法，但可以标记为deprecated或者删除
    @Deprecated
    public static List<BufferedImage> loadImages(String dir, int start, int end, int width, int height) {
        List<BufferedImage> batch = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            try {
                String path = dir + "\\img" + i + ".webp";
                BufferedImage original = ImageIO.read(new File(path));
                BufferedImage resized = resizeImage(original, width, height);
                batch.add(resized);
                // 及时释放原始图片引用
                original.flush();
            } catch (Exception e) {
                System.err.println("Error loading image " + i + ": " + e.getMessage());
            }
        }
        System.out.println("已经加载了" + batch.size() + "张图片, start[" + start + "], end[" + end + "]");
        System.out.println("batch 中开始 batch[0]" + batch.get(0));
        System.out.println("batch 中开始 batch[size - 1]" + batch.get(batch.size() - 1));
        return batch;
    }

    private static BufferedImage resizeImage(BufferedImage original, int maxWidth, int maxHeight) {
        // 计算保持宽高比的缩放比例
        double widthRatio = (double)maxWidth / original.getWidth();
        double heightRatio = (double)maxHeight / original.getHeight();
        double ratio = Math.min(widthRatio, heightRatio);

        // 计算新尺寸
        int newWidth = (int)(original.getWidth() * ratio);
        int newHeight = (int)(original.getHeight() * ratio);

        // 执行缩放
        // Image scaled = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(original, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resized;
    }
}

