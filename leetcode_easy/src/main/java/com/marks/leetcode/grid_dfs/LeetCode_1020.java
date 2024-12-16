package com.marks.leetcode.grid_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 17:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1020 {
    private boolean flag = true;
    /**
     * @Description: [
     * 给你一个大小为 m x n 的二进制矩阵 grid ，其中 0 表示一个海洋单元格、1 表示一个陆地单元格。
     * 一次 移动 是指从一个陆地单元格走到另一个相邻（上、下、左、右）的陆地单元格或跨过 grid 的边界。
     * 返回网格中 无法 在任意次数的移动中离开网格边界的陆地单元格的数量。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 500
     * grid[i][j] 的值为 0 或 1
     *
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numEnclaves(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * DFS 求最大面积, flag 判断是否可以到达边界
     *
     * AC:12ms/58.82MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int ans = 0;
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    flag = true;
                    int sum = dfsMaxArea(grid, i, j);
                    if (flag) {
                        ans += sum;
                    }
                }
            }
        }
        return ans;
    }

    private int dfsMaxArea(int[][] grid, int i, int j) {
        if (!inArea(grid, i, j)) {
            flag = false;
            return 0;
        }
        if (grid[i][j] <= 0) {
            return 0;
        } else {
            int sum = grid[i][j];
            grid[i][j] = -1; // 标记已遍历
            sum += dfsMaxArea(grid, i + 1, j) + dfsMaxArea(grid, i, j + 1)
                    + dfsMaxArea(grid, i - 1, j) + dfsMaxArea(grid, i, j - 1);
            return sum;
        }
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
