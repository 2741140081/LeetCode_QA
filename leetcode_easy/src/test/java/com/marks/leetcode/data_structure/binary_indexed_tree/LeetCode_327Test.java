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
 * @date 2025/3/24 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_327Test {

    @Test
    void countRangeSum() {
        // nums = [-2,5,-1], lower = -2, upper = 2
        int[] nums = {-2, 5, -1};
        int lower = -2, upper = 2;
        int result = new LeetCode_327().countRangeSum(nums, lower, upper);
        System.out.println(result);
    }
}