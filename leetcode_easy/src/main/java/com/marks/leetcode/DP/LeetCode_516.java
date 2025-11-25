package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_516 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/24 15:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_516 {

    /**
     * @Description:
     * 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
     * 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestPalindromeSubseq(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * AC: 35ms/63.00MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

}
