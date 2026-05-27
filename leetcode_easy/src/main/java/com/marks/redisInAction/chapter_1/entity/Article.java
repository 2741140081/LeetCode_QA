package com.marks.redisInAction.chapter_1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Article </p>
 * <p>描述: 文章实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/27 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Long articleId;           // 文章ID
    private String title;             // 文章标题
    private String link;              // 文章链接
    private Long poster;              // 发布者用户ID
    private Double time;              // 发布时间（Unix时间戳）
    private Integer votes;            // 投票数量
}
