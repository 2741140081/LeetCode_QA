package com.marks.leetcode.hash_table;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: AuthenticationManager </p>
 * <p>描述: LeetCode_1797 设计一个验证系统 </p>
 * 你需要设计一个包含验证码的验证系统。
 * 每一次验证中，用户会收到一个新的验证码，这个验证码在 currentTime 时刻之后 timeToLive 秒过期。
 * 如果验证码被更新了，那么它会在 currentTime （可能与之前的 currentTime 不同）时刻延长 timeToLive 秒。
 *
 * 请你实现 AuthenticationManager 类：
 *
 * AuthenticationManager(int timeToLive) 构造 AuthenticationManager 并设置 timeToLive 参数。
 * generate(string tokenId, int currentTime) 给定 tokenId ，在当前时间 currentTime 生成一个新的验证码。
 * renew(string tokenId, int currentTime) 将给定 tokenId 且 未过期 的验证码在 currentTime 时刻更新。如果给定 tokenId 对应的验证码不存在或已过期，请你忽略该操作，不会有任何更新操作发生。
 * countUnexpiredTokens(int currentTime) 请返回在给定 currentTime 时刻，未过期 的验证码数目。
 * 如果一个验证码在时刻 t 过期，且另一个操作恰好在时刻 t 发生（renew 或者 countUnexpiredTokens 操作），过期事件 优先于 其他操作。
 *
 * tips:
 * 1 <= timeToLive <= 10^8
 * 1 <= currentTime <= 10^8
 * 1 <= tokenId.length <= 5
 * tokenId 只包含小写英文字母。
 * 所有 generate 函数的调用都会包含独一无二的 tokenId 值。
 * 所有函数调用中，currentTime 的值 严格递增 。
 * 所有函数的调用次数总共不超过 2000 次。
 * @author marks
 * @version v1.0
 * @date 2026/6/3 9:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class AuthenticationManager {
    private int timeToLive;
    private Map<String, Integer> tokenMap;
    private PriorityQueue<Token> tokenQueue;
    private PriorityQueue<Token> lazyDel;

    /**
     * @Description:
     * 1. 使用 map 存储 tokenId 和 生成的时间
     * 2. 重点是如何高效的处理 countUnexpiredTokens 方法, 不可能每次调用方法时都去遍历 map, 通过计数方式得到结果
     * 3. 是否可以将所有的 过期时间存储到一个 list 集合, 按照过期时间从小到大排序, 然后通过二分查找得到结果
     * 4. 这种方式下就需要关联起 下标与 tokenId 的关系, 这种不行, 因为当向list 中间插入一个数后, 其它 index 都会被修改, 这就导致关联失效
     * 5. 还有一个条件未使用, 即 currentTime 是一个严格递增的序列, 也就是说当调用 countUnexpiredTokens(int currentTime) 时, currentTime 即
     * 为最大值, 也就是判断当前未过期的 tokenId 的数量, 可以添加优先队列, 按照过期时间小到大排序, 需要小根堆
     * 6. 当处理 countUnexpiredTokens 方法是, 通过 int currentTime 来对优先队列进行弹出, 如果 pq.peek().expireTime <= currentTime, 则说明该 tokenId过期
     * 需要执行 pq.poll() 弹出, 最后返回 pq.size() 即可
     * 7. 还有一个问题是 renew 方法会向 pq 添加重复的 tokenId, 需要做一个懒删除, 需要增加一个懒删除的优先队列, 存储的是 renew 前的 tokenId 和 expireTime
     * 8. 如果 pq.peek() 与 lazyDel.peek() 是相同的, 则两种均进行 poll 操作, 相同的定义是 tokenId 相等且 expireTime 相等
     * 9. 另外  countUnexpiredTokens 方法返回值 需要 pq.size() - lazyDel.size(); 这样才是正确答案
     * AC: 44ms/47.23MB
     * @param: timeToLive
     * @return
     * @author marks
     * @CreateDate: 2026/06/03 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public AuthenticationManager(int timeToLive) {
        this.timeToLive = timeToLive;
        tokenMap = new HashMap<>();
        tokenQueue = new PriorityQueue<>();
        lazyDel = new PriorityQueue<>();
    }

    public void generate(String tokenId, int currentTime) {
        // 添加到 map 中, 并且添加到 tokenQueue 中
        tokenMap.put(tokenId, currentTime);
        // 创建 Token
        Token token = new Token(tokenId, currentTime + timeToLive);
        tokenQueue.offer(token);
    }

    public void renew(String tokenId, int currentTime) {
        // 先判断 tokenId 是否在 map 中
        if (!tokenMap.containsKey(tokenId)) {
            return;
        }
        // 判断 currentTime是否过期
        if ((tokenMap.get(tokenId) + timeToLive) <= currentTime) {
            return;
        }
        // 如果没有过期, 则将新的 token 加到 tokenQueue 中, 将 旧的token 添加到 lazyDel 中
        int prevTime = tokenMap.get(tokenId);
        int renewTime = currentTime + timeToLive;
        Token renewToken = new Token(tokenId, renewTime);
        Token oldToken = new Token(tokenId, prevTime + timeToLive);
        tokenQueue.add(renewToken);
        lazyDel.add(oldToken);
        // 更新 map 集合
        tokenMap.put(tokenId, currentTime);
    }

    public int countUnexpiredTokens(int currentTime) {
        while (!tokenQueue.isEmpty() && tokenQueue.peek().expirationTime <= currentTime) {
            Token token = tokenQueue.poll();
            if (!lazyDel.isEmpty()) {
                Token top = lazyDel.peek();
                if (token.equals(top)) {
                    lazyDel.poll();
                }
            }
        }

        return tokenQueue.size() - lazyDel.size();
    }

    private class Token implements Comparable<Token> {
        private String tokenId;
        private int expirationTime;

        public Token(String tokenId, int expirationTime) {
            this.tokenId = tokenId;
            this.expirationTime = expirationTime;
        }

        @Override
        public int compareTo(@NotNull Token o) {
            return this.expirationTime - o.expirationTime;
        }

        // equals 方法
        public boolean equals(Token o) {
            // 即判断 tokenId 是否相等 并且 expirationTime 是否相等
            return this.tokenId.equals(o.tokenId) && this.expirationTime == o.expirationTime;
        }
    }

}
