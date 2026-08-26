package com.mangareader.service;

import com.mangareader.model.entity.MangaImage;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaImageDownloadService </p>
 * <p>描述: 图片下载服务接口 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/26 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */


public interface MangaImageDownloadService {
    /** 执行图片下载核心逻辑 */
    void downloadImage(MangaImage task);
    /** 处理下载失败逻辑 */
    void handleFailure(long imageId, String errorMsg);
    /** 获取任务统计 */
    Map<String, Object> getStatistics();
}