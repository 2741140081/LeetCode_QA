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
        int n = grid[0].length;
        // 初始化dp, 0表示不可达, 1表示可达
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[0][i] = 1;
        }
        /*
        判断
         */
        for (int i = 1; i < n && i < m; i++) {
            dp[i][0] = 0;
            for (int j = 1; j <= i; j++) {
                if (grid[i][j] > Math.min(grid[i-1][j+1],Math.min(grid[i-1][j], grid[i-1][j+1]))) {
//                    dp[]
                }
            }
            if (grid[i][m-1] > Math.min(grid[i-1][m-2], grid[i-1][m-1])) {
                dp[i][m-1] = 1;
            }
        }

        int res = 0;
        return res;
    }
}
