package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/22 15:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2556 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的 m x n 二进制 矩阵 grid 。
     * 你可以从一个格子 (row, col) 移动到格子 (row + 1, col) 或者 (row, col + 1) ，前提是前往的格子值为 1 。
     * 如果从 (0, 0) 到 (m - 1, n - 1) 没有任何路径，我们称该矩阵是 不连通 的。
     *
     * 你可以翻转 最多一个 格子的值（也可以不翻转）。你 不能翻转 格子 (0, 0) 和 (m - 1, n - 1) 。
     *
     * 如果可以使矩阵不连通，请你返回 true ，否则返回 false 。
     *
     * 注意 ，翻转一个格子的值，可以使它的值从 0 变 1 ，或从 1 变 0 。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 1000
     * 1 <= m * n <= 10^5
     * grid[0][0] == grid[m - 1][n - 1] == 1
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/22 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPossibleToCutPath(int[][] grid) {
        boolean result;
        result = method_01(grid);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 1. 用深度优先搜索找到 (0,0) 到 (m - 1, n - 1) 的一条路径
     * AC: 2ms/53.84MB
     * @param grid 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/22 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        List<int[]> path = new ArrayList<>();
        if (dfs(grid, 0, 0, visited, path)) {
            for (int[] point : path) {
                int x = point[0];
                int y = point[1];
                if ((x == 0 && y == 0) || (x == m - 1 && y == n - 1)) {
                    continue;
                }
                grid[x][y] = 0;
            }
        } else {
            // 无法找到第一条路径从(0, 0) 到达 (m - 1, n - 1)
            return true;
        }
        path.clear();
        visited = new boolean[m][n];
        if (dfs(grid, 0, 0, visited, path)) {
            // 找到了第二条路径
            return false;
        } else {
            return true;
        }
    }

    private boolean dfs(int[][] grid, int i, int j, boolean[][] visited, List<int[]> path) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length ||
                visited[i][j] || grid[i][j] == 0) {
            return false;
        }

        if (i == grid.length - 1 && j == grid[0].length - 1) {
            path.add(new int[]{i, j});
            return true;
        }
        visited[i][j] = true;
        path.add(new int[]{i, j});
        if (dfs(grid, i + 1, j, visited, path) || dfs(grid, i, j + 1, visited, path)) {
            return true;
        }

        path.remove(path.size() - 1);
        return false;
    }

}
