package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/26 10:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_122 {
    /**
     * @Description: [
     * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
     *
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
     *
     * 返回 你能获得的 最大 利润 。
     * tips:
     * 1 <= prices.length <= 3 * 10^4
     * 0 <= prices[i] <= 10^4
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int[] prices) {
        int result = 0;
//        result = method_01(prices);
        result = method_02(prices);
        return result;
    }

    /**
     * @Description: [动态规划: 空间优化]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[] dp = new int[2];
        dp[0] = 0;
        dp[1] = -prices[0];
        for (int i = 1; i < n; i++) {
            int temp_dp_0 = Math.max(dp[0], dp[1] + prices[i]);
            int temp_dp_1 = Math.max(dp[1], dp[0] - prices[i]);
            dp[0] = temp_dp_0;
            dp[1] = temp_dp_1;
        }
        return dp[0];
    }
    /**
     * @Description: [相当于贪心, 只计算上升的差值, 并且更新最小值]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int ans = 0;
        int minValue = prices[0];
        for (int i = 1; i < n; i++) {
            if (minValue > prices[i]) {
                minValue = prices[i];
            }else {
                ans += prices[i] - minValue;
                minValue = prices[i];
            }
        }
        return ans;
    }
}
