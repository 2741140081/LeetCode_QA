package com.marks.leetcode.gird_dfs;

import com.marks.utils.UnionFind;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/10 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1559 {
    private int m;
    private int n;
    private int[][] pre;

    private static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    /**
     * @Description: [
     * 给你一个二维字符网格数组 grid ，大小为 m x n ，你需要检查 grid 中是否存在 相同值 形成的环。
     *
     * 一个环是一条开始和结束于同一个格子的长度 大于等于 4 的路径。对于一个给定的格子，你可以移动到它上、下、左、右四个方向相邻的格子之一，可以移动的前提是这两个格子有 相同的值 。
     *
     * 同时，你也不能回到上一次移动时所在的格子。比方说，环  (1, 1) -> (1, 2) -> (1, 1) 是不合法的，因为从 (1, 2) 移动到 (1, 1) 回到了上一次移动时的格子。
     *
     * 如果 grid 中有相同值形成的环，请你返回 true ，否则返回 false 。
     * ]
     * @param grid 
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/10 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean containsCycle(char[][] grid) {
        boolean result;
//        result = method_01(grid);
        result = method_02(grid);
        return result;
    }

    /**
     * @Description: [
     * 官方题解: 并查集
     * AC:9ms/71.68MB
     * ]
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/11 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        UnionFind uf = new UnionFind(m * n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0 && grid[i][j] == grid[i - 1][j]) {
                    if (!uf.findAndUnite(i * n + j, (i - 1) * n + j)) {
                        return true;
                    }
                }

                if (j > 0 && grid[i][j] == grid[i][j - 1]) {
                    if (!uf.findAndUnite(i * n + j, i * n + (j - 1))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @Description: [
     * AC:21ms/105.55MB
     * 查看其他人的题解, 再去看看官方的并查集
     * ]
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/12/10 17:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        pre = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pre[i][j] == 0) {
                    if (dfsMaxArea(grid, i, j, -1, -1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dfsMaxArea(char[][] grid, int i, int j, int pre_i, int pre_j) {
        pre[i][j] = 1;
        for (int[] dir : dirs) {
            int next_i = i + dir[0];
            int next_j = j + dir[1];

            if (!inArea(grid, next_i, next_j) || grid[i][j] != grid[next_i][next_j] || (next_i == pre_i && next_j == pre_j)) {
                continue;
            }
            if (pre[next_i][next_j] == 1) {
                return true;
            } else {
                if (dfsMaxArea(grid, next_i, next_j, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inArea(char[][] grid, int i, int j) {
        if (i >= 0 && i < m && j >= 0 && j < n) {
            return true;
        }
        return false;
    }
}
