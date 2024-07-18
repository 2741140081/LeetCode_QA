package com.marks.leetcode.gridDP;

public class LeetCode_62 {
    /**
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     *
     * E1:
     * 输入：m = 3, n = 7
     * 输出：28
     *
     * E2:
     * 输入：m = 3, n = 2
     * 输出：3
     * 解释：
     * 从左上角开始，总共有 3 条路径可以到达右下角。
     * 1. 向右 -> 向下 -> 向下
     * 2. 向下 -> 向下 -> 向右
     * 3. 向下 -> 向右 -> 向下
     *
     * E3:
     * 输入：m = 7, n = 3
     * 输出：28
     *
     * tips:
     * 1 <= m, n <= 100
     * 题目数据保证答案小于等于 2 * 10^9
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int result = 0;
        result = method_01(m, n);
        return result;
    }

    /**
     * 思路:
     * 对于i, j坐标的元素
     * dp[0][0] = 0;
     * dp[i][j] = d[i-1][j] + dp[i][j-1]
     * @param m
     * @param n
     * @return
     */
    private int method_01(int m, int n) {
        int[][] dp = new int[m][n];
        // 初始化数据
        dp[0][0] = 1;
        for (int i = 1; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }

        return dp[m-1][n-1];
    }
}
