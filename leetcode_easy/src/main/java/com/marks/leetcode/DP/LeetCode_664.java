package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_664 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/31 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_664 {

    /***
     * @Description:
     * 有台奇怪的打印机有以下两个特殊要求：
     *
     * 打印机每次只能打印由 同一个字符 组成的序列。
     * 每次可以在从起始到结束的任意位置打印新字符，并且会覆盖掉原来已有的字符。
     * 给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。
     *
     * tips:
     * 1 <= s.length <= 100
     * s 由小写英文字母组成
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/31 14:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int strangePrinter(String s) {
        int result;
        result = method_01(s);
        return result;
    }


    /***
     * @Description:
     * E1:
     * s = "aba"
     * 1. 当前正在打印 i, dp[i] = dp[i - 1], 相同, dp[i] = dp[i - 1] + 1
     * 2. dp[0] = 1, dp[1] = 2, dp[2] = Math.min(dp[1] + 1, dp[0] + tmp(中间的) 相当于是dp[1])
     * 3. char prev, curr, next 分别表示上一个, 当前, 下一个字符, 如果next == prev 并且 next != curr, dp[i] = dp[i - 1]
     * 然后更新 prev = curr, curr = next;
     * 4. curr = next, dp[i] = dp[i - 1]
     * 5. curr != next, 并且 prev != next, dp[i] = dp[i - 1] + 1
     * 6. 存在问题, 如 'abababa' 4,
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2025/10/31 14:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int[] dp = new int[n];

        return 0;
    }

}
