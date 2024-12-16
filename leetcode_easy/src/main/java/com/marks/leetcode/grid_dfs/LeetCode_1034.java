package com.marks.leetcode.grid_dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/9 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1034 {
    private int[][] pre;
    /**
     * @Description: [
     * 给你一个大小为 m x n 的整数矩阵 grid ，表示一个网格。另给你三个整数 row、col 和 color 。网格中的每个值表示该位置处的网格块的颜色。
     * 如果两个方块在任意 4 个方向上相邻，则称它们 相邻 。
     * 如果两个方块具有相同的颜色且相邻，它们则属于同一个 连通分量 。
     * 连通分量的边界 是指连通分量中满足下述条件之一的所有网格块：
     *
     * 在上、下、左、右任意一个方向上与不属于同一连通分量的网格块相邻
     * 在网格的边界上（第一行/列或最后一行/列）
     * 请你使用指定颜色 color 为所有包含网格块 grid[row][col] 的 连通分量的边界 进行着色。
     *
     * 并返回最终的网格 grid 。
     * ]
     * @param grid 
     * @param row 
     * @param col 
     * @param color
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/9 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        int[][] result;
        result =  method_01(grid, row, col, color);
        return result;
    }

    /**
     * @Description: [
     * 1. 找到连通分量
     * 2. 判断是否已经遍历, 并且使用额外数据空间来存储该标记位
     * 3. 修改gird[i][j]的color
     *
     * 理解有问题
     * a: 是从grid[row][col] 开始遍历, 不需要遍历整个矩阵
     * b: 连通分量可以是一个或者以上
     * 查看题解后修改
     * AC:1ms/44.70MB
     * ]
     * @param grid
     * @param row
     * @param col
     * @param color
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/9 16:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] grid, int row, int col, int color) {
        int m = grid.length;
        int n = grid[0].length;
        int target = grid[row][col];
        pre = new int[m][n]; // 0:未遍历, 1:已遍历
        List<int[]> list = new ArrayList<>();
        dfsMaxArea(grid, row, col, target, list);

        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i);
            grid[temp[0]][temp[1]] = color;
        }
        return grid;
    }

    private void dfsMaxArea(int[][] grid, int i, int j, int target, List<int[]> list) {
        if (!inArea(grid, i, j) || grid[i][j] != target || pre[i][j] != 0) {
            return;
        }
        // 判断当前坐标是否是边界
        if (checkIsBorder(grid, i, j)) {
            list.add(new int[]{i, j});
        }
        pre[i][j] = 1; //标记已遍历
        dfsMaxArea(grid, i + 1, j, target, list);
        dfsMaxArea(grid, i, j + 1, target, list);
        dfsMaxArea(grid, i - 1, j, target, list);
        dfsMaxArea(grid, i, j - 1, target, list);
        return;
    }

    private boolean checkIsBorder(int[][] grid, int i, int j) {
        if (i == 0 || j == 0 || i == grid.length - 1 || j == grid[0].length - 1) {
            // 在网格的边界上
            return true;
        }
        // 不在边界时, 判断相邻是否全部相同
        if (grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i - 1][j] && grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j - 1]) {
            return false;
        }
        return true;
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
