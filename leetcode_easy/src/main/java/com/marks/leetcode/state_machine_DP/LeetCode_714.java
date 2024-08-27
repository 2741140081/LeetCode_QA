package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称: 买卖股票之含手续费 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/27 15:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_714 {
    /**
     * @Description: [
     * 给定一个整数数组 prices，其中 prices[i]表示第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
     * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     * 返回获得利润的最大值。
     *
     * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
     * tips:
     * 1 <= prices.length <= 5 * 10^4
     * 1 <= prices[i] < 5 * 10^4
     * 0 <= fee < 5 * 10^4
     * ]
     * @param prices
     * @param fee
     * @return int
     * @author marks
     * @CreateDate: 2024/8/27 15:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int[] prices, int fee) {
        int result = 0;
//        result = method_01(prices, fee);
        result = method_02(prices, fee);
        return result;
    }

    /**
     * @Description: [
     * 动态规划之空间优化
     * AC:5ms/53.72MB
     * ]
     * @param prices
     * @param fee
     * @return int
     * @author marks
     * @CreateDate: 2024/8/27 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] prices, int fee) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int dp_0 = 0;
        int dp_1 = -prices[0] - fee;
        // dp_0 不持有股票
        // dp_1 持有一只股票
        for (int i = 1; i < n; i++) {
            int pre_0 = dp_0;
            int pre_1 = dp_1;
            dp_0 = Math.max(pre_0, pre_1 + prices[i]);
            dp_1 = Math.max(pre_1, pre_0 - prices[i] - fee);
        }
        return dp_0;
    }

    /**
     * @Description: [
     * 1.没有冷冻期, 但是有手续费, 我是应该把手续费放在买入时还是应该放在卖出时？
     * 直觉上是直接放在买入时, 相当于是沉没成本, 如果沉没成本 >= 卖出收益 这笔买卖不做也罢
     * 2.可以买卖无数次
     * 3.最大持有1只股票
     * 4.数量级限制不能是O(n^2)的时间复杂度
     * AC:21ms/53.8MB
     *
     * 空间优化: method_02
     * ]
     * @param prices
     * @param fee
     * @return int
     * @author marks
     * @CreateDate: 2024/8/27 15:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] prices, int fee) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[][] dp = new int[n][2];
        // dp[i][0] 不持有股票
        // dp[i][1] 持有一只股票
        dp[0][1] = -prices[0] - fee;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i] - fee);
        }
        return dp[n - 1][0];
    }
}
