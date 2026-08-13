package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Chapter </p>
 * <p>描述: 章节实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/13 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class Chapter {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 所属漫画ID
     */
    private Long mangaId;

    /**
     * 章节序号
     */
    private Integer chapterNum;

    /**
     * 章节标题
     */
    private String title;

    /**
     * 图片数量
     */
    private Integer imageCount;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
