package com.marks.leetcode;

import java.util.Arrays;

public class LeetCode_740 {
    /**
     * 给你一个整数数组 nums ，你可以对它进行一些操作。
     * 每次操作中，选择任意一个 nums[i] ，删除它并获得 nums[i] 的点数。之后，你必须删除 所有 等于 nums[i] - 1 和 nums[i] + 1 的元素。
     * 开始你拥有 0 个点数。返回你能通过这些操作获得的最大点数。
     *
     * E1:
     * 输入：nums = [3,4,2]
     * 输出：6
     * 解释：
     * 删除 4 获得 4 个点数，因此 3 也被删除。
     * 之后，删除 2 获得 2 个点数。总共获得 6 个点数。
     *
     * E2:
     * 输入：nums = [2,2,3,3,3,4]
     * 输出：9
     * 解释：
     * 删除 3 获得 3 个点数，接着要删除两个 2 和 4 。
     * 之后，再次删除 3 获得 3 个点数，再次删除 3 获得 3 个点数。
     * 总共获得 9 个点数。
     *
     * @param nums
     * @return
     */
    public int deleteAndEarn(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * 思路: 对数组进行排序, 对于i节点, 分为以下情况
     * a: num[i-1] != num[i] || num[i+1] != num[i]
     * 1.num[i-1] == num[i] - 1 || num[i+1] == num[i] + 1
     * 2.num[i-1] != num[i] - 1 || num[i+1] != num[i] + 1
     * @param nums
     * @return
     */
    private int method_01(int[] nums) {
        // 排序
        if (nums.length == 1) {
            return nums[0];
        }
        Arrays.sort(nums);
        int[] ints = new int[nums[nums.length-1] + 1];
        int current_sum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i-1]) {
                current_sum += nums[i];
            }else {
                ints[nums[i-1]] = current_sum;
                current_sum = nums[i];
            }
        }
        if (current_sum != 0) {
            ints[nums[nums.length-1]] = current_sum;
        }
        int max_result = 0;


        max_result = rob(ints);
        return max_result;
    }

    /**
     * 对于第 k 间房屋, 分为以下情况
     * 1.不偷取, 则总和为k-1间房的最大值
     * 2. 偷取, 则总和为sum[k-2] + ints[k]
     * [0, 0, 4, 9, 4]
     * sum[k] = max(sum[k-2] + ints[k], sum[k-1])
     *
     * @param ints
     * @return
     */
    private int rob(int[] ints) {
        if (ints.length == 1) {
            return ints[0];
        }
        if (ints.length == 2) {
            return Math.max(ints[0], ints[1]);
        }
        // 房屋数量大于2间房屋时
        int result = 0;
        int[] dp = new int[ints.length];
        dp[0] = ints[0];
        dp[1] = Math.max(ints[0], ints[1]);
        for (int i = 2; i < ints.length; i++) {
            // 如果偷取第i个
            dp[i] = Math.max(dp[i-2] + ints[i], dp[i-1]);

        }
        return dp[ints.length-1];
    }
}
