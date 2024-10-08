package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  最长回文子串</p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/27 15:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_5 {
    /**
     * @Description: [
     * 给你一个字符串 s，找到 s 中最长的 回文子串。
     * E1:
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     * ]
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/9/27 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String longestPalindrome(String s) {
        String result = "";
//        result = method_01(s);
        result = method_02(s);
        return result;
    }
    /**
     * @Description: [
     * 这个只能算暴力破解
     * AC:299ms/45.73MB
     * ]
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/9/27 16:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_02(String s) {
        String ans = s.substring(0, 1);
        int n = s.length();
        boolean[][] pre = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(pre[i], true); // 设置默认值为true
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                pre[i][j] = (s.charAt(i) == s.charAt(j)) && pre[i + 1][j - 1];
            }
        }
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (j - i > maxLen && pre[i][j]) {
                    maxLen = Math.max(maxLen, j - i);
                    ans = s.substring(i, j + 1);
                }
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * Manacher 算法(马拉车算法)
     * AC:7ms/42.06
     * ]
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/9/27 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        StringBuilder temp = new StringBuilder("^#");
        for (int i = 0; i < s.length(); i++) {
            temp.append(s.charAt(i)).append("#");
        }
        temp.append("$");
        int n = temp.length();
        int[] dp = new int[n];
        int center = 0;
        int right = 0;
        for (int i = 1; i < n - 1; i++) {
            int i_mirror = 2 * center - i;
            if (right > i) {
                dp[i] = Math.min(right - i, dp[i_mirror]); // 防止超出right
            }else {
                dp[i] = 0; // 等于right
            }

            while (temp.charAt(i + 1 + dp[i]) == temp.charAt(i - 1 - dp[i])) {
                dp[i]++;
            }

            // 判断是否需要更新right
            if (i + dp[i] > right) {
                center = i;
                right = i + dp[i];
            }
        }

        // 找出 P 的最大值
        int maxLen = 0;
        int centerIndex = 0;
        for (int i = 1; i < n - 1; i++) {
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                centerIndex = i;
            }
        }
        int start = (centerIndex - maxLen) / 2; //最开始讲的求原字符串下标
        return s.substring(start, start + maxLen);
    }
}
