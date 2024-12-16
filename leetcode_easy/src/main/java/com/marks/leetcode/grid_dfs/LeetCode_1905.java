package com.marks.leetcode.grid_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/10 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1905 {
    private boolean flag = true; // 标记grid2[i][j] 是否是grid1[i][j] 的子岛屿
    private int m;
    private int n;
    private int[][] pre;
    /**
     * @Description: [
     * 给你两个 m x n 的二进制矩阵 grid1 和 grid2 ，它们只包含 0 （表示水域）和 1 （表示陆地）。
     * 一个 岛屿 是由 四个方向 （水平或者竖直）上相邻的 1 组成的区域。任何矩阵以外的区域都视为水域。
     *
     * 如果 grid2 的一个岛屿，被 grid1 的一个岛屿 完全 包含，也就是说 grid2 中该岛屿的每一个格子都被 grid1 中同一个岛屿完全包含，
     * 那么我们称 grid2 中的这个岛屿为 子岛屿 。
     *
     * 请你返回 grid2 中 子岛屿 的 数目 。
     * ]
     * @param grid1 
     * @param grid2
     * @return int
     * @author marks
     * @CreateDate: 2024/12/10 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int result;
        result = method_01(grid1, grid2);
        return result;
    }

    /**
     * @Description: [
     * 标准DFS模板, 需要额外pre[][] 判断是否访问过grid2, flag 判断是否gird2岛屿是否是grid1岛屿的子岛屿
     * AC:29ms/85.91MB
     * ]
     * @param grid1
     * @param grid2
     * @return int
     * @author marks
     * @CreateDate: 2024/12/10 11:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid1, int[][] grid2) {
        m = grid1.length;
        n = grid1[0].length;
        pre = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1 && pre[i][j] == 0) {
                    flag = grid1[i][j] == grid2[i][j];
                    dfsMaxArea(grid1, grid2, i, j);
                    if (flag) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

    private void dfsMaxArea(int[][] grid1, int[][] grid2, int i, int j) {
        if (!inArea(grid2, i, j) || pre[i][j] != 0 || grid2[i][j] != 1) {
            return;
        }
        if (grid2[i][j] == 1) {
            if (grid2[i][j] != grid1[i][j]) {
                flag = false;
            }
            pre[i][j] = 1; // 标记
            dfsMaxArea(grid1, grid2, i + 1, j);
            dfsMaxArea(grid1, grid2, i - 1, j);
            dfsMaxArea(grid1, grid2, i, j + 1);
            dfsMaxArea(grid1, grid2, i, j - 1);
        }
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
