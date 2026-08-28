package com.mangareader.service.impl;

import com.mangareader.config.MangaDownloadConfig;
import com.mangareader.enums.ProcessStatus;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.MangaImageDownloadService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
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

        // todo: 完善 requestBuilder 信息
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

            // 从下载URL中提取文件扩展名, 拼接到文件名
            String fileName = task.getImageName();
            String extension = extractExtension(task.getDownloadUrl());
            if (extension != null && !extension.isEmpty()) {
                fileName = fileName + extension;
            }
            Path targetPath = Paths.get(task.getImageUrl()).resolve(fileName);
            resolveConflict(targetPath); // 删除原文件

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
                mapper.updateStatus(imageId, ProcessStatus.PENDING.getCode(), task.getDownloadedSize(), "断点续传中途异常: " + e.getMessage());
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
            mapper.updateStatus(imageId, ProcessStatus.PENDING.getCode(), task.getDownloadedSize(), errorMsg);
            log.info("[Image-{}] 已自动安排定时重试, 当前重试次数: {}/{}", imageId, retryCount + 1, config.getMaxRetry());
        } else {
            // 达到最大重试次数,标记为永久失败
            mapper.updateStatus(imageId, ProcessStatus.FAILED.getCode(), task.getDownloadedSize(), errorMsg);
            log.error("[Image-{}] 超过最大重试次数{}, 标记为永久失败", imageId, config.getMaxRetry());
        }
    }

    /**
     * 文件名冲突自动处理, 自动追加序号后缀
     */
    private void resolveConflict(Path path) {
        if (!Files.exists(path)) return;
        try {
            Files.delete(path);
            log.info("文件已存在，采用覆盖重写模式删除原文件: {}", path);
        } catch (IOException e) {
            log.error("覆盖重写模式删除原文件失败: {}", path, e);
        }
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

    /**
     * 从URL中提取文件扩展名 (如 .jpg, .png, .webp)
     */
    private String extractExtension(String url) {
        if (url == null) return null;
        // 去除URL参数部分
        String path = url.contains("?") ? url.substring(0, url.indexOf("?")) : url;
        int dotIndex = path.lastIndexOf('.');
        int slashIndex = path.lastIndexOf('/');
        if (dotIndex > slashIndex && dotIndex < path.length() - 1) {
            return path.substring(dotIndex);
        }
        return null;
    }
}
