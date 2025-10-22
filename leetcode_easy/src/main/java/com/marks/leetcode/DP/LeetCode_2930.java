package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/22 11:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2930 {

    private static final int MOD = 1000000007;
    /**
     * @Description:
     * 给你一个整数 n 。
     * 如果一个字符串 s 只包含小写英文字母，且 将 s 的字符重新排列后，新字符串包含 子字符串 "leet" ，那么我们称字符串 s 是一个 好 字符串。
     * 比方说：
     * 字符串 "lteer" 是好字符串，因为重新排列后可以得到 "leetr" 。
     * "letl" 不是好字符串，因为无法重新排列并得到子字符串 "leet" 。
     * 请你返回长度为 n 的好字符串 总 数目。
     *
     * 由于答案可能很大，将答案对 10^9 + 7 取余 后返回。
     *
     * 子字符串 是一个字符串中一段连续的字符序列。
     * tips:
     * 1 <= n <= 10^5
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/22 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int stringCount(int n) {
        int result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }
    
    private int n;
    private int[][] memo;

    /**
     * @Description:
     * 查看题解后
     * AC:323ms/58.42MB
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/22 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n) {
        this.n = n;
        int all = 1 << 4;
        memo = new int[n][all];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, all - 1);
    }



    /**
     * @Description:
     * memo = {
     *     {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
     *     {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
     *     {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
     *     {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
     * }
     * i = 0, mask = 15
     *
     * @param i
     * @param mask
     * @return int
     * @author marks
     * @CreateDate: 2025/10/22 14:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int dfs(int i, int mask) {
        if (i == n) {
            return mask == 0 ? 1 : 0;
        }
        if (memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int ans = 0;
        for (char j = 'a'; j <= 'z'; j++) {

            if ((mask & 1) == 1 && j == 'l') {
                ans =(ans + dfs(i + 1, mask ^ 1)) % MOD;
            } else if ((mask & 2) != 0 && j == 'e') {
                ans = (ans + dfs(i + 1, mask ^ 2)) % MOD;

            } else if ((mask & 4) != 0 && j == 'e') {
                ans = (ans + dfs(i + 1, mask ^ 4)) % MOD;

            } else if ((mask & 8) != 0 && j == 't') {
                ans = (ans + dfs(i + 1, mask ^ 8)) % MOD;

            } else {
                ans = (ans + dfs(i + 1, mask)) % MOD;
            }
        }
        return memo[i][mask] = ans;
    }


    /**
     * @Description:
     * E1:
     * 输入：n = 4
     * 输出：12
     * l => 4, t => 3, ee => 1,
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/22 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        return (int) dfsCount(0, 0, 0, 0);
    }

    private long dfsCount(int i, int l, int e, int t) {
        if (i == n) {
            // 满足条件, 返回1
            return l >= 1 && e >= 2 && t >= 1 ? 1 : 0;
        }
        long ans = 0;
        for (char j = 'a'; j <= 'z'; j++) {
            if (j == 'l') {
                ans = (ans + dfsCount(i + 1, l + 1, e, t)) % MOD;
            } else if (j == 'e' ) {
                ans = (ans + dfsCount(i + 1, l, e + 1, t)) % MOD;
            } else if (j == 't') {
                ans = (ans + dfsCount(i + 1, l, e, t + 1)) % MOD;
            } else {
                ans = (ans + dfsCount(i + 1, l, e, t)) % MOD;
            }
        }
        return ans % MOD;
    }

}
