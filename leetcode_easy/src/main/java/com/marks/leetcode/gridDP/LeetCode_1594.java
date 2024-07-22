package com.marks.leetcode.gridDP;

import java.util.Arrays;

public class LeetCode_1594 {
    /**
     * 给你一个大小为 m x n 的矩阵 grid 。最初，你位于左上角 (0, 0) ，每一步，你可以在矩阵中 向右 或 向下 移动。
     * 在从左上角 (0, 0) 开始到右下角 (m - 1, n - 1) 结束的所有路径中，找出具有 最大非负积 的路径。路径的积是沿路径访问的单元格中所有整数的乘积。
     * 返回 最大非负积 对 10^9 + 7 取余 的结果。如果最大积为 负数 ，则返回 -1 。
     * 注意，取余是在得到最大积之后执行的。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 15
     * -4 <= grid[i][j] <= 4
     *
     * @param grid
     * @return
     */
    public int maxProductPath(int[][] grid) {
        int result = 0;
        result = method_01(grid);
        return result;
    }

    /**
     * E1:
     * 输入：grid = [[-1,-2,-3],[-2,-3,-3],[-3,-3,-2]]
     * 输出：-1
     * 解释：从 (0, 0) 到 (2, 2) 的路径中无法得到非负积，所以返回 -1 。
     *
     * E2:
     * 输入：grid = [[1,-2,1],[1,-2,1],[3,-4,1]]
     * 输出：8
     * 解释：最大非负积对应的路径如图所示 (1 * 1 * -2 * -4 * 1 = 8)
     *
     * 思路:
     * 之前好像做过一个类似的
     * 需要获取最大值和最小值
     * 因为可能由于当前的值不确定正负
     * 所以需要存储最大值和最小值, 以便负负得正
     * 案例为LeetCode_152.java
     * 维护2个dp, 分别为minDP maxDP存储乘积的最小值和最大值
     * 思考1. 是否可以利用滚动数组优化dp空间
     * 思考2. dp[i][j] 的公式是什么？
     * 先做思考2
     * 1. 初始化dp[0][0] = grid[0][0]
     * 2. 只能 向右或者向下移动
     * a. 向右移动 dp[i][j] = dp[i][j-1] * grid[i][j]
     * b. 向下移动 dp[i][j] = dp[i-1][j] * grid[i][j]
     *
     * 思考1 暂时不考虑
     * int[][] minDP = Math.min(dp[i][j-1] * grid[i][j], dp[i-1][j] * grid[i][j])
     * int[][] maxDP = Math.max(dp[i][j-1] * grid[i][j], dp[i-1][j] * grid[i][j])
     * @param grid
     * @return
     */
    private int method_01(int[][] grid) {
        final int MOD = 1000000007;
        int m = grid.length;
        int n = grid[0].length;

        // minDP maxDP存储乘积的最小值和最大值
        long[][] minDP = new long[m][n];
        long[][] maxDP = new long[m][n];

        // 初始化, 可能需要初始化第0行所有元素, 同样需要初始化第0列所有元素
        minDP[0][0] = maxDP[0][0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            minDP[0][i] = minDP[0][i-1] * grid[0][i] % MOD;
            maxDP[0][i] = maxDP[0][i-1] * grid[0][i] % MOD;
        }

        for (int i = 1; i < m; i++) {
            minDP[i][0] = minDP[i-1][0] * grid[i][0] % MOD;
            maxDP[i][0] = maxDP[i-1][0] * grid[i][0] % MOD;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                minDP[i][j] = Math.min(Math.min(maxDP[i][j-1] * grid[i][j], maxDP[i-1][j] * grid[i][j]),
                        Math.min(minDP[i][j-1] * grid[i][j], minDP[i-1][j] * grid[i][j])) % MOD;
                maxDP[i][j] = Math.max(Math.max(maxDP[i][j-1] * grid[i][j], maxDP[i-1][j] * grid[i][j]),
                        Math.max(minDP[i][j-1] * grid[i][j], minDP[i-1][j] * grid[i][j])) % MOD;;
            }
        }
        // 获取maxDP[m-1] 的最大值
        long res = maxDP[m-1][n-1];
        return (int) (res < 0 ? -1 : res);
    }
}
