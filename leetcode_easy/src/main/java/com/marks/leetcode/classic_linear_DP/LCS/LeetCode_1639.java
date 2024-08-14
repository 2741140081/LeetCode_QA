package com.marks.leetcode.classic_linear_DP.LCS;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/13 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1639 {
    private final int MOD = (int) 1e9 + 7;

    public int numWays(String[] words, String target) {
        int result = 0;
        result = method_01(words, target);
        return result;
    }

    private int method_01(String[] words, String target) {
        int n = words[0].length();
        int m = words.length;
        int t = target.length();

        int[][] f = new int[n][26];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int k = words[i].charAt(j) - 'a';
                f[j][k]++;
            }
        }
        long[][] dp = new long[t + 1][n + 1];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i <= t; i++) {
            int k = target.charAt(i - 1) - 'a';
            for (int j = i; j <= n; j++) {
                if (f[j - 1][k] > 0) {
                    dp[i][j] = (dp[i - 1][j - 1] * f[j - 1][k] + dp[i][j-1] ) % MOD;
                }else {
                    dp[i][j] = dp[i][j - 1];
                }
            }
        }
        return (int) dp[t][n];
    }
}
