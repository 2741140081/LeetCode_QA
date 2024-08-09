package com.marks.leetcode.classic_linear_DP.LCS;

import java.util.Arrays;

/**
 * <p>项目名称: LCS </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_712 {
    public int minimumDeleteSum(String s1, String s2) {
        int result = 0;
        result = method_01(s1, s2);
        return result;
    }

    private int method_01(String s1, String s2) {
        char[] char1 = s1.toCharArray();
        char[] char2 = s2.toCharArray();
        int m = char1.length;
        int n = char2.length;

        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            dp[0][i] = dp[0][i - 1] + (int) char2[i - 1];
        }
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + (int) char1[i - 1];
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (char1[i - 1] == char2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                }else {
                    dp[i][j] = Math.min(dp[i][j - 1] + (int) char2[j - 1], dp[i - 1][j] + (int) char1[i - 1]);

                }
            }
        }
        return dp[m][n];
    }
}
