package com.mangareader.service.impl;

import com.mangareader.config.MangaDownloadConfig;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.MangaImageDownloadService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

@Slf4j
@Service
public class MangaImageDownloadServiceImpl implements MangaImageDownloadService {

    private final MangaImageMapper mapper;
    private final MangaDownloadConfig config;
    private final OkHttpClient httpClient;

    public MangaImageDownloadServiceImpl(MangaImageMapper mapper,
                                         MangaDownloadConfig config,
                                         @Qualifier("mangaDownloadHttpClient") OkHttpClient httpClient) {
        this.mapper = mapper;
        this.config = config;
        this.httpClient = httpClient;
    }

    /**
     * 核心下载逻辑 - 支持断点续传
     */
    @Override
    public void downloadImage(MangaImage task) {
        long imageId = task.getImageId();
        log.info("[Image-{}] 开始执行漫画图片定时下载: {}", imageId, task.getDownloadUrl());

        Request.Builder requestBuilder = new Request.Builder()
                .url(task.getDownloadUrl())
                .header("User-Agent", "Mozilla/5.0 MangaScheduledDownloader/1.0");

        // 如果有未完成的已下载数据, 自动启用断点续传
        if (task.getDownloadedSize() != null && task.getDownloadedSize() > 0) {
            requestBuilder.header("Range", "bytes=" + task.getDownloadedSize() + "-");
        }

        Request request = requestBuilder.build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                handleFailure(imageId, "HTTP 状态码异常: " + response.code());
                return;
            }

            long contentLength = response.body().contentLength();
            long startPos = task.getDownloadedSize() != null ? task.getDownloadedSize() : 0;

            // 生成合法文件名, 避免冲突
            String fileName = extractFileName(task.getDownloadUrl(), response, imageId);
            Path targetPath = Paths.get(task.getImageUrl()).resolve(fileName);
            targetPath = resolveConflict(targetPath);

            // 确保目录存在
            try {
                Files.createDirectories(targetPath.getParent());
            } catch (IOException e) {
                handleFailure(imageId, "创建目录失败: " + e.getMessage());
                return;
            }

            // 使用NIO缓冲流追加写入, 适配断点续传场景
            try (BufferedOutputStream fos = new BufferedOutputStream(
                    new FileOutputStream(targetPath.toFile(), true), config.getBufferSize())) {

                long totalDownloaded = startPos;
                try (BufferedInputStream bis = new BufferedInputStream(
                        response.body().byteStream(), config.getBufferSize())) {

                    byte[] buffer = new byte[config.getBufferSize()];
                    int bytesRead;
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                        totalDownloaded += bytesRead;

                        // 每累计下载1MB更新一次数据库进度, 大幅降低数据库压力
                        if (totalDownloaded % (1024 * 1024) < bytesRead) {
                            mapper.updateStatus(imageId, 1, totalDownloaded, null);
                        }
                    }
                    fos.flush();
                }

                // 下载完成,标记为成功
                long finalSize = contentLength > 0 ? contentLength : targetPath.toFile().length();
                mapper.markAsSuccess(imageId, finalSize);
                log.info("[Image-{}] 漫画图片定时下载完成: {} -> {} (总大小: {} 字节)",
                        imageId, task.getDownloadUrl(), targetPath, finalSize);

            }

        } catch (IOException e) {
            // 断点续传中途异常, 保留已下载进度, 等待下一次定时任务自动重试
            if (task.getDownloadedSize() != null && task.getDownloadedSize() > 0) {
                mapper.updateStatus(imageId, 1, task.getDownloadedSize(), "断点续传中途异常: " + e.getMessage());
                log.warn("[Image-{}] 断点续传中断, 已保存下载进度: {} 字节, 等待下次定时任务重试", imageId, task.getDownloadedSize());
            } else {
                handleFailure(imageId, e.getMessage());
            }
        } catch (Exception e) {
            handleFailure(imageId, e.getMessage());
        }
    }

    /**
     * 下载失败处理,自动执行重试机制
     */
    @Override
    public void handleFailure(long imageId, String errorMsg) {
        log.error("[Image-{}] 漫画图片下载失败: {}", imageId, errorMsg);

        MangaImage task = mapper.selectById(imageId);
        if (task == null) return;

        int retryCount = task.getRetryCount() != null ? task.getRetryCount() : 0;
        if (retryCount < config.getMaxRetry()) {
            // 未达到最大重试次数,重置为待下载状态,等待下一次定时调度自动重试
            mapper.incrementRetryCount(imageId);
            mapper.updateStatus(imageId, 1, task.getDownloadedSize(), errorMsg);
            log.info("[Image-{}] 已自动安排定时重试, 当前重试次数: {}/{}", imageId, retryCount + 1, config.getMaxRetry());
        } else {
            // 达到最大重试次数,标记为永久失败
            mapper.updateStatus(imageId, 2, task.getDownloadedSize(), errorMsg);
            log.error("[Image-{}] 超过最大重试次数{}, 标记为永久失败", imageId, config.getMaxRetry());
        }
    }

    /**
     * 从URL或Content-Disposition提取文件名
     */
    private String extractFileName(String url, Response response, long imageId) {
        // 优先从响应头Content-Disposition获取文件名
        String contentDisp = response.header("Content-Disposition");
        if (contentDisp != null && contentDisp.contains("filename=")) {
            String name = contentDisp.split("filename=")[1]
                    .replace("\"", "").trim();
            if (!name.isEmpty()) {
                return sanitizeFileName(name);
            }
        }

        // 从图片URL中提取文件名
        try {
            String path = new URL(url).getPath();
            String fileName = path.substring(path.lastIndexOf('/') + 1);
            if (fileName.isEmpty() || !fileName.contains(".")) {
                fileName = "image_" + imageId + "_" + System.currentTimeMillis() + ".jpg";
            }
            return sanitizeFileName(fileName);
        } catch (Exception e) {
            return "image_" + imageId + "_" + System.currentTimeMillis() + ".jpg";
        }
    }

    /**
     * 文件名清洗, 防止路径注入和非法字符
     */
    private String sanitizeFileName(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]", "_")
                .replaceAll("\\s+", "_")
                .substring(0, Math.min(name.length(), 200));
    }

    /**
     * 文件名冲突自动处理, 自动追加序号后缀
     */
    private Path resolveConflict(Path path) {
        if (!Files.exists(path)) return path;
        String name = path.getFileName().toString();
        int dotIndex = name.lastIndexOf('.');
        String base = dotIndex > 0 ? name.substring(0, dotIndex) : name;
        String ext = dotIndex > 0 ? name.substring(dotIndex) : "";

        int counter = 1;
        Path newPath;
        do {
            newPath = path.getParent().resolve(base + "_" + counter++ + ext);
        } while (Files.exists(newPath));

        return newPath;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("pending", mapper.countPendingTasks());
        stats.put("downloading", mapper.countDownloadingTasks());
        stats.put("maxRetry", config.getMaxRetry());
        stats.put("scanCron", config.getScanCron());
        return stats;
    }
}
