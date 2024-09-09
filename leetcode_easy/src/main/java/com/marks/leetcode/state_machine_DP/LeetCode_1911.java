package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/9 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1911 {
    /**
     * @Description: [
     * 一个下标从 0 开始的数组的 交替和 定义为 偶数 下标处元素之 和 减去 奇数 下标处元素之 和 。
     *
     * 比方说，数组 [4,2,5,3] 的交替和为 (4 + 5) - (2 + 3) = 4 。
     * 给你一个数组 nums ，请你返回 nums 中任意子序列的 最大交替和 （子序列的下标 重新 从 0 开始编号）。
     *
     * 一个数组的 子序列 是从原数组中删除一些元素后（也可能一个也不删除）剩余元素不改变顺序组成的数组。
     * 比方说，[2,7,4] 是 [4,2,3,7,2,1,4] 的一个子序列（加粗元素），但是 [2,4,2] 不是。
     *
     * tips:
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 105
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/9/9 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxAlternatingSum(int[] nums) {
        long result = 0;
        result = method_01(nums);
        return result;
    }
    /**
     * @Description: [
     * 感觉这个题目相当于买卖股票
     * int[] dp = new int[]
     * dp 存储前i个元素能组成的最大交替和
     * dp[i][0] 表示当前是偶数, dp[i][1] 表示当前是奇数
     * 对于nums[i]
     * 输入：nums = [6,2,1,2,4,5]
     * dp[0][0] = nums[0] = 6
     * dp[1][0] = nums[1] = 2
     *
     * dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + nums[i])
     * dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - num[i])
     *
     * AC:8ms/57.92MB
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/9/9 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        long[] dp = new long[2];
        dp[0] = nums[0];
        dp[1] = 0;
        for (int i = 1; i < n; i++) {
            // 新增变量temp_0, temp_1 防止值覆盖, 虽然某些题目不会发生覆盖, 但是也有可能会有题目产生覆盖,
            // 因此用临时变量存储值, 防止发生覆盖
            long temp_0 = Math.max(dp[0], dp[1] + nums[i]);
            long temp_1 = Math.max(dp[1], dp[0] - nums[i]);
            dp[0] = temp_0;
            dp[1] = temp_1;
        }
        return dp[0];
    }
}
