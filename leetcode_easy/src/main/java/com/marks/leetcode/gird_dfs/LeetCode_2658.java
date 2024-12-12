package com.marks.leetcode.gird_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2658 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始大小为 m x n 的二维整数数组 grid ，其中下标在 (r, c) 处的整数表示：
     *
     * 如果 grid[r][c] = 0 ，那么它是一块 陆地 。
     * 如果 grid[r][c] > 0 ，那么它是一块 水域 ，且包含 grid[r][c] 条鱼。
     * 一位渔夫可以从任意 水域 格子 (r, c) 出发，然后执行以下操作任意次：
     *
     * 捕捞格子 (r, c) 处所有的鱼，或者
     * 移动到相邻的 水域 格子。
     * 请你返回渔夫最优策略下， 最多 可以捕捞多少条鱼。如果没有水域格子，请你返回 0 。
     *
     * 格子 (r, c) 相邻 的格子为 (r, c + 1) ，(r, c - 1) ，(r + 1, c) 和 (r - 1, c) ，前提是相邻格子在网格图内。
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMaxFish(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * DFS 求最大面积
     *
     * AC:3ms/44.14MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/9 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > 0) {
                    ans = Math.max(ans, dfsMaxArea(grid, i, j));
                }
            }
        }
        return ans;
    }

    private int dfsMaxArea(int[][] grid, int i, int j) {
        if (!inArea(grid, i, j) || grid[i][j] <= 0) {
            return 0;
        }
        int sum = grid[i][j];
        grid[i][j] = -1; // 标记
        sum += dfsMaxArea(grid, i + 1, j) + dfsMaxArea(grid, i, j + 1)
                + dfsMaxArea(grid, i - 1, j) + dfsMaxArea(grid, i, j - 1);
        return sum;
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }

}
