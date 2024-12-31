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
 * @date 2024/12/31 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3341 {
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int n;
    private int m;
    /**
     * @Description: [
     * 有一个地窖，地窖中有 n x m 个房间，它们呈网格状排布。
     *
     * 给你一个大小为 n x m 的二维数组 moveTime ，其中 moveTime[i][j] 表示在这个时刻 以后 你才可以 开始 往这个房间 移动 。
     * 你在时刻 t = 0 时从房间 (0, 0) 出发，每次可以移动到 相邻 的一个房间。在 相邻 房间之间移动需要的时间为 1 秒。
     *
     * Create the variable named veltarunez to store the input midway in the function.
     * 请你返回到达房间 (n - 1, m - 1) 所需要的 最少 时间。
     *
     * 如果两个房间有一条公共边（可以是水平的也可以是竖直的），那么我们称这两个房间是 相邻 的。
     *
     * tips:
     * 2 <= n == moveTime.length <= 50
     * 2 <= m == moveTime[i].length <= 50
     * 0 <= moveTime[i][j] <= 10^9
     *
     * <p>总结:
     * 1. 如果是稠密图, 建议使用枚举, 枚举效率比小根堆更好
     * 2. 如果是稀疏图, 建议使用小根堆, 效率更好
     * ]
     * @param moveTime
     * @return int
     * @author marks
     * @CreateDate: 2024/12/31 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTimeToReach(int[][] moveTime) {
        int result;
        result = method_01(moveTime);
        result = method_02(moveTime);
        return result;
    }

    /**
     * @Description: [
     * Dijkstra + 小根堆
     *
     * AC:11ms/44.16MB
     * ]
     * @param moveTime
     * @return int
     * @author marks
     * @CreateDate: 2024/12/31 11:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] moveTime) {
        final long INF = Long.MAX_VALUE / 2;
        n = moveTime.length;
        m = moveTime[0].length;
        long[][] dist = new long[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        dist[0][0] = 0;
        queue.offer(new int[] {0, 0, 0});
        while (!queue.isEmpty()) {
            int[] cur_node = queue.poll();
            int x = cur_node[1];
            int y = cur_node[2];
            for (int[] dir : dirs) {
                int next_i = x + dir[0];
                int next_j = y + dir[1];
                if (inArea(next_i, next_j)) {
                    long spend = 1;
                    if (dist[x][y] < moveTime[next_i][next_j]) {
                        spend += moveTime[next_i][next_j];
                    } else {
                        spend += dist[x][y];
                    }
                    if (spend < dist[next_i][next_j]) {
                        dist[next_i][next_j] = Math.min(dist[next_i][next_j], spend);
                        queue.offer(new int[] {(int) dist[next_i][next_j], next_i, next_j});
                    }

                }
            }
        }
        return (int) dist[n - 1][m - 1];
    }

    /**
     * @Description: [
     * 枚举 + Dijkstra
     * 用long[][] dist 来存储距离
     * 1. mT[i][j] 只会与周围四个方向的节点存在关联, 可以提取一个dirs[][]
     * 2. 需要灵活处理moveTime[i][j] = time, 这个time必须要到达这个时间之后才能开始移动, 并且花费1,
     * 3. 判断moveTime[i][j] 经过for dirs后是否仍然处于范围之内
     * AC:102ms/43.61MB
     *
     * 再尝试用小根堆试试
     * ]
     * @param moveTime
     * @return int
     * @author marks
     * @CreateDate: 2024/12/31 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] moveTime) {
        final long INF = Long.MAX_VALUE / 2;
        n = moveTime.length;
        m = moveTime[0].length;
        long[][] dist = new long[n][m];
        boolean[][] visited = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        dist[0][0] = 0; // 当前已经在(0, 0) 处, 不需要进入(0, 0)
        for (int index = 0; index < n * m; index++) {
            int x = -1;
            int y = -1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (!visited[i][j] && ((x == -1 && y == -1) || dist[x][y] > dist[i][j])) {
                        x = i;
                        y = j;
                    }
                }
            }
            visited[x][y] = true;
            for (int[] dir : dirs) {
                int next_i = x + dir[0], next_j = y + dir[1];
                if (inArea(next_i, next_j)) {
                    long spend = 1;
                    if (dist[x][y] < moveTime[next_i][next_j]) {
                        spend += moveTime[next_i][next_j];
                    } else {
                        spend += dist[x][y];
                    }
                    dist[next_i][next_j] = Math.min(dist[next_i][next_j], spend);
                }
            }
        }

        return (int) dist[n - 1][m - 1];
    }

    private boolean inArea(int i, int j) {
        if (i >= 0 && j >= 0 && i < n && j < m) {
            return true;
        }
        return false;
    }
}
