package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/2 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1631 {
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int n;
    private int m;

    /**
     * @Description: [
     * <p> 你准备参加一场远足活动。给你一个二维 rows x columns 的地图 heights ，其中 heights[row][col] 表示格子 (row, col) 的高度。
     * 一开始你在最左上角的格子 (0, 0) ，且你希望去最右下角的格子 (rows-1, columns-1) （注意下标从 0 开始编号）。
     * 你每次可以往 上，下，左，右 四个方向之一移动，你想要找到耗费 体力 最小的一条路径。
     *
     * <p>一条路径耗费的 体力值 是路径上相邻格子之间 高度差绝对值 的 最大值 决定的。
     *
     * <p>请你返回从左上角走到右下角的最小 体力消耗值 。
     * <p>tips:
     * rows == heights.length
     * columns == heights[i].length
     * 1 <= rows, columns <= 100
     * 1 <= heights[i][j] <= 10^6
     * ]
     * @param heights
     * @return int
     * @author marks
     * @CreateDate: 2025/1/2 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumEffortPath(int[][] heights) {
        int result;
        result = method_01(heights);
        return result;
    }

    /**
     * @Description: [
     * 关键步骤是: int MaxAbs = Math.max(Math.abs(heights[x][y] - heights[next_i][next_j]), value);
     * 找到这条路径中高度差的最大值, 并且用最大值与 原本的dist[i][j] 进行比较
     * AC:68ms/44.27MB
     * ]
     * @param heights
     * @return int
     * @author marks
     * @CreateDate: 2025/1/2 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] heights) {
        final int INF = Integer.MAX_VALUE / 2;
        n = heights.length;
        m = heights[0].length;
        int[][] dist = new int[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        dist[0][0] = 0;
        queue.offer(new int[] {0, 0, 0}); // 下表1, 2表示坐标
        while (!queue.isEmpty()) {
            int[] cur_node = queue.poll();
            int value = cur_node[0]; // 相邻的高度相差的绝对值
            int x = cur_node[1];
            int y = cur_node[2];
            if (x == n - 1 && y == m - 1) {
                break;
            }
            if (dist[x][y] < value) {
                continue;
            }
            for (int[] dir : dirs) {
                int next_i = x + dir[0];
                int next_j = y + dir[1];
                if (inArea(next_i, next_j)) {
                    int MaxAbs = Math.max(Math.abs(heights[x][y] - heights[next_i][next_j]), value);
                    if (MaxAbs < dist[next_i][next_j]) {
                        dist[next_i][next_j] = Math.min(dist[next_i][next_j], MaxAbs);
                        queue.offer(new int[] {dist[next_i][next_j], next_i, next_j});
                    }

                }
            }
        }
        return dist[n - 1][m - 1];
    }

    private boolean inArea(int i, int j) {
        if (i >= 0 && j >= 0 && i < n && j < m) {
            return true;
        }
        return false;
    }
}
