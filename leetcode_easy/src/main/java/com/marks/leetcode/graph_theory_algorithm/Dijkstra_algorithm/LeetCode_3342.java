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
 * @date 2025/1/2 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3342 {
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int n;
    private int m;
    
    /**
     * @Description: [
     * <p>有一个地窖，地窖中有 n x m 个房间，它们呈网格状排布。
     *
     * <p>给你一个大小为 n x m 的二维数组 moveTime ，其中 moveTime[i][j] 表示在这个时刻 以后 你才可以 开始 往这个房间 移动 。
     * <p>你在时刻 t = 0 时从房间 (0, 0) 出发，每次可以移动到 相邻 的一个房间。在 相邻 房间之间移动需要的时间为：第一次花费 1 秒，第二次花费 2 秒，第三次花费 1 秒，第四次花费 2 秒……如此 往复 。
     *
     * <p>请你返回到达房间 (n - 1, m - 1) 所需要的 最少 时间。
     *
     * 如果两个房间有一条公共边（可以是水平的也可以是竖直的），那么我们称这两个房间是 相邻 的。
     *
     * <p>tips:
     * 2 <= n == moveTime.length <= 750
     * 2 <= m == moveTime[i].length <= 750
     * 0 <= moveTime[i][j] <= 10^9
     * ]
     * @param moveTime 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/2 15:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTimeToReach(int[][] moveTime) {
        int result;
        result = method_01(moveTime);
        return result;
    }

    /**
     * @Description: [
     * <p> 1. 在LeetCode_3341的基础上添加了条件, 花费1s, 2s交替, 相当于奇数步骤1s, 偶数步骤2s, (step % 2 == 0 ? 2 : 1)
     * <p> 2. 需要再优先队列中存储额外的步数信息, 方便判断花费的时间
     * AC:464ms/107.62MB
     * ]
     * @param moveTime
     * @return int
     * @author marks
     * @CreateDate: 2025/1/2 15:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] moveTime) {
        final long INF = Long.MAX_VALUE / 2;
        n = moveTime.length;
        m = moveTime[0].length;
        long[][] dist = new long[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        dist[0][0] = 0;
        queue.offer(new int[] {0, 0, 0, 1}); // 下表1, 2表示坐标, index = 3, 表示步数
        while (!queue.isEmpty()) {
            int[] cur_node = queue.poll();
            int value = cur_node[0];
            int x = cur_node[1];
            int y = cur_node[2];
            int step = cur_node[3];
            if (x == n - 1 && y == m - 1) {
                return (int) dist[n - 1][m - 1];
            }
            if (dist[x][y] < value) {
                continue;
            }
            for (int[] dir : dirs) {
                int next_i = x + dir[0];
                int next_j = y + dir[1];
                if (inArea(next_i, next_j)) {
                    long spend = step % 2 == 0 ? 2 : 1;
                    if (dist[x][y] < moveTime[next_i][next_j]) {
                        spend += moveTime[next_i][next_j];
                    } else {
                        spend += dist[x][y];
                    }
                    if (spend < dist[next_i][next_j]) {
                        dist[next_i][next_j] = Math.min(dist[next_i][next_j], spend);
                        queue.offer(new int[] {(int) dist[next_i][next_j], next_i, next_j, step + 1});
                    }

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
