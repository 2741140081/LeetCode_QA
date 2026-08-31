package com.mangareader.task;

import com.mangareader.enums.ProcessStatus;
import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaMapper;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
import com.mangareader.service.DownloadEventPublisher;
import com.mangareader.service.MangaDownloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaDownloadScheduledTask </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/27 11:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class MangaDownloadScheduledTask {
    private final MangaMapper mangaMapper;
    private final ChapterMapper chapterMapper;
    private final MangaDownloadService mangaDownloadService;
    private final DownloadEventPublisher eventPublisher;

    private static final int HEARTBEAT_TIMEOUT_MINUTES = 60;

    /**
     * 定时扫描并处理漫画下载任务
     * 每分钟执行一次
     * 1. 如果有正在处理的漫画
     * 1.1 心跳超时
     * 1.2 如果已处理的漫画章节数量等于当前漫画章节总数, 并且需要判断章节总数是一个大于 0 的数.
     * 那么更新当前漫画状态为已完成状态, 然后返回等待下一次任务执行
     *
     */
    @Scheduled(fixedRate = 60000)
    public void processMangaDownload() {
        try {
            // 1. 检查是否有正在处理的漫画
            Manga processingManga = mangaMapper.selectProcessingManga();

            if (processingManga != null) {
                // 检查心跳是否超时
                if (isHeartbeatTimeout(processingManga.getLastHeartBeat())) {
                    // 心跳超时，标记为异常中断
                    mangaMapper.updateMangaStatus(processingManga.getMangaId(), ProcessStatus.FAILED.getCode());
                    log.info("漫画[{}]心跳超时，已标记为异常中断", processingManga.getMangaName());
                    eventPublisher.publishMangaStatus(processingManga.getMangaId(), processingManga.getMangaName(),
                            ProcessStatus.FAILED.getCode(), "心跳超时");
                }
                // 检查是否所有章节都已处理完成
                Integer totalChapters = processingManga.getTotalChapters();
                Integer processedChapters = processingManga.getProcessedChapters();

                if (totalChapters != null && totalChapters > 0 &&
                        processedChapters != null && processedChapters.equals(totalChapters)) {
                    // 所有章节已处理完成，更新状态为已完成
                    mangaMapper.updateMangaStatus(processingManga.getMangaId(), ProcessStatus.COMPLETED.getCode());
                    log.info("漫画[{}]所有章节处理完成，已更新为已完成状态", processingManga.getMangaName());
                    eventPublisher.publishMangaStatus(processingManga.getMangaId(), processingManga.getMangaName(),
                            ProcessStatus.COMPLETED.getCode(), "已完成");
                    return;
                }

                // 如果有正在处理的漫画且未完成，直接返回
                log.debug("漫画[{}]正在处理中，已处理: {}/{}",
                        processingManga.getMangaName(),
                        processedChapters != null ? processedChapters : 0,
                        totalChapters != null ? totalChapters : 0);
                return;
            }

            // 2. 查询待处理的漫画
            Manga pendingManga = mangaMapper.selectPendingManga();
            if (pendingManga == null) {
                return;
            }

            // 3. 标记为正在处理
            mangaMapper.updateMangaStatus(pendingManga.getMangaId(), ProcessStatus.PROCESSING.getCode());
            mangaMapper.updateHeartBeat(pendingManga.getMangaId(), LocalDateTime.now());
            eventPublisher.publishMangaStatus(pendingManga.getMangaId(), pendingManga.getMangaName(),
                    ProcessStatus.PROCESSING.getCode(), "正在处理");

            log.info("开始处理漫画: {}", pendingManga.getMangaName());

            // 4. 获取章节信息
            List<Chapter> chapters = chapterMapper.findByMangaId(pendingManga.getMangaId());
            mangaMapper.updateTotalChapters(pendingManga.getMangaId(), chapters.size());

            // 5. 开始下载, 多线程处理章节
            mangaDownloadService.downloadManga(pendingManga);

        } catch (Exception e) {
            System.err.println("处理漫画下载任务时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 检查心跳是否超时
     */
    private boolean isHeartbeatTimeout(LocalDateTime lastHeartBeat) {
        if (lastHeartBeat == null) {
            return true;
        }
        return lastHeartBeat.plusMinutes(HEARTBEAT_TIMEOUT_MINUTES).isBefore(LocalDateTime.now());
    }
}
