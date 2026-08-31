package com.mangareader.service;

import com.mangareader.model.entity.MangaImage;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaImageService </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 17:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface MangaImageService {
    List<MangaImage> getImagesByChapterId(Long chapterId);
    String getFullImagePath(MangaImage image);
    /** 获取浏览器可访问的图片 URL（/images/...） */
    String getImageUrl(MangaImage image);
}
