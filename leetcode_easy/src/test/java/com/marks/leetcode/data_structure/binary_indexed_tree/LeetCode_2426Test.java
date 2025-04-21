package com.marks.leetcode.data_structure.binary_indexed_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/24 17:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2426Test {

    @Test
    void numberOfPairs() {
        // nums1 = [3,2,5], nums2 = [2,2,1], diff = 1
        int[] nums1 = {3,2,5};
        int[] nums2 = {2, 2, 1};
        int diff = 1;
        long result = new LeetCode_2426().numberOfPairs(nums1, nums2, diff);
        System.out.println(result);
    }
}