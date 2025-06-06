package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/31 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2567 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 。
     *
     * nums 的 最小 得分是满足 0 <= i < j < nums.length 的 |nums[i] - nums[j]| 的最小值。
     * nums的 最大 得分是满足 0 <= i < j < nums.length 的 |nums[i] - nums[j]| 的最大值。
     * nums 的分数是 最大 得分与 最小 得分的和。
     * 我们的目标是最小化 nums 的分数。你 最多 可以修改 nums 中 2 个元素的值。
     *
     * 请你返回修改 nums 中 至多两个 元素的值后，可以得到的 最小分数 。
     *
     * |x| 表示 x 的绝对值。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimizeSum(int[] nums) {
        int result = method_01(nums);
        return result;
    }

    
    /**
     * @Description:
     * 输入：nums = [1,4,7,8,5]
     * 输出：3
     * 解释：
     * 将 nums[0] 和 nums[1] 的值变为 6 ，nums 变为 [6,6,7,8,5] 。
     * 最小得分是 i = 0 且 j = 1 时得到的 |nums[i] - nums[j]| = |6 - 6| = 0 。
     * 最大得分是 i = 3 且 j = 4 时得到的 |nums[i] - nums[j]| = |8 - 5| = 3 。
     * 最大得分与最小得分之和为 3 。这是最优答案。
     *
     * 假设 nums = [1,4,5,7,8]
     *
     * [i, j] 最大值 (nums[j] - nums[i])
     * 1. 将两个最小的提高 nums[n - 1] - nums[2] + 0
     * 2. 将两个最大的减少 nums[n - 3] - nums[0] + 0
     * 3. 将一个最大的减少, 最小的增大, nums[n - 2] - nums[1] + 0
     *
     * AC: 16ms/54.90MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int rs_1 = nums[n - 1] - nums[2];
        int rs_3 = nums[n - 2] - nums[1];
        int rs_2 = nums[n - 3] - nums[0];
        int ans = Math.min(rs_1, Math.min(rs_2, rs_3));
        return ans;
    }
}
