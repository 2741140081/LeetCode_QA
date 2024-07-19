package com.marks.leetcode.gridDP;

public class LeetCode_931 {
    /**
     * 下降路径最小和
     * 给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。
     * 下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。在下一行选择的元素和当前行所选元素最多相隔一列
     * （即位于正下方或者沿对角线向左或者向右的第一个元素）。具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、
     * (row + 1, col) 或者 (row + 1, col + 1) 。
     *
     * E1:
     * 输入：matrix = [[2,1,3],[6,5,4],[7,8,9]]
     * 输出：13
     * 解释：如图所示，为和最小的两条下降路径
     *
     * E2:
     * 输入：matrix = [[-19,57],[-40,-5]]
     * 输出：-59
     * 解释：如图所示，为和最小的下降路径
     *
     * tips:
     * n == matrix.length == matrix[i].length
     * 1 <= n <= 100
     * -100 <= matrix[i][j] <= 100
     *
     * @param matrix
     * @return
     */
    public int minFallingPathSum(int[][] matrix) {
        int result = 0;
//        result = method_01(matrix);
        result = method_02(matrix);
        return result;
    }

    /**
     * 采用标准的dp实现
     * dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i-1][j+1]))
     * @param matrix
     * @return
     */
    private int method_01(int[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        int[][] dp = new int[n][n];
        // 初始化dp的第一行
        for (int i = 0; i < n; i++) {
            dp[0][i] = matrix[0][i];
        }
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.min(dp[i-1][0], dp[i-1][1]) + matrix[i][0];
            for (int j = 1; j < n-1; j++) {
                dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i-1][j+1])) + matrix[i][j];
            }
            dp[i][n-1] = Math.min(dp[i-1][n-2], dp[i-1][n-1]) + matrix[i][n-1];
        }
        int res = dp[n-1][0];
        for (int i = 1; i < n; i++) {
            res = Math.min(dp[n-1][i], res);
        }
        return res;
    }

    /**
     * 由于dp[i][j] 只与 dp[i-1] 的[j] [j-1] [j+1]
     * 是否可以将二维数组优化为一维数组
     * 即使用滚动数组优化二维dp数组
     * 将空间复杂度由O(n2) 优化为O(n)
     *
     * 思考后感觉会污染一维数组, 没有足够的空间(可能需要空间+1)
     * 我决定采用仅含2行的二维数组
     * int[][] dp = new int[2][n];
     * 通过模仿上一题(LeetCode_120.java)
     * 使用奇偶性来分别存储i 以及 i-1的 dp[] 值
     * @param matrix
     * @return
     */
    private int method_02(int[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        int[][] dp = new int[2][n];
        // 初始化dp的第一行
        for (int i = 0; i < n; i++) {
            dp[0][i] = matrix[0][i];
        }

        for (int i = 1; i < n; i++) {
            int curr = i % 2;
            int pre = 1 - curr;
            dp[curr][0] = Math.min(dp[pre][0], dp[pre][1]) + matrix[i][0];

            for (int j = 1; j < n-1; j++) {
                dp[curr][j] = Math.min(dp[pre][j-1], Math.min(dp[pre][j], dp[pre][j+1])) + matrix[i][j];
            }

            dp[curr][n-1] = Math.min(dp[pre][n-2], dp[pre][n-1]) + matrix[i][n-1];
        }
        int currReslut = (n - 1) % 2;

        int res = dp[currReslut][0];
        for (int i = 1; i < n; i++) {
            res = Math.min(dp[currReslut][i], res);
        }
        return res;
    }
}
