package com.mangareader.service;

import com.mangareader.config.SseConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 下载事件发布服务：在状态变更时推送 SSE 事件
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadEventPublisher {

    private final SseConfig.SseEmitterRegistry registry;

    /**
     * 推送漫画下载进度事件
     */
    public void publishMangaProgress(Long mangaId, String mangaName,
                                      Integer totalChapters, Integer processedChapters,
                                      Integer statusCode, String statusDesc) {
        Map<String, Object> data = new HashMap<>();
        data.put("mangaId", mangaId);
        data.put("mangaName", mangaName);
        data.put("totalChapters", totalChapters);
        data.put("processedChapters", processedChapters);
        data.put("statusCode", statusCode);
        data.put("statusDesc", statusDesc);

        try {
            registry.sendEvent("manga-progress", data);
        } catch (Exception e) {
            log.debug("推送漫画进度事件失败: {}", e.getMessage());
        }
    }

    /**
     * 推送漫画状态变更事件
     */
    public void publishMangaStatus(Long mangaId, String mangaName,
                                    Integer statusCode, String statusDesc) {
        Map<String, Object> data = new HashMap<>();
        data.put("mangaId", mangaId);
        data.put("mangaName", mangaName);
        data.put("statusCode", statusCode);
        data.put("statusDesc", statusDesc);

        try {
            registry.sendEvent("manga-status", data);
        } catch (Exception e) {
            log.debug("推送漫画状态事件失败: {}", e.getMessage());
        }
    }

    /**
     * 推送图片下载统计事件
     */
    public void publishImageStats(Integer pending, Integer downloading,
                                   Integer completed, Integer failed) {
        Map<String, Object> data = new HashMap<>();
        data.put("pending", pending);
        data.put("downloading", downloading);
        data.put("completed", completed);
        data.put("failed", failed);

        try {
            registry.sendEvent("image-stats", data);
        } catch (Exception e) {
            log.debug("推送图片统计事件失败: {}", e.getMessage());
        }
    }
}
