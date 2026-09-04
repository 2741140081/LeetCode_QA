package com.mangareader.mapper;

import com.mangareader.model.entity.ShelfManga;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书架漫画关联 Mapper 接口
 *
 * @author marks
 * @version v1.0
 */
@Mapper
public interface ShelfMangaMapper {

    /**
     * 查询用户书架中的所有漫画
     */
    List<ShelfManga> findByUserId(@Param("userId") Long userId);

    /**
     * 按文件夹查询漫画
     */
    List<ShelfManga> findByUserIdAndFolderId(@Param("userId") Long userId, @Param("folderId") Long folderId);

    /**
     * 查询未分类的漫画
     */
    List<ShelfManga> findUncategorized(@Param("userId") Long userId);

    /**
     * 查询指定漫画是否在书架中
     */
    ShelfManga findByUserIdAndMangaId(@Param("userId") Long userId, @Param("mangaId") Long mangaId);

    /**
     * 添加漫画到书架
     */
    int insert(ShelfManga shelfManga);

    /**
     * 移动漫画到指定文件夹
     */
    int updateFolder(@Param("userId") Long userId, @Param("mangaId") Long mangaId, @Param("folderId") Long folderId);

    /**
     * 将文件夹内漫画移至未分类（删除文件夹前调用）
     */
    int clearFolder(@Param("folderId") Long folderId);

    /**
     * 从书架移除漫画
     */
    int deleteByUserIdAndMangaId(@Param("userId") Long userId, @Param("mangaId") Long mangaId);
}
