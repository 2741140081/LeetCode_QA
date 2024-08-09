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
    /**
     * @Description: [
     * 给定两个字符串s1 和 s2，返回 使两个字符串相等所需删除字符的 ASCII 值的最小和 。
     * E1:
     * 输入: s1 = "delete", s2 = "leet"
     * 输出: 403
     * 解释: 在 "delete" 中删除 "dee" 字符串变成 "let"，
     * 将 100[d]+101[e]+101[e] 加入总和。在 "leet" 中删除 "e" 将 101[e] 加入总和。
     * 结束时，两个字符串都等于 "let"，结果即为 100+101+101+101 = 403 。
     * 如果改为将两个字符串转换为 "lee" 或 "eet"，我们会得到 433 或 417 的结果，比答案更大
     * ]
     * @param s1
     * @param s2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/9 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumDeleteSum(String s1, String s2) {
        int result = 0;
//        result = method_01(s1, s2);
        result = method_02(s1, s2);
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

    private int method_02(String s1, String s2) {
        char[] char1 = s1.toCharArray();
        char[] char2 = s2.toCharArray();
        int m = char1.length;
        int n = char2.length;
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        int sum = 0;
        for (char c : char1) {
            sum += (int) c;
        }
        for (char c : char2) {
            sum += (int) c;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (char1[i - 1] == char2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + (int) char1[i - 1] + (int) char2[j - 1];
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return sum - dp[m][n];
    }
}
