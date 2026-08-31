package com.mangareader.task;


import com.mangareader.config.MangaDownloadConfig;
import com.mangareader.enums.ProcessStatus;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.model.entity.MangaImage;
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
}

