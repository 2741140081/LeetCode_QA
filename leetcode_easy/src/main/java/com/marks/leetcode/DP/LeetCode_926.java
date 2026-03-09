package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_926 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_926 {

    /**
     * @Description:
     * 如果一个二进制字符串，是以一些 0（可能没有 0）后面跟着一些 1（也可能没有 1）的形式组成的，那么该字符串是 单调递增 的。
     * 给你一个二进制字符串 s，你可以将任何 0 翻转为 1 或者将 1 翻转为 0 。
     * 返回使 s 单调递增的最小翻转次数。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/03/09 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minFlipsMonoIncr(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划, int[] dp = new int[2]; 表示字符串以0结尾或者1结尾的最小翻转次数
     * 2. 对于s[i], 有如下情况:
     * s[i] = 0; dp[0] = dp[0]; 保持不变, dp[1] = dp[1] + 1; 需要将当前0翻转为1 或者是 dp[1] = dp[0] + 1; 所以 dp[1] = Math.min(dp[0], dp[1]) + 1;
     * s[i] = 1; dp[0] = dp[0] + 1; 需要将当前1翻转为0; dp[1] = Math.min(dp[1], dp[0]);
     * AC: 11ms/46.4MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/03/09 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int[] dp = new int[2];
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                dp[1] = Math.min(dp[0], dp[1]) + 1;
            } else {
                dp[1] = Math.min(dp[0], dp[1]);
                dp[0] = dp[0] + 1;
            }
        }
        return Math.min(dp[0], dp[1]);
    }

}
