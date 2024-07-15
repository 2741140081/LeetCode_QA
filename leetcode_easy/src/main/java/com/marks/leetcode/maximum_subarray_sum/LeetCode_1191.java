package com.marks.leetcode.maximum_subarray_sum;

import java.util.Arrays;

public class LeetCode_1191 {
    /**
     * 给定一个整数数组 arr 和一个整数 k ，通过重复 k 次来修改数组。
     * 例如，如果 arr = [1, 2] ， k = 3 ，那么修改后的数组将是 [1, 2, 1, 2, 1, 2] 。
     * 返回修改后的数组中的最大的子数组之和。注意，子数组长度可以是 0，在这种情况下它的总和也是 0。
     * 由于 结果可能会很大，需要返回的 109 + 7 的 模 。
     *
     * E1:
     * 输入：arr = [1,2], k = 3
     * 输出：9
     *
     * E2:
     * 输入：arr = [1,-2,1], k = 5
     * 输出：2
     *
     * E3:
     * 输入：arr = [-1,-2], k = 7
     * 输出：0
     *
     * tips:
     * 1 <= arr.length <= 10^5
     * 1 <= k <= 10^5
     * -10^4 <= arr[i] <= 10^4
     *
     * @param arr
     * @param k
     * @return
     */
    public int kConcatenationMaxSum(int[] arr, int k) {
        int result = 0;
        result = method_01(arr, k);
        return result;
    }


    private int method_01(int[] arr, int k) {
        final int MOD = 1000000007;
        // 1. 判定sum(arr) < 0
        long arrSum = Arrays.stream(arr).sum();
        long sumMaxArray = maxSubArray(arr) % MOD;
        if (k == 1) {
            return (int)sumMaxArray;
        }
        // k >= 2
        int[] value = new int[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            value[i] = value[i+arr.length] = arr[i];
        }
        long doubleSumMaxArray = maxSubArray(value) % MOD;
        if (arrSum <= 0) {
            return (int) Math.max(sumMaxArray, doubleSumMaxArray) % MOD;
        }else {
            return (int) ((doubleSumMaxArray + (k-2) * arrSum) % MOD);
        }

    }

    private long maxSubArray(int[] arr) {
        if (arr.length == 1) {
            return Math.max(arr[0], 0);
        }
        long[] dp = new long[arr.length];
        dp[0] = Math.max(arr[0], 0);
        long max = Math.max(arr[0], 0);
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(dp[i-1] + arr[i], arr[i]);
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
