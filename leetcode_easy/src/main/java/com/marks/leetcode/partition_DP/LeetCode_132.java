package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/20 10:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_132 {
    /**
     * @Description: [
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文串。
     *
     * 返回符合要求的 最少分割次数 。
     * tips:
     * 1 <= s.length <= 2000
     * s 仅由小写英文字母组成
     * 回文串:判断一个字符串是否是回文串的方法有多种，其中最直观的方法是将字符串反转后与原字符串进行比较，
     * 如果两者相同，则原字符串是回文串；否则，不是回文串。
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/20 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCut(String s) {
        int result = 0;
//        result = method_01(s);
        result = method_02(s);
        return result;
    }

    /**
     * @Description: [官方题解]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/20 11:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
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
        Arrays.fill(ans, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            if (dp[0][i]) {
                ans[i] = 0;
            }else {
                for (int j = 0; j < i; j++) {
                    if (dp[j + 1][i]) {
                        ans[i] = Math.min(ans[i], ans[j] + 1);
                    }
                }
            }
        }
        return ans[n - 1];
    }

    /**
     * @Description: [
     * E1:输入：s = "aab"
     * 输出：1
     * 解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
     * 解答错误
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/20 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @Deprecated
    private int method_01(String s) {
        if (checkIsPalindrome(s)) {
            return 0;
        }
        int n = s.length();
        int ans = 0;
        int leftIndex = 0;
        while (leftIndex < n){
            for (int j = n; j > leftIndex; j--) {
                // String substring(int beginIndex, int endIndex) 其中endIndex是不包含
                boolean flag = checkIsPalindrome(s.substring(leftIndex, j));
                if (flag) {
                    ans++;
                    leftIndex = j;
                    // 是否需要继续判断剩余的字符串是否符合回文串
                    if (checkIsPalindrome(s.substring(leftIndex, n))) {
                        return ans;
                    }
                }
            }
        }
        return ans;
    }
    @Deprecated
    private boolean checkIsPalindrome(String str) {
        int l = 0;
        int r = str.length() - 1;
        while (l < r) {
            if (str.charAt(l) == str.charAt(r)) {
                l++;
                r--;
            }else {
                return false;
            }
        }
        return true;
    }
}
