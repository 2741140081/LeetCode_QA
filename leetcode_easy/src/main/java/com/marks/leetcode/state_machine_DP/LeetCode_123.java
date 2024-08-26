package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/26 11:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_123 {
    /**
     * @Description: [
     * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
     *
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
     *
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     *
     * tips:
     * 1 <= prices.length <= 10^5
     * 0 <= prices[i] <= 10^5
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 11:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int[] prices) {
//        int result = method_01(prices);
        int result = method_02(prices);
        return result;
    }

    /**
     * @Description: [灵神题解: 动态规划
     * int[][][] dp = new int[n + 1][k + 2][2];
     * 定义3维数组 dp[i][j][k]: i 表示前i个元素, j表示交易次数, k表示是否持有股票, 0表示没有, 1表示有
     * dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i])
     * dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i])
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int k = 2;
        int[][][] dp = new int[n + 1][k + 2][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k + 2; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE / 2); // 因为如果仅设置最小值, 当最小值 - prices[i] 时会产生溢出
            }
        }
        for (int j = 1; j <= k + 1; j++) {
            dp[0][j][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k + 1; j++) {
                dp[i + 1][j][0] = Math.max(dp[i][j][0], dp[i][j][1] + prices[i]);
                dp[i + 1][j][1] = Math.max(dp[i][j][1], dp[i][j - 1][0] - prices[i]);
            }
        }
        return dp[n][k + 1][0];
    }

    /**
     * @Description: [
     * [0~i] 买卖第一次股票交易
     * [i+1 ~ n] 买卖第二次股票交易
     * 这是O(n^2)的时间复杂度, 根据tips:10^5 必然超时
     * 先这样做着看看
     * 不出所料timeout, 看了下灵神题解 method_02
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 14:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int ans = 0;
        int[] dp = new int[2];
        dp[0] = 0;
        dp[1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[0] = Math.max(dp[0], dp[1] + prices[i]);
            dp[1] = Math.max(dp[1], -prices[i]);
            int[] dp_2 = new int[2];
            if (i < n - 1) {
                // 需要处理i = n - 1时
                dp_2[0] = 0;
                dp_2[1] = -prices[i + 1];
                for (int j = i + 1; j < n; j++) {
                    dp_2[0] = Math.max(dp_2[0], dp_2[1] + prices[j]);
                    dp_2[1] = Math.max(dp_2[1], -prices[j]);
                }
                ans = Math.max(ans, dp[0] + dp_2[0]);
            }else {
                ans = Math.max(ans, dp[0]);
            }
        }
        return ans;
    }
}
