package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1027 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 11:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1027 {


    /**
     * @Description:
     * 给你一个整数数组 nums，返回 nums 中最长等差子序列的长度。
     *
     * 回想一下，nums 的子序列是一个列表 nums[i1], nums[i2], ..., nums[ik] ，且 0 <= i1 < i2 < ... < ik <= nums.length - 1。
     * 并且如果 seq[i+1] - seq[i]( 0 <= i < seq.length - 1) 的值都相同，那么序列 seq 是等差的。
     * tips:
     * 2 <= nums.length <= 1000
     * 0 <= nums[i] <= 500
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestArithSeqLength(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. nums[] 是一个无序的数组
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/25 11:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {

        return 0;
    }

}
