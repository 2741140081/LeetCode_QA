package com.mangareader.task;

import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaChapterPageRecordMapper;
import com.mangareader.mapper.MangaMapper;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
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

    private static final int HEARTBEAT_TIMEOUT_MINUTES = 60;
    private static final int THREAD_COUNT = 3;

    /**
     * 定时扫描并处理漫画下载任务
     * 每分钟执行一次
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
                    mangaMapper.updateMangaStatus(processingManga.getMangaId(), 3);
                    System.out.println("漫画[" + processingManga.getMangaName() + "]心跳超时，已标记为异常中断");
                }
                // 如果有正在处理的漫画且未超时，直接返回
                return;
            }

            // 2. 查询待处理的漫画
            Manga pendingManga = mangaMapper.selectPendingManga();
            if (pendingManga == null) {
                return;
            }

            // 3. 标记为正在处理
            mangaMapper.updateMangaStatus(pendingManga.getMangaId(), 1);
            mangaMapper.updateHeartBeat(pendingManga.getMangaId(), LocalDateTime.now());

            System.out.println("开始处理漫画: " + pendingManga.getMangaName());

            // 4. 获取章节信息
            List<Chapter> chapters = chapterMapper.findByMangaId(pendingManga.getMangaId());
            mangaMapper.updateTotalChapters(pendingManga.getMangaId(), chapters.size());

            // 5. 开始下载
            mangaDownloadService.downloadManga(pendingManga, THREAD_COUNT);

            // 6. 下载完成后标记为已完成
            mangaMapper.updateMangaStatus(pendingManga.getMangaId(), 2);
            System.out.println("漫画[" + pendingManga.getMangaName() + "]下载完成");

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
