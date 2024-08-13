package com.marks.leetcode.classic_linear_DP.LCS;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/12 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1092 {
    public String shortestCommonSupersequence(String str1, String str2) {
        String res = "";
        res = method_01(str1, str2);
        return res;
    }

    private String method_01(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        // 求str1 与str2的最长公共子序列
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        // 逆序还原
        StringBuilder sb = new StringBuilder();
        int t1 = m, t2 = n;
        while (t1 > 0 || t2 > 0) {
            if (t1 == 0) {
                sb.append(str2.charAt(t2 - 1));
                t2--;
            }else if (t2 == 0) {
                sb.append(str1.charAt(t1 - 1));
                t1--;
            }else {
                if (str1.charAt(t1 - 1) == str2.charAt(t2 - 1)) {
                    sb.append(str1.charAt(t1 - 1));
                    t1--;
                    t2--;
                } else if (dp[t1][t2] == dp[t1 - 1][t2]) {
                    sb.append(str1.charAt(t1 - 1));
                    t1--;
                } else if (dp[t1][t2] == dp[t1][t2 - 1]) {
                    sb.append(str2.charAt(t2 - 1));
                    t2--;
                }
            }
        }
        return sb.toString();
    }
}
