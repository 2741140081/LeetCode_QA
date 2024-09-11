package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/11 14:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3196 {
    /**
     * @Description: [
     * 给你一个长度为 n 的整数数组 nums。
     * 子数组 nums[l..r]（其中 0 <= l <= r < n）的 成本 定义为：
     * cost(l, r) = nums[l] - nums[l + 1] + ... + nums[r] * (−1)r − l
     *
     * 你的任务是将 nums 分割成若干子数组，使得所有子数组的成本之和 最大化，并确保每个元素 正好 属于一个子数组。
     * 具体来说，如果 nums 被分割成 k 个子数组，且分割点为索引 i1, i2, ..., ik − 1（其中 0 <= i1 < i2 < ... < ik - 1 < n - 1），则总成本为：
     * cost(0, i1) + cost(i1 + 1, i2) + ... + cost(ik − 1 + 1, n − 1)
     *
     * 返回在最优分割方式下的子数组成本之和的最大值。
     * 注意：如果 nums 没有被分割，即 k = 1，则总成本即为 cost(0, n - 1)。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/9/11 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumTotalCost(int[] nums) {
        long result = 0;
//        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description: [
     * 基于method_01, 使用空间优化复杂度
     * AC:2ms/60.43MB
     * @see #method_01(int[])
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/9/11 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        long dp0 = nums[0];
        long dp1 = nums[0];
        for (int i = 1; i < n; i++) {
            long temp0 = Math.max(dp0, dp1) + nums[i];
            long temp1 = Math.max(dp0 - nums[i], dp1 + nums[i]);
            dp0 = temp0;
            dp1 = temp1;
        }
        return Math.max(dp0, dp1);
    }

    /**
     * @Description: [
     * 首先想到的是动态规划
     * 因为对于nums[i], 最少可以分割成长度为1的子数组, 那么最终答案就是sum
     * 设置两种状态dp[i][0]表示 未转换(可以看做是长度全1)
     * dp[i][1] 表示转换了, 可以看做是将划分成长度2的数组
     * E1:
     *  nums = [1,-2,3,4]
     * 我去还真让我写出来了
     * AC:2ms/62.55MB
     * dp[i] 只与dp[i - 1]关联, 但是如果需要使用空间优化, 需要再for循环中添加临时变量存储dp[i - 1]的值, 防止值覆盖影响结果
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/9/11 15:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        long[] dp0 = new long[n];
        long[] dp1 = new long[n];
        dp0[0] = nums[0];
        dp1[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp0[i] = Math.max(dp0[i - 1], dp1[i - 1]) + nums[i];
            dp1[i] = Math.max(dp0[i - 1] - nums[i], dp1[i - 1] + nums[i]);
        }
        return Math.max(dp0[n - 1], dp1[n - 1]);
    }
}
