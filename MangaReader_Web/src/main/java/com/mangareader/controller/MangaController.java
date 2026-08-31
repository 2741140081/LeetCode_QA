package com.mangareader.controller;

import com.mangareader.config.MangaProperties;
import com.mangareader.enums.ProcessStatus;
import com.mangareader.model.common.BusinessException;
import com.mangareader.model.common.Result;
import com.mangareader.model.dto.MangaAddRequest;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.vo.MangaVO;
import com.mangareader.service.MangaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 漫画模块 REST 控制器
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/manga")
@RequiredArgsConstructor
public class MangaController {

    private final MangaService mangaService;
    private final MangaProperties mangaProperties;

    /**
     * 书架漫画列表
     */
    @GetMapping("/list")
    public Result<List<MangaVO>> list() {
        List<Manga> mangas = mangaService.getAllManga();
        List<MangaVO> voList = mangas.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
        return Result.ok(voList);
    }

    /**
     * 漫画详情
     */
    @GetMapping("/{mangaId}")
    public Result<MangaVO> detail(@PathVariable Long mangaId) {
        Manga manga = mangaService.getMangaById(mangaId);
        if (manga == null) {
            throw new BusinessException(404, "漫画不存在");
        }
        return Result.ok(toVO(manga));
    }

    /**
     * 新增下载任务
     */
    @PostMapping
    public Result<MangaVO> add(@Valid @RequestBody MangaAddRequest request) {
        Manga manga = mangaService.addManga(request.getMangaName(), request.getMangaUrl());
        if (manga == null) {
            throw new BusinessException("添加漫画失败，可能已存在或参数无效");
        }
        return Result.ok("漫画已添加到下载队列，系统将自动处理", toVO(manga));
    }

    /**
     * Entity -> VO 转换
     */
    private MangaVO toVO(Manga manga) {
        MangaVO vo = new MangaVO();
        vo.setMangaId(manga.getMangaId());
        vo.setMangaName(manga.getMangaName());
        vo.setTotalChapters(manga.getTotalChapters());
        vo.setProcessedChapters(manga.getProcessedChapters());
        vo.setCreatedAt(manga.getCreatedAt());
        vo.setUpdatedAt(manga.getUpdatedAt());

        // 状态
        if (manga.getMangaStatus() != null) {
            vo.setMangaStatusCode(manga.getMangaStatus().getCode());
            vo.setMangaStatusDesc(manga.getMangaStatus().getDesc());
        }

        // 封面 URL
        vo.setCoverUrl(buildCoverUrl(manga));

        return vo;
    }

    /**
     * 构建封面图片 URL
     */
    private String buildCoverUrl(Manga manga) {
        if (manga.getCoverImage() != null && !manga.getCoverImage().isEmpty()) {
            return "/covers/" + manga.getCoverImage();
        }
        // 尝试从本地目录查找封面
        String dirId = manga.getDirId();
        if (dirId != null) {
            File dir = new File(dirId);
            if (dir.exists() && dir.isDirectory()) {
                File[] covers = dir.listFiles((d, name) ->
                        name.toLowerCase().startsWith("cover") &&
                                (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".webp")));
                if (covers != null && covers.length > 0) {
                    // 返回相对路径
                    String basePath = mangaProperties.getStorage().getRoot();
                    String fullPath = covers[0].getAbsolutePath();
                    if (fullPath.startsWith(basePath)) {
                        return "/covers" + fullPath.substring(basePath.length()).replace("\\", "/");
                    }
                }
            }
        }
        return null; // 前端使用默认封面兜底
    }
}
