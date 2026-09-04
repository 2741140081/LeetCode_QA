package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 书架文件夹实体类
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ShelfFolder {
    /**
     * 主键ID
     */
    private Long folderId;

    /**
     * 所属用户ID
     */
    private Long userId;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 排序序号
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
