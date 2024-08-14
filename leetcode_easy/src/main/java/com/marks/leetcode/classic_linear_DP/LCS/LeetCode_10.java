package com.marks.leetcode.classic_linear_DP.LCS;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/14 10:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_10 {
    /**
     * @Description: [
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     *
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素
     * 所谓匹配，是要涵盖 整个 字符串 s 的，而不是部分字符串。
     *
     * tips:
     * 1 <= s.length <= 20
     * 1 <= p.length <= 20
     * s 只包含从 a-z 的小写字母。
     * p 只包含从 a-z 的小写字母，以及字符 . 和 *。
     * 保证每次出现字符 * 时，前面都匹配到有效的字符
     * ]
     * @param s
     * @param p
     * @return boolean
     * @author marks
     * @CreateDate: 2024/8/14 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isMatch(String s, String p) {
        boolean res = false;
        res = method_01(s, p);
//        res = method_02(s, p);
        return res;
    }

    /**
     * @Description: [
     * E1:
     * 输入：s = "aa", p = "a*"
     * 输出：true
     * 解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
     *
     * E2:
     * 输入：s = "ab", p = ".*"
     * 输出：true
     * 解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
     * ]
     * @param s
     * @param p
     * @return boolean
     * @author marks
     * @CreateDate: 2024/8/14 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 0; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char pChar = p.charAt(j - 1);
                if (pChar == '*') {
                    // 保证每次出现字符 * 时，前面都匹配到有效的字符
                    dp[i][j] = dp[i][j - 2];
                    if (match(s, p, i, j - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    if (match(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[m][n];
    }

    private boolean match(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}
