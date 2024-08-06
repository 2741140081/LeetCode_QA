package com.marks.leetcode.knapsack.complete_knapsack;

import java.util.Arrays;

/**
 * <p>项目名称: 完全背包问题, 求方案数 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/6 16:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_518 {
    /**
     * @Description: [
     * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
     * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
     * 假设每一种面额的硬币有无限个。
     * 题目数据保证结果符合 32 位带符号整数]
     * @param amount
     * @param coins
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int change(int amount, int[] coins) {
        int result = 0;
//        result = method_01(amount, coins);
//        result = method_02(amount, coins);
        result = method_03(amount, coins);
        return result;
    }

    /**
     * @Description: [
     * 输入：amount = 5, coins = [1, 2, 5]
     * 输出：4
     * 解释：有四种方式可以凑成总金额：
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     * dp[i][j] = k
     *
     * ]
     * @param amount
     * @param coins
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n + 1][amount + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            int temp = coins[i - 1];
            for (int j = 0; j <= amount; j++) {
                int num = j / temp; // 最多可以拿num个coins[i - 1]
                for (int k = 0; k <= num; k++) {
                    if (j >= k * temp) {
                        dp[i][j] += dp[i - 1][j - k * temp];
                    }else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }
        return dp[n][amount];
    }

    /**
     * @Description: [滚动数组
     * 需要关注 前一个数组的数据需要及时清理
     * ]
     * @param amount
     * @param coins
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 17:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[2][amount + 1];
        dp[0][0] = 1;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = coins[i - 1];
            for (int j = 0; j <= amount; j++) {
                int num = j / temp; // 最多可以拿num个coins[i - 1]
                dp[curr][j] = 0; // 清除pre[i] 存在值时, 会发生错误, 需要将值重新赋值为0
                for (int k = 0; k <= num; k++) {
                    if (j >= k * temp) {
                        dp[curr][j] += dp[pre][j - k * temp];
                    }else {
                        dp[curr][j] = dp[pre][j];
                    }
                }
            }
        }
        return dp[curr][amount];
    }

    private int method_03(int amount, int[] coins) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            int temp = coins[i - 1];
            for (int j = temp; j <= amount; j++) {
                dp[j] += dp[j - temp];
            }
        }
        return dp[amount];
    }
}
