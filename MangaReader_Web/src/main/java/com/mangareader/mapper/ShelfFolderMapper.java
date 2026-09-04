package com.mangareader.mapper;

import com.mangareader.model.entity.ShelfFolder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书架文件夹 Mapper 接口
 *
 * @author marks
 * @version v1.0
 */
@Mapper
public interface ShelfFolderMapper {

    /**
     * 查询用户的所有文件夹
     */
    List<ShelfFolder> findByUserId(@Param("userId") Long userId);

    /**
     * 根据ID查询文件夹
     */
    ShelfFolder findByFolderId(@Param("folderId") Long folderId);

    /**
     * 新增文件夹
     */
    int insert(ShelfFolder folder);

    /**
     * 更新文件夹名称
     */
    int updateFolderName(@Param("folderId") Long folderId, @Param("folderName") String folderName);

    /**
     * 删除文件夹
     */
    int deleteByFolderId(@Param("folderId") Long folderId);

    /**
     * 获取用户最大排序值
     */
    Integer getMaxSortOrder(@Param("userId") Long userId);
}
