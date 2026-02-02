package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3651 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/28 10:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3651 {

    /**
     * @Description:
     * 给你一个 m x n 的二维整数数组 grid 和一个整数 k。你从左上角的单元格 (0, 0) 出发，目标是到达右下角的单元格 (m - 1, n - 1)。
     * 有两种移动方式可用：
     * 普通移动：你可以从当前单元格 (i, j) 向右或向下移动，即移动到 (i, j + 1)（右）或 (i + 1, j)（下）。成本为目标单元格的值。
     * 传送：你可以从任意单元格 (i, j) 传送到任意满足 grid[x][y] <= grid[i][j] 的单元格 (x, y)；此移动的成本为 0。你最多可以传送 k 次。
     * 返回从 (0, 0) 到达单元格 (m - 1, n - 1) 的 最小 总成本。
     * tips:
     * 2 <= m, n <= 80
     * m == grid.length
     * n == grid[i].length
     * 0 <= grid[i][j] <= 10^4
     * 0 <= k <= 10
     * @param: grid
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/28 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int[][] grid, int k) {
        int result;
        result = method_01(grid, k);
        return result;
    }

    /**
     * @Description:
     * 1. 数据范围不大, 怎么方便的构建邻接表?
     * 2. 如果把所有节点进行排序, 并且将下标(i, j) 转换成一维 i * 100 + j,
     * 3. 使用Dijkstra算法求最短路径, 基于优先队列, 不想使用朴素的Dijkstra算法
     * 4. int[] dist = new int[m * 100 + n];
     * 5. 还需要处理 k, 仅能进行 k 次传送
     * need todo
     * @param: grid
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/28 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid, int k) {
        final int INF = Integer.MAX_VALUE;
        int m = grid.length, n = grid[0].length;
        int len = m * 100 + n;
        // 构建邻接表 start
        List<int[]>[] graph = new ArrayList[len];
        for (int i = 0; i < len; i++) {
            graph[i] = new ArrayList<>();
        }
        int[] arr = new int[m * 100 + n]; // 存储所有节点{grid[i][j]}, 方便进行排序操作
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int index = i * 100 + j;
                arr[index] = grid[i][j];
                if (i + 1 < m && grid[i][j] < grid[i + 1][j]) {
                    int nextIndex = (i + 1) * 100 + j;
                    graph[index].add(new int[]{nextIndex, grid[i + 1][j]});
                }
                if (j + 1 < n && grid[i][j] < grid[i][j + 1]) {
                    int nextIndex = i * 100 + (j + 1);
                    graph[index].add(new int[]{nextIndex, grid[i][j + 1]});
                }
            }
        }
        // 对 arr 进行排序, 算了直接升序吧
        Arrays.sort(arr);
        // 倒序遍历
        for (int i = len - 1; i >= 0; i--) {
            int nextTndex = arr[i];
            // 0 ~ i - 1 的节点都可以传送到 i 节点
            for (int j = i - 1; j >= 0; j--) {
                int preIndex = arr[j];
                graph[preIndex].add(new int[] {nextTndex, -1}); // 使用 -1 进行特殊标记, 因为grid[i][j] 可能是 0
            }
        }
        // 邻接表构建 end
        // 开始Dijkstra, 构建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, 0, 0}); // {花费, 已使用的传说次数, index}
        int[][] dist = new int[len][k + 1]; // 最多k次(包含 k )
        for (int i = 0; i < len; i++) {
            Arrays.fill(dist[i], INF);
        }

        dist[0][0] = 0;
        int end = (m - 1) * 100 + n - 1;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], usedK = cur[1], index = cur[2];
            if (cost > dist[index][usedK]) {
                continue;
            }
            // 判断end 节点
            if (index == end) {
                return cost;
            }
            for (int[] next : graph[index]) {
                int nextIndex = next[0], nextCost = next[1];
                if (nextCost == -1 && usedK < k) {
                    // 进行传送操作
                    if (dist[nextIndex][usedK + 1] > cost) {
                        dist[nextIndex][usedK + 1] = cost;
                        pq.offer(new int[]{dist[nextIndex][usedK + 1], usedK + 1, nextIndex});
                    }
                } else if (nextCost != -1) {
                    // 普通移动
                    if (dist[nextIndex][usedK] > cost + nextCost) {
                        dist[nextIndex][usedK] = cost + nextCost;
                        pq.offer(new int[]{dist[nextIndex][usedK], usedK, nextIndex});
                    }
                }

            }
        }
        return 0;
    }

}
