package com.mangareader.service.impl;

import com.mangareader.mapper.ChapterMapper;
import com.mangareader.model.entity.Chapter;
import com.mangareader.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ChapterServiceImpl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 17:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public List<Chapter> getChaptersByMangaId(Long mangaId) {
        return chapterMapper.findByMangaId(mangaId);
    }

    @Override
    public Chapter getChapterById(Long chapterId) {
        return chapterMapper.findById(chapterId);
    }

    @Override
    public Chapter getNextChapter(Long mangaId, Integer currentChapterNum) {
        return chapterMapper.findNextChapter(mangaId, currentChapterNum);
    }

    @Override
    public Chapter getPrevChapter(Long mangaId, Integer currentChapterNum) {
        return chapterMapper.findPrevChapter(mangaId, currentChapterNum);
    }
}
