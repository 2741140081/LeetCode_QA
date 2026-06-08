package com.marks.leetcode.hash_table;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3654 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 15:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3654 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k。
     * 你可以 多次 选择 连续 子数组 nums，其元素和可以被 k 整除，并将其删除；每次删除后，剩余元素会填补空缺。
     * 返回在执行任意次数此类删除操作后，nums 的最小可能 和。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= k <= 10^5
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/08 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minArraySum(int[] nums, int k) {
        long result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description: [方法描述]
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/08 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {

        return 0;
    }

}
