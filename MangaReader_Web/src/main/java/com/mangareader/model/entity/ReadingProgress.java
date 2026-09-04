package com.mangareader.model.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ReadingProgress </p>
 * <p>描述: 阅读进度实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/13 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class ReadingProgress {
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
     * 章节ID
     */
    private Long chapterId;

    /**
     * 当前阅读到该章节的第N张图片(从0开始)
     */
    private Integer imageIndex;

    /**
     * 分页页码(从0开始, 每页100张图)
     */
    private Integer pageIndex;

    /**
     * 该章节总图片数
     */
    private Integer totalImages;

    /**
     * 该章节阅读进度百分比 0~100
     */
    private BigDecimal progressPct;

    /**
     * 最后阅读时间
     */
    private LocalDateTime lastReadAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
