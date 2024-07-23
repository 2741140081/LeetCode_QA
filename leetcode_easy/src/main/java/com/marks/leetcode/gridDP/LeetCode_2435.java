package com.marks.leetcode.gridDP;

public class LeetCode_2435 {
    /**
     * 给你一个下标从 0 开始的 m x n 整数矩阵 grid 和一个整数 k 。
     * 你从起点 (0, 0) 出发，每一步只能往 下 或者往 右 ，你想要到达终点 (m - 1, n - 1) 。
     * 请你返回路径和能被 k 整除的路径数目，由于答案可能很大，返回答案对 109 + 7 取余 的结果。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 5 * 10^4
     * 1 <= m * n <= 5 * 10^4
     * 0 <= grid[i][j] <= 100
     * 1 <= k <= 50
     *
     * @param grid
     * @param k
     * @return
     */
    public int numberOfPaths(int[][] grid, int k) {
        int result = 0;
        result = method_01(grid, k);
        return result;
    }

    /**
     * E1:
     * 输入：grid = [[5,2,4],[3,0,5],[0,7,2]], k = 3
     * 输出：2
     * 解释：有两条路径满足路径上元素的和能被 k 整除。
     * 第一条路径为上图中用红色标注的路径，和为 5 + 2 + 4 + 5 + 2 = 18 ，能被 3 整除。
     * 第二条路径为上图中用蓝色标注的路径，和为 5 + 3 + 0 + 5 + 2 = 15 ，能被 3 整除。
     * k = 3
     * 5    7   11
     * 8
     * 8
     * @param grid
     * @param k
     * @return
     */
    private int method_01(int[][] grid, int k) {
        final int MOD = (int) 1e9 + 7;
        int m = grid.length;
        int n = grid[0].length;
        int[][][] dp = new int[m][n][k];
        // 初始化
        dp[0][0][grid[0][0] % k] = 1;
        int tempRow = grid[0][0] % k;
        // 初始化首行
        for (int i = 1; i < n; i++) {
            tempRow = (tempRow + grid[0][i]) % k;
            dp[0][i][tempRow] = 1;
        }
        int tempCol = grid[0][0] % k;
        // 初始化首列
        for (int i = 1; i < m; i++) {
            tempCol = (tempCol + grid[i][0])% k;
            dp[i][0][tempCol] = 1;
        }
        // 状态转移方程怎么写?
        // dp[i][j][value] = dp[i-1][j][value] + dp[i][j-1][value]

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                int value = grid[i][j] % k;
                for (int l = 0; l < k; l++) {
                    // l 表示 (prev + num) % k = l
                    // prev = (l - num + k) % k
                    int prev = (l - value + k) % k;
                    dp[i][j][l] = (dp[i-1][j][prev] + dp[i][j-1][prev]) % MOD;
                }
            }
        }
        // 可能会产生大量的无用数据, 如何剪枝

        return dp[m-1][n-1][0];
    }
}
