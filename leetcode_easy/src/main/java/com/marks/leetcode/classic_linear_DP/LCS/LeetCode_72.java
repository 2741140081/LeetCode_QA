package com.marks.leetcode.classic_linear_DP.LCS;

/**
 * <p>项目名称: 编辑距离 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 14:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_72 {
    /**
     * @Description: [
     * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
     *
     * 你可以对一个单词进行如下三种操作：
     *
     * 插入一个字符
     * 删除一个字符
     * 替换一个字符
     * ]
     * @param word1 
     * @param word2 
     * @return int
     * @author marks
     * @CreateDate: 2024/8/9 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDistance(String word1, String word2) {
        int result = 0;
        result = method_01(word1, word2);
        return result;
    }

    /**
     * @Description: [
     * 输入：word1 = "horse", word2 = "ros"
     * 输出：3
     * 解释：
     * horse -> rorse (将 'h' 替换为 'r')
     * rorse -> rose (删除 'r')
     * rose -> ros (删除 'e')
     * ]
     * @param word1 
     * @param word2 
     * @return int
     * @author marks
     * @CreateDate: 2024/8/9 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String word1, String word2) {
        char[] char1 = word1.toCharArray();
        char[] char2 = word2.toCharArray();
        int m = char1.length;
        int n = char2.length;
        int[][] dp = new int[m + 1][n + 1];
        // 初始化
        dp[0][0] = 0;
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + 1;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (char1[i - 1] == char2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                }else {
                    dp[i][j] = minValue(dp[i - 1][j - 1], dp[i][j - 1], dp[i - 1][j]) + 1;
                }
            }
        }
        return dp[m][n];
    }

    private int minValue(int i, int... values) {
        int res = i;
        for (int val : values) {
            res = Math.min(res, val);
        }
        return res;
    }
}
