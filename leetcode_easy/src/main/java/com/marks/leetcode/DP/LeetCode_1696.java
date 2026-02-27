package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1696 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/27 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1696 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
     * 一开始你在下标 0 处。每一步，你最多可以往前跳 k 步，但你不能跳出数组的边界。
     * 也就是说，你可以从下标 i 跳到 [i + 1， min(n - 1, i + k)] 包含 两个端点的任意位置。
     * 你的目标是到达数组最后一个位置（下标为 n - 1 ），你的 得分 为经过的所有数字之和。
     * 请你返回你能得到的 最大得分 。
     * tips:
     * 1 <= nums.length, k <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxResult(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 最简单的办法是暴力求解 dp[i] = Math.max(dp[j]) + nums[i]; j => {i - k, i - 1}, 时间复杂度是O(n^2), 大概率超时, 舍弃
     * 2.
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {

        return 0;
    }

}
