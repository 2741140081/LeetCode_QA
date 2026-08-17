package com.mangareader.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageUtil </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */


/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageUtil </p>
 * <p>描述: 图片工具类，提供图片加载、缩放和缓存功能 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public class ImageUtil {

    /**
     * 从磁盘缓存加载图片
     * @param cacheFile 缓存文件
     * @return 加载的图片，如果加载失败返回 null
     */
    public static Image loadFromDiskCache(File cacheFile) {
        if (!cacheFile.exists() || !cacheFile.isFile()) {
            log.debug("缓存文件不存在: {}", cacheFile.getAbsolutePath());
            return null;
        }

        try (FileInputStream fis = new FileInputStream(cacheFile)) {
            BufferedImage bufferedImage = ImageIO.read(fis);
            if (bufferedImage != null) {
                return SwingFXUtils.toFXImage(bufferedImage, null);
            }
        } catch (IOException e) {
            log.error("从磁盘缓存加载图片失败: {}", cacheFile.getAbsolutePath(), e);
        }
        return null;
    }

    /**
     * 解码并缩放图片
     * @param path 图片路径
     * @param scale 缩放比例
     * @return 缩放后的图片
     */
    public static Image decodeAndScale(String path, double scale) {
        try {
            // 加载原始图片
            BufferedImage originalImage = ImageIO.read(new File(path));
            if (originalImage == null) {
                log.error("无法读取图片文件: {}", path);
                return null;
            }

            // 如果缩放比例为 1，直接返回
            if (scale == 1.0) {
                return SwingFXUtils.toFXImage(originalImage, null);
            }

            // 计算缩放后的尺寸
            int scaledWidth = (int) (originalImage.getWidth() * scale);
            int scaledHeight = (int) (originalImage.getHeight() * scale);

            // 创建缩放后的图片
            BufferedImage scaledImage = new BufferedImage(
                    scaledWidth,
                    scaledHeight,
                    BufferedImage.TYPE_INT_ARGB
            );

            // 使用高质量缩放
            Graphics2D g2d = scaledImage.createGraphics();
            try {
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            } finally {
                g2d.dispose();
            }

            return SwingFXUtils.toFXImage(scaledImage, null);
        } catch (IOException e) {
            log.error("解码图片失败: {}", path, e);
            return null;
        }
    }

    /**
     * 保存图片到磁盘缓存
     * @param img 要保存的图片
     * @param cacheFile 缓存文件
     */
    public static void saveToDiskCache(Image img, File cacheFile) {
        if (img == null) {
            log.warn("尝试保存 null 图片到缓存");
            return;
        }

        // 确保缓存目录存在
        File parentDir = cacheFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                log.error("创建缓存目录失败: {}", parentDir.getAbsolutePath());
                return;
            }
        }

        try {
            // 将 JavaFX Image 转换为 BufferedImage
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);

            // 保存为 PNG 格式
            if (!ImageIO.write(bufferedImage, "png", cacheFile)) {
                log.error("无法写入缓存文件: {}", cacheFile.getAbsolutePath());
            }
        } catch (IOException e) {
            log.error("保存图片到缓存失败: {}", cacheFile.getAbsolutePath(), e);
        }
    }
}
