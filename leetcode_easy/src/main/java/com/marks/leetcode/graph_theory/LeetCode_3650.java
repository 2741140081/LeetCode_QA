package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3650 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3650 {

    /**
     * @Description:
     * 给你一个包含 n 个节点的有向带权图，节点编号从 0 到 n - 1。同时给你一个数组 edges，
     * 其中 edges[i] = [ui, vi, wi] 表示一条从节点 ui 到节点 vi 的有向边，其成本为 wi。
     * 每个节点 ui 都有一个 最多可使用一次 的开关：当你到达 ui 且尚未使用其开关时，你可以对其一条入边 vi → ui 激活开关，将该边反转为 ui → vi 并 立即 穿过它。
     * 反转仅对那一次移动有效，使用反转边的成本为 2 * wi。
     * 返回从节点 0 到达节点 n - 1 的 最小 总成本。如果无法到达，则返回 -1。
     * tips:
     * 2 <= n <= 5 * 10^4
     * 1 <= edges.length <= 10^5
     * edges[i] = [ui, vi, wi]
     * 0 <= ui, vi <= n - 1
     * 1 <= wi <= 1000
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 16:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int n, int[][] edges) {
        int result;
        result = method_01(n, edges);
        return result;
    }


    /**
     * @Description:
     * 1. 构建邻接表, 构建一个有向图, 节点编号从 0 到 n - 1.
     * 2. 使用Dijkstra算法, 从节点 0 到其他节点的最小成本.
     * AC: 181ms/258.84MB
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 16:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        final int INF = Integer.MAX_VALUE / 2;
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph[u].add(new int[]{v, w});
            graph[v].add(new int[]{u, w * 2}); // 反转边的成本为 2 * w
        }
        int[] dist = new int[n]; // 节点0到其他节点的最小成本
        Arrays.fill(dist, INF);
        dist[0] = 0;
        boolean[] visited = new boolean[n]; // 节点是否被访问过
        // 优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        pq.offer(new int[]{0, 0}); // 成本, 节点编号
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currCost = curr[0];
            int currNode = curr[1];
            if (visited[currNode]) {
                continue;
            }
            visited[currNode] = true;
            for (int[] next : graph[currNode]) {
                int nextNode = next[0];
                int nextCost = next[1];
                if (dist[nextNode] > currCost + nextCost && !visited[nextNode]) {
                    dist[nextNode] = currCost + nextCost;
                    pq.offer(new int[]{dist[nextNode], nextNode});
                }
            }
        }
        return dist[n - 1] == INF ? -1 : dist[n - 1];
    }

}
