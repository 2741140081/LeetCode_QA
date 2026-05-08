package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3660 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/7 14:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3660 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 从任意下标 i 出发，你可以根据以下规则跳跃到另一个下标 j：
     * 仅当 nums[j] < nums[i] 时，才允许跳跃到下标 j，其中 j > i。
     * 仅当 nums[j] > nums[i] 时，才允许跳跃到下标 j，其中 j < i。
     * 对于每个下标 i，找出从 i 出发且可以跳跃 任意 次，能够到达 nums 中的 最大值 是多少。
     *
     * 返回一个数组 ans，其中 ans[i] 是从下标 i 出发可以到达的最大值。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/07 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maxValue(int[] nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 输入: nums = [2,3,1]
     * 输出: [3,3,3]
     * 1. i = 0, nums[0] > nums[2] && 0 < 2; 跳到 下标 2 处; nums[1] > nums[2] && 1 < 2; 跳到 下标 2 处; 此时值最大值为 3;
     * 2. 总结规律是, 假设位于 i 处, 可以向后跳到小于 nums[i] 处, 或者向前跳到大于 nums[i] 处, 跳到 i 处时, 值最大值为 nums[i];
     * 3.
     * 没什么思路: need todo
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/07 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {

        return null;
    }

}
