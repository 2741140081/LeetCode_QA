package com.marks.leetcode.hash_table;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_659 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/1 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_659 {

    /**
     * @Description:
     * 给你一个按 非递减顺序 排列的整数数组 nums 。
     * 请你判断是否能在将 nums 分割成 一个或多个子序列 的同时满足下述 两个 条件：
     * 每个子序列都是一个 连续递增序列（即，每个整数 恰好 比前一个整数大 1 ）。
     * 所有子序列的长度 至少 为 3 。
     * 如果可以分割 nums 并满足上述条件，则返回 true ；否则，返回 false 。
     *
     * tips:
     * 1 <= nums.length <= 10^4
     * -1000 <= nums[i] <= 1000
     * nums 按非递减顺序排列
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/01 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPossible(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. need todo
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/01 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {

        return false;
    }

}
