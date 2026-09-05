package com.mangareader.service;

import javafx.scene.image.Image;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageService </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/14 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ImageService {

    void init();

    Image loadImage(String path, double scale);

    CompletableFuture<Image> loadImageAsync(String path, double scale);

    void evictCache(String path);

    void evictByPaths(List<String> paths);

    void evictAll();
}
