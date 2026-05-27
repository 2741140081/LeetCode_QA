package com.marks.redisInAction.chapter_1.service.impl;

import com.marks.redisInAction.chapter_1.entity.Article;
import com.marks.redisInAction.chapter_1.service.ArticleVotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ArticleVotingServiceImpl </p>
 * <p>描述: [文章投票服务实现类] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/27 16:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Service
public class ArticleVotingServiceImpl implements ArticleVotingService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 常量定义
    private static final int VOTE_SCORE = 432;  // 每张票的分数贡献
    private static final int ARTICLES_PER_PAGE = 25;  // 每页显示的文章数
    private static final int ONE_WEEK_IN_SECONDS = 7 * 86400;  // 一周的秒数
    private static final int INTERESTING_VOTES_THRESHOLD = 200;  // 有趣文章的票数阈值

    // Redis键前缀
    private static final String ARTICLE_PREFIX = "article:";
    private static final String VOTED_PREFIX = "voted:";
    private static final String SCORE_PREFIX = "score:";
    private static final String TIME_PREFIX = "time:";
    private static final String GROUP_PREFIX = "group:";


    @Override
    public boolean articleVote(Long articleId, Long userId) {
        String articleKey = ARTICLE_PREFIX + articleId;
        String votedKey = VOTED_PREFIX + articleId;
        // 判断文章是否存在
        if (!redisTemplate.hasKey(articleKey)) {
            return false;
        }

        // 检查是否超过投票有效期
        double cutoff = (System.currentTimeMillis() / 1000.0) - ONE_WEEK_IN_SECONDS;
        Double articleTime = (Double) redisTemplate.opsForHash().get(articleKey, TIME_PREFIX);
        if (articleTime != null && articleTime < cutoff) {
            return false;
        }

        // 检查用户是否已经投票
        if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(votedKey, userId))) {
            return false;
        }
        // 开始执行投票事务, 事务部分待添加
        // 添加用户到已投票集合中
        redisTemplate.opsForSet().add(votedKey, userId);

        // 更新文章分数和文章的投票数
        redisTemplate.opsForZSet().incrementScore(SCORE_PREFIX + "articles", articleId, VOTE_SCORE);
        redisTemplate.opsForHash().increment(articleKey, "votes", 1);
        // 结束事务
        return true;
    }

    @Override
    public Long postArticle(Long userId, String title, String link) {
        // 生成新的文章id
        Long articleId = redisTemplate.opsForValue().increment("article:counter");
        // 构建文章数据
        Map<String, Object> articleData = new HashMap<>();
        articleData.put("title", title);
        articleData.put("link", link);
        articleData.put("poster", userId);
        articleData.put("time", System.currentTimeMillis() / 1000.0);
        articleData.put("votes", 1); // 默认文章的票数是1, 即作者自身投票

        // 保存文章数据
        String articleKey = ARTICLE_PREFIX + articleId;
        redisTemplate.opsForHash().putAll(articleKey, articleData);

        // 设置文章投票有效期
        String votedKey = VOTED_PREFIX + articleId;
        redisTemplate.opsForSet().add(votedKey, userId); // 添加作者自己为已投票用户
        redisTemplate.expire(votedKey, ONE_WEEK_IN_SECONDS, java.util.concurrent.TimeUnit.SECONDS); // 设置文章过期时间

        // 添加文章到排序集合中
        double currTime = (System.currentTimeMillis() / 1000.0);
        double score = currTime + VOTE_SCORE; // 当前时间 + 投票分数(默认1票)
        redisTemplate.opsForZSet().add(SCORE_PREFIX + "articles", articleId, score);
        redisTemplate.opsForZSet().add(TIME_PREFIX + "articles", articleId, currTime);
        return articleId;
    }

    @Override
    public List<Article> getArticles(int page, String orderBy) {
        String sortKey;
        if ("score".equals(orderBy)) {
            sortKey = SCORE_PREFIX + "articles";
        } else {
            sortKey = TIME_PREFIX + "articles";
        }

        // 计算分页范围
        long start = (long) (page - 1) * ARTICLES_PER_PAGE;
        long end = start + ARTICLES_PER_PAGE - 1;

        // 获取排序后的文章ID列表（降序）
        Set<Object> articleIds = redisTemplate.opsForZSet().reverseRange(sortKey, start, end);

        if (articleIds == null || articleIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 获取文章详细信息
        List<Article> articles = new ArrayList<>();
        for (Object articleIdObj : articleIds) {
            Long articleId = ((Number) articleIdObj).longValue();
            Article article = getArticleById(articleId);
            if (article != null) {
                articles.add(article);
            }
        }

        return articles;
    }

    @Override
    public boolean addArticleToGroup(Long articleId, String group) {
        String articleKey = ARTICLE_PREFIX + articleId;

        // 检查文章是否存在
        if (!redisTemplate.hasKey(articleKey)) {
            return false;
        }

        // 将文章添加到分组
        String groupKey = GROUP_PREFIX + group;
        redisTemplate.opsForZSet().add(groupKey + ":time", articleId,
                (Double) redisTemplate.opsForHash().get(articleKey, "time"));
        redisTemplate.opsForZSet().add(groupKey + ":score", articleId,
                (Double) redisTemplate.opsForHash().get(articleKey, "votes") * VOTE_SCORE);

        return true;
    }

    @Override
    public List<Article> getGroupArticles(String group, int page, String orderBy) {
        String groupKey = GROUP_PREFIX + group;
        String sortKey;

        if ("score".equals(orderBy)) {
            sortKey = groupKey + ":score";
        } else {
            sortKey = groupKey + ":time";
        }

        // 计算分页范围
        long start = (long) (page - 1) * ARTICLES_PER_PAGE;
        long end = start + ARTICLES_PER_PAGE - 1;

        // 获取排序后的文章ID列表（降序）
        Set<Object> articleIds = redisTemplate.opsForZSet().reverseRange(sortKey, start, end);

        if (articleIds == null || articleIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 获取文章详细信息
        List<Article> articles = new ArrayList<>();
        for (Object articleIdObj : articleIds) {
            Long articleId = ((Number) articleIdObj).longValue();
            Article article = getArticleById(articleId);
            if (article != null) {
                articles.add(article);
            }
        }

        return articles;
    }

    /**
     * 根据文章ID获取文章详情
     */
    private Article getArticleById(Long articleId) {
        String articleKey = ARTICLE_PREFIX + articleId;
        Map<Object, Object> articleData = redisTemplate.opsForHash().entries(articleKey);

        if (articleData.isEmpty()) {
            return null;
        }

        Article article = new Article();
        article.setArticleId(articleId);
        article.setTitle((String) articleData.get("title"));
        article.setLink((String) articleData.get("link"));
        article.setPoster(((Number) articleData.get("poster")).longValue());
        article.setTime((Double) articleData.get("time"));
        article.setVotes(((Number) articleData.get("votes")).intValue());

        return article;
    }
}
