package com.marks.redisInAction.chapter_1.service;

import com.marks.redisInAction.chapter_1.entity.Article;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ArticleVotingService </p>
 * <p>描述: 文章投票服务接口 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/27 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ArticleVotingService {

    /**
     * 对文章进行投票
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 是否投票成功
     */
    boolean articleVote(Long articleId, Long userId);

    /**
     * 发布新文章
     * @param userId 用户ID（发布者）
     * @param title 文章标题
     * @param link 文章链接
     * @return 新文章的ID
     */
    Long postArticle(Long userId, String title, String link);

    /**
     * 获取排序后的文章列表（支持分页）
     * @param page 页码（从1开始）
     * @param orderBy 排序方式（"time"按时间排序，"score"按分数排序）
     * @return 文章列表
     */
    List<Article> getArticles(int page, String orderBy);

    /**
     * 将文章添加到分组
     * @param articleId 文章ID
     * @param group 分组名称
     * @return 是否操作成功
     */
    boolean addArticleToGroup(Long articleId, String group);

    /**
     * 获取指定分组的文章列表
     * @param group 分组名称
     * @param page 页码（从1开始）
     * @param orderBy 排序方式（"time"按时间排序，"score"按分数排序）
     * @return 文章列表
     */
    List<Article> getGroupArticles(String group, int page, String orderBy);

}
