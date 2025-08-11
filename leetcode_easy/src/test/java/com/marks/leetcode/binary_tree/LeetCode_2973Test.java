package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LeetCode_2973Test {

    @Test
    void testPlacedCoinsExample1() {
        // 示例1:
        // 输入：edges = [[0,1],[0,2],[0,3],[0,4],[0,5]], cost = [1,2,3,4,5,6]
        // 输出：[120,1,1,1,1,1]
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{0,2},{0,3},{0,4},{0,5}};
        int[] cost = {1,2,3,4,5,6};
        long[] expected = {120,1,1,1,1,1};
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsExample2() {
        // 示例2:
        // 输入：edges = [[0,1],[0,2],[1,3],[1,4],[1,5],[2,6],[2,7],[2,8]], cost = [1,4,2,3,5,7,8,-4,2]
        // 输出：[280,140,32,1,1,1,1,1,1]
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{0,2},{1,3},{1,4},{1,5},{2,6},{2,7},{2,8}};
        int[] cost = {1,4,2,3,5,7,8,-4,2};
        long[] expected = {280,140,32,1,1,1,1,1,1};
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsExample3() {
        // 示例3:
        // 输入：edges = [[0,1],[0,2]], cost = [1,2,-2]
        // 输出：[0,1,1]
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{0,2}};
        int[] cost = {1,2,-2};
        long[] expected = {0,1,1};
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsSingleNode() {
        // 测试只有一个节点的情况
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {};
        int[] cost = {5};
        long[] expected = {1}; // 小于3个节点，返回1
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsTwoNodes() {
        // 测试只有两个节点的情况
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1}};
        int[] cost = {3,4};
        long[] expected = {1,1}; // 每个节点子树都小于3个节点，返回1
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsAllNegative() {
        // 测试所有cost都是负数的情况
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{0,2}};
        int[] cost = {-1,-2,-3};
        long[] expected = {0,1,1}; // 根节点三个负数相乘为负数，所以是0
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsMixedWithZeros() {
        // 测试包含零的情况
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{0,2},{1,3},{1,4}};
        int[] cost = {0,-2,3,-1,4};
        long[] expected = {8,8,1,1,1}; // 根节点包含0，所以最大乘积为0
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsLargeNegativeProduct() {
        // 测试两个负数和一个正数产生最大正数乘积的情况
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{0,2},{0,3}};
        int[] cost = {-5,-3,4,2};
        long[] expected = {60,1,1,1}; // (-5)*(-3)*4 = 60
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsLinearTree() {
        // 测试线性树结构
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{1,2},{2,3}};
        int[] cost = {2,3,1,4};
        long[] expected = {24,12,1,1};
        long[] result = solution.placedCoins(edges, cost);
        assertArrayEquals(expected, result);
    }

    @Test
    void testPlacedCoinsComplexTree() {
        // 测试更复杂的树结构
        LeetCode_2973 solution = new LeetCode_2973();
        int[][] edges = {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6}};
        int[] cost = {1,-2,3,-4,5,-6,7};
        long[] result = solution.placedCoins(edges, cost);
        // 根节点需要从 [1,-2,3,-4,5,-6,7] 中选择3个数使得乘积最大
        // 最大乘积应该是 7*5*3 = 105 或 (-6)*(-4)*7 = 168，所以是168
        assertEquals(168, result[0]);
        // 节点1子树包含 [-2,-4,5]，最大乘积是 (-4)*(-2)*5 = 40
        assertEquals(40, result[1]);
        // 节点2子树包含 [3,-6,7]，最大乘积是 7*3*(-6) = -126，所以是0
        assertEquals(0, result[2]);
    }
}