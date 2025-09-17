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
 * @date 2025/9/12 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_231Test {

    @Test
    void isPowerOfTwo() {
        LeetCode_231 leetCode_231 = new LeetCode_231();
        assertTrue(leetCode_231.isPowerOfTwo(1));
        assertTrue(leetCode_231.isPowerOfTwo(2));
        assertFalse(leetCode_231.isPowerOfTwo(3));
        assertTrue(leetCode_231.isPowerOfTwo(4));
        assertFalse(leetCode_231.isPowerOfTwo(5));
        assertTrue(leetCode_231.isPowerOfTwo(-8));
    }
}