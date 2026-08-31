package com.mangareader.controller;

import com.mangareader.config.SseConfig;
import com.mangareader.enums.ProcessStatus;
import com.mangareader.model.common.Result;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.vo.DownloadStatsVO;
import com.mangareader.model.vo.MangaVO;
import com.mangareader.service.MangaImageDownloadService;
import com.mangareader.service.MangaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 下载中心模块 REST 控制器
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class DownloadController {

    private final MangaService mangaService;
    private final MangaImageDownloadService mangaImageDownloadService;
    private final SseConfig.SseEmitterRegistry sseRegistry;

    /**
     * 漫画下载任务列表（支持按状态过滤）
     */
    @GetMapping("/manga/list")
    public Result<List<MangaVO>> mangaList(@RequestParam(required = false) Integer status) {
        List<Manga> mangas = mangaService.getAllManga();

        // 按状态过滤
        if (status != null) {
            ProcessStatus filterStatus = ProcessStatus.fromCode(status);
            mangas = mangas.stream()
                    .filter(m -> m.getMangaStatus() == filterStatus)
                    .collect(Collectors.toList());
        }

        List<MangaVO> voList = mangas.stream()
                .map(this::toTaskVO)
                .collect(Collectors.toList());
        return Result.ok(voList);
    }

    /**
     * 图片下载统计
     */
    @GetMapping("/image/stats")
    public Result<DownloadStatsVO> imageStats() {
        Map<String, Object> stats = mangaImageDownloadService.getStatistics();

        DownloadStatsVO vo = new DownloadStatsVO();
        vo.setPending(getInt(stats, "pending"));
        vo.setDownloading(getInt(stats, "downloading"));

        // 计算总数和成功率
        int pending = vo.getPending() != null ? vo.getPending() : 0;
        int downloading = vo.getDownloading() != null ? vo.getDownloading() : 0;
        // 从 mapper 获取更多统计
        int total = pending + downloading; // 简化计算
        vo.setTotal(total);
        vo.setCompleted(0);
        vo.setFailed(0);
        vo.setSuccessRate(total > 0 ? (double) vo.getCompleted() / total * 100 : 100.0);

        return Result.ok(vo);
    }

    /**
     * SSE 端点：推送下载进度事件
     */
    @GetMapping(value = "/events", produces = "text/event-stream")
    public SseEmitter events() {
        String clientId = UUID.randomUUID().toString();
        // 超时 30 分钟，心跳 15 秒保活
        return sseRegistry.register(clientId, 30 * 60 * 1000L);
    }

    private MangaVO toTaskVO(Manga manga) {
        MangaVO vo = new MangaVO();
        vo.setMangaId(manga.getMangaId());
        vo.setMangaName(manga.getMangaName());
        vo.setTotalChapters(manga.getTotalChapters());
        vo.setProcessedChapters(manga.getProcessedChapters());
        vo.setCreatedAt(manga.getCreatedAt());
        vo.setUpdatedAt(manga.getUpdatedAt());

        if (manga.getMangaStatus() != null) {
            vo.setMangaStatusCode(manga.getMangaStatus().getCode());
            vo.setMangaStatusDesc(manga.getMangaStatus().getDesc());
        }

        return vo;
    }

    private Integer getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        return 0;
    }
}
