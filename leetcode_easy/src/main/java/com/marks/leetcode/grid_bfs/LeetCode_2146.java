package com.marks.leetcode.grid_bfs;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/16 17:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2146 {
    private int[][] pre;
    private int[][] dirs = {{-1, 0},  {0, -1}, {0, 1}, {1, 0}};
    /**
     * @Description: [
     * 给你一个下标从 0 开始的二维整数数组 grid ，它的大小为 m x n ，表示一个商店中物品的分布图。数组中的整数含义为：
     *
     * 0 表示无法穿越的一堵墙。
     * 1 表示可以自由通过的一个空格子。
     * 所有其他正整数表示该格子内的一样物品的价格。你可以自由经过这些格子。
     * 从一个格子走到上下左右相邻格子花费 1 步。
     *
     * 同时给你一个整数数组 pricing 和 start ，其中 pricing = [low, high] 且 start = [row, col] ，表示你开始位置为 (row, col) ，同时你只对物品价格在 闭区间 [low, high] 之内的物品感兴趣。同时给你一个整数 k 。
     *
     * 你想知道给定范围 内 且 排名最高 的 k 件物品的 位置 。排名按照优先级从高到低的以下规则制定：
     *
     * 距离：定义为从 start 到一件物品的最短路径需要的步数（较近 距离的排名更高）。
     * 价格：较低 价格的物品有更高优先级，但只考虑在给定范围之内的价格。
     * 行坐标：较小 行坐标的有更高优先级。
     * 列坐标：较小 列坐标的有更高优先级。
     * 请你返回给定价格内排名最高的 k 件物品的坐标，将它们按照排名排序后返回。如果给定价格内少于 k 件物品，那么请将它们的坐标 全部 返回。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 0 <= grid[i][j] <= 10^5
     * pricing.length == 2
     * 2 <= low <= high <= 10^5
     * start.length == 2
     * 0 <= row <= m - 1
     * 0 <= col <= n - 1
     * grid[row][col] > 0
     * 1 <= k <= m * n
     * ]
     * @param grid
     * @param pricing
     * @param start
     * @param k
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/16 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public List<List<Integer>> highestRankedKItems(int[][] grid, int[] pricing, int[] start, int k) {
        List<List<Integer>> result;
        result = method_01(grid, pricing, start, k);
        return result;
    }

    /**
     * @Description: [
     * [
     * [2,0,2],
     * [4,5,3],
     * [2,0,2]
     * ]
     *
     * pricing = [2,5]
     *
     * start = [1,1]
     *
     * 这题主要就是自定义比较器 new Comparator<int[]>() {
     *             @Override
     *             public int compare(int[] o1, int[] o2) {
     *                 for (int i = 0; i < 4; i++) {
     *                     if (o1[i] != o2[i]) {
     *                         return o1[i] - o2[i];
     *                     }
     *                 }
     *                 return 0;
     *             }
     * }
     *
     * 按照题目要求, 将前4个元素进行升序排序
     *
     * AC:140ms/79.96MB
     * ]
     * @param grid
     * @param pricing
     * @param start
     * @param k
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/12/16 18:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[][] grid, int[] pricing, int[] start, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int low = pricing[0];
        int high = pricing[1];
        pre = new int[m][n];
        List<List<Integer>> ans = new ArrayList<>();
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            for (int i = 0; i < 4; i++) {
                if (o1[i] != o2[i]) {
                    return o1[i] - o2[i];
                }
            }
            return 0;
        });

        queue.offer(new int[] {0, grid[start[0]][start[1]], start[0], start[1]});
        while (!queue.isEmpty() && ans.size() < k) {
            int[] array = queue.poll();
            int deep = array[0];
            int value = array[1];
            int curr_i = array[2];
            int curr_j = array[3];
            if (value >= low && value <= high) {
                List<Integer> list = new ArrayList<>();
                list.add(curr_i);
                list.add(curr_j);
                ans.add(list);
            }
            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];
                if (inArea(next_i, next_j, grid) && grid[next_i][next_j] != 0 && pre[next_i][next_j] == 0) {
                    queue.offer(new int[] {deep + 1, grid[next_i][next_j], next_i, next_j});
                    pre[next_i][next_j] = 1;
                }
            }
        }
        return ans;
    }

    private boolean inArea(int i, int j, int[][] grid) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
