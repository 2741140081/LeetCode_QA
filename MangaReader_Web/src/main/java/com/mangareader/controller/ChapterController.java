package com.mangareader.controller;

import com.mangareader.model.common.BusinessException;
import com.mangareader.model.common.Result;
import com.mangareader.model.entity.Chapter;
import com.mangareader.model.entity.MangaImage;
import com.mangareader.model.vo.ChapterImageVO;
import com.mangareader.model.vo.ChapterVO;
import com.mangareader.service.ChapterService;
import com.mangareader.service.MangaImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 章节与图片模块 REST 控制器
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ChapterController {

    private static final int DEFAULT_PAGE_SIZE = 100;

    private final ChapterService chapterService;
    private final MangaImageService mangaImageService;

    /**
     * 章节列表
     */
    @GetMapping("/api/manga/{mangaId}/chapters")
    public Result<List<Chapter>> chapters(@PathVariable Long mangaId) {
        List<Chapter> chapters = chapterService.getChaptersByMangaId(mangaId);
        return Result.ok(chapters);
    }

    /**
     * 章节详情（含上一章/下一章导航）
     */
    @GetMapping("/api/chapter/{chapterId}")
    public Result<ChapterVO> chapterDetail(@PathVariable Long chapterId) {
        Chapter chapter = chapterService.getChapterById(chapterId);
        if (chapter == null) {
            throw new BusinessException(404, "章节不存在");
        }

        ChapterVO vo = new ChapterVO();
        vo.setChapterId(chapter.getChapterId());
        vo.setMangaId(chapter.getMangaId());
        vo.setChapterNum(chapter.getChapterNum());
        vo.setChapterUrl(chapter.getChapterUrl());
        vo.setTitle(chapter.getTitle());
        vo.setImageCount(chapter.getImageCount());
        vo.setCreatedAt(chapter.getCreatedAt());

        // 上一章/下一章
        Chapter prev = chapterService.getPrevChapter(chapter.getMangaId(), chapter.getChapterNum());
        Chapter next = chapterService.getNextChapter(chapter.getMangaId(), chapter.getChapterNum());
        vo.setPrevChapterId(prev != null ? prev.getChapterId() : null);
        vo.setNextChapterId(next != null ? next.getChapterId() : null);

        return Result.ok(vo);
    }

    /**
     * 章节图片列表（全量）
     */
    @GetMapping("/api/chapter/{chapterId}/images")
    public Result<List<ChapterImageVO>> images(@PathVariable Long chapterId) {
        List<MangaImage> images = mangaImageService.getImagesByChapterId(chapterId);
        List<ChapterImageVO> voList = images.stream()
                .map(this::toImageVO)
                .collect(Collectors.toList());
        return Result.ok(voList);
    }

    /**
     * 章节图片分页接口
     * GET /api/chapter/{chapterId}/images/paged?page=0&size=100
     */
    @GetMapping("/api/chapter/{chapterId}/images/paged")
    public Result<Map<String, Object>> imagesPaged(
            @PathVariable Long chapterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        int offset = page * size;
        List<MangaImage> images = mangaImageService.getImagesByChapterIdPaged(chapterId, offset, size);
        int totalCount = mangaImageService.countImagesByChapterId(chapterId);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        List<ChapterImageVO> voList = images.stream()
                .map(this::toImageVO)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("images", voList);
        result.put("currentPage", page);
        result.put("totalPages", totalPages);
        result.put("totalCount", totalCount);
        result.put("hasNext", page < totalPages - 1);

        return Result.ok(result);
    }

    /**
     * 上一章
     */
    @GetMapping("/api/chapter/{chapterId}/prev")
    public Result<Chapter> prevChapter(@PathVariable Long chapterId) {
        Chapter chapter = chapterService.getChapterById(chapterId);
        if (chapter == null) {
            throw new BusinessException(404, "章节不存在");
        }
        Chapter prev = chapterService.getPrevChapter(chapter.getMangaId(), chapter.getChapterNum());
        return Result.ok(prev);
    }

    /**
     * 下一章
     */
    @GetMapping("/api/chapter/{chapterId}/next")
    public Result<Chapter> nextChapter(@PathVariable Long chapterId) {
        Chapter chapter = chapterService.getChapterById(chapterId);
        if (chapter == null) {
            throw new BusinessException(404, "章节不存在");
        }
        Chapter next = chapterService.getNextChapter(chapter.getMangaId(), chapter.getChapterNum());
        return Result.ok(next);
    }

    /**
     * MangaImage -> ChapterImageVO
     */
    private ChapterImageVO toImageVO(MangaImage image) {
        ChapterImageVO vo = new ChapterImageVO();
        vo.setImageId(image.getImageId());
        vo.setSortOrder(image.getSortOrder());
        vo.setUrl(mangaImageService.getImageUrl(image));
        vo.setWidth(image.getImageWidth());
        vo.setHeight(image.getImageHeight());
        return vo;
    }
}
