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
 * @date 2025/3/18 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3072Test {

    @Test
    void resultArray() {
        // 5,14,3,1,2
        int[] nums = {9,58,43,42,46,25,38,2,42,72,54,96,78,44,2,52,74,70,66,8};
//        int[] nums = {5,14,3,1,2};
        int[] result = new LeetCode_3072().resultArray(nums);
    }
}