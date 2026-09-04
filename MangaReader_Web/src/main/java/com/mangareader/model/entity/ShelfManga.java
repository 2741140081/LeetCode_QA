package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 书架漫画关联实体类
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ShelfManga {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 漫画ID
     */
    private Long mangaId;

    /**
     * 所属文件夹ID, NULL表示未分类
     */
    private Long folderId;

    /**
     * 添加时间
     */
    private LocalDateTime addedAt;
}
