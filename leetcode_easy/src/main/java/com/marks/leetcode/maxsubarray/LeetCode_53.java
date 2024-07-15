package com.marks.leetcode.maxsubarray;

import java.util.Arrays;

public class LeetCode_53 {
    /**
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组
     * 是数组中的一个连续部分。
     *
     * E1:
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     *
     * E2:
     * 输入：nums = [1]
     * 输出：1
     *
     * E3:
     * 输入：nums = [5,4,-1,7,8]
     * 输出：23
     *
     * @param nums
     * @return
     * @Author
     * @Date
     * @Version
     * @Descript 走完这一生 如果我和你在一起会变得更好，那我们就在一起，否则我就丢下你。 我回顾我最光辉的时刻就是和不同人在一起，变得更好的最长连续时刻
     */
    public int maxSubArray(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * eg:nums = [-2,1,-3,4,-1,2,1,-5,4] result = [4,-1,2,1] 为6。
     * 思路: 对于长度为k的子数组
     *
     * 官方题解思路:
     * 1.遍历数组
     * 2.存储nums[i] 总和的最大值
     * @param nums
     * @return
     */
    private int method_01(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        // len >= 2
        int[] dp = new int[nums.length+1];
        dp[0] = nums[0];
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {

            dp[i] = Math.max(dp[i-1] + nums[i], nums[i]);
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
