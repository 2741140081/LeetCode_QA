package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description: LeetCode_980 单元测试类
 * @Author: marks
 * @CreateDate: 2025/4/5
 */
class LeetCode_980Test {

    private final LeetCode_980 solution = new LeetCode_980();

    /**
     * TC01: 示例输入1
     * 输入：[[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
     * 期望输出：2
     */
    @Test
    void testUniquePathsIII_Example1() {
        int[][] grid = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 2, -1}
        };
        int result = solution.uniquePathsIII(grid);
        assertEquals(2, result, "路径数应为2");
    }

    /**
     * TC02: 全通路径
     * 输入：[[1,0,0,0],[0,0,0,0],[0,0,0,2]]
     * 期望输出：4
     */
    @Test
    void testUniquePathsIII_AllPass() {
        int[][] grid = {
                {1, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 2}
        };
        int result = solution.uniquePathsIII(grid);
        assertEquals(4, result, "路径数应为4");
    }

    /**
     * TC03: 起点终点相邻但被障碍阻断
     * 输入：[[1,-1],[2,-1]]
     * 期望输出：0
     */
    @Test
    void testUniquePathsIII_Blocked() {
        int[][] grid = {
                {1, -1},
                {2, -1}
        };
        int result = solution.uniquePathsIII(grid);
        assertEquals(1, result, "路径数应为0");
    }

    /**
     * TC04: 起点终点相邻无障碍
     * 输入：[[1,2]]
     * 期望输出：1
     */
    @Test
    void testUniquePathsIII_Adjacent() {
        int[][] grid = {{1, 2}};
        int result = solution.uniquePathsIII(grid);
        assertEquals(1, result, "路径数应为1");
    }

    /**
     * TC05: 两步路径
     * 输入：[[1,0],[0,2]]
     * 期望输出：2
     */
    @Test
    void testUniquePathsIII_TwoSteps() {
        int[][] grid = {
                {1, 0},
                {0, 2}
        };
        int result = solution.uniquePathsIII(grid);
        assertEquals(0, result, "路径数应为2");
    }
}
