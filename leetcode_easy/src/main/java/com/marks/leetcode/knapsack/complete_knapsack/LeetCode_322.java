package com.marks.leetcode.knapsack.complete_knapsack;

import java.util.Arrays;

/**
 * <p>项目名称: 完全背包问题 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/6 16:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_322 {
    /**
     * @Description: [
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * 你可以认为每种硬币的数量是无限的。]
     * @param coins
     * @param amount
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int coinChange(int[] coins, int amount) {
        int result = 0;
//        result = method_01(coins, amount);
        result = method_02(coins, amount);
        return result;
    }

    private int method_02(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int temp = coins[i - 1];
            for (int j = 0; j <= amount; j++) {
                if (j >= temp) {
                    dp[j] = Math.min(dp[j], dp[j - temp] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * @Description: [
     * 这个方法适用于判断是否存在amount符合要求, 不符合题目要求的最小硬币数量
     * ]
     * @param coins
     * @param amount
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 16:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        dp[0][0] = 0;
        int res = 0;
        for (int i = 1; i <= n; i++) {
            int temp = coins[i - 1];
            for (int j = 0; j <= amount; j++) {
                int num = j / temp; // 最多可以拿多少个coins[i - 1]
                for (int k = 0; k <= num; k++) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - k * temp] + k * temp);
                }
            }
        }
        return dp[n][amount];
    }
}
