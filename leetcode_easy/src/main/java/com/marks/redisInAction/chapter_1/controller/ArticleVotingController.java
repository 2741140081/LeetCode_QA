package com.marks.redisInAction.chapter_1.controller;

import com.marks.redisInAction.chapter_1.entity.Article;
import com.marks.redisInAction.chapter_1.service.ArticleVotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ArticleVotingController </p>
 * <p>描述: 章投票控制器 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/27 17:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@RestController
@RequestMapping("/api/articles")
public class ArticleVotingController {

    @Autowired
    private ArticleVotingService articleVotingService;


    /**
     * 对文章进行投票
     */
    @PostMapping("/{articleId}/vote")
    public ResponseEntity<Map<String, Object>> voteArticle(@PathVariable Long articleId,
                                                           @RequestParam Long userId) {
        Map<String, Object> response = new HashMap<>();

        boolean success = articleVotingService.articleVote(articleId, userId);

        if (success) {
            response.put("success", true);
            response.put("message", "投票成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "投票失败：文章不存在、投票已过期或已投过票");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 发布新文章
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createArticle(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        Long userId = ((Number) request.get("userId")).longValue();
        String title = (String) request.get("title");
        String link = (String) request.get("link");

        if (userId == null || title == null || link == null) {
            response.put("success", false);
            response.put("message", "缺少必要参数");
            return ResponseEntity.badRequest().body(response);
        }

        Long articleId = articleVotingService.postArticle(userId, title, link);

        response.put("success", true);
        response.put("articleId", articleId);
        response.put("message", "文章发布成功");
        return ResponseEntity.ok(response);
    }

    /**
     * 获取文章列表（支持分页和排序）
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getArticles(@RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "score") String orderBy) {
        Map<String, Object> response = new HashMap<>();

        if (!"time".equals(orderBy) && !"score".equals(orderBy)) {
            response.put("success", false);
            response.put("message", "排序方式只能是 'time' 或 'score'");
            return ResponseEntity.badRequest().body(response);
        }

        List<Article> articles = articleVotingService.getArticles(page, orderBy);

        response.put("success", true);
        response.put("articles", articles);
        response.put("page", page);
        response.put("orderBy", orderBy);
        response.put("totalArticles", articles.size());
        return ResponseEntity.ok(response);
    }

    /**
     * 将文章添加到分组
     */
    @PostMapping("/{articleId}/group")
    public ResponseEntity<Map<String, Object>> addToGroup(@PathVariable Long articleId,
                                                          @RequestParam String group) {
        Map<String, Object> response = new HashMap<>();

        boolean success = articleVotingService.addArticleToGroup(articleId, group);

        if (success) {
            response.put("success", true);
            response.put("message", "文章已成功添加到分组: " + group);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "添加失败：文章不存在");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 获取指定分组的文章列表
     */
    @GetMapping("/group/{group}")
    public ResponseEntity<Map<String, Object>> getGroupArticles(@PathVariable String group,
                                                                @RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "score") String orderBy) {
        Map<String, Object> response = new HashMap<>();

        if (!"time".equals(orderBy) && !"score".equals(orderBy)) {
            response.put("success", false);
            response.put("message", "排序方式只能是 'time' 或 'score'");
            return ResponseEntity.badRequest().body(response);
        }

        List<Article> articles = articleVotingService.getGroupArticles(group, page, orderBy);

        response.put("success", true);
        response.put("articles", articles);
        response.put("group", group);
        response.put("page", page);
        response.put("orderBy", orderBy);
        response.put("totalArticles", articles.size());
        return ResponseEntity.ok(response);
    }
}
