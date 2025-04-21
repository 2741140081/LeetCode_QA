package com.marks.leetcode.grid_dfs;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/11 15:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_827 {
    private int n;
    private int curValue = 1;
    private int preValue = 2;
    /**
     * @Description: [
     * 给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
     *
     * 返回执行此操作后，grid 中最大的岛屿面积是多少？
     *
     * 岛屿 由一组上、下、左、右四个方向相连的 1 形成。
     *
     * tips:
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 500
     * grid[i][j] 为 0 或 1
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/11 15:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestIsland(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 暴力超时!!! 真的浮夸, 明明数据量不大, 但是还是超时了
     * 想简单了, 看到500就想着暴力, 但是暴力的时间复杂度是O(n^4) = O(500^4) = 625亿最坏的情况
     * 想一想, 如果要剪枝, 那么对于grid[i][j] = 0, 判断它周围的1的数量, 我们只修改value为1及其以上的0, 将其改为1
     * 70/75 感觉后面无法完成, 待后续看官解完善
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/11 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        n = grid.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += Arrays.stream(grid[i]).sum();
        }
        if (sum == 0) {
            return 1;
        }
        int ans = maxAreaOfIsland(grid);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && checkValue(grid, i, j) >= 1) {
                    grid[i][j] = curValue;
                    int maxArea = maxAreaOfIsland(grid);
                    ans = Math.max(ans, maxArea);
                    grid[i][j] = 0;
                }
            }
        }
        return ans;
    }

    private int checkValue(int[][] grid, int i, int j) {
        int count = 0;
        if (i - 1 >= 0 && grid[i - 1][j] != 0) {
            count++;
        }
        if (i + 1 < n && grid[i + 1][j] != 0) {
            count++;
        }
        if (j - 1 >= 0 && grid[i][j - 1] != 0) {
            count++;
        }
        if (j + 1 < n && grid[i][j + 1] != 0) {
            count++;
        }
        return count;
    }

    private int maxAreaOfIsland(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == curValue) {
                    ans = Math.max(ans, dfsMaxArea(grid, i, j));
                }
            }
        }
        int tempValue = curValue;
        curValue = preValue;
        preValue = tempValue;
        return ans;
    }

    private int dfsMaxArea(int[][] grid, int i, int j) {
        if (!inArea(grid, i, j)) {
            return 0;
        }else {
            if (grid[i][j] != curValue) {
                return 0;
            } else {
                int currArea = grid[i][j];
                grid[i][j] = preValue; // 标记该网格已经被遍历, 不需要再次遍历
                return currArea + (dfsMaxArea(grid, i + 1, j)
                        + dfsMaxArea(grid, i, j + 1)
                        + dfsMaxArea(grid, i - 1, j)
                        + dfsMaxArea(grid, i, j - 1));
            }
        }
    }

    private boolean inArea(int[][] grid, int i, int j) {
        if (i >= 0 && j >= 0 && i < n && j < n) {
            return true;
        }
        return false;
    }
}
