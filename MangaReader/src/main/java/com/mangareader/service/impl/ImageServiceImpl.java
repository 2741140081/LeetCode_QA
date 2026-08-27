package com.mangareader.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mangareader.constant.ImageConstant;
import com.mangareader.service.ImageService;
import com.mangareader.utils.ImageUtil;
import jakarta.annotation.PostConstruct;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    // todo: 将多线程统一放入一个文件
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
    );;

    @Value("${manga.storage.cache-root}")
    private String diskCacheDir; // 硬盘缓存目录

    private Cache<String, Image> memoryCache; // 内存缓存

    @PostConstruct
    @Override
    public void init() {
        // 初始化内存缓存，最大缓存50张图片
        memoryCache = CacheBuilder.newBuilder()
                .maximumSize(ImageConstant.MAX_MEMORY_CACHE_SIZE)
                .build();

        // 初始化磁盘缓存目录
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
        // 1. 查内存缓存
        try {
            Image cachedImg = memoryCache.getIfPresent(path + "_" + scale);
            if (cachedImg != null) return cachedImg;

            // 2. 查磁盘缓存
            String cacheKey = path.replaceAll("[^a-zA-Z0-9]", "_") + "_" + scale;
            File cacheFile = new File(diskCacheDir, cacheKey + ".dat");
            if (cacheFile.exists()) {
                Image img = ImageUtil.loadFromDiskCache(cacheFile);
                if (img != null) {
                    memoryCache.put(path + "_" + scale, img);
                    return img;
                }
                // 如果从缓存加载失败，删除缓存文件并继续加载原始图片
                cacheFile.delete();
            }

            // 3. 磁盘读取解码缩放
            Image img = ImageUtil.decodeAndScale(path, scale);
            if (img == null) {
                log.warn("图片解码失败, 返回默认错误图 path:{}", path);
                return getDefaultErrorImage();
            }
            // 4. 写入磁盘缓存
            ImageUtil.saveToDiskCache(img, cacheFile);
            memoryCache.put(path + "_" + scale, img);
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
        memoryCache.invalidate(path);
    }


    private Image getDefaultErrorImage() {
        try {
            // 从类路径加载默认错误图片
            InputStream inputStream = getClass().getResourceAsStream(ImageConstant.DEFAULT_ERROR_IMAGE_PATH);
            if (inputStream != null) {
                return new Image(inputStream);
            }
            log.warn("默认错误图片资源不存在: {}", ImageConstant.DEFAULT_ERROR_IMAGE_PATH);
        } catch (Exception e) {
            log.error("加载默认错误图片失败", e);
        }
        // 后备方案：返回一个简单的占位图（1x1透明PNG的Base64编码）
        return new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==");
    }
}
