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
 * @date 2025/3/19 17:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_493Test {

    @Test
    void reversePairs() {
        int[] nums = {2,4,3,5,1};
//        int[] nums = {1,2,1,2,1};
//        int[] nums = {1,3,2,3,1};
//        int[] nums = {2147483647,2147483647,2147483647,2147483647,2147483647,2147483647};
        int result = new LeetCode_493().reversePairs(nums);
        System.out.println(result);
    }
}