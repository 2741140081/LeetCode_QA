package com.marks.leetcode.gridDP;

public class LeetCode_2684 {
    /**
     * 给你一个下标从 0 开始、大小为 m x n 的矩阵 grid ，矩阵由若干 正 整数组成。
     * 你可以从矩阵第一列中的 任一 单元格出发，按以下方式遍历 grid ;
     * 从单元格 (row, col) 可以移动到 (row - 1, col + 1)、(row, col + 1) 和 (row + 1, col + 1)
     * 三个单元格中任一满足值 严格 大于当前单元格的单元格。
     *
     * 返回你在矩阵中能够 移动 的 最大 次数。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 1000
     * 4 <= m * n <= 10^5
     * 1 <= grid[i][j] <= 10^6
     *
     * @param grid
     * @return
     */

    private int res;

    public int maxMoves(int[][] grid) {
        int result = 0;
        result = method_01(grid);
        return result;
    }

    /**
     * dp[m][n] 中存储0/1 其中0表示不可达, 1表示可达
     * 假设(3, 3) -->
     * (2, 4) (3, 4) (4, 4)
     * @param grid
     * @return
     */
    private int method_01(int[][] grid) {
        int m = grid.length;
        for (int i = 0; i < m; i++) {
            dfs(grid,  i, 0);
        }
        return res;
    }

    private void dfs(int[][] grid, int i, int j) {
        res = Math.max(j, res);

        // 如果到达最大值
        if (res == grid[0].length - 1) {
            return;
        }
        // 判断是否可以到达(i-1, i, i + 1), j + 1
        if (i-1 >= 0 && i < grid.length && grid[i][j] < grid[i-1][j+1]) {
            dfs(grid, i-1, j+1);
        }

        if (i < grid.length && grid[i][j] < grid[i][j+1]) {
            dfs(grid, i, j+1);
        }

        if (i+1 < grid.length && grid[i][j] < grid[i+1][j+1]) {
            dfs(grid, i+1, j+1);
        }
        // 将已访问的元素值重置为0
        /**
         * 如何理解将数组元素重置为0
         * 1.假设grid[1, 1] 可以由grid[1, 0] 和 grid[2, 0] 访问
         * 2.首先是grid[1, 0] 访问 grid[1, 1], 并且将重置grid[1, 1] = 0
         * 3.当我由grid[2, 0] 访问grid[1, 1]时, 无法访问
         * 4.并且有grid[1, 1]向后访问的res 值和路径是固定的。
         * 5.需要后来者自行寻找更好的路径, 设置grid[i, j] = 0 仅仅只是将这条路给堵住了。
         * 6.如果grid[2, 0] 也走 grid[1, 1] 也只是和grid[1, 0]的res一样长, 不可能更长, 只能需要新的路径来实现更长。
         * 7.所以综上所述, grid[i][j] = 0 是这道题的点睛之笔
         */
        grid[i][j] = 0;
    }
}
