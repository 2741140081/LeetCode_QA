package com.marks.leetcode.graph_theory;

import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3286 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3286 {

    /**
     * @Description:
     * 给你一个 m x n 的二进制矩形 grid 和一个整数 health 表示你的健康值。
     * 你开始于矩形的左上角 (0, 0) ，你的目标是矩形的右下角 (m - 1, n - 1) 。
     * 你可以在矩形中往上下左右相邻格子移动，但前提是你的健康值始终是 正数 。
     * 对于格子 (i, j) ，如果 grid[i][j] = 1 ，那么这个格子视为 不安全 的，会使你的健康值减少 1 。
     * 如果你可以到达最终的格子，请你返回 true ，否则返回 false 。
     * 注意 ，当你在最终格子的时候，你的健康值也必须为 正数 。
     * @param: grid
     * @param: health
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/09 10:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {
        boolean result;
        result = method_01(grid, health);
        return result;
    }

    /**
     * @Description:
     * 1. 可以使用 Dijkstra 算法, 使用矩阵记录从0.0 节点到 i, j 节点剩余最大的健康值.
     * AC: 14ms/46.29MB
     * @param: grid
     * @param: health
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/09 10:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(List<List<Integer>> grid, int health) {
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] dp = new int[m][n]; // 创建二维数组，用于记录从0.0 节点到 i, j 节点剩余最大的健康值
        dp[0][0] = health - grid.get(0).get(0); // 需要减去初始点的值
        boolean[][] visited = new boolean[m][n]; // 创建一个二维数组, 用于记录节点是否被访问过
        // 使用优先队列
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> b[2] - a[2]); // 用大根堆, 真的服了
        queue.offer(new int[]{0, 0, dp[0][0]});
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int i = cur[0];
            int j = cur[1];
            int curHealth = cur[2];
            if (i == m - 1 && j == n - 1) {
                return true;
            }
            if (visited[i][j]) {
                continue;
            }
            visited[i][j] = true;
            for (int[] dir : dirs) {
                int newI = i + dir[0];
                int newJ = j + dir[1];
                if (newI >= 0 && newI < m && newJ >= 0 && newJ < n && !visited[newI][newJ]) {
                    int newHealth = curHealth - grid.get(newI).get(newJ);
                    if (newHealth > 0 && newHealth > dp[newI][newJ]) {
                        dp[newI][newJ] = newHealth;
                        queue.offer(new int[]{newI, newJ, newHealth});
                    }
                }
            }
        }

        return false;
    }

}
