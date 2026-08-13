package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Bookmark </p>
 * <p>描述: 书签实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/13 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class Bookmark {
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
     * 书签所在图片序号(从0开始)
     */
    private Integer imageIndex;

    /**
     * 书签名称/备注
     */
    private String bookmarkName;

    /**
     * 书签颜色标签
     */
    private String colorTag;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
