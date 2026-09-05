package com.mangareader.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mangareader.constant.ImageConstant;
import com.mangareader.service.ImageService;
import com.mangareader.utils.ImageUtil;
import jakarta.annotation.PostConstruct;
import javafx.application.Platform;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageServiceImpl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final ExecutorService imageLoadExecutor = new ThreadPoolExecutor(
            4, 8, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),
            new ThreadFactory() {
                private final AtomicInteger num = new AtomicInteger(1);
                @Override public Thread newThread(Runnable r) {
                    return new Thread(r, "img-loader-" + num.getAndIncrement());
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Value("${manga.storage.cache-root}")
    private String diskCacheDir;

    @Value("${manga.image.max-memory-cache-size:30}")
    private int maxMemoryCacheSize;

    private Cache<String, Image> memoryCache;

    @PostConstruct
    @Override
    public void init() {
        memoryCache = CacheBuilder.newBuilder()
                .maximumSize(maxMemoryCacheSize)
                .removalListener(notification -> {
                    // Guava Cache 淘汰时移除引用，GC 会自动回收 Image 及其本地内存
                })
                .build();

        File cacheDir = new File(diskCacheDir);
        if (!cacheDir.exists()) {
            boolean created = cacheDir.mkdirs();
            if (!created) {
                log.error("创建磁盘缓存目录失败: {}", diskCacheDir);
                throw new IllegalStateException("无法创建磁盘缓存目录: " + diskCacheDir);
            }
            log.info("成功创建磁盘缓存目录: {}", diskCacheDir);
        }
    }

    @Override
    public Image loadImage(String path, double scale) {
        try {
            String cacheKey = path + "_" + scale;
            Image cachedImg = memoryCache.getIfPresent(cacheKey);
            if (cachedImg != null) return cachedImg;

            String diskCacheKey = path.replaceAll("[^a-zA-Z0-9]", "_") + "_" + scale;
            File cacheFile = new File(diskCacheDir, diskCacheKey + ".dat");

            // 尝试从磁盘缓存加载（直接得到 BufferedImage，避免 FX Image 中间层）
            if (cacheFile.exists()) {
                BufferedImage buffered = ImageUtil.loadFromDiskCacheAsBufferedImage(cacheFile);
                if (buffered != null) {
                    Image img = SwingFXUtils.toFXImage(buffered, null);
                    memoryCache.put(cacheKey, img);
                    return img;
                }
            }

            // 使用优化的解码方法：子采样 + 尽早释放原始 BufferedImage
            BufferedImage decoded = ImageUtil.decodeAndScaleAsBufferedImage(path, scale);
            if (decoded == null) {
                log.warn("图片解码失败, 返回默认错误图 path:{}", path);
                return getDefaultErrorImage();
            }

            // 先将 BufferedImage 写入磁盘缓存（避免后续 FX Image -> BufferedImage 的额外转换）
            ImageUtil.saveToDiskCache(decoded, cacheFile);
            // 再转换为 JavaFX Image
            Image img = SwingFXUtils.toFXImage(decoded, null);
            decoded.flush();

            memoryCache.put(cacheKey, img);
            return img;
        } catch (OutOfMemoryError e) {
            log.warn("图片加载触发内存溢出，执行缓存回收", e);
            memoryCache.invalidateAll();
            System.gc();
            return loadImage(path, scale);
        }
    }

    @Override
    public CompletableFuture<Image> loadImageAsync(String path, double scale) {
        return CompletableFuture.supplyAsync(() -> loadImage(path, scale), imageLoadExecutor)
                .exceptionally(e -> {
                    log.error("异步加载图片失败 path:{}", path, e);
                    return getDefaultErrorImage();
                });
    }

    @Override
    public void evictCache(String path) {
        // 淘汰所有匹配路径前缀的缓存条目（兼容不同 scale 后缀）
        for (String key : memoryCache.asMap().keySet()) {
            if (key.startsWith(path)) {
                memoryCache.invalidate(key);
            }
        }
    }

    @Override
    public void evictByPaths(List<String> paths) {
        if (paths == null || paths.isEmpty()) {
            return;
        }
        int count = 0;
        for (String path : paths) {
            // 使用路径前缀匹配，确保能淘汰所有 scale 的缓存条目
            for (String key : memoryCache.asMap().keySet()) {
                if (key.startsWith(path)) {
                    memoryCache.invalidate(key);
                    count++;
                }
            }
        }
        log.info("已淘汰 {} 条图片缓存", count);
    }

    @Override
    public void evictAll() {
        long size = memoryCache.size();
        memoryCache.invalidateAll();
        System.gc();
        log.info("已清空全部图片缓存, 释放 {} 条记录", size);
    }

    private Image getDefaultErrorImage() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(ImageConstant.DEFAULT_ERROR_IMAGE_PATH);
            if (inputStream != null) {
                return new Image(inputStream);
            }
            log.warn("默认错误图片资源不存在: {}", ImageConstant.DEFAULT_ERROR_IMAGE_PATH);
        } catch (Exception e) {
            log.error("加载默认错误图片失败", e);
        }
        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==");
    }
}
