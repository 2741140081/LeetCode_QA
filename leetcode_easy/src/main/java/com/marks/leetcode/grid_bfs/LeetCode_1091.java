package com.marks.leetcode.grid_bfs;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/13 14:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1091 {
    private int n;
    private int[][] pre;

    /**
     * @Description: [
     * 给你一个 n x n 的二进制矩阵 grid 中，返回矩阵中最短 畅通路径 的长度。如果不存在这样的路径，返回 -1 。
     *
     * 二进制矩阵中的 畅通路径 是一条从 左上角 单元格（即，(0, 0)）到 右下角 单元格（即，(n - 1, n - 1)）的路径，该路径同时满足下述要求：
     *
     * 路径途经的所有单元格的值都是 0 。
     * 路径中所有相邻的单元格应当在 8 个方向之一 上连通（即，相邻两单元之间彼此不同且共享一条边或者一个角）。
     * 畅通路径的长度 是该路径途经的单元格总数。
     *
     * tips:
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 100
     * grid[i][j] 为 0 或 1
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/13 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shortestPathBinaryMatrix(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 标准BFS模板图
     * AC:14ms/44.63MB
     * ]
     * @param grid 
     * @return int
     * @author marks
     * @CreateDate: 2024/12/13 14:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        Deque<int[]> queue = new LinkedList<>();
        n = grid.length;
        pre = new int[n][n];
        int ans = -1;
        if (grid[0][0] == 0) {
            queue.addFirst(new int[]{0, 0, 1});
            pre[0][0] = 1;
        }

        while (!queue.isEmpty()) {
            int[] array = queue.pollLast();
            int curr_i = array[0];
            int curr_j = array[1];
            int deep = array[2];
            if (curr_i == n - 1 && curr_j == n - 1) {
                return deep;
            }
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    int next_i = curr_i + i;
                    int next_j = curr_j + j;
                    if (inArea(next_i, next_j, grid) && pre[next_i][next_j] == 0 && grid[next_i][next_j] == 0) {
                        queue.addFirst(new int[] {next_i, next_j, deep + 1});
                        pre[next_i][next_j] = 1; // 标记已遍历和添加
                    }
                }
            }
        }

        return ans;
    }

    private boolean inArea(int i, int j, int[][] grid) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid.length) {
            return true;
        }
        return false;
    }
}
