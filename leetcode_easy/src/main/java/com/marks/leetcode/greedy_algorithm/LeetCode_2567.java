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
     *
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);

        return 0;
    }
}
