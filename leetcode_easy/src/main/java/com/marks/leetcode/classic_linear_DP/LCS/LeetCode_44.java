package com.marks.leetcode.classic_linear_DP.LCS;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/14 9:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_44 {
    /**
     * @Description: [
     * 给你一个输入字符串 (s) 和一个字符模式 (p) ，请你实现一个支持 '?' 和 '*' 匹配规则的通配符匹配：
     * '?' 可以匹配任何单个字符。
     * '*' 可以匹配任意字符序列（包括空字符序列）。
     * 判定匹配成功的充要条件是：字符模式必须能够 完全匹配 输入字符串（而不是部分匹配）。
     * tips:
     * 0 <= s.length, p.length <= 2000
     * s 仅由小写英文字母组成
     * p 仅由小写英文字母、'?' 或 '*' 组成
     * ]
     * @param s
     * @param p
     * @return boolean
     * @author marks
     * @CreateDate: 2024/8/14 9:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isMatch(String s, String p) {
        boolean res = false;
//        res = method_01(s, p);
        res = method_02(s, p);
        return res;
    }

    /**
     * @Description: [
     * 滚动数组优化空间复杂度
     * ]
     * @param s
     * @param p
     * @return boolean
     * @author marks
     * @CreateDate: 2024/8/14 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[2][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = true;
            }else{
                break;
            }
        }
        int curr = 0;
        for (int i = 1; i <= m; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            char sChar = s.charAt(i - 1);
            Arrays.fill(dp[curr], false);
            for (int j = 1; j <= n; j++) {
                char pChar = p.charAt(j - 1);
                if (pChar == '*') {
                    dp[curr][j] = dp[pre][j] || dp[curr][j - 1];
                } else if (sChar == pChar || pChar == '?') {
                    dp[curr][j] = dp[pre][j - 1];
                }
            }
        }
        return dp[curr][n];
    }

    /**
     * @Description: [
     * E1:
     * 输入：s = "aa", p = "a"
     * 输出：false
     * 解释："a" 无法匹配 "aa" 整个字符串。
     *
     * E2:
     * 输入：s = "aa", p = "*"
     * 输出：true
     * 解释：'*' 可以匹配任意字符串。
     * ]
     * @param s
     * @param p
     * @return boolean
     * @author marks
     * @CreateDate: 2024/8/14 9:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int i = 1; i <= n; i++) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = true;
            }else {
                break;
            }
        }
        for (int i = 1; i <= m; i++) {
            char sChar = s.charAt(i - 1);
            for (int j = 1; j <= n; j++) {
                char pChar = p.charAt(j - 1);
                if (pChar == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else if (sChar == pChar || pChar == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[m][n];
    }
}
