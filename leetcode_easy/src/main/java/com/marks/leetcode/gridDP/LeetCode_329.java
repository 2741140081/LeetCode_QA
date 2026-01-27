package com.marks.leetcode.gridDP;

import java.util.LinkedList;
import java.util.Queue;

public class LeetCode_329 {
    // 二维数组行
    private int m;
    // 二维数组列
    private int n;
    // 上、下、左、右移动
    public int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    /**
     * 矩阵中的最长递增路径<p>
     * 给定一个 m x n 整数矩阵 matrix，找出其中 最长递增路径 的长度。<p>
     * 对于每个单元格，你可以往上，下，左，右四个方向移动。<p>
     * 你不能在对角线方向上移动或移动到 边界外（即不允许环绕）。<p>
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        int result = 0;
//        result = method_01(matrix);
        result = method_02(matrix);
        return result;
    }


    /**
     * <p>E1:</p>
     * <p>输入：matrix = [[9,9,4],[6,6,8],[2,1,1]]</p>
     * <p>输出：4 </p>
     * <p>解释：最长递增路径为 [1, 2, 6, 9]。</p>
     * <img src= "https://assets.leetcode.com/uploads/2021/01/05/grid1.jpg"></img>
     * 想不到什么突破点, 直接暴力解决
     * dp[i][j] 存储当前(i, j)的最长递增路径, 初始化一个dp[i][j] = 0;
     * 1.
     * a. dp[i][j] >= dp[i][j+1]
     * b. dp[i][j] <  dp[i][j+1]
     * 2.
     * a. dp[i][j] >= dp[i+1][j]
     * a. dp[i][j] <  dp[i+1][j]
     * @param matrix
     * @return
     */
    private int method_01(int[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        // memo缓存最长递增路径值
        int[][] memo = new int[m][n];
        int max = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                memo[i][j] = dfs(matrix, memo, i, j);
                max = Math.max(max, memo[i][j]);
            }
        }
        return max;
    }

    private int dfs(int[][] matrix, int[][] memo, int i, int j) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        // 初始化值为1, 设置已访问, eg{{1}} memo[0][0] = 1
        memo[i][j]++;
        for (int[] dir : dirs) {
            int row = i + dir[0];
            int col = j + dir[1];
            // 判断是否越界
            if (row >= 0 && col >= 0 && row < m && col < n && matrix[i][j] < matrix[row][col]){
                memo[i][j] = Math.max(memo[i][j], dfs(matrix, memo, row, col) + 1);
            }
        }
        return memo[i][j];
    }

    /**
     * 基于拓扑排序
     * 对于每一个节点x, y 存在出度和入度
     * 1. 对于二维数组中的最大值, 它的出度为0, 我需要遍历整个matrix[m][n]。
     * 2. 初始化每个节点的出度存放在数组 outDegrees[m][n]
     * AC: 16ms/46.5MB
     * @param matrix
     * @return
     */
    private int method_02(int[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        // memo缓存最长递增路径值
        int[][] outDegrees = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] dir : dirs) {
                    int row = i + dir[0];
                    int col = j + dir[1];
                    // 判断是否越界 以及 是否符合条件
                    if (row >= 0 && col >= 0 && row < m && col < n && matrix[i][j] < matrix[row][col]){
                        outDegrees[i][j]++;
                    }
                }
            }
        }
        // 将所有出度为0的节点添加到队列中, 值是当前元素的坐标
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (outDegrees[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        int res = 0;
        while (!queue.isEmpty()) {
            res++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int x = cell[0];
                int y = cell[1];
                for (int[] dir : dirs) {
                    int row = x + dir[0];
                    int col = y + dir[1];
                    // 判断是否越界 以及 是否符合条件
                    if (row >= 0 && col >= 0 && row < m && col < n && matrix[row][col] < matrix[x][y]){
                        outDegrees[row][col]--;
                        if (outDegrees[row][col] == 0) {
                            queue.offer(new int[]{row, col});
                        }
                    }
                }
            }
        }
        return res;
    }
}
