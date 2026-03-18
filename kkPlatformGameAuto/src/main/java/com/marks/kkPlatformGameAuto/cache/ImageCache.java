package com.marks.kkPlatformGameAuto.cache;

import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Point;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>项目名称：LeetCode_QA </p>
 * <p>文件名称：ImageCache </p>
 * <p>描述：图片识别缓存类，用于存储找到的图片信息和位置 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18
 * @update [序号][日期 YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class ImageCache {

    private final ConcurrentHashMap<String, Point> cache = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取图片位置
     *
     * @param key 图片标识 key
     * @return 图片位置坐标，如果不存在则返回 null
     */
    public Point get(String key) {
        Point point = cache.get(key);
        if (point != null) {
            log.debug("缓存命中：key={}, point=({}, {})", key, point.x, point.y);
        } else {
            log.debug("缓存未命中：key={}", key);
        }
        return point;
    }

    /**
     * 将图片位置存入缓存
     *
     * @param key 图片标识 key
     * @param point 图片位置坐标
     */
    public void put(String key, Point point) {
        if (key == null || point == null) {
            log.warn("尝试存入空值：key={}, point={}", key, point);
            return;
        }
        cache.put(key, point);
        log.debug("缓存已更新：key={}, point=({}, {})", key, point.x, point.y);
    }

    /**
     * 从缓存中移除图片位置
     *
     * @param key 图片标识 key
     */
    public void remove(String key) {
        cache.remove(key);
        log.debug("缓存已移除：key={}", key);
    }

    /**
     * 清空所有缓存
     */
    public void clear() {
        cache.clear();
        log.info("缓存已清空");
    }

    /**
     * 检查缓存中是否存在指定的 key
     *
     * @param key 图片标识 key
     * @return 是否存在
     */
    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    /**
     * 获取缓存大小
     *
     * @return 缓存中的元素数量
     */
    public int size() {
        return cache.size();
    }
}
