package com.mangareader.service;

import com.mangareader.model.vo.ShelfFolderVO;
import com.mangareader.model.vo.ShelfMangaVO;

import java.util.List;

/**
 * 书架服务接口
 *
 * @author marks
 * @version v1.0
 */
public interface ShelfService {

    /**
     * 获取用户所有文件夹
     */
    List<ShelfFolderVO> getFolders(Long userId);

    /**
     * 创建文件夹
     */
    ShelfFolderVO createFolder(Long userId, String folderName);

    /**
     * 重命名文件夹
     */
    void renameFolder(Long userId, Long folderId, String folderName);

    /**
     * 删除文件夹（内漫画移至未分类）
     */
    void deleteFolder(Long userId, Long folderId);

    /**
     * 获取书架漫画列表（支持文件夹筛选）
     */
    List<ShelfMangaVO> getShelfMangas(Long userId, Long folderId);

    /**
     * 获取未分类的漫画
     */
    List<ShelfMangaVO> getUncategorizedMangas(Long userId);

    /**
     * 添加漫画到书架
     */
    void addMangaToShelf(Long userId, Long mangaId, Long folderId);

    /**
     * 移动漫画到指定文件夹
     */
    void moveMangaToFolder(Long userId, Long mangaId, Long folderId);

    /**
     * 从书架移除漫画
     */
    void removeMangaFromShelf(Long userId, Long mangaId);
}
