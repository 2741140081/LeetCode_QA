package com.mangareader.mapper;

import com.mangareader.model.entity.MangaChapterPageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaChapterPageRecordMapper </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/27 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Mapper
public interface MangaChapterPageRecordMapper {
    /**
     * 插入单条记录
     */
    int insertSelective(MangaChapterPageRecord record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKeySelective(MangaChapterPageRecord record);

    /**
     * 根据章节ID查询所有页面记录
     */
    List<MangaChapterPageRecord> selectByChapterId(@Param("chapterId") Long chapterId);

    /**
     * 查询所有下载失败的页面记录
     */
    List<MangaChapterPageRecord> selectFailedPages();

    /**
     * 批量插入页面记录
     */
    int batchInsert(@Param("list") List<MangaChapterPageRecord> records);
}

