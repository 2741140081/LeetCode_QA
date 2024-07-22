package com.marks.leetcode.gridDP;

import java.util.Arrays;

public class LeetCode_1289 {
    /**
     * 给你一个 n x n 整数矩阵 grid ，请你返回 非零偏移下降路径 数字和的最小值。
     *
     * 非零偏移下降路径 定义为：从 grid 数组中的每一行选择一个数字，
     * 且按顺序选出来的数字中，相邻数字不在原数组的同一列。
     *
     * tips:
     * n == grid.length == grid[i].length
     * 1 <= n <= 200
     * -99 <= grid[i][j] <= 99
     *
     * @param grid
     * @return
     */
    public int minFallingPathSum(int[][] grid) {
        int result = 0;
        result = method_01(grid);
        return result;
    }

    /**
     * 输入：grid = [[1,2,3],[4,5,6],[7,8,9]]
     * 1    2   3
     * 4    5   6
     * 7    8   9
     * 输出：13
     * 解释：
     * 所有非零偏移下降路径包括：
     * [1,5,9], [1,5,7], [1,6,7], [1,6,8],
     * [2,4,8], [2,4,9], [2,6,7], [2,6,8],
     * [3,4,8], [3,4,9], [3,5,7], [3,5,9]
     * 下降路径中数字和最小的是 [1,5,7] ，所以答案是 13 。
     *
     * 思路:
     * dp[i][j] = Math.min(dp[i-1][0~n(不包含j)]) + grid[i][j]
     * @param grid
     * @return
     */
    private int method_01(int[][] grid) {
        // n == 1
        int n = grid.length;
        if (n == 1) {
            return grid[0][0];
        }

        // n > 1
        int[][] dp = new int[2][n];

        // 初始化dp[0]
        for (int i = 0; i < n; i++) {
            dp[0][i] = grid[0][i];
        }
        int curr = 0;
        for (int i = 1; i < n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            for (int j = 0; j < n; j++) {
                dp[curr][j] = minPathSumValue(grid, dp[pre], i, j);
            }
        }

        int res = Arrays.stream(dp[curr]).min().getAsInt();
        return res;
    }

    private int minPathSumValue(int[][] grid, int[] dp, int i, int j) {
        int res = j != 0 ? dp[0] : dp[1];
        for (int k = 0; k < grid.length; k++) {
            if (k == j) {
                continue;
            }
            res = Math.min(res, dp[k]);
        }

        return res + grid[i][j];
    }
}
