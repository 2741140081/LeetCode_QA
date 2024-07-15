package com.marks.leetcode.climb_stairs;

public class LeetCode_377 {
    /**
     * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。
     * 题目数据保证答案符合 32 位整数范围。
     *
     * E1:
     * 输入：nums = [1,2,3], target = 4
     * 输出：7
     * 解释：
     * 所有可能的组合为：
     * (1, 1, 1, 1)
     * (1, 1, 2)
     * (1, 2, 1)
     * (1, 3)
     * (2, 1, 1)
     * (2, 2)
     * (3, 1)
     * 请注意，顺序不同的序列被视作不同的组合。
     *
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4(int[] nums, int target) {
        int result = 0;
        result = method_01(nums, target);
        return result;
    }

    /**
     * 思路: 类似于爬楼梯, 其中nums[] 数组元素为每次可以爬的楼梯数, eg 1, 2, 3 每次可以爬1/2/3阶楼梯。其中target为楼梯总数。
     * 假设案例 nums = [1,2,3], target = 4
     * new dp[target + 1];
     * dp[0] = 1
     * dp[1] = 1
     * dp[2] = 2
     * dp[3] = dp[2] + dp[1] + d[0] = 4
     * dp[i] = dp[i - num[0]] + dp[i - num[1]] + dp[i - num[2]];
     * @param nums
     * @param target
     * @return
     */
    private int method_01(int[] nums, int target) {
        int[] dp = new int[target + 1];
        // 找到符合条件的nums元素
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            for (int num : nums) {
                if (num<=i) {
                    dp[i] += dp[i - num];
                }
            }
        }
        return dp[target];
    }
}
