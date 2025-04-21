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
 * @date 2024/12/16 16:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_934 {
    private int[][] pre;
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private Queue<int[]> queue = new LinkedList<>();
    /**
     * @Description: [
     * 给你一个大小为 n x n 的二元矩阵 grid ，其中 1 表示陆地，0 表示水域。
     * 岛 是由四面相连的 1 形成的一个最大组，即不会与非组内的任何其他 1 相连。grid 中 恰好存在两座岛 。
     * 你可以将任意数量的 0 变为 1 ，以使两座岛连接起来，变成 一座岛 。
     * 返回必须翻转的 0 的最小数目。
     *
     * tips:
     * n == grid.length == grid[i].length
     * 2 <= n <= 100
     * grid[i][j] 为 0 或 1
     * grid 中恰有两个岛
     *
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/16 16:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shortestBridge(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 多源BFS, 但是我用DFS来找岛屿的所有坐标, 并且将坐标添加到队列中
     * AC:9ms/44.68MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/16 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int n = grid.length;
        pre = new int[n][n];
        for (int i = 0; i < n; i++) {
            if (!queue.isEmpty()) {
                break;
            }
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j, 0});
                    DFSMaxArea(grid, i, j);
                    break;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int curr_i = array[0];
            int curr_j = array[1];
            int deep = array[2]; // 表示需要翻转0的数量
            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];
                if (inArea(next_i, next_j, grid)) {
                    if ( grid[next_i][next_j] == 1 && pre[next_i][next_j] == 0) {
                        return deep;
                    }

                    if (grid[next_i][next_j] == 0 && pre[next_i][next_j] == 0) {
                        pre[next_i][next_j] = 1;
                        queue.offer(new int[] {next_i, next_j, deep + 1});
                    }
                }
            }
        }
        return 0;
    }

    private void DFSMaxArea(int[][] grid, int i, int j) {
        if (!inArea(i, j, grid) || pre[i][j] == 1 || grid[i][j] == 0) {
            return;
        }
        queue.offer(new int[]{i, j, 0});
        pre[i][j] = 1;
        for (int[] dir : dirs) {
            int next_i = i + dir[0];
            int next_j = j + dir[1];
            DFSMaxArea(grid, next_i, next_j);
        }
    }

    private boolean inArea(int i, int j, int[][] grid) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
