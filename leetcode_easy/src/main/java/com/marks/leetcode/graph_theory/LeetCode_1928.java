package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1928 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/16 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1928 {

    /**
     * @Description:
     * 一个国家有 n 个城市，城市编号为 0 到 n - 1 ，题目保证 所有城市 都由双向道路 连接在一起 。
     * 道路由二维整数数组 edges 表示，其中 edges[i] = [xi, yi, timei] 表示城市 xi 和 yi 之间有一条双向道路，耗费时间为 timei 分钟。
     * 两个城市之间可能会有多条耗费时间不同的道路，但是不会有道路两头连接着同一座城市。
     * 每次经过一个城市时，你需要付通行费。通行费用一个长度为 n 且下标从 0 开始的整数数组 passingFees 表示，其中 passingFees[j] 是你经过城市 j 需要支付的费用。
     * 一开始，你在城市 0 ，你想要在 maxTime 分钟以内 （包含 maxTime 分钟）到达城市 n - 1 。旅行的 费用 为你经过的所有城市 通行费之和 （包括 起点和终点城市的通行费）。
     * 给你 maxTime，edges 和 passingFees ，请你返回完成旅行的 最小费用 ，如果无法在 maxTime 分钟以内完成旅行，请你返回 -1 。
     * tips:
     * 1 <= maxTime <= 1000
     * n == passingFees.length
     * 2 <= n <= 1000
     * n - 1 <= edges.length <= 1000
     * 0 <= xi, yi <= n - 1
     * 1 <= timei <= 1000
     * 1 <= passingFees[j] <= 1000
     * 图中两个节点之间可能有多条路径。
     * 图中不含有自环。
     * @param: maxTime
     * @param: edges
     * @param: passingFees
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        int result;
        result = method_01(maxTime, edges, passingFees);
        result = method_02(maxTime, edges, passingFees);
        return result;
    }

    /**
     * @Description:
     * 1. 官方题解的动态规划解法
     * AC: 94ms/52.28MB
     * @param: maxTime
     * @param: edges
     * @param: passingFees
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int method_02(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        int[][] f = new int[maxTime + 1][n];
        for (int i = 0; i <= maxTime; i++) {
            Arrays.fill(f[i], Integer.MAX_VALUE);
        }
        f[0][0] = passingFees[0];
        for (int t = 1; t <= maxTime; t++) {
            for (int[] edge : edges) {
                int i = edge[0], j = edge[1], cost = edge[2];
                if (cost <= t) {
                    if (f[t - cost][j] != Integer.MAX_VALUE) {
                        f[t][i] = Math.min(f[t][i], f[t - cost][j] + passingFees[i]);
                    }
                    if (f[t - cost][i] != Integer.MAX_VALUE) {
                        f[t][j] = Math.min(f[t][j], f[t - cost][i] + passingFees[j]);
                    }
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int t = 1; t <= maxTime; t++) {
            ans = Math.min(ans, f[t][n - 1]);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * @Description:
     * 1. 这种找最小花费的方法是使用Dijkstra算法, 并且此题需要两个条件: 最小花费 和 小于 maxTime.
     * 2. 应该使用时间最为最短, 同时统计到达城市n - 1 的时间小于 maxTime 的花费, 并且找到 最小花费
     * 3. 图中两个节点之间可能有多条路径(取唯一最短路径), 费用是你到达就计费, 包括节点0和 n - 1.
     * 4. 稀疏图使用优先队列。
     * AC: 1325ms/107.47MB, 时间复杂度和空间复杂度都很高
     * @param: maxTime
     * @param: edges
     * @param: passingFees
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int maxTime, int[][] edges, int[] passingFees) {
        final int INF = Integer.MAX_VALUE / 2;
        // 构建邻接表
        int n = passingFees.length;
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 存在多条路径, 需要取最短路径, 可以对 edges 进行排序
        Arrays.sort(edges, (a, b) -> a[2]-b[2]); // 升序排序
        // Set 存储路径是否已经遍历
        Set<Integer> set = new HashSet<>();
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int time = edge[2];
            int key = u * n + v;
            if (!set.contains(key)) {
                graph[u].add(new int[]{v, time});
                graph[v].add(new int[]{u, time});
                set.add(key);
                set.add(v * n + u); // key2
            }
        }
        int[][] dist = new int[maxTime + 1][n]; // 存储从节点 0 到其他节点的最小花费, 但是应该是一个二维的数组
        for (int i = 0; i <= maxTime; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = INF;
            }
        }
        dist[0][0] = passingFees[0];
        // 构建优先队列, 存储节点编号和花费以及时间
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.offer(new int[]{0, dist[0][0], 0});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int cost = curr[1];
            int time = curr[2];
            if (time > maxTime) {
                // 超时
                continue;
            }

            if (u == n - 1) {
                // 到达终点
                return cost;
            }
            for (int[] next : graph[u]) {
                int v = next[0];
                int time_v = next[1];
                int cost_v = passingFees[v];
                if (time + time_v <= maxTime && cost + cost_v < dist[time + time_v][v]) {
                    dist[time + time_v][v] = cost + cost_v;
                    pq.offer(new int[]{v, dist[time + time_v][v], time + time_v});
                }
            }
        }

        return -1;
    }

}
