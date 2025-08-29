package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @Description: LeetCode_967 单元测试类
 * @Author: marks
 * @CreateDate: 2025/8/27 11:38
 */
class LeetCode_967Test {

    private LeetCode_967 leetCode967;

    @BeforeEach
    void setUp() {
        leetCode967 = new LeetCode_967();
    }

    /**
     * 测试用例：n = 3, k = 7
     * 预期输出：[181, 292, 707, 818, 929]
     */
    @Test
    void testNumsSameConsecDiff_case1() {
        int n = 3;
        int k = 7;
        int[] expected = {181, 292, 707, 818, 929};
        int[] actual = leetCode967.numsSameConsecDiff(n, k);
        Arrays.sort(actual);
        Arrays.sort(expected);
        assertArrayEquals(expected, actual);
    }

    /**
     * 测试用例：n = 2, k = 1
     * 预期输出：[10, 12, 21, 23, 32, 34, 43, 45, 54, 56, 65, 67, 76, 78, 87, 89, 98]
     */
    @Test
    void testNumsSameConsecDiff_case2() {
        int n = 2;
        int k = 1;
        int[] expected = {10, 12, 21, 23, 32, 34, 43, 45, 54, 56, 65, 67, 76, 78, 87, 89, 98};
        int[] actual = leetCode967.numsSameConsecDiff(n, k);
        Arrays.sort(actual);
        Arrays.sort(expected);
        assertArrayEquals(expected, actual);
    }

    /**
     * 测试用例：n = 2, k = 0
     * 预期输出：[11, 22, 33, 44, 55, 66, 77, 88, 99]
     */
    @Test
    void testNumsSameConsecDiff_case3() {
        int n = 2;
        int k = 0;
        int[] expected = {11, 22, 33, 44, 55, 66, 77, 88, 99};
        int[] actual = leetCode967.numsSameConsecDiff(n, k);
        Arrays.sort(actual);
        Arrays.sort(expected);
        assertArrayEquals(expected, actual);
    }

    /**
     * 测试用例：n = 2, k = 9
     * 预期输出：[10, 90]
     */
    @Test
    void testNumsSameConsecDiff_case4() {
        int n = 2;
        int k = 9;
        int[] expected = {90};
        int[] actual = leetCode967.numsSameConsecDiff(n, k);
        Arrays.sort(actual);
        Arrays.sort(expected);
        assertArrayEquals(expected, actual);
    }
}
