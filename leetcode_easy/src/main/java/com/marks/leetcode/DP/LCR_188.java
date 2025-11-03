package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_188 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/31 15:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_188 {

    /**
     * @Description:
     * 数组 prices 记录了某芯片近期的交易价格，其中 prices[i] 表示的 i 天该芯片的价格。
     * 你只能选择 某一天 买入芯片，并选择在 未来的某一个不同的日子 卖出该芯片。
     * 请设计一个算法计算并返回你从这笔交易中能获取的最大利润。
     *
     * 如果你不能获取任何利润，返回 0。
     * @param: prices
     * @return int
     * @author marks
     * @CreateDate: 2025/10/31 15:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int bestTiming(int[] prices) {
        int result;
        result = method_01(prices);
        return result;
    }

    /***
     * @Description: [方法描述]
     * @param: prices
     * @return int
     * @author marks
     * @CreateDate: 2025/10/31 15:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }
}
