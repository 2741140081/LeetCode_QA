package com.mangareader.task;


import com.mangareader.config.MangaDownloadConfig;
import com.mangareader.enums.ProcessStatus;
import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.mapper.MangaMapper;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.service.DownloadEventPublisher;
import com.mangareader.service.MangaImageDownloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaImageDownloadScheduledTask </p>
 * <p>描述: 定时任务类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/26 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class MangaImageDownloadScheduledTask {

    private final MangaImageMapper mapper;
    private final MangaDownloadConfig config;
    private final Executor mangaDownloadExecutor;
    private final MangaImageDownloadService downloadService;
    private final MangaMapper mangaMapper;
    private final ChapterMapper chapterMapper;
    private final DownloadEventPublisher eventPublisher;

    /**
     * 定时扫描待下载任务
     */
    @Scheduled(cron = "${manga.download-config.scan-cron}")
    public void scanPendingTasks() {
        List<MangaImage> tasks = mapper.selectPendingTasks(ProcessStatus.PENDING.getCode(), config.getBatchSize()); // 1表示未下载
        if (tasks.isEmpty()) {
            return;
        }
        log.info("定时任务扫描到 {} 个待下载漫画图片任务", tasks.size());

        for (MangaImage task : tasks) {
            // 数据库乐观锁标记为下载中, 防止分布式环境下重复抢占任务
            int updated = mapper.markAsDownloading(task.getImageId());
            if (updated == 0) {
                continue;
            }
            // 提交至多线程池异步下载
            mangaDownloadExecutor.execute(() -> downloadService.downloadImage(task));
        }
    }

    /**
     * 定时扫描僵死超时任务
     */
    @Scheduled(fixedDelay = 30000)
    public void recoverTimeoutTasks() {
        List<MangaImage> timeoutTasks = mapper.selectTimeoutDownloadingTasks(config.getDownloadTimeout(), 100);
        if (timeoutTasks.isEmpty()) {
            return;
        }
        log.warn("扫描到 {} 个超时僵死的漫画图片下载任务, 自动重置重试", timeoutTasks.size());

        for (MangaImage task : timeoutTasks) {
            downloadService.handleFailure(task.getImageId(), "下载任务超时,触发定时自动恢复");
        }
    }

    /**
     * 定时同步漫画/章节下载状态
     * 每分钟执行一次，根据图片实际下载状态汇总更新章节和漫画状态
     */
    @Scheduled(fixedDelay = 60000)
    public void syncDownloadStatus() {
        // 查询所有待处理和正在处理的漫画
        List<Manga> pendingMangas = mangaMapper.selectMangasByStatus(ProcessStatus.PENDING.getCode());
        List<Manga> processingMangas = mangaMapper.selectMangasByStatus(ProcessStatus.PROCESSING.getCode());

        List<Manga> allMangas = new java.util.ArrayList<>();
        allMangas.addAll(pendingMangas);
        allMangas.addAll(processingMangas);

        if (allMangas.isEmpty()) {
            return;
        }

        log.debug("开始同步 {} 个漫画的下载状态", allMangas.size());

        for (Manga manga : allMangas) {
            syncMangaStatus(manga);
        }
    }

    /**
     * 同步单个漫画的状态
     */
    private void syncMangaStatus(Manga manga) {
        Long mangaId = manga.getMangaId();
        List<Chapter> chapters = chapterMapper.findByMangaId(mangaId);
        if (chapters.isEmpty()) {
            return;
        }

        boolean allChaptersCompleted = true;
        boolean hasProcessingChapter = false;

        for (Chapter chapter : chapters) {
            int chapterStatus = syncChapterStatus(chapter);

            if (chapterStatus != ProcessStatus.COMPLETED.getCode()) {
                allChaptersCompleted = false;
            }
            if (chapterStatus == ProcessStatus.PROCESSING.getCode() ||
                chapterStatus == ProcessStatus.PENDING.getCode()) {
                hasProcessingChapter = true;
            }
        }

        // 如果所有章节都下载完成，更新漫画状态为完成
        if (allChaptersCompleted && !chapters.isEmpty()) {
            mangaMapper.updateMangaStatus(mangaId, ProcessStatus.COMPLETED.getCode());
            log.info("漫画[{}] 所有章节下载完成，状态已更新为完成", manga.getMangaName());
            eventPublisher.publishMangaStatus(mangaId, manga.getMangaName(),
                    ProcessStatus.COMPLETED.getCode(), "已完成");
        }
    }

    /**
     * 同步单个章节的状态，返回章节状态码
     */
    private int syncChapterStatus(Chapter chapter) {
        Long chapterId = chapter.getChapterId();
        int totalImages = mapper.countByChapterId(chapterId);

        if (totalImages == 0) {
            return ProcessStatus.PENDING.getCode();
        }

        int completedImages = mapper.countByChapterIdAndStatus(chapterId, ProcessStatus.COMPLETED.getCode());
        int pendingImages = mapper.countByChapterIdAndStatus(chapterId, ProcessStatus.PENDING.getCode());
        int processingImages = mapper.countByChapterIdAndStatus(chapterId, ProcessStatus.PROCESSING.getCode());
        int failedImages = mapper.countByChapterIdAndStatus(chapterId, ProcessStatus.FAILED.getCode());

        int newStatus;
        if (completedImages == totalImages) {
            // 所有图片下载完成
            newStatus = ProcessStatus.COMPLETED.getCode();
        } else if (pendingImages > 0 || processingImages > 0) {
            // 还有图片或正在处理
            newStatus = ProcessStatus.PROCESSING.getCode();
        } else if (failedImages == totalImages) {
            // 所有图片都失败
            newStatus = ProcessStatus.FAILED.getCode();
        } else {
            newStatus = ProcessStatus.PROCESSING.getCode();
        }

        // 更新章节状态
        chapterMapper.updateChapterStatus(chapterId, newStatus);
        return newStatus;
    }
}

