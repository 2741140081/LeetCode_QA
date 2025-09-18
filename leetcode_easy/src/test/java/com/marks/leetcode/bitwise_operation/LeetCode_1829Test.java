package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/18 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1829Test {

    @Test
    void getMaximumXor() {
        int[] nums = {0,1,1,3};
        int maximumBit = 2;
        int[] expected = {0,3,2,3};
        LeetCode_1829 leetCode_1829 = new LeetCode_1829();
        int[] result = leetCode_1829.getMaximumXor(nums, maximumBit);
        assertArrayEquals(expected, result);
    }
}