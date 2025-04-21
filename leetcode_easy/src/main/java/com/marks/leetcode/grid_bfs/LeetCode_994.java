package com.marks.leetcode.grid_bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/16 14:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_994 {
    private int[][] pre;
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    /**
     * @Description: [
     * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
     *
     * 值 0 代表空单元格；
     * 值 1 代表新鲜橘子；
     * 值 2 代表腐烂的橘子。
     * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
     *
     * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
     * 
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10
     * grid[i][j] 仅为 0、1 或 2
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/16 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int orangesRotting(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 多源BFS,
     * AC:2ms/41.70MB
     * ]
     * @param grid 
     * @return int
     * @author marks
     * @CreateDate: 2024/12/16 14:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        pre = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[] {i, j, 0});
                    pre[i][j] = 1;
                }
            }
        }
        int ans = -1;
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int curr_i = array[0];
            int curr_j = array[1];
            int deep = array[2];
            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];
                if (inArea(next_i, next_j, grid) && pre[next_i][next_j] == 0 && grid[next_i][next_j] == 1) {
                    grid[next_i][next_j] = 2;
                    queue.offer(new int[] {next_i, next_j, deep + 1});
                    pre[next_i][next_j] = 1;
                    ans = Math.max(ans, deep + 1);
                }
            }
        }
        boolean flag = true;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    flag = false;
                }
            }
        }
        return flag ? ans : -1;
    }

    private boolean inArea(int i, int j, int[][] mat) {
        if (i >= 0 && j >= 0 && i < mat.length && j < mat[0].length) {
            return true;
        }
        return false;
    }
}
