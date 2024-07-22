package com.marks.leetcode.gridDP;

import java.util.Arrays;

public class LeetCode_2304 {
    /**
     * 给你一个下标从 0 开始的整数矩阵 grid ，矩阵大小为 m x n ，由从 0 到 m * n - 1 的不同整数组成。你可以在此矩阵中，
     * 从一个单元格移动到 下一行 的任何其他单元格。
     * 如果你位于单元格 (x, y) ，且满足 x < m - 1 ，你可以移动到 (x + 1, 0), (x + 1, 1), ..., (x + 1, n - 1) 中的任何一个单元格。
     * 注意： 在最后一行中的单元格不能触发移动。
     *
     * 每次可能的移动都需要付出对应的代价，代价用一个下标从 0 开始的二维数组 moveCost 表示，
     * 该数组大小为 (m * n) x n ，其中 moveCost[i][j] 是从值为 i 的单元格移动到下一行第 j 列单元格的代价。
     * 从 grid 最后一行的单元格移动的代价可以忽略。
     *
     * grid 一条路径的代价是：所有路径经过的单元格的 值之和 加上 所有移动的 代价之和 。
     * 从 第一行 任意单元格出发，返回到达 最后一行 任意单元格的最小路径代价。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 50
     * grid 由从 0 到 m * n - 1 的不同整数组成
     * moveCost.length == m * n
     * moveCost[i].length == n
     * 1 <= moveCost[i][j] <= 100
     *
     * @param grid
     * @param moveCost
     * @return
     */
    public int minPathCost(int[][] grid, int[][] moveCost) {
        int result = 0;
        result = method_01(grid, moveCost);
        return result;
    }

    /**
     * E1:
     * 输入：grid = [[5,3],[4,0],[2,1]], moveCost = [[9,8],[1,5],[10,12],[18,6],[2,4],[14,3]]
     * grid
     * 5    3
     * 4    0
     * 2    1
     * moveCost[5][1] = 3
     * moveCost[0][1] = 8
     * 输出：17
     * 解释：最小代价的路径是 5 -> 0 -> 1 。
     * - 路径途经单元格值之和 5 + 0 + 1 = 6 。
     * - 从 5 移动到 0 的代价为 3 。
     * - 从 0 移动到 1 的代价为 8 。
     * 路径总代价为 6 + 3 + 8 = 17 。
     *
     * 假设:
     * 我现在位于grid[i][j] 处
     * dp[i][j] = Math.min(dp[i-1][0~n-1], moveCost[grid[i][0-n-1]][j])
     * @param grid
     * @param moveCost
     * @return
     */
    private int method_01(int[][] grid, int[][] moveCost) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[2][n];

        // 初始化第一行
        for (int i = 0; i < n; i++) {
            dp[0][i] = grid[0][i];
        }

        // 遍历grid, 从第1行开始遍历
        for (int i = 1; i < m; i++) {
            int curr = i % 2;
            int pre = 1 - curr;
            for (int j = 0; j < n; j++) {
                dp[curr][j] = minPathCost(grid, moveCost, dp[pre], i, j);
            }
        }
        int result =  (m-1) % 2;
        int res = Arrays.stream(dp[result]).min().getAsInt();
        return res;
    }

    private int minPathCost(int[][] grid, int[][] moveCost, int[] dp, int i, int j) {
        // 设置为第0个value
        int res = dp[0] + moveCost[grid[i-1][0]][j];
        for (int k = 1; k < dp.length; k++) {
            int value = dp[k] + moveCost[grid[i-1][k]][j];
            res = Math.min(res, value);
        }
        return res + grid[i][j];
    }
}
