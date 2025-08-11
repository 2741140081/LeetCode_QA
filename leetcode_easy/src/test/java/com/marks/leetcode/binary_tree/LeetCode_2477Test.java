package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LeetCode_2477Test {

    @Test
    void testMinimumFuelCostExample1() {
        // 示例1:
        // 输入: roads = [[3,1],[3,2],[1,0],[0,4],[0,5],[4,6]], seats = 2
        // 输出: 7
        LeetCode_2477 solution = new LeetCode_2477();
        int[][] roads = {{3,1},{3,2},{1,0},{0,4},{0,5},{4,6}};
        int seats = 2;
        long expected = 7;
        long result = solution.minimumFuelCost(roads, seats);
        assertEquals(expected, result);
    }

    @Test
    void testMinimumFuelCostExample2() {
        // 示例2:
        // 输入: roads = [[0,1],[0,2],[0,3]], seats = 5
        // 输出: 3
        LeetCode_2477 solution = new LeetCode_2477();
        int[][] roads = {{0,1},{0,2},{0,3}};
        int seats = 5;
        long expected = 3;
        long result = solution.minimumFuelCost(roads, seats);
        assertEquals(expected, result);
    }

    @Test
    void testMinimumFuelCostSingleEdge() {
        // 测试只有一个边的情况
        LeetCode_2477 solution = new LeetCode_2477();
        int[][] roads = {{0,1}};
        int seats = 1;
        long expected = 1;
        long result = solution.minimumFuelCost(roads, seats);
        assertEquals(expected, result);
    }

    @Test
    void testMinimumFuelCostEmptyRoads() {
        // 测试没有道路的情况 (只有一个节点)
        LeetCode_2477 solution = new LeetCode_2477();
        int[][] roads = {};
        int seats = 1;
        long expected = 0;
        long result = solution.minimumFuelCost(roads, seats);
        assertEquals(expected, result);
    }

    @Test
    void testMinimumFuelCostLinearTree() {
        // 测试线性树结构
        LeetCode_2477 solution = new LeetCode_2477();
        int[][] roads = {{0,1},{1,2},{2,3}};
        int seats = 1;
        long expected = 6; // 3+2+1 = 6
        long result = solution.minimumFuelCost(roads, seats);
        assertEquals(expected, result);
    }

    @Test
    void testMinimumFuelCostWithLargeSeats() {
        // 测试当座位数很大时的情况
        LeetCode_2477 solution = new LeetCode_2477();
        int[][] roads = {{0,1},{1,2},{1,3}};
        int seats = 100000;
        long expected = 3; // 每个节点只需一辆车
        long result = solution.minimumFuelCost(roads, seats);
        assertEquals(expected, result);
    }
}