package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2770 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/26 17:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2770 {

    /**
     * @Description:
     * 给你一个下标从 0 开始、由 n 个整数组成的数组 nums 和一个整数 target 。
     * 你的初始位置在下标 0 。在一步操作中，你可以从下标 i 跳跃到任意满足下述条件的下标 j ：
     * 0 <= i < j < n
     * -target <= nums[j] - nums[i] <= target
     * 返回到达下标 n - 1 处所需的 最大跳跃次数 。
     * 如果无法到达下标 n - 1 ，返回 -1 。
     *
     * tips:
     * 2 <= nums.length == n <= 1000
     * -10^9 <= nums[i] <= 10^9
     * 0 <= target <= 2 * 10^9
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/03/26 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumJumps(int[] nums, int target) {
        int result;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description:
     * 1. 使用动态规划, 创建一个长度为 n 的数组 dp[i]，表示到达 i 处所需的最大跳跃次数。
     * 2. 对于 i 位置, 并且 Math.abs(nums[i] - nums[j]) <= target, 使用 int 即可, 不会发生数据溢出
     * AC: 20ms/45.93MB
     * @param: nums
     * @param: target
     * @return int
     * @author marks
     * @CreateDate: 2026/03/26 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (Math.abs(nums[i] - nums[j]) <= target && dp[j] != -1) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n - 1];
    }

}
