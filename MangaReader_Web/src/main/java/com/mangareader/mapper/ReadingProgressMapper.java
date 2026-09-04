package com.mangareader.mapper;

import com.mangareader.model.entity.ReadingProgress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 阅读进度 Mapper 接口
 *
 * @author marks
 * @version v1.0
 */
@Mapper
public interface ReadingProgressMapper {

    /**
     * 查询用户对某漫画的阅读进度
     */
    ReadingProgress findByUserIdAndMangaId(@Param("userId") Long userId, @Param("mangaId") Long mangaId);

    /**
     * 插入阅读进度
     */
    int insert(ReadingProgress progress);

    /**
     * 更新阅读进度
     */
    int update(ReadingProgress progress);
}
