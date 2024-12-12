package com.marks.leetcode.gird_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/10 9:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1254 {
    private boolean flag = true; // 标记位, true: 无法到达边界, false: 可以到达边界
    /**
     * @Description: [
     * 二维矩阵 grid 由 0 （土地）和 1 （水）组成。
     * 岛是由最大的4个方向连通的 0 组成的群，封闭岛是一个 完全 由1包围（左、上、右、下）的岛。
     *
     * 请返回 封闭岛屿 的数目。
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/10 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int closedIsland(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 标准的DFS网格,
     *
     * AC:2ms/43.61MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/10 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int ans = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                if (grid[i][j] == 0) {
                    flag = true;
                    dfsMaxArea(grid, i, j);
                    if (flag) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

    private void dfsMaxArea(int[][] grid, int i, int j) {
        if (!inArea(grid, i, j)) {
            flag = false;
            return;
        }
        if (grid[i][j] > 0) {
            return;
        }

        if (grid[i][j] == 0) {
            grid[i][j] = 2; // 标记已访问
            dfsMaxArea(grid, i + 1, j);
            dfsMaxArea(grid, i - 1, j);
            dfsMaxArea(grid, i, j + 1);
            dfsMaxArea(grid, i, j - 1);
        }
        return;
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
