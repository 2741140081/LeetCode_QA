package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3620 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/21 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3620 {

    /**
     * @Description:
     * 给你一个包含 n 个节点（编号从 0 到 n - 1）的有向无环图。图由长度为 m 的二维数组 edges 表示，
     * 其中 edges[i] = [ui, vi, costi] 表示从节点 ui 到节点 vi 的单向通信，恢复成本为 costi。
     * 一些节点可能处于离线状态。给定一个布尔数组 online，其中 online[i] = true 表示节点 i 在线。节点 0 和 n - 1 始终在线。
     * 从 0 到 n - 1 的路径如果满足以下条件，那么它是 有效 的：
     * 路径上的所有中间节点都在线。
     * 路径上所有边的总恢复成本不超过 k。
     * 对于每条有效路径，其 分数 定义为该路径上的最小边成本。
     *
     * 返回所有有效路径中的 最大 路径分数（即最大 最小 边成本）。如果没有有效路径，则返回 -1。
     * tips:
     * n == online.length
     * 2 <= n <= 5 * 10^4
     * 0 <= m == edges.length <= min(10^5, n * (n - 1) / 2)
     * edges[i] = [ui, vi, costi]
     * 0 <= ui, vi < n
     * ui != vi
     * 0 <= costi <= 10^9
     * 0 <= k <= 5 * 10^13
     * online[i] 是 true 或 false，且 online[0] 和 online[n - 1] 均为 true。
     * 给定的图是一个有向无环图。
     * @param: edges
     * @param: online
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/21 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int result;
        result = method_01(edges, online, k);
        return result;
    }

    
    /**
     * @Description:
     * 1. 有向无环图找最小边成本, 并且满足总恢复成本不超过 k
     * 2. 有效路径的分数定义为该路径上的最小边成本, 1 -> 2 花费 c1, 2 -> 3 花费 c2,
     * 那么最小边成本是 min(c1, c2), 总花费是 c1 + c2(假设2和3都是离线状态)
     * 3. 使用 Dijkstra 算法, 记录2个值, dist[i][0] = cost(总恢复成本), dist[i][1] = minCost(路径上的最小边成本)
     * dist[i][x] = minCost; 当从 i 向 j 移动, 恢复成本为 dist[i][Math.min(x, cij)] = minCost + (online[j] ? 0 : cij)
     * 4. 什么情况下不能到下一个点 j? ans[j] = false; cost + cij > k; 并且图是无环图, 所以只可能一条边走到底
     * 5. dist[i][x] = minCost: i 表示节点编号, x 表示路径上的最小边成本, minCost 表示路径的最小恢复成本, 由于 x 过大,
     * 所以需要用二分法来找到一个有效值 int l = 0, r = Math.max(cost[i]); 找到最大的 dist[n - 1][mid] != INF
     * 6. 先判断能否在 k 的范围到达 n - 1, 如果不能到达, 则返回 -1; 直接 Dijkstra 先一步判断 n - 1 是否能到达,
     * 7. 理解错了, 恢复成本不是说将下一个节点进行恢复的成本, 而是距离的概念, 如果节点是离线的, 那么就不能走这条路(此路不通)
     * 8. 修改代码, 成功 AC: 617ms/153.4MB
     * 9. 优化代码结构(提前剪枝 "判断是否到达 n - 1" ) AC: 126ms/205.11MB
     * @param: edges
     * @param: online
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/21 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long INF;
    private int method_01(int[][] edges, boolean[] online, long k) {
        INF = Long.MAX_VALUE / 2;
        int n = online.length;
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        int max0 = 0, maxN = 0; // 节点0的出度最大值, 节点 n - 1 的入度最大值
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (online[u] && online[v]) {
                // 只使用在线的节点的线路
                if (u == 0) {
                    max0 = Math.max(max0, edge[2]);
                }
                if (v == n - 1) {
                    maxN = Math.max(maxN, edge[2]);
                }

                graph[u].add(new int[]{v, edge[2]});
            }
        }

        // 使用二分法, l 取两者中较小的值
        int l = 0, r = Math.min(maxN, max0);
        int ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (Dijkstra(n, mid, graph, k)) {
                ans = mid;
                l = mid + 1;
            }  else {
                r = mid - 1;
            }
        }

        return ans;
    }

    private boolean Dijkstra(int n, int limit, List<int[]>[] graph, long k) {
        long[] cost = new long[n]; // 到达 i 节点的最小恢复成本
        Arrays.fill(cost, INF);
        cost[0] = 0;
        // 优先队列
        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
        pq.offer(new long[]{0, 0}); // 恢复成本, 节点编号
        while (!pq.isEmpty()) {
            long[] poll = pq.poll();
            long curCost = poll[0];
            int curNode = (int) poll[1];
            if (curCost > cost[curNode]) {
                continue;
            }
            // 判断是否到达 n - 1
            if (curNode == n - 1) {
                return true;
            }
            for (int[] next : graph[curNode]) {
                int nextNode = next[0];
                int nextCost = next[1];
                if (nextCost < limit) {
                    continue;
                }
                // 判断下一个节点总和超过 k
                if (curCost + nextCost > k) {
                    continue;
                }
                // 去往下一个节点
                if (cost[nextNode] > nextCost + curCost) {
                    cost[nextNode] = nextCost + curCost;
                    pq.offer(new long[]{cost[nextNode], nextNode});
                }
            }
        }
        return false;
    }

}
