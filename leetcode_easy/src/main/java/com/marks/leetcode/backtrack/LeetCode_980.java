package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/29 14:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_980 {

    /**
     * @Description:
     * 在二维网格 grid 上，有 4 种类型的方格：
     *
     * 1 表示起始方格。且只有一个起始方格。
     * 2 表示结束方格，且只有一个结束方格。
     * 0 表示我们可以走过的空方格。
     * -1 表示我们无法跨越的障碍。
     * 返回在四个方向（上、下、左、右）上行走时，从起始方格到结束方格的不同路径的数目。
     *
     * 每一个无障碍方格都要通过一次，但是一条路径中不能重复通过同一个方格。
     *
     * tips:
     * 1 <= grid.length * grid[0].length <= 20
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/8/29 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int uniquePathsIII(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：grid = [
     * [1,0,0,0],
     * [0,0,0,0],
     * [0,0,2,-1]
     * ]
     * 输出：2
     * 1. 每一个无障碍方格都要通过一次，但一条路径中不能重复通过同一个方格。需要一个方法验证当我们到达终点时, 是否所有的无障碍方格都被访问过
     * 2. 基于递归和回溯, 对于每个访问过的方格, grid[i][j] = -1, 回溯后恢复grid[i][j] = 0
     * 3. 递归终止条件: 访问过的方格为终点方格, 遍历 grid[][], 任意 grid[i][j] != 0, 则返回 true，否则返回 false, validGraph(grid)
     * 4. 通过记录sum 值来判断是否所有无障碍方格都被访问过, 当所有的无障碍方格都被访问过时, 此时的值为 m * n * (-1) + 2 + 3 =5 - m * n;
     * 按照E1的案例, sumEnd = -7, 这样多了一步计算, 但是由于之前有错误判断, 即判断 grid[nextX][nextY] == 0, 这个逻辑是错误的,
     * 导致无法到达终点, 因为终点 grid[endx][endy] == 2。现在直接记录非障碍物格子数量, 包括起始点和终点, 没经过一个格子, 将sum - 1,
     * 当经过所有的非障碍物格子, sum == 0。
     * just do it!
     * AC: 1ms/40.02MB
     * @param grid 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/29 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int endx, endy;

    private int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int method_01(int[][] grid) {
        ans = 0;
        int startx = 0, starty = 0;
        int sum = 0; // 记录grid[][] 所有方格之和
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    startx = i;
                    starty = j;
                }
                if (grid[i][j] == 2) {
                    endx = i;
                    endy = j;
                }
                if (grid[i][j] != -1) {
                    // 记录非障碍方格的数量, 包括起始点和终点
                    sum++;
                }
            }
        }
        grid[startx][starty] = -1;
        backtrack(grid, startx, starty, sum - 1);
        return ans;
    }

    private void backtrack(int[][] grid, int startX, int startY, int sum) {
        if (startX == endx && startY == endy) {
            if (sum == 0) {
                ans++;
            }
            return;
        }
        for (int[] dir : dirs) {
            int nextX = startX + dir[0];
            int nextY = startY + dir[1];
            if (nextX >= 0 && nextX < grid.length && nextY >= 0 && nextY < grid[0].length && grid[nextX][nextY] != -1) {
                int temp = grid[nextX][nextY];
                grid[startX][startY] = -1;
                backtrack(grid, nextX, nextY, sum - 1);
                grid[startX][startY] = temp;
            }
        }
    }

    private boolean validGraph(int[][] grid) {
        boolean flag = true;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return flag;
    }

}
