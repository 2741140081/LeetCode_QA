package com.marks.leetcode.grid_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 11:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_463 {
    /**
     * @Description: [
     * 给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
     * 网格中的格子 水平和垂直 方向相连（对角线方向不相连）。
     * 整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
     *
     * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。
     * 格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int islandPerimeter(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * dfs(i + 1, j) == 0, return 1;
     *
     * AC:7ms/45.04MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int ans = 0;
        boolean flag = true;
        for (int i = 0; i < grid.length && flag; i++) {
            for (int j = 0; j < grid[0].length && flag; j++) {
                if (grid[i][j] == 1) {
                    ans = dfsGirth(grid, i, j);
                    return ans;
                }
            }
        }
        return ans;
    }

    private int dfsGirth(int[][] grid, int i, int j) {
        if (!inArea(grid, i, j)) {
            return 1; // 边界即为周长
        }
        if (grid[i][j] == 0) {
            return 1;
        } else if (grid[i][j] == -1){
            return 0;
        } else {
            grid[i][j] = -1; // 标记
            int sum = dfsGirth(grid, i + 1, j) + dfsGirth(grid, i, j + 1)
                    + dfsGirth(grid, i - 1, j) + dfsGirth(grid, i, j - 1);
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
