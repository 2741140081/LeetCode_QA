package com.marks.leetcode.graph_theory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2290 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/14 10:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2290 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的二维整数数组 grid ，数组大小为 m x n 。每个单元格都是两个值之一：
     * 0 表示一个 空 单元格，
     * 1 表示一个可以移除的 障碍物 。
     * 你可以向上、下、左、右移动，从一个空单元格移动到另一个空单元格。
     * 现在你需要从左上角 (0, 0) 移动到右下角 (m - 1, n - 1) ，返回需要移除的障碍物的 最小 数目。
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/01/14 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumObstacles(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 能否用Dijkstra算法解决, 我们把障碍物抽象成为一个距离，那么最短路径就是最少的障碍物数量.
     * 2. int[][] dp = new int[m][n]; 存储(0,0) 到 (i,j) 的最短距离, 试试看
     * AC: 145ms/140MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/01/14 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        final int INF = Integer.MAX_VALUE / 2;
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // 初始化dp
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = grid[0][0];
        // 优先队列, 进行BFS, int[] = {dist, i, j};
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        queue.offer(new int[]{dp[0][0], 0, 0});

        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int dist = cur[0];
            int i = cur[1];
            int j = cur[2];
            for (int[] dir : dirs) {
                int ni = i + dir[0];
                int nj = j + dir[1];
                if (ni < 0 || ni >= m || nj < 0 || nj >= n) {
                    continue;
                }
                if (dp[ni][nj] > dist + grid[ni][nj]) {
                    dp[ni][nj] = dist + grid[ni][nj];
                    queue.offer(new int[]{dp[ni][nj], ni, nj});
                }
            }
        }
        return dp[m - 1][n - 1];
    }

}
