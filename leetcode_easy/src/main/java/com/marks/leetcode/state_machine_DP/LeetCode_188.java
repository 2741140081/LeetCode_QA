package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/26 15:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_188 {
    /**
     * @Description: [
     * 给你一个整数数组 prices 和一个整数 k ，其中 prices[i] 是某支给定的股票在第 i 天的价格。
     *
     * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。
     *
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * ]
     * @param k
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int k, int[] prices) {
        int result = 0;
        result = method_01(k, prices);
        int result_1 = method_02(k, prices);
        if (result != result_1) {
            System.out.println("这里有些bug存在LeetCode_188.java");
        }
        return result_1;
    }

    /**
     * @Description: [动态规划: 空间优化]
     * @param k
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int k, int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[][] dp = new int[k + 2][2];

        for (int j = 0; j <= k + 1; j++) {
            Arrays.fill(dp[j], Integer.MIN_VALUE / 2);
        }


        for (int j = 1; j <= k + 1; j++) {
            dp[j][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k + 1; j++) {
                dp[j][0] = Math.max(dp[j][0], dp[j][1] + prices[i]);
                dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i]);
            }

        }
        return dp[k + 1][0];
    }

    private int method_01(int k, int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[][][] dp = new int[n + 1][k + 2][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k + 1; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE / 2);
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
}
