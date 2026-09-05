package com.mangareader.controller;

import com.mangareader.config.MangaProperties;
import com.mangareader.enums.ProcessStatus;
import com.mangareader.mapper.ChapterMapper;
import com.mangareader.mapper.MangaImageMapper;
import com.mangareader.mapper.MangaMapper;
import com.mangareader.model.common.BusinessException;
import com.mangareader.model.common.Result;
import com.mangareader.model.dto.MangaAddRequest;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.Manga;
import com.mangareader.model.entity.MangaImage;
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
    private final ChapterMapper chapterMapper;
    private final MangaImageMapper mangaImageMapper;
    private final MangaMapper mangaMapper;
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
     * coverImage 存储的是相对于 imagePath 的路径，通过 /images/ 端点访问
     * 若 coverImage 为空，则懒加载：查询第一章第一图并持久化到数据库
     */
    private String buildCoverUrl(Manga manga) {
        if (manga.getCoverImage() != null && !manga.getCoverImage().isEmpty()) {
            return "/images/" + manga.getCoverImage();
        }
        // 懒加载：查询第一章第一图作为封面
        return resolveAndPersistCoverImage(manga);
    }

    /**
     * 懒加载封面：查询第一章的第一张图片，计算相对路径，持久化到数据库并返回 URL
     */
    private String resolveAndPersistCoverImage(Manga manga) {
        try {
            Chapter firstChapter = chapterMapper.findByMangaIdAndChapterNum(manga.getMangaId(), 0);
            if (firstChapter == null) return null;

            MangaImage firstImage = mangaImageMapper.findFirstImageByChapterId(firstChapter.getChapterId());
            if (firstImage == null) return null;

            String basePath = mangaProperties.getStorage().getImagePath();
            String fullPath = firstImage.getImageUrl() + File.separator
                    + firstImage.getImageName() + "." + firstImage.getImageType();

            if (fullPath.startsWith(basePath)) {
                String relativePath = fullPath.substring(basePath.length());
                relativePath = relativePath.replace("\\", "/");
                if (relativePath.startsWith("/")) {
                    relativePath = relativePath.substring(1);
                }
                // 持久化到数据库（仅当 cover_image 为空时更新）
                mangaMapper.updateCoverImage(manga.getMangaId(), relativePath);
                return "/images/" + relativePath;
            }
        } catch (Exception e) {
            log.warn("漫画[{}]懒加载封面失败: {}", manga.getMangaName(), e.getMessage());
        }
        return null;
    }
}
