package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/19 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2563Test {

    @Test
    void countFairPairs() {
        // nums = [0,1,7,4,4,5], lower = 3, upper = 6
        int[] nums = {0,1,7,4,4,5};
        int lower = 3;
        int upper = 6;
        long result = new LeetCode_2563().countFairPairs(nums, lower, upper);
    }
}