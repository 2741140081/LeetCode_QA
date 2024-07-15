package com.marks.leetcode;

import java.util.Arrays;

/**
 * TODO 需要解决"超出内存限制"的问题，当使用LeetCode_3186TestData.csv文件时.
 */
public class LeetCode_3186 {
    /**
     * 一个魔法师有许多不同的咒语。
     * 给你一个数组 power ，其中每个元素表示一个咒语的伤害值，可能会有多个咒语有相同的伤害值。
     * 已知魔法师使用伤害值为 power[i] 的咒语时，他们就 不能 使用伤害为 power[i] - 2 ，power[i] - 1 ，power[i] + 1 或者 power[i] + 2 的咒语
     * 每个咒语最多只能被使用 一次 。
     * 请你返回这个魔法师可以达到的伤害值之和的 最大值 。
     * @param power
     * @return
     */
    public long maximumTotalDamage(int[] power) {
        long result = 0;
        result = method_01(power);
        return result;
    }

    private long method_01(int[] power) {
        // 排序
        if (power.length == 1) {
            return power[0];
        }
        Arrays.sort(power);
        int[] ints = new int[power[power.length-1] + 1];
        int current_sum = power[0];
        for (int i = 1; i < power.length; i++) {
            if (power[i] == power[i-1]) {
                current_sum += power[i];
            }else {
                ints[power[i-1]] = current_sum;
                current_sum = power[i];
            }
        }
        if (current_sum != 0) {
            ints[power[power.length-1]] = current_sum;
        }
        long max_result = 0;


        max_result = rob(ints);
        return max_result;
    }

    /**
     * 对于k, 存在以下两种情况
     * if k >= 3;
     * 1. 使用k, 则不能使用k+/-1 or k+/-2
     * 2. 不使用k, 则能用的最大值是k-2
     * dp[k] = max(dp[k-3]+ints[k], dp[k-2])
     * @param ints
     * @return
     */
    private long rob(int[] ints) {
        int len = ints.length;
        if (len == 1) {
            return ints[0];
        } else if (len == 2) {
            return Math.max(ints[0], ints[1]);
        } else if (len == 3) {
            return Math.max(Math.max(ints[0], ints[1]), ints[2]);
        }
        long[] dp = new long[len];
        dp[0] = ints[0];
        dp[1] = Math.max(ints[0], ints[1]);
        dp[2] = Math.max(Math.max(ints[0], ints[1]), ints[2]);
        // len >= 3时
        for (int i = 3; i < len; i++) {
            dp[i] = Math.max(Math.max(dp[i-3] + ints[i], dp[i-2]), dp[i-1]);
        }
        return dp[len-1];
    }
}
