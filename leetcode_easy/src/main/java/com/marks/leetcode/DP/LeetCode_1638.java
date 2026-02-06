package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1638 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/6 10:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1638 {

    /**
     * @Description:
     * 给你两个字符串 s 和 t ，请你找出 s 中的非空子串的数目，
     * 这些子串满足替换 一个不同字符 以后，是 t 串的子串。换言之，请你找到 s 和 t 串中 恰好 只有一个字符不同的子字符串对的数目。
     * 比方说， "computer" and "computation" 只有一个字符不同： 'e'/'a' ，所以这一对子字符串会给答案加 1 。
     *
     * 请你返回满足上述条件的不同子字符串对数目。
     *
     * 一个 子字符串 是一个字符串中连续的字符。
     * @param: s
     * @param: t
     * @return int
     * @author marks
     * @CreateDate: 2026/02/06 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countSubstrings(String s, String t) {
        int result;
        result = method_01(s, t);
        result = method_02(s, t);
        return result;
    }

    /**
     * @Description:
     * s = "abcddabcd"
     * t = "abcdcabcd"
     * 1. 仅s[4] != t[4], 存在的可能子串数 dpl[i - 1][j - 1] * dp[i + 1][j + 1] + 1(本身s[4])
     * AC: 5ms/43MB
     * @param: s
     * @param: t
     * @return int
     * @author marks
     * @CreateDate: 2026/02/06 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s, String t) {
        int m = s.length(), n = t.length();
        int[][] dpl = new int[m + 1][n + 1];
        int[][] dpr = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s.charAt(i) == t.charAt(j)) {
                    dpl[i + 1][j + 1] = dpl[i][j] + 1;
                } else {
                    dpl[i + 1][j + 1] = 0;
                }
            }
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (s.charAt(i) == t.charAt(j)) {
                    dpr[i][j] = dpr[i + 1][j + 1] + 1;
                } else {
                    dpr[i][j] = 0;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s.charAt(i) != t.charAt(j)) {
                    ans += (dpl[i][j] + 1) * (dpr[i + 1][j + 1] + 1);
                }
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 暴力吧
     * AC: 5ms/42.13MB
     * @param: s
     * @param: t
     * @return int
     * @author marks
     * @CreateDate: 2026/02/06 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, String t) {
        int m = t.length();
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int k = 0;
                int count = 0;
                while (i + k < n && j + k < m && count < 2) {
                    if (s.charAt(i + k) != t.charAt(j + k)) {
                        count++;
                    }
                    if (count == 1) {
                        ans++;
                    }
                    k++;
                }
            }
        }
        return ans;
    }

}
