package com.marks.leetcode.maximum_subarray_sum;

public class LeetCode_152 {
    /**
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续子数组
     * （该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     * 测试用例的答案是一个 32-位 整数。
     *
     * E1:
     * 输入: nums = [2,3,-2,4]
     * 输出: 6
     * 解释: 子数组 [2,3] 有最大乘积 6。
     *
     * E2:
     * 输入: nums = [-2,0,-1]
     * 输出: 0
     * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
     *
     * tips：
     * 1 <= nums.length <= 2 * 104
     * -10 <= nums[i] <= 10
     * nums 的任何前缀或后缀的乘积都 保证 是一个 32-位 整数
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * 不能单纯的套用最大子串和
     * 需要考虑数组元素的正负性
     * eg:
     * 如果当前元素i
     * if num > 0
     * 那么求最大值是希望该元素前i-1元素的乘积为一个正数, 越大越好
     * max = max(dp[i-1]*num[i], num[i]);
     * if num < 0
     * 如果当前元素是一个负数, 根据负负得正, 我们希望其i-1个元素的乘积越小越好
     * min = min(dp[i-1] * num[i], num[i])
     *
     * 因此我们需要维护2个dp
     *
     * @param nums
     * @return
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        double[] maxF = new double[n];
        double[] minF = new double[n];
        for (int i = 0; i < nums.length; i++) {
            maxF[i] = nums[i];
            minF[i] = nums[i];
        }

        for (int i = 1; i < n; i++) {
            maxF[i] = Math.max(maxF[i-1] * nums[i], Math.max(minF[i-1] * nums[i], nums[i]));
            minF[i] = Math.min(maxF[i-1] * nums[i], Math.min(minF[i-1] * nums[i], nums[i]));
        }
        double res = maxF[0];
        for (int i = 1; i < maxF.length; i++) {
            res = Math.max(res, maxF[i]);
        }

        return (int) res;
    }
}
