package com.marks.leetcode.graph_theory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2577 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/16 15:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2577 {

    /**
     * @Description:
     * 给你一个 m x n 的矩阵 grid ，每个元素都为 非负 整数，其中 grid[row][col] 表示可以访问格子 (row, col) 的 最早 时间。
     * 也就是说当你访问格子 (row, col) 时，最少已经经过的时间为 grid[row][col] 。
     * 你从 最左上角 出发，出发时刻为 0 ，你必须一直移动到上下左右相邻四个格子中的 任意 一个格子（即不能停留在格子上）。每次移动都需要花费 1 单位时间。
     *
     * 请你返回 最早 到达右下角格子的时间，如果你无法到达右下角的格子，请你返回 -1 。
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 1000
     * 4 <= m * n <= 10^5
     * 0 <= grid[i][j] <= 10^5
     * grid[0][0] == 0
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumTime(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 什么时候返回 -1, 即无法移动的时候返回-1. 被锁死在起始点, grid[0][1] >= 2 && grid[1][0] >= 2
     * 2. 假设当前处于节点[i, j], 向右侧节点移动[i, j + 1], 假设此时的时间是 t, grid[i][j + 1] = x > t + 1; int sub;
     * 如果sub 是偶数2, x = t + 2, 那么我们能踏上[i, j + 1] 的时间是 x + 1; 如果 sub 是奇数 3, x = t + 3, 那么我们能 踏上[i, j + 1] 的时间是 x + 1
     * 3. 稀疏图使用基于优先队列的Dijkstra算法 int[] curr = {i, j, t}
     * 4. 他不一定是这样来回的走动
     * AC: 256ms/94MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        final int INF = Integer.MAX_VALUE / 2;
        int m = grid.length;
        int n = grid[0].length;
        if (grid[0][1] >= 2 && grid[1][0] >= 2) {
            return -1;
        }
        int[][] dist = new int[m][n];
        // 对 dist 初始化
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], INF);
        }
        dist[0][0] = 0;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 4个方向
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        pq.offer(new int[]{0, 0, grid[0][0]});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int i = curr[0], j = curr[1], t = curr[2];
            if (i == m - 1 && j == n - 1) {
                return t;
            }
            for (int[] dir : dirs) {
                int x = i + dir[0];
                int y = j + dir[1];
                if (x < 0 || x >= m || y < 0 || y >= n) {
                    continue;
                }
                if (t + 1 < dist[x][y]) {
                    // 将上面两者情况合并
                    if (grid[x][y] <= t + 1) {
                        dist[x][y] = t + 1;
                    } else if (grid[x][y] > t + 1) {
                        // 4 -> 7 和 5 -> 7 区别
                        int sub = grid[x][y] - t;
                        if (sub % 2 == 0) {
                            dist[x][y] = grid[x][y] + 1;
                        } else {
                            dist[x][y] = grid[x][y];
                        }
                    }
                    pq.offer(new int[]{x, y, dist[x][y]});
                }
            }
        }
        return 0;
    }

}
