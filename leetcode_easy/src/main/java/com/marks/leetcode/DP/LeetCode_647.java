package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 9:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_647 {

    /**
     * @Description:
     * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
     * 回文字符串 是正着读和倒过来读一样的字符串。
     * 子字符串 是字符串中的由连续字符组成的一个序列。
     *
     * tips:
     * 1 <= s.length <= 1000
     * s 由小写英文字母组成
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 9:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countSubstrings(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    
    /**
     * @Description:
     * 1. dp[i] = dp[i - 1]; 当前节点为i for l = 0 ~ i - 1, dp[i] = dp[i] + 1;当且仅当 [l, r] 为回文串时
     * 2. 构建一个方法, 判断[l, r]是否为回文串, 通过双指针判断
     * AC: 25ms/44.61MB
     * @param s 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 9:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n]; // 存储dp[i][j] 是否是回文串
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], true); // 设置默认值为true
        }
        // 预处理dp[i][j], 判断是否是回文串
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
            }
        }
        int[] ans = new int[n];
        ans[0] = 1;
        for (int i = 1; i < n; i++) {
            ans[i] = ans[i - 1];
            for (int j = i; j >= 0; j--) {
                if (dp[j][i]) {
                    ans[i]++;
                }
            }
        }

        
        return ans[n - 1];
    }



}
