package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_2873Test {

    @Test
    void testMaxKDivisibleComponentsExample1() {
        // 测试用例1: n = 7, edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]], values = [3,0,6,1,5,2,1], k = 3
        // 预期输出: 3
        LeetCode_2873 solution = new LeetCode_2873();
        int n = 7;
        int[][] edges = {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6}};
        int[] values = {3,0,6,1,5,2,1};
        int k = 3;
        int expected = 3;
        int actual = solution.maxKDivisibleComponents(n, edges, values, k);
        assertEquals(expected, actual);
    }

    @Test
    void testMaxKDivisibleComponentsExample2() {
        // 测试用例2: n = 5, edges = [[0,1],[0,2],[1,3],[1,4]], values = [1,2,3,4,2], k = 2
        // values总和 = 1+2+3+4+2 = 12, 12 % 2 == 0
        // 预期输出: 2
        LeetCode_2873 solution = new LeetCode_2873();
        int n = 5;
        int[][] edges = {{0,1},{0,2},{1,3},{1,4}};
        int[] values = {1,2,3,4,2};
        int k = 2;
        int expected = 4;
        int actual = solution.maxKDivisibleComponents(n, edges, values, k);
        assertEquals(expected, actual);
    }

    @Test
    void testMaxKDivisibleComponentsSingleNode() {
        // 测试用例3: 单个节点的情况 n = 1, edges = [], values = [1], k = 1
        // 预期输出: 1
        LeetCode_2873 solution = new LeetCode_2873();
        int n = 1;
        int[][] edges = {};
        int[] values = {1};
        int k = 1;
        int expected = 1;
        int actual = solution.maxKDivisibleComponents(n, edges, values, k);
        assertEquals(expected, actual);
    }

    @Test
    void testMaxKDivisibleComponentsAllDivisible() {
        // 测试用例4: 所有节点值都能被k整除的情况
        // n = 4, edges = [[0,1],[1,2],[2,3]], values = [3,6,9,12], k = 3
        // values总和 = 3+6+9+12 = 30, 30 % 3 == 0
        // 预期输出: 4
        LeetCode_2873 solution = new LeetCode_2873();
        int n = 4;
        int[][] edges = {{0,1},{1,2},{2,3}};
        int[] values = {3,6,9,12};
        int k = 3;
        int expected = 4;
        int actual = solution.maxKDivisibleComponents(n, edges, values, k);
        assertEquals(expected, actual);
    }

    @Test
    void testMaxKDivisibleComponentsTwoNodes() {
        // 测试用例5: 只有两个节点的情况
        // n = 2, edges = [[0,1]], values = [2,4], k = 3
        // values总和 = 2+4 = 6, 6 % 3 == 0
        // 预期输出: 1
        LeetCode_2873 solution = new LeetCode_2873();
        int n = 2;
        int[][] edges = {{0,1}};
        int[] values = {2,4};
        int k = 3;
        int expected = 1;
        int actual = solution.maxKDivisibleComponents(n, edges, values, k);
        assertEquals(expected, actual);
    }
}