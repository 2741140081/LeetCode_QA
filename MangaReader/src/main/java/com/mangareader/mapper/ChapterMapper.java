package com.mangareader.mapper;

import com.mangareader.model.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ChapterMapper </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 17:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Mapper
public interface ChapterMapper {

    List<Chapter> findByMangaId(Long mangaId);

    Chapter findById(long chapterId);

    Chapter findByMangaIdAndChapterNum(@Param("mangaId") Long mangaId, @Param("chapterNum") Integer chapterNum);

    Chapter findNextChapter(@Param("mangaId") Long mangaId, @Param("currentChapterNum") Integer currentChapterNum);

    Chapter findPrevChapter(@Param("mangaId") Long mangaId, @Param("currentChapterNum") Integer currentChapterNum);
}
