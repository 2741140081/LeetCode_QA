package com.marks.leetcode.DP;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_688 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/11 10:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_688 {
    private int[][] dirs = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
    private final double P = 0.125;
    /**
     * @Description:
     * 在一个 n x n 的国际象棋棋盘上，一个骑士从单元格 (row, column) 开始，并尝试进行 k 次移动。
     * 行和列是 从 0 开始 的，所以左上单元格是 (0,0) ，右下单元格是 (n - 1, n - 1) 。
     * 象棋骑士有8种可能的走法，如下图所示。每次移动在基本方向上是两个单元格，然后在正交方向上是一个单元格。
     * tips:
     * 1 <= n <= 25
     * 0 <= k <= 100
     * 0 <= row, column <= n - 1
     * @param: n
     * @param: k
     * @param: row
     * @param: column
     * @return double
     * @author marks
     * @CreateDate: 2026/02/11 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double knightProbability(int n, int k, int row, int column) {
        double result;
        result = method_01(n, k, row, column);
        result = method_02(n, k, row, column);
        return result;
    }

    /**
     * @Description:
     * 1. 直接使用动态规划来解决
     * dp[k + 1][n][n], 进行 K 次遍历, 更新 dp[k][i][j] 的概率
     * @param: n
     * @param: k
     * @param: row
     * @param: column
     * @return double
     * @author marks
     * @CreateDate: 2026/02/11 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_02(int n, int k, int row, int column) {

        return 0;
    }

    /**
     * @Description:
     * 1. 先定义移动的方向 int[][] dirs = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
     * 2. double[][][] dp = new double[n][n][k + 1]; dp[i][j][t] 表示从 (i, j) 移动 t 步，在 (i, j) 处能留在棋盘上的概率
     * AC: 12ms/46.39MB
     * @param: n
     * @param: k
     * @param: row
     * @param: column
     * @return double
     * @author marks
     * @CreateDate: 2026/02/11 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int n, int k, int row, int column) {
        double[][][] dp = new double[n][n][k + 1];
        // 使用队列
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{row, column, k});
        dp[row][column][k] = 1.0;
        // 需要检查重复添加的情况
        double ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();

            boolean[][] visited = new boolean[n][n];
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int curr_i = cur[0], curr_j = cur[1], t = cur[2];
                if (t == 0) {
                    ans += dp[curr_i][curr_j][t];
                    continue;
                }
                for (int[] dir : dirs) {
                    int nextI = curr_i + dir[0];
                    int nextJ = curr_j + dir[1];
                    if (nextI >= 0 && nextI < n && nextJ >= 0 && nextJ < n) {
                        dp[nextI][nextJ][t - 1] += P * dp[curr_i][curr_j][t];
                        if (!visited[nextI][nextJ]) {
                            queue.offer(new int[]{nextI, nextJ, t - 1});
                            visited[nextI][nextJ] = true;
                        }
                    }
                }
            }
        }

        return ans;
    }

}
