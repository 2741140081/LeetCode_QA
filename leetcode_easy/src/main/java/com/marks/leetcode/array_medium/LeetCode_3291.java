package com.marks.leetcode.array_medium;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3291 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/16 10:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3291 {

    /**
     * @Description:
     * 给你一个字符串数组 words 和一个字符串 target。
     * 如果字符串 x 是 words 中 任意 字符串的 前缀，则认为 x 是一个 有效 字符串。
     * 现计划通过 连接 有效字符串形成 target ，请你计算并返回需要连接的 最少 字符串数量。
     * 如果无法通过这种方式形成 target，则返回 -1。
     *
     * tips:
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 5 * 10^3
     * 输入确保 sum(words[i].length) <= 10^5。
     * words[i] 只包含小写英文字母。
     * 1 <= target.length <= 5 * 10^3
     * target 只包含小写英文字母。
     * @param: words
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/09/16 10:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minValidStrings(String[] words, String target) {
        int result;
        result = method_01(words, target);
        result = method_02(words, target);
        result = method_03(words, target);
        result = method_04(words, target);
        return result;
    }

    /**
     * @Description:
     * 1. 官方题解: KMP + DP
     * @param: words
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/09/16 11:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_04(String[] words, String target) {
        int n = target.length();
        int[] back = new int[n];
        for (String word : words) {
            int[] pi = prefixFunction(word, target);
            int m = word.length();
            for (int i = 0; i < n; i++) {
                back[i] = Math.max(back[i], pi[m + 1 + i]);
            }
        }
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 1, n + 1, (int) 1e9);
        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i + 1 - back[i]] + 1;
            if (dp[i + 1] > n) {
                return -1;
            }
        }
        return dp[n];
    }

    private int[] prefixFunction(String word, String target) {
        String s = word + "#" + target;
        int n = s.length();
        int[] pi = new int[n];
        for (int i = 1; i < n; i++) {
            int j = pi[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = pi[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }

    /**
     * @Description:
     * 1. 瓶颈 1：boolean[][] isPrefix 空间与时间浪费
     * isPrefix[i][j] 存储了 target[i..j] 是否为合法前缀，但有个关键性质：
     * 如果 target[i..j] 是合法前缀，那么 target[i..j-1] 也一定是（前缀的前缀仍是前缀）。
     * 所以每个起始位置 i 只需要记录一个 最大匹配长度 maxLen[i]，不需要存完整的二维数组。
     * isPrefix[j][i] == true 等价于 j + maxLen[j] > i。
     * 2. 瓶颈 2：DP 内层循环做了大量无效迭代
     * 当前 DP 内层从 j = i 遍历到 j = 0，逐一检查 isPrefix[j][i]，但绝大多数都是 false。
     * 实际上只有满足 j + maxLen[j] > i 的 j 才是有效转移。
     * 3. AI 优化代码, AC: 653ms/61.3MB
     * @param: words
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/09/16 11:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(String[] words, String target) {
        int n = target.length();
        TireNode root = new TireNode();
        for (String word : words) {
            TireNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TireNode();
                }
                node = node.children[c - 'a'];
            }
            node.isEnd = true;
        }
        // 优化: 用 maxLen[i] 替代 boolean[][] isPrefix
        // maxLen[i] = 从 target[i] 开始, 能匹配 words 中某个字符串前缀的最大长度
        int[] maxLen = new int[n];
        for (int i = 0; i < n; i++) {
            TireNode node = root;
            int len = 0;
            for (int j = i; j < n; j++) {
                if (node.children[target.charAt(j) - 'a'] == null) {
                    break;
                }
                node = node.children[target.charAt(j) - 'a'];
                len++;
            }
            maxLen[i] = len;
        }

        int INF = Integer.MAX_VALUE / 2;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                if (j + maxLen[j] > i) {
                    dp[i + 1] = Math.min(dp[i + 1], dp[j] + 1);
                }
            }
        }

        return dp[n] == INF ? -1 : dp[n];
    }

    /**
     * @Description:
     * 1. 使用动态规划, 转移方程为: dp[i] = Math.min(dp[i], dp[j] + 1), 其中 0 <= j < i,
     * 并且需要判断 [i, j] 是 words 中的字符串的一个前缀, 使用字典树进行判断, 并且 j 是从 i - 1 到 0 进行遍历, 倒序遍历,
     * 如果 j 不符合, 则 j - 1 必定不符合, 因此可以提前进行剪枝
     * 2. 使用字典树存储 words, 然后用双重for 循环来进行动态规划, 查找 [i, j] 是否是 words 中的字符串的一个前缀
     * 3. 是否应该提前存储 boolean[][] isPrefix, 其中 isPrefix[i][j] 表示 target[i~j] 是否是 words 中的字符串的一个前缀
     * 4. 不能提前剪枝, 因为 "bc" 不是一个前缀, 但是 "abc" 是一个前缀, 所以不能提前剪枝
     * 5. 超时, 928/929, 由于 n <= 5 * 10^3, 所以 n^2 不会超时, 那么只能是字典树的问题, 也就是构建字典树导致超时
     * 6. 分析字典树构建的时间复杂度, m <= 100, words[i].length <= 5 * 10^3, sum(words[i].length) <= 10^5,
     * 所以字典树构建的时间复杂度是 10^5, 但是双重for 循环的时间复杂度是 25 * 10^6, 即 10^7, 这才是导致超时的原因
     * 7. AI 优化 @see method_03
     * @param: words
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/09/16 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String[] words, String target) {
        int n = target.length();
        TireNode root = new TireNode();
        for (String word : words) {
            // 由于 word 的每一个前缀都是一个合法的字符串, 所以需要将每个前缀的 isEnd 都设置为 true
            TireNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TireNode();
                }
                node = node.children[c - 'a'];
            }
            node.isEnd = true;
        }
        // 预处理 isPrefix
        boolean[][] isPrefix = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            TireNode node = root;
            for (int j = i; j < n; j++) {
                if (node.children[target.charAt(j) - 'a'] == null) {
                    break;
                }
                isPrefix[i][j] = true;
                node = node.children[target.charAt(j) - 'a'];
            }
        }
        int INF = Integer.MAX_VALUE / 2;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                if (isPrefix[j][i]) {
                    dp[i + 1] = Math.min(dp[i + 1], dp[j] + 1);
                }
            }
        }

        return dp[n] == INF ? -1 : dp[n];
    }


    /**
     * @Description:
     * 1. 由于 words.length <= 100 的很小, 所以可以采用遍历的方式选择合适的字符串
     * 2. 使用字典树存储 words, 然后通过遍历 target 的有效下标, 最初的有效下标是 0,
     * 然后从0开始遍历 target, 当遇到字典树中的 isEnd 为 true 的节点时, 则说明找到了一个有效的字符串,
     * 将此时的下标添加到有效下标列表中, 继续遍历, 直到字典树为 null 或者 (到达末尾并且此时 isEnd 为 true)
     * 3. int[] dp, 记录从下标 i 到末尾的最小字符串数量, 初始值设置为 Integer.MAX_VALUE, 大小是 n + 1,
     * dp[0] = 0.
     * 超时, 770/929
     * @param: words
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/09/16 10:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] words, String target) {
        int n = target.length();
        TireNode root = new TireNode();
        for (String word : words) {
            // 由于 word 的每一个前缀都是一个合法的字符串, 所以需要将每个前缀的 isEnd 都设置为 true
            TireNode node = root;
            for (char c : word.toCharArray()) {
                if (node.children[c - 'a'] == null) {
                    node.children[c - 'a'] = new TireNode();
                }
                node.isEnd = true;
                node = node.children[c - 'a'];
            }
            node.isEnd = true;
        }

        // 创建一个优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {cnt, index}, 小根堆
        pq.offer(new int[] {0, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cnt = cur[0], index = cur[1];
            if (index == n) {
                return cnt;
            }
            TireNode node = root;
            for (int i = index; i < n; i++) {
                // i: 来匹配字典树
                char c = target.charAt(i);
                if (node.children[c - 'a'] == null) {
                    break;
                }
                node = node.children[c - 'a'];
                if (node.isEnd) {
                    pq.offer(new int[] {cnt + 1, i + 1});
                }
            }
        }
        return -1;
    }

    // 构建一个字典树
    class TireNode {
        TireNode[] children;
        boolean isEnd;

        public TireNode() {
            children = new TireNode[26];
            isEnd = false;
        }
    }

}
