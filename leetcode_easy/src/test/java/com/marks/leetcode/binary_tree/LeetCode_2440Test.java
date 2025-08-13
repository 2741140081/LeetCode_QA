package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_2440测试类
 */
class LeetCode_2440Test {

    private final LeetCode_2440 solution = new LeetCode_2440();

    @Test
    void testExample1() {
        // 测试用例: nums = [6,2,2,2,6], edges = [[0,1],[1,2],[1,3],[3,4]]
        // 期望输出: 2
        int[] nums = {6, 2, 2, 2, 6};
        int[][] edges = {{0, 1}, {1, 2}, {1, 3}, {3, 4}};
        int expected = 2;
        int actual = solution.componentValue(nums, edges);
        assertEquals(expected, actual);
    }

    @Test
    void testExample2() {
        // 测试用例: nums = [2], edges = []
        // 期望输出: 0 (只有一个节点)
        int[] nums = {2};
        int[][] edges = {};
        int expected = 0;
        int actual = solution.componentValue(nums, edges);
        assertEquals(expected, actual);
    }

    @Test
    void testAllSameValues() {
        // 测试用例: 所有节点值相同
        // nums = [3,3,3,3], edges = [[0,1],[1,2],[2,3]]
        // 期望输出: 3 (可以分割成4个连通块，每个和为3)
        int[] nums = {3, 3, 3, 3};
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}};
        int expected = 3;
        int actual = solution.componentValue(nums, edges);
        assertEquals(expected, actual);
    }

    @Test
    void testCannotSplit() {
        // 测试用例: 无法分割成多个相等价值的连通块
        // nums = [1,2,3], edges = [[0,1],[1,2]]
        // 期望输出: 0 (只能保持一个连通块)
        int[] nums = {1, 2, 3};
        int[][] edges = {{0, 1}, {1, 2}};
        int expected = 1;
        int actual = solution.componentValue(nums, edges);
        assertEquals(expected, actual);
    }

    @Test
    void testStarGraph() {
        // 测试用例: 星形图
        // nums = [1,1,1,1,1], edges = [[0,1],[0,2],[0,3],[0,4]]
        // 期望输出: 4 (可以分割成5个连通块，每个和为1)
        int[] nums = {1, 1, 1, 1, 1};
        int[][] edges = {{0, 1}, {0, 2}, {0, 3}, {0, 4}};
        int expected = 4;
        int actual = solution.componentValue(nums, edges);
        assertEquals(expected, actual);
    }

    @Test
    void testLinearGraph() {
        // 测试用例: 线性图
        // nums = [2,2,2,2], edges = [[0,1],[1,2],[2,3]]
        // 期望输出: 3 (可以分割成4个连通块，每个和为2)
        int[] nums = {2, 2, 2, 2};
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}};
        int expected = 3;
        int actual = solution.componentValue(nums, edges);
        assertEquals(expected, actual);
    }

    @Test
    void testComplexCase() {
        // 测试用例: 复杂情况
        // nums = [1,1,2,1,1,1,1,2,1,1], edges = [[0,1],[1,2],[2,3],[3,4],[4,5],[5,6],[6,7],[7,8],[8,9]]
        // 总和为12，可以分割成3个和为4的连通块，所以期望输出为2
        int[] nums = {1, 1, 2, 1, 1, 1, 1, 2, 1, 1};
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}, {7, 8}, {8, 9}};
        int expected = 5;
        int actual = solution.componentValue(nums, edges);
        assertEquals(expected, actual);
    }
}