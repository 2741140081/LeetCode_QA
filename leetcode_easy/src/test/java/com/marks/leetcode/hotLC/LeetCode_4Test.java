package com.marks.leetcode.hotLC;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_4Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/16 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_4Test {

    @Test
    void findMedianSortedArrays() {
        // int[] nums1 = {1, 3}; int[] nums2 = {2};
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        double res = new LeetCode_4().findMedianSortedArrays(nums1, nums2);
        System.out.println(res);

    }
}