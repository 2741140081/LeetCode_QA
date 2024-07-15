package com.marks.leetcode.climb_stairs;

public class LeetCode_746 {
    /**
     * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
     * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
     * 请你计算并返回达到楼梯顶部的最低花费。
     *
     * E1:
     * 输入：cost = [10,15,20]
     * 输出：15
     * 解释：你将从下标为 1 的台阶开始。
     * - 支付 15 ，向上爬两个台阶，到达楼梯顶部。
     * 总花费为 15
     *
     * E2:
     * 输入：cost = [1,100,1,1,1,100,1,1,100,1]
     * 输出：6
     * 解释：你将从下标为 0 的台阶开始。
     * - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
     * 总花费为 6
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        int result = 0;
        result = method_01(cost);
        return result;
    }


    /**
     * 思路: 使用dp[i]保存到达阶梯i的最少花费
     * 初始化dp[0] dp[1]
     * 递推公式 dp[i] = dp[i-1] + cost[i-1] < dp[i-2] + cost[i-2] ? dp[i-1] + cost[i-1] : dp[i-2] + cost[i-2];
     * @param cost
     * @return int result = dp[cost.length-1]
     */
    private int method_01(int[] cost) {
        //cost = [1,100,1,1,1,100,1,1,100,1]
        int[] dp = new int[cost.length + 1];
        dp[0] = 0;
        dp[1] = 0;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = dp[i-1] + cost[i-1] < dp[i-2] + cost[i-2] ? dp[i-1] + cost[i-1] : dp[i-2] + cost[i-2];
        }
        return dp[cost.length];
    }
}
