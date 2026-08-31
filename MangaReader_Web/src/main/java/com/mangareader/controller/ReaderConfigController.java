package com.mangareader.controller;

import com.mangareader.config.AutoplayConfig;
import com.mangareader.config.MangaProperties;
import com.mangareader.model.common.Result;
import com.mangareader.model.vo.AutoplayVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 阅读器配置模块 REST 控制器
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ReaderConfigController {

    private final AutoplayConfig autoplayConfig;
    private final MangaProperties mangaProperties;

    /**
     * 自动播放配置
     */
    @GetMapping("/autoplay")
    public Result<AutoplayVO> autoplay() {
        AutoplayVO vo = new AutoplayVO();
        vo.setDefaultScrollDistance(autoplayConfig.getDefaultScrollDistance());
        vo.setDefaultScrollInterval(autoplayConfig.getDefaultScrollInterval());
        vo.setScrollDistanceStep(autoplayConfig.getScrollDistanceStep());
        vo.setMinScrollDistance(autoplayConfig.getMinScrollDistance());
        vo.setMaxScrollDistance(autoplayConfig.getMaxScrollDistance());
        return Result.ok(vo);
    }

    /**
     * 阅读器配置
     */
    @GetMapping("/reader")
    public Result<Map<String, Object>> reader() {
        Map<String, Object> config = new HashMap<>();
        config.put("preloadCount", mangaProperties.getReader().getPreloadCount());
        config.put("saveInterval", mangaProperties.getReader().getSaveInterval());
        config.put("defaultScale", mangaProperties.getReader().getDefaultScale());
        config.put("scrollStep", mangaProperties.getReader().getScrollStep());
        config.put("supportedFormats", mangaProperties.getImage().getSupportedFormats());
        config.put("maxMemoryCacheSize", mangaProperties.getImage().getMaxMemoryCacheSize());
        return Result.ok(config);
    }
}
