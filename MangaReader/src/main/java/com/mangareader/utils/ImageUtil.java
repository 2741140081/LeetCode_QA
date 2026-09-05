package com.mangareader.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

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

        if (cacheFile.length() == 0) {
            log.warn("缓存文件大小为0，删除: {}", cacheFile.getAbsolutePath());
            cacheFile.delete();
            return null;
        }

        try (FileInputStream fis = new FileInputStream(cacheFile)) {
            BufferedImage bufferedImage = ImageIO.read(fis);
            if (bufferedImage != null) {
                return SwingFXUtils.toFXImage(bufferedImage, null);
            }
            log.warn("缓存文件解码返回null，文件可能已损坏: {}", cacheFile.getAbsolutePath());
        } catch (IOException e) {
            log.warn("从磁盘缓存加载图片失败(文件可能已损坏)，将删除缓存: {}", cacheFile.getAbsolutePath(), e);
            cacheFile.delete();
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
            File imageFile = new File(path);
            if (!imageFile.exists()) {
                log.error("图片文件不存在: {}", path);
                return null;
            }

            BufferedImage originalImage;
            String lowerPath = path.toLowerCase();
            if (lowerPath.endsWith(".webp")) {
                originalImage = readWebp(imageFile);
            } else {
                originalImage = ImageIO.read(imageFile);
            }

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
     * 使用 webp-imageio 读取 WebP 格式图片
     */
    private static BufferedImage readWebp(File webpFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(webpFile)) {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByMIMEType("image/webp");
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(ImageIO.createImageInputStream(fis));
                    return reader.read(0);
                } finally {
                    reader.dispose();
                }
            }
            log.error("未找到 WebP 图片读取器, 请确认 webp-imageio 依赖已正确引入");
            return null;
        }
    }

    /**
     * 从磁盘缓存加载图片（返回 BufferedImage，供内部缓存使用，避免 FX Image 中间层）
     * @param cacheFile 缓存文件
     * @return 加载的 BufferedImage，如果加载失败返回 null
     */
    public static BufferedImage loadFromDiskCacheAsBufferedImage(File cacheFile) {
        if (!cacheFile.exists() || !cacheFile.isFile()) {
            log.debug("缓存文件不存在: {}", cacheFile.getAbsolutePath());
            return null;
        }

        if (cacheFile.length() == 0) {
            log.warn("缓存文件大小为0，删除: {}", cacheFile.getAbsolutePath());
            cacheFile.delete();
            return null;
        }

        try (FileInputStream fis = new FileInputStream(cacheFile)) {
            BufferedImage bufferedImage = ImageIO.read(fis);
            if (bufferedImage != null) {
                return bufferedImage;
            }
            log.warn("缓存文件解码返回null，文件可能已损坏: {}", cacheFile.getAbsolutePath());
        } catch (IOException e) {
            log.warn("从磁盘缓存加载图片失败(文件可能已损坏)，将删除缓存: {}", cacheFile.getAbsolutePath(), e);
            cacheFile.delete();
        }
        return null;
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
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(img, null);
            saveToDiskCache(bufferedImage, cacheFile);
        } catch (Exception e) {
            log.error("保存图片到缓存失败: {}", cacheFile.getAbsolutePath(), e);
        }
    }

    /**
     * 保存 BufferedImage 到磁盘缓存（避免 FX Image 转换开销）
     * @param bufferedImage 要保存的 BufferedImage
     * @param cacheFile 缓存文件
     */
    public static void saveToDiskCache(BufferedImage bufferedImage, File cacheFile) {
        if (bufferedImage == null) {
            log.warn("尝试保存 null BufferedImage 到缓存");
            return;
        }

        File parentDir = cacheFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                log.error("创建缓存目录失败: {}", parentDir.getAbsolutePath());
                return;
            }
        }

        File tempFile = new File(cacheFile.getAbsolutePath() + ".tmp");
        try {
            if (!ImageIO.write(bufferedImage, "png", tempFile)) {
                log.error("无法写入缓存文件: {}", tempFile.getAbsolutePath());
                return;
            }

            if (tempFile.length() == 0) {
                log.error("写入缓存文件大小为0: {}", tempFile.getAbsolutePath());
                tempFile.delete();
                return;
            }

            if (cacheFile.exists()) {
                cacheFile.delete();
            }
            if (!tempFile.renameTo(cacheFile)) {
                log.error("重命名临时缓存文件失败: {} -> {}", tempFile.getAbsolutePath(), cacheFile.getAbsolutePath());
                tempFile.delete();
            }
        } catch (IOException e) {
            log.error("保存图片到缓存失败: {}", cacheFile.getAbsolutePath(), e);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * 优化的解码和缩放：返回 BufferedImage，供调用方灵活转换（如先写磁盘缓存再转 FX Image）
     * 使用子采样减少峰值内存（当 scale < 1.0 时）
     * @param path 图片路径
     * @param scale 缩放比例
     * @return 缩放后的 BufferedImage，调用方负责 flush
     */
    public static BufferedImage decodeAndScaleAsBufferedImage(String path, double scale) {
        try {
            File imageFile = new File(path);
            if (!imageFile.exists()) {
                log.error("图片文件不存在: {}", path);
                return null;
            }

            String lowerPath = path.toLowerCase();
            boolean isWebp = lowerPath.endsWith(".webp");

            if (isWebp) {
                return readWebp(imageFile);
            }

            // 尝试使用 ImageReader 子采样减少峰值内存
            try (var iis = ImageIO.createImageInputStream(imageFile)) {
                Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix(
                        lowerPath.substring(lowerPath.lastIndexOf('.') + 1));
                if (readers.hasNext()) {
                    ImageReader reader = readers.next();
                    try {
                        reader.setInput(iis, true, true);
                        int srcWidth = reader.getWidth(0);
                        int srcHeight = reader.getHeight(0);

                        if (scale < 1.0 && srcWidth > 0) {
                            int subsampling = Math.max(1, (int) (1.0 / scale));
                            if (subsampling > 1) {
                                var readParam = new javax.imageio.ImageReadParam();
                                readParam.setSourceSubsampling(subsampling, subsampling, 0, 0);
                                BufferedImage sampled = reader.read(0, readParam);

                                int targetW = (int) (srcWidth * scale);
                                int targetH = (int) (srcHeight * scale);
                                if (sampled.getWidth() != targetW || sampled.getHeight() != targetH) {
                                    BufferedImage scaled = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
                                    Graphics2D g2d = scaled.createGraphics();
                                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                                    g2d.drawImage(sampled, 0, 0, targetW, targetH, null);
                                    g2d.dispose();
                                    sampled.flush();
                                    return scaled;
                                }
                                return sampled;
                            }
                        }

                        return reader.read(0);
                    } finally {
                        reader.dispose();
                    }
                }
            }

            // 最终回退
            return ImageIO.read(imageFile);

        } catch (IOException e) {
            log.error("解码图片失败: {}", path, e);
            return null;
        }
    }

    /**
     * 优化的解码和缩放（返回 JavaFX Image）
     * @param path 图片路径
     * @param scale 缩放比例
     * @return 缩放后的 JavaFX Image
     */
    public static Image decodeAndScaleOptimized(String path, double scale) {
        BufferedImage buffered = decodeAndScaleAsBufferedImage(path, scale);
        if (buffered == null) return null;
        return SwingFXUtils.toFXImage(buffered, null);
    }

}
