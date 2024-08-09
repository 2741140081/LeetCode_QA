package com.marks.leetcode.classic_linear_DP.LCS;

import java.util.Arrays;

/**
 * <p>项目名称: 最长公共子序列 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 9:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1143 {
    /**
     * @Description: [
     * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
     * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
     * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
     * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
     *
     * 根据百度搜索结果可知: LCS的转态转移方程如下
     * S1[], S2[]
     *
     * dp[i][j]:
     * 1. x = 0, or y = 0. 则 dp[i][j] = 0
     * 2. S1[i] == S2[j]. 则 dp[i][j] = dp[i - 1][j - 1] + 1
     * 3. S1[i] != S2[j]. 则 dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1])
     *
     * 详细情况查看:
     * 动态规划 最长公共子序列 过程图解: https://blog.csdn.net/hrn1216/article/details/51534607
     * ]
     * @param text1
     * @param text2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/9 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int result = 0;
        result = method_01(text1, text2);
        return result;
    }

    /**
     * @Description: [
     * 输入：text1 = "abcde", text2 = "ace"
     * 输出：3
     * 解释：最长公共子序列是 "ace" ，它的长度为 3 。
     * ]
     * @param text1
     * @param text2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/9 9:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String text1, String text2) {
        char[] char1 = text1.toCharArray();
        char[] char2 = text2.toCharArray();
        int m = char1.length;
        int n = char2.length;
        int[][] dp = new int[m + 1][n + 1];
        // 初始化, i = 0 or j = 0 , dp[i][j] = 0;
        Arrays.fill(dp[0], 0);
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 0;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (char1[i - 1] == char2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
