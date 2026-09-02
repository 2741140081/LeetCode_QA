package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2541Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/2 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2541Test {

    @Test
    void minOperations() {
        // nums1 = [5,1,0], nums2 = [9,7,6], k = 2;
        int[] nums1 = {5,1,0};
        int[] nums2 = {9,7,6};
        int k = 2;
        long result = new LeetCode_2541().minOperations(nums1, nums2, k);
        System.out.println(result);
    }
}