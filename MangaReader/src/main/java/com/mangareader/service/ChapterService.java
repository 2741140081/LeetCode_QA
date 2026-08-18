package com.mangareader.service;

import com.mangareader.model.entity.Chapter;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ChapterService </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 17:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ChapterService {
    List<Chapter> getChaptersByMangaId(Long mangaId);
    Chapter getChapterById(Long chapterId);
    Chapter getNextChapter(Long mangaId, Integer currentChapterNum);
    Chapter getPrevChapter(Long mangaId, Integer currentChapterNum);
}
