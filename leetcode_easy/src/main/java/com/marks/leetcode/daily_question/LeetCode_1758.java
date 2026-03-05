package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1758 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1758 {

    /**
     * @Description:
     * 给你一个仅由字符 '0' 和 '1' 组成的字符串 s 。一步操作中，你可以将任一 '0' 变成 '1' ，或者将 '1' 变成 '0' 。
     * 交替字符串 定义为：如果字符串中不存在相邻两个字符相等的情况，那么该字符串就是交替字符串。例如，字符串 "010" 是交替字符串，而字符串 "0100" 不是。
     * 返回使 s 变成 交替字符串 所需的 最少 操作数。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/03/05 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 动态规划, 分别 int[] dp = new int[2], dp[0] 表示 s[i] == '0' 时的答案, dp[1] 表示 s[i] == '1' 时的答案
     * AC: 4ms/43.04MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/03/05 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int method_01(String s) {
        int[] dp = new int[2];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '0') {
                int temp = dp[0];
                dp[0] = dp[1];
                dp[1] = temp + 1;
            } else {
                int temp = dp[0];
                dp[0] = dp[1] + 1;
                dp[1] = temp;
            }
        }
        return Math.min(dp[0], dp[1]);
    }

}
