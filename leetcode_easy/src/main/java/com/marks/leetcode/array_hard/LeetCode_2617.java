package com.marks.leetcode.array_hard;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2617 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/4 16:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2617 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的 m x n 整数矩阵 grid 。你一开始的位置在 左上角 格子 (0, 0) 。
     * 当你在格子 (i, j) 的时候，你可以移动到以下格子之一：
     * 满足 j < k <= grid[i][j] + j 的格子 (i, k) （向右移动），或者
     * 满足 i < k <= grid[i][j] + i 的格子 (k, j) （向下移动）。
     * 请你返回到达 右下角 格子 (m - 1, n - 1) 需要经过的最少移动格子数，如果无法到达右下角格子，请你返回 -1 。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 0 <= grid[i][j] < m * n
     * grid[m - 1][n - 1] == 0
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/09/04 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumVisitedCells(int[][] grid) {
        int result;
        result = method_01(grid);
        result = method_02(grid);
        return result;
    }

    /**
     * @Description:
     * DP + 线段树优化, 时间复杂度 O(mn * log(max(m,n)))
     * dp[i][j] = min(区间最小值 from row/col segment trees) + 1
     * rowTree[i]: 第 i 行的 dp 值线段树, 查询 [j - grid[i][k], j-1] 范围最小值
     * colTree[j]: 第 j 列的 dp 值线段树, 查询 [i - grid[k][j], i-1] 范围最小值
     * @param: grid
     * return int
     * @author marks
     * @CreateDate: 2026/09/04
     */
    @SuppressWarnings("all")
    private int method_02(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; ++i) {
            Arrays.fill(dist[i], -1);
        }
        dist[0][0] = 1;
        PriorityQueue<int[]>[] row = new PriorityQueue[m];
        PriorityQueue<int[]>[] col = new PriorityQueue[n];
        for (int i = 0; i < m; ++i) {
            row[i] = new PriorityQueue<int[]>(Comparator.comparingInt(a -> a[0]));
        }
        for (int i = 0; i < n; ++i) {
            col[i] = new PriorityQueue<int[]>(Comparator.comparingInt(a -> a[0]));
        }

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                while (!row[i].isEmpty() && row[i].peek()[1] + grid[i][row[i].peek()[1]] < j) {
                    row[i].poll();
                }
                if (!row[i].isEmpty()) {
                    dist[i][j] = update(dist[i][j], dist[i][row[i].peek()[1]] + 1);
                }

                while (!col[j].isEmpty() && col[j].peek()[1] + grid[col[j].peek()[1]][j] < i) {
                    col[j].poll();
                }
                if (!col[j].isEmpty()) {
                    dist[i][j] = update(dist[i][j], dist[col[j].peek()[1]][j] + 1);
                }

                if (dist[i][j] != -1) {
                    row[i].offer(new int[]{dist[i][j], j});
                    col[j].offer(new int[]{dist[i][j], i});
                }
            }
        }

        return dist[m - 1][n - 1];
    }

    public int update(int x, int y) {
        return x == -1 || y < x ? y : x;
    }

    /**
     * @Description:
     * 1. 常规思路是, dp, 需要到达 i, j, 可以通过向右 / 向下移动得到
     * 向右移动: [i, k] 移动到 [i, j], 尝试使用 Dijkstra 算法
     * 2. 算法超时, 需要优化时间复杂度, case: 1067/1074
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/09/04 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dist = new int[m][n]; // 从 [0,0] 到达 [i,j] 的最小移动次数
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], -1);
        }
        dist[0][0] = 1; // 当前位于 [0, 0] 处, 计算的是经过的格子数量
        // 创建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2])); // [i, j, dist]
        pq.offer(new int[]{0, 0, 1});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int i = cur[0], j = cur[1], d = cur[2];
            if (i == m - 1 && j == n - 1) {
                return d;
            }
            for (int k = j + 1; k <= Math.min(n - 1, j + grid[i][j]); k++) {
                if (dist[i][k] == -1 || dist[i][k] > d + 1) {
                    dist[i][k] = d + 1;
                    pq.offer(new int[]{i, k, d + 1});
                }
            }
            for (int k = i + 1; k <= Math.min(m - 1, i + grid[i][j]); k++) {
                if (dist[k][j] == -1 || dist[k][j] > d + 1) {
                    dist[k][j] = d + 1;
                    pq.offer(new int[]{k, j, d + 1});
                }
            }
        }

        return -1;
    }

}
