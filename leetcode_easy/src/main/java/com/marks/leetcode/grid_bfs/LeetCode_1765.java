package com.marks.leetcode.grid_bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/16 16:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1765 {
    private int[][] pre;
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    /**
     * @Description: [
     * 给你一个大小为 m x n 的整数矩阵 isWater ，它代表了一个由 陆地 和 水域 单元格组成的地图。
     *
     * 如果 isWater[i][j] == 0 ，格子 (i, j) 是一个 陆地 格子。
     * 如果 isWater[i][j] == 1 ，格子 (i, j) 是一个 水域 格子。
     * 你需要按照如下规则给每个单元格安排高度：
     *
     * 每个格子的高度都必须是非负的。
     * 如果一个格子是 水域 ，那么它的高度必须为 0 。
     * 任意相邻的格子高度差 至多 为 1 。当两个格子在正东、南、西、北方向上相互紧挨着，就称它们为相邻的格子。（也就是说它们有一条公共边）
     * 找到一种安排高度的方案，使得矩阵中的最高高度值 最大 。
     *
     * 请你返回一个大小为 m x n 的整数矩阵 height ，其中 height[i][j] 是格子 (i, j) 的高度。如果有多种解法，请返回 任意一个 。
     *
     * ]
     * @param isWater 
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/16 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] highestPeak(int[][] isWater) {
        int[][] result;
        result = method_01(isWater);
        return result;
    }

    /**
     * @Description: [
     * isWater[i][j] == 0 ，格子 (i, j) 是一个 陆地 格子。
     * isWater[i][j] == 1 ，格子 (i, j) 是一个 水域 格子。
     * 同样是多源BFS,
     * AC:66ms/165.79MB
     * ]
     * @param isWater
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/16 16:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        pre = new int[m][n];
        int[][] ans = new int[m][n];

        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    queue.offer(new int[] {i, j, 0});
                    pre[i][j] = 1;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int curr_i = array[0];
            int curr_j = array[1];
            int high = array[2];
            ans[curr_i][curr_j] = high;
            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];

                if (inArea(next_i, next_j, isWater) && pre[next_i][next_j] == 0) {
                    queue.offer(new int[] {next_i, next_j, high + 1});
                    pre[next_i][next_j] = 1;
                }
            }
        }
        return ans;
    }

    private boolean inArea(int i, int j, int[][] mat) {
        if (i >= 0 && j >= 0 && i < mat.length && j < mat[0].length) {
            return true;
        }
        return false;
    }
}
