package com.marks.leetcode.maximum_subarray_sum;

public class LeetCode_1749 {
    /**
     * 给你一个整数数组 nums 。一个子数组 [numsl, numsl+1, ..., numsr-1, numsr] 的 和的绝对值 为 abs(numsl + numsl+1 + ... + numsr-1 + numsr) 。
     * 请你找出 nums 中 和的绝对值 最大的任意子数组（可能为空），并返回该 最大值 。
     * abs(x) 定义如下：
     * 如果 x 是负整数，那么 abs(x) = -x 。
     * 如果 x 是非负整数，那么 abs(x) = x 。
     * E1:
     * 输入：nums = [1,-3,2,3,-4]
     * 输出：5
     * 解释：子数组 [2,3] 和的绝对值最大，为 abs(2+3) = abs(5) = 5 。
     *
     * E2:
     * 输入：nums = [2,-5,1,-4,3,-2]
     * 输出：8
     * 解释：子数组 [-5,1,-4] 和的绝对值最大，为 abs(-5+1-4) = abs(-8) = 8 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     *
     * @param nums
     * @return
     */
    public int maxAbsoluteSum(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * 找出最大值和最小值即可, 最终结果为Math.max(最大值, abs(最小值))
     * @param nums
     * @return
     */
    private int method_01(int[] nums) {
        if (nums.length == 1) {
            return Math.abs(nums[0]);
        }
        int[] dp_max = new int[nums.length];
        int[] dp_min = new int[nums.length];
        dp_max[0] = Math.max(nums[0], 0);
        dp_min[0] = Math.min(nums[0], 0);
        int max = Math.max(dp_max[0], 0);
        int min = Math.min(dp_min[0], 0);

        for (int i = 1; i < nums.length; i++) {
            dp_max[i] = Math.max(dp_max[i-1] + nums[i], nums[i]);
            max = Math.max(dp_max[i], max);
            dp_min[i] = Math.min(dp_min[i-1] + nums[i], nums[i]);
            min = Math.min(dp_min[i], min);
        }
        return max > Math.abs(min) ? max : Math.abs(min);
    }
}
