package com.marks.leetcode.state_machine_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/18 11:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1537Test {

    @Test
    void maxSum() {
        int[] nums1 = {2,4,5,8,10};
        int[] nums2 = {4,6,8,9};
        int result = new LeetCode_1537().maxSum(nums1, nums2);
        System.out.println(result);
    }
}