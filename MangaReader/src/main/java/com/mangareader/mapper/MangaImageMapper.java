package com.mangareader.mapper;

import com.mangareader.model.entity.MangaImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaImageMapper </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/18 17:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Mapper
public interface MangaImageMapper {
    List<MangaImage> findByChapterId(@Param("chapterId") Long chapterId);

    // 添加批量插入方法
    int batchInsert(@Param("list") List<MangaImage> imageList);
}
