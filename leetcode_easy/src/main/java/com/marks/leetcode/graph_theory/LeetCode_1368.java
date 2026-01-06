package com.marks.leetcode.graph_theory;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1368 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/5 9:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1368 {

    /**
     * @Description:
     * 给你一个 m x n 的网格图 grid 。 grid 中每个格子都有一个数字，对应着从该格子出发下一步走的方向。
     * grid[i][j] 中的数字可能为以下几种情况：
     *
     * 1 ，下一步往右走，也就是你会从 grid[i][j] 走到 grid[i][j + 1]
     * 2 ，下一步往左走，也就是你会从 grid[i][j] 走到 grid[i][j - 1]
     * 3 ，下一步往下走，也就是你会从 grid[i][j] 走到 grid[i + 1][j]
     * 4 ，下一步往上走，也就是你会从 grid[i][j] 走到 grid[i - 1][j]
     * 注意网格图中可能会有 无效数字 ，因为它们可能指向 grid 以外的区域。
     *
     * 一开始，你会从最左上角的格子 (0,0) 出发。我们定义一条 有效路径 为从格子 (0,0) 出发，每一步都顺着数字对应方向走，最终在最右下角的格子 (m - 1, n - 1) 结束的路径。有效路径 不需要是最短路径 。
     *
     * 你可以花费 cost = 1 的代价修改一个格子中的数字，但每个格子中的数字 只能修改一次 。
     *
     * 请你返回让网格图至少有一条有效路径的最小代价。
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/01/05 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int[][] grid) {
        int result;
//        result = method_01(grid);
        result = method_02(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官方题解后, 使用dijkstra算法
     * AC: 24ms/46.44MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/01/05 10:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // 初始化
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = Integer.MAX_VALUE / 2;
            }
        }
        dp[0][0] = 0;
        // 基于优先队列的迪杰斯特拉算法
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        queue.offer(new int[]{dp[0][0], 0, 0});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int i = cur[1];
            int j = cur[2];
            for (int[] dir : dirs) {
                int nextI = i + dir[0];
                int nextJ = j + dir[1];
                if (nextI < 0 || nextI >= m || nextJ < 0 || nextJ >= n) {
                    continue;
                }
                int cost = dp[i][j] + getCost(grid[i][j], dir);
                if (cost < dp[nextI][nextJ]) {
                    dp[nextI][nextJ] = cost;
                    queue.offer(new int[]{dp[nextI][nextJ], nextI, nextJ});
                }
            }
        }

        return dp[m - 1][n - 1];
    }



    /**
     * @Description:
     * 1. 考虑使用回溯法 + 深度优先搜索
     * 2. 创建一个二维数组，记录每个格子是否被访问过, 并且记录当前格子到最右下角的最小代价
     * 3. 回溯还是超时了, 4 ^ (100 * 100) 的时间复杂度
     * TLE(time limit exceeded): 35/69
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/01/05 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int m;
    private int n;
    private int ans;
    private int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int method_01(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        ans = m + n - 1; // 不需要考虑最后一个格子, 只要到达最后一个格子即可
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;
        backTrackDFS(grid, 0, 0, 0, visited);
        return ans;
    }

    private void backTrackDFS(int[][] grid, int i, int j, int cost, boolean[][] visited) {
        if (i == m - 1 && j == n - 1) {
            // 找到最右下角
            ans = Math.min(ans, cost);
            return;
        }

        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI < 0 || nextI >= m || nextJ < 0 || nextJ >= n || visited[nextI][nextJ] || ans <= cost) {
                // 边界条件, 超出范围或者已经访问过
                continue;
            }
            visited[nextI][nextJ] = true;
            backTrackDFS(grid, nextI, nextJ, cost + getCost(grid[i][j], dir), visited);
            visited[nextI][nextJ] = false;
        }
    }

    private int getCost(int i, int[] dir) {
        if (i > 4) {
            return 1; // 需要修改
        } else {
            return dirs[i - 1][0] == dir[0] && dirs[i - 1][1] == dir[1] ? 0 : 1;
        }
    }

}
