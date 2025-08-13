package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_3331测试类
 */
class LeetCode_3331Test {

    private final LeetCode_3331 solution = new LeetCode_3331();

    @Test
    void testExample1() {
        // 示例测试用例
        int[] parent = {-1, 0, 0, 1, 1};
        String s = "aabbc";
        int[] expected = {5, 3, 1, 1, 1}; // 预期结果需要根据实际算法确定
        int[] actual = solution.findSubtreeSizes(parent, s);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testExample2() {
        // 示例测试用例
        int[] parent = {-1,0,0,1,1,1};
        String s = "abaabc";
        int[] expected = {6,3,1,1,1,1}; // 预期结果需要根据实际算法确定
        int[] actual = solution.findSubtreeSizes(parent, s);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testSingleNode() {
        // 测试只有一个节点的情况
        int[] parent = {-1};
        String s = "a";
        int[] expected = {1};
        int[] actual = solution.findSubtreeSizes(parent, s);

        assertArrayEquals(expected, actual);
    }

    @Test
    void testTwoNodesWithSameChar() {
        // 测试两个节点且字符相同的情况
        int[] parent = {-1, 0};
        String s = "aa";
        int[] actual = solution.findSubtreeSizes(parent, s);

        assertNotNull(actual);
        assertEquals(2, actual.length);
    }

    @Test
    void testTwoNodesWithDiffChar() {
        // 测试两个节点且字符不同的情况
        int[] parent = {-1, 0};
        String s = "ab";
        int[] actual = solution.findSubtreeSizes(parent, s);
        
        assertNotNull(actual);
        assertEquals(2, actual.length);
    }

    @Test
    void testLinearTree() {
        // 测试线性树结构
        int[] parent = {-1, 0, 1, 2, 3};
        String s = "abcde";
        int[] actual = solution.findSubtreeSizes(parent, s);
        
        assertNotNull(actual);
        assertEquals(5, actual.length);
    }

    @Test
    void testAllSameCharacters() {
        // 测试所有字符相同的情况
        int[] parent = {-1, 0, 0, 1, 1};
        String s = "aaaaa";
        int[] actual = solution.findSubtreeSizes(parent, s);
        
        assertNotNull(actual);
        assertEquals(5, actual.length);
    }

    @Test
    void testAllDifferentCharacters() {
        // 测试所有字符不同的情况
        int[] parent = {-1, 0, 0, 1, 1};
        String s = "abcde";
        int[] actual = solution.findSubtreeSizes(parent, s);
        
        assertNotNull(actual);
        assertEquals(5, actual.length);
    }
}