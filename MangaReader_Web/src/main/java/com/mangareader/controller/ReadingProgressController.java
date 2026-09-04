package com.mangareader.controller;

import com.mangareader.model.common.BusinessException;
import com.mangareader.model.common.Result;
import com.mangareader.model.entity.ReadingProgress;
import com.mangareader.mapper.ReadingProgressMapper;
import com.mangareader.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 阅读进度控制器
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/reading-progress")
@RequiredArgsConstructor
public class ReadingProgressController {

    private final ReadingProgressMapper readingProgressMapper;
    private final JwtUtils jwtUtils;

    /**
     * 获取某漫画的阅读进度
     */
    @GetMapping("/{mangaId}")
    public Result<Map<String, Object>> getProgress(@PathVariable Long mangaId,
                                                    HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        ReadingProgress progress = readingProgressMapper.findByUserIdAndMangaId(userId, mangaId);

        Map<String, Object> result = new HashMap<>();
        if (progress != null) {
            result.put("chapterId", progress.getChapterId());
            result.put("imageIndex", progress.getImageIndex());
            result.put("pageIndex", progress.getPageIndex());
            result.put("totalImages", progress.getTotalImages());
            result.put("progressPct", progress.getProgressPct());
            result.put("lastReadAt", progress.getLastReadAt());
        } else {
            result.put("chapterId", null);
            result.put("imageIndex", 0);
            result.put("pageIndex", 0);
            result.put("totalImages", 0);
            result.put("progressPct", 0);
        }
        return Result.ok(result);
    }

    /**
     * 保存/更新阅读进度
     */
    @PostMapping
    public Result<Void> saveProgress(@RequestBody ProgressSaveRequest body,
                                     HttpServletRequest request) {
        Long userId = getCurrentUserId(request);

        ReadingProgress existing = readingProgressMapper.findByUserIdAndMangaId(userId, body.getMangaId());

        if (existing != null) {
            existing.setChapterId(body.getChapterId());
            existing.setImageIndex(body.getImageIndex());
            existing.setPageIndex(body.getPageIndex());
            existing.setTotalImages(body.getTotalImages());
            if (body.getTotalImages() != null && body.getTotalImages() > 0 && body.getImageIndex() != null) {
                BigDecimal pct = BigDecimal.valueOf(body.getImageIndex())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(body.getTotalImages()), 1, RoundingMode.HALF_UP);
                existing.setProgressPct(pct);
            }
            readingProgressMapper.update(existing);
        } else {
            ReadingProgress progress = new ReadingProgress();
            progress.setUserId(userId);
            progress.setMangaId(body.getMangaId());
            progress.setChapterId(body.getChapterId());
            progress.setImageIndex(body.getImageIndex());
            progress.setPageIndex(body.getPageIndex());
            progress.setTotalImages(body.getTotalImages());
            if (body.getTotalImages() != null && body.getTotalImages() > 0 && body.getImageIndex() != null) {
                BigDecimal pct = BigDecimal.valueOf(body.getImageIndex())
                        .multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(body.getTotalImages()), 1, RoundingMode.HALF_UP);
                progress.setProgressPct(pct);
            }
            readingProgressMapper.insert(progress);
        }

        return Result.ok("进度已保存", null);
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            throw new BusinessException(401, "未登录");
        }
        return jwtUtils.getUserIdFromToken(bearerToken.substring(7));
    }

    @Data
    public static class ProgressSaveRequest {
        private Long mangaId;
        private Long chapterId;
        private Integer imageIndex;
        private Integer pageIndex;
        private Integer totalImages;
    }
}
