package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1388 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/8 14:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1388 {

    /**
     * @Description:
     * 给你一个披萨，它由 3n 块不同大小的部分组成，现在你和你的朋友们需要按照如下规则来分披萨：
     *
     * 你挑选 任意 一块披萨。
     * Alice 将会挑选你所选择的披萨逆时针方向的下一块披萨。
     * Bob 将会挑选你所选择的披萨顺时针方向的下一块披萨。
     * 重复上述过程直到没有披萨剩下。
     * 每一块披萨的大小按顺时针方向由循环数组 slices 表示。
     *
     * 请你返回你可以获得的披萨大小总和的最大值。
     *
     * tips:
     * 1 <= slices.length <= 500
     * slices.length % 3 == 0
     * 1 <= slices[i] <= 1000
     * @param: slices
     * @return int
     * @author marks
     * @CreateDate: 2026/04/08 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSizeSlices(int[] slices) {
        int result;
        result = method_01(slices);
        return result;
    }

    /**
     * @Description:
     * 1. t = n / 3, 为挑选的次数
     * 2. 最简单和直观的方法是回溯, 但是会超时. 只能用动态规划
     * 3. 必定不可能取相邻的两块披萨,
     * 4. 怎么从数组中选择 t 个元素, 并且元素之间不相邻, 使得总和最大?
     * @param: slices
     * @return int
     * @author marks
     * @CreateDate: 2026/04/08 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] slices) {
        int n = slices.length;
        int t = n / 3;

        // 情况1：不选第一个元素，考虑 slices[1..n-1]
        int case1 = solveLinear(slices, 1, n - 1, t);

        // 情况2：不选最后一个元素，考虑 slices[0..n-2]
        int case2 = solveLinear(slices, 0, n - 2, t);

        return Math.max(case1, case2);
    }

    /**
     * @Description: 在线性数组 slices[start..end] 中选择 t 个不相邻元素的最大和
     * @param: slices 原数组
     * @param: start 起始索引
     * @param: end 结束索引
     * @param: t 需要选择的元素个数
     * @return int 最大和
     * @author marks
     * @CreateDate: 2026/04/08 14:41
     */
    private int solveLinear(int[] slices, int start, int end, int t) {
        int len = end - start + 1;

        // dp[i][j] 表示在前i个元素中选择j个不相邻元素的最大和
        // i的范围是0到len，j的范围是0到t
        int[][] dp = new int[len + 1][t + 1];

        // 初始化：dp[0][j] = 0 (没有元素可选)，dp[i][0] = 0 (选0个元素)
        // 其他位置初始化为负无穷，表示不可达状态
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= t; j++) {
                dp[i][j] = Integer.MIN_VALUE;
            }
        }

        // 基础情况：选0个元素的和为0
        for (int i = 0; i <= len; i++) {
            dp[i][0] = 0;
        }

        // 状态转移
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= t; j++) {
                // 不选第i个元素（对应slices[start + i - 1]）
                dp[i][j] = dp[i - 1][j];

                // 选第i个元素
                if (i >= 2) {
                    // 从前i-2个元素中选j-1个，再加上当前元素
                    if (dp[i - 2][j - 1] != Integer.MIN_VALUE) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 2][j - 1] + slices[start + i - 1]);
                    }
                } else if (j == 1) {
                    // 特殊情况：i=1且只选1个元素
                    dp[i][j] = Math.max(dp[i][j], slices[start + i - 1]);
                }
            }
        }

        return dp[len][t];
    }

}
