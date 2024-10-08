package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/8 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1278 {
    /**
     * @Description: [
     * 给你一个由小写字母组成的字符串 s，和一个整数 k。
     *
     * 请你按下面的要求分割字符串：
     *
     * 首先，你可以将 s 中的部分字符修改为其他的小写英文字母。
     * 接着，你需要把 s 分割成 k 个非空且不相交的子串，并且每个子串都是回文串。
     * 请返回以这种方式分割字符串所需修改的最少字符数。
     *
     * tips:
     * 1 <= k <= s.length <= 100
     * s 中只含有小写英文字母。
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/8 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int palindromePartition(String s, int k) {
        int result = 0;
        result = method_01(s, k);
        return result;
    }

    /**
     * @Description: [
     * 输入：s = "abc", k = 2
     * 输出：1
     * 解释：你可以把字符串分割成 "ab" 和 "c"，并修改 "ab" 中的 1 个字符，将它变成回文串。
     *
     * 一般看起来是首先对字符串s进行预处理, pre[i][j]中存储s_i ~ s_j修改为回文串需要的数量
     * 无法进行空间优化
     * ]
     * @param s 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/8 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        int[][] pre = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                pre[i][j] = (s.charAt(i) == s.charAt(j) ? 0 : 1) + pre[i + 1][j - 1];
            }
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                if (j == 1) {
                    dp[i][j] = pre[j - 1][i - 1];
                } else{
                    for (int x = j - 1; x < i; x++) {
                        dp[i][j] = Math.min(dp[i][j], dp[x][j - 1] + pre[x][i - 1]);
                    }
                }
            }
        }
        return dp[n][k];
    }
}
