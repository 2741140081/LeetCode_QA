package com.marks.leetcode.classic_linear_DP.LCS;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_115 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 给你两个字符串 s 和 t ，统计并返回在 s 的 子序列 中 t 出现的个数，结果需要对 10^9 + 7 取模。
     * ]
     * @param s
     * @param t
     * @return int
     * @author marks
     * @CreateDate: 2024/8/9 16:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numDistinct(String s, String t) {
        int result = 0;
//        result = method_01(s, t);
        result = method_02(s, t);
        return result;
    }

    /**
     * @Description: [基于method_01, 使用滚动数组优化空间复杂度]
     * @param s
     * @param t
     * @return int
     * @author marks
     * @CreateDate: 2024/8/12 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s, String t) {
        int m = t.length();
        int n = s.length();
        if (n < m) {
            return 0;
        }
        int[][] dp = new int[2][n + 1];
        // i = 0时, t 为空字符串
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 1;
        }
        int curr = 0;

        for (int i = 1; i <= m; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            char tChar = t.charAt(i - 1);
            Arrays.fill(dp[curr], 0);
            for (int j = i; j <= n; j++) {
                char sChar = s.charAt(j - 1);
                if (sChar == tChar) {
                    dp[curr][j] = (dp[pre][j - 1] + dp[curr][j - 1]) % MOD;
                }else {
                    dp[curr][j] = dp[curr][j - 1] % MOD;
                }
            }
        }
        return dp[curr][n];
    }

    /**
     * 初始化数组 dp[i][j] = new int[m + 1][n + 1];
     * 1. 判断边界情况
     * 当 j = n, 此时 t 为空字符串, 空字符是任何字符串的子串, dp[i][n] = 1
     * i = m时, s 为空字符串 则 0 <= j < n, t不为空字符串, 所以t不可能是s的子串, dp[m][j] = 0
     *
     * 2. if s[i] == t[j]
     *
     *
     * @param s
     * @param t
     * @return
     */
    private int method_01(String s, String t) {
        int m = t.length();
        int n = s.length();
        if (n < m) {
            return 0;
        }
        int[][] dp = new int[m + 1][n + 1];
        // i = 0时, t 为空字符串
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 1;
        }
        // j = 0时, s 为空字符串 则 1 <= j <= m
        for (int j = 1; j <= m; j++) {
            dp[j][0] = 0;
        }

        for (int i = 1; i <= m; i++) {
            char tChar = t.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char sChar = s.charAt(j - 1);
                if (sChar == tChar) {
                    dp[i][j] = (dp[i - 1][j - 1] + dp[i][j - 1]) % MOD;
                }else {
                    dp[i][j] = dp[i][j - 1] % MOD;
                }
            }
        }



        return dp[m][n];
    }
}
