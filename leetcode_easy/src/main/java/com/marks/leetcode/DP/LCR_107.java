package com.marks.leetcode.DP;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_107 {

    /**
     * @Description:
     * 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
     *
     * 两个相邻元素间的距离为 1。
     *
     * tips:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 10^4
     * 1 <= m * n <= 10^4
     * mat[i][j] is either 0 or 1.
     * mat 中至少有一个 0
     * @param mat
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/10/15 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] updateMatrix(int[][] mat) {
        int[][] result;
        result = method_01(mat);
        return result;
    }

    // dirs
    private static final int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};


    /**
     * @Description:
     * E1:
     * 输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
     * 输出：[[0,0,0],[0,1,0],[1,2,1]]
     * 1. 需要用一个队列存储所有0的位置，然后从这些0的位置开始，向四周扩展，记录距离
     * 2. int[][] visited 存储访问过的位置, 防止死循环
     * 3. int[][] dp 存储结果
     * AC: 14ms/47.46MB
     * @param mat 
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/10/15 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        boolean[][] visited = new boolean[m][n];
        int[][] dp = new int[m][n];
        // 初始化dp
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    dp[i][j] = 0;
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] curr = queue.poll();
                int x = curr[0];
                int y = curr[1];
                // 向四个方向扩展
                for (int[] dir : dirs) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];
                    if (newX < 0 || newX >= m || newY < 0 || newY >= n || visited[newX][newY]) {
                        continue;
                    }
                    visited[newX][newY] = true;
                    // 找到最短距离
                    dp[newX][newY] = Math.min(dp[newX][newY], dp[x][y] + 1);
                    queue.offer(new int[]{newX, newY});
                }
            }
        }
        
        return dp;
    }
}
