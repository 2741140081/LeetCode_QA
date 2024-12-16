package com.marks.leetcode.grid_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 9:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_695 {
    /**
     * @Description: [
     * 给你一个大小为 m x n 的二进制矩阵 grid 。
     * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都被 0（代表水）包围着。
     * 岛屿的面积是岛上值为 1 的单元格的数目。
     * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxAreaOfIsland(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * ans = Math.max(ans, dfs(grid, i , j))
     *
     * dfs(grid, i, j)
     * grid[i][j] + grid[i + 1][j] + grid[i][j + 1] + grid[i - 1][j] + grid[i][j - 1];
     *
     * AC:2ms/43.57MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    ans = Math.max(ans, dfsMaxArea(grid, i, j));
                }
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 深度优先遍历, DFS,
     * 假设全部为 "1" 的grid中, 深度体现在(0, 0) -> (1, 0) -> (2, 0) -> (3, 0) -> ... (n - 1, 0)
     * ]
     * @param grid
     * @param i
     * @param j
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 10:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int dfsMaxArea(int[][] grid, int i, int j) {
        if (!inArea(grid, i, j)) {
            return 0;
        }else {
            if (grid[i][j] != 1) {
                return 0;
            } else {
                // grid[i][j] == 1
                int currArea = grid[i][j];
                grid[i][j] = 2; // 标记该网格已经被遍历, 不需要再次遍历
                return currArea + (dfsMaxArea(grid, i + 1, j)
                        + dfsMaxArea(grid, i, j + 1)
                        + dfsMaxArea(grid, i - 1, j)
                        + dfsMaxArea(grid, i, j - 1));
            }
        }
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
