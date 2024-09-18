package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/18 14:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2919 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始、长度为 n 的整数数组 nums ，和一个整数 k 。
     * 你可以执行下述 递增 运算 任意 次（可以是 0 次）：
     * 从范围 [0, n - 1] 中选择一个下标 i ，并将 nums[i] 的值加 1 。
     * 如果数组中任何长度 大于或等于 3 的子数组，其 最大 元素都大于或等于 k ，则认为数组是一个 美丽数组 。
     *
     * 以整数形式返回使数组变为 美丽数组 需要执行的 最小 递增运算数。
     *
     * 子数组是数组中的一个连续 非空 元素序列。
     *
     * tips:
     * 3 <= n == nums.length <= 10^5
     * 0 <= nums[i] <= 10^9
     * 0 <= k <= 10^9
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/9/18 14:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minIncrementOperations(int[] nums, int k) {
        long result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 案例E1:nums = [2,3,0,0,2,2,3,0,0,2], k = 4
     * 假设我用dp[] 存储最小的操作数
     * dp[0] = k - nums[0] = 2
     * dp[1] = k - nums[1] = 1
     * dp[2] = k - nums[2] = 4
     *
     * dp[3] = k - nums[3] = 4
     * dp[4] = k - nums[4] = 2
     * dp[5] = k - nums[5] = 2
     *
     * dp[6] = k - nums[6] = 1
     * dp[7] = k - nums[7] = 4
     * dp[8] = k - nums[8] = 4
     *
     * dp[9] = k - nums[9] = 2
     *
     * 如果n == 3, return min(dp[1])
     * i = 3
     * dp[3] = Math.min(dp[0] + dp[3], dp[1], dp[2])
     *
     * i = 4
     * dp[4] = Math.min(dp[0] + dp[3], dp[1] + dp[4], dp[2])
     *
     * AC:3ms/55.15MB
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/9/18 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        long[] dp = new long[3];
        for (int i = 0; i < 3; i++) {
            dp[i] = Math.max(0, k - nums[i]);
        }
        // i >= 3
        for (int i = 3; i < n; i++) {
            long curr = Math.min(Math.min(dp[0], dp[1]), dp[2]) + Math.max(0, k - nums[i]);
            dp[0] = dp[1];
            dp[1] = dp[2];
            dp[2] = curr;
        }
        return Math.min(Math.min(dp[0], dp[1]), dp[2]);
    }
}
