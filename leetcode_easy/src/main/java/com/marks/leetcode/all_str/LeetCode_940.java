package com.marks.leetcode.all_str;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_940 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/7 14:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_940 {

    /**
     * @Description:
     * 给定一个字符串 s，计算 s 的 不同非空子序列 的个数。因为结果可能很大，所以返回答案需要对 10^9 + 7 取余 。
     * 字符串的 子序列 是经由原字符串删除一些（也可能不删除）字符但不改变剩余字符相对位置的一个新字符串。
     * 例如，"ace" 是 "abcde" 的一个子序列，但 "aec" 不是
     *
     * tips:
     * 1 <= s.length <= 2000
     * s 仅由小写英文字母组成
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/09/07 14:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int distinctSubseqII(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 查看题解, 使用动态规划解决
     * AC: 8ms/42.75MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/09/07 14:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int MOD = 1000000007;
        int[] dp = new int[n];
        int[] last = new int[26];
        Arrays.fill(last, -1);
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            int x = s.charAt(i) - 'a';
            for (int j = 0; j < 26; j++) {
                if (last[j] != -1) {
                    dp[i] = (dp[i] + dp[last[j]]) % MOD;
                }
            }
            last[x] = i;
        }
        int ans = 0;
        for (int i = 0; i < 26; i++) {
            if (last[i] != -1) {
                ans = (ans + dp[last[i]]) % MOD;
            }
        }
        return ans;
    }

}
