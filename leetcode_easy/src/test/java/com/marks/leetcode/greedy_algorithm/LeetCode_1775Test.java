package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/1 14:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1775Test {

    @Test
    void minOperations() {
        int[] nums1 = {1,2,3,4,5,6}, nums2 = {1,1,2,2,2,2};
        int result = new LeetCode_1775().minOperations(nums1, nums2);
        System.out.println(result);
    }
}