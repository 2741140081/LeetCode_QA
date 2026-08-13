package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Manga </p>
 * <p>描述: 漫画实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/13 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class Manga {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 漫画名称/标题
     */
    private String name;

    /**
     * 本地磁盘目录ID
     */
    private String dirId;

    /**
     * 作者
     */
    private String author;

    /**
     * 简介/描述
     */
    private String description;

    /**
     * 封面图片相对路径
     */
    private String coverImage;

    /**
     * 状态: 0:启用 1:禁用 2:待下载
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
