package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description: LeetCode_1219 单元测试类
 * @Author: marks
 * @CreateDate: 2025/8/27 11:38
 */
class LeetCode_1219Test {

    private final LeetCode_1219 solution = new LeetCode_1219();

    /**
     * 测试用例1：正常情况
     * 输入：[[0,6,0],[5,8,7],[0,9,0]]
     * 期望输出：24
     */
    @Test
    void testGetMaximumGold_NormalCase() {
        int[][] grid = {
                {0, 6, 0},
                {5, 8, 7},
                {0, 9, 0}
        };
        int result = solution.getMaximumGold(grid);
        assertEquals(24, result);
    }

    /**
     * 测试用例2：全部为0
     * 输入：[[0,0,0],[0,0,0]]
     * 期望输出：0
     */
    @Test
    void testGetMaximumGold_AllZero() {
        int[][] grid = {
                {0, 0, 0},
                {0, 0, 0}
        };
        int result = solution.getMaximumGold(grid);
        assertEquals(0, result);
    }

    /**
     * 测试用例3：只有一个格子有黄金
     * 输入：[[0,3,0],[0,0,0]]
     * 期望输出：3
     */
    @Test
    void testGetMaximumGold_SingleGold() {
        int[][] grid = {
                {0, 3, 0},
                {0, 0, 0}
        };
        int result = solution.getMaximumGold(grid);
        assertEquals(3, result);
    }

    /**
     * 测试用例4：多个起点，最优路径
     * 输入：[[1,0,7],[2,0,6],[3,4,5]]
     * 期望输出：28 (路径可以采集所有黄金: 1→2→3→4→5→6→7)
     */
    @Test
    void testGetMaximumGold_MultiStartOptimalPath() {
        int[][] grid = {
                {1, 0, 7},
                {2, 0, 6},
                {3, 4, 5}
        };
        int result = solution.getMaximumGold(grid);
        assertEquals(28, result); // 修正期望值为28
    }

    /**
     * 测试用例5：边界情况，最多25个黄金格子
     * 构造一个3x9的网格，其中25个格子为1，其余为0
     * 期望输出：25（每个格子只能走一次）
     */
    @Test
    void testGetMaximumGold_BoundaryCase() {
        int[][] grid = {
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,0,0}
        };
        int result = solution.getMaximumGold(grid);
        assertEquals(25, result);
    }
}
