package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2359 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 17:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2359 {

    /**
     * @Description:
     * 给你一个 n 个节点的 有向图 ，节点编号为 0 到 n - 1 ，每个节点 至多 有一条出边。
     * 有向图用大小为 n 下标从 0 开始的数组 edges 表示，表示节点 i 有一条有向边指向 edges[i] 。
     * 如果节点 i 没有出边，那么 edges[i] == -1 。
     * 同时给你两个节点 node1 和 node2 。
     * 请你返回一个从 node1 和 node2 都能到达节点的编号，使节点 node1 和节点 node2 到这个节点的距离 较大值最小化。
     * 如果有多个答案，请返回 最小 的节点编号。如果答案不存在，返回 -1 。
     *
     * 注意 edges 可能包含环。
     * @param: edges
     * @param: node1
     * @param: node2
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 17:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int result;
        result = method_01(edges, node1, node2);
        return result;
    }

    /**
     * @Description:
     * 1. 差不多理解, 即 node1 到节点 i 距离 x , node2 到节点 i 距离 y, ans = Math.min{ Math.max(x, y)[0 ~ n - 1] }
     * 2. 构建邻接表, 使用dijkstra算法 求解 node1 到其他节点的最短距离和 node2 到其他节点的最短距离
     * AC: 127ms/170.39MB
     * @param: edges
     * @param: node1
     * @param: node2
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 17:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private final int INF = Integer.MAX_VALUE / 2;

    private int method_01(int[] edges, int node1, int node2) {
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[edges.length];
        for (int i = 0; i < edges.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] != -1) {
                graph[i].add(new int[]{edges[i], 1});
            }
        }

        // 节点 node1 到其他节点的最短距离
        int[] time1  = DijkstraMinDistance(node1, graph);
        int[] time2  = DijkstraMinDistance(node2, graph);
        int ans = -1;
        int maxTime = INF;
        for (int i = 0; i < time1.length; i++) {
            if (time1[i] != INF && time2[i] != INF) {
                if (maxTime > Math.max(time1[i], time2[i])) {
                    maxTime = Math.max(time1[i], time2[i]);
                    ans = i;
                }
            }
        }
        return ans;
    }

    private int[] DijkstraMinDistance(int node1, List<int[]>[] graph) {
        int n = graph.length;
        int[] time = new int[n]; // 存储节点 node1 到其他节点的最短距离
        Arrays.fill(time, INF);
        time[node1] = 0;
        boolean[] visited = new boolean[n];
        // 使用优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> time[a[0]]));
        pq.offer(new int[]{node1, 0});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currNode = curr[0];
            int currTime = curr[1];
            if (visited[currNode]) continue;
            visited[currNode] = true;
            for (int[] next : graph[currNode]) {
                int nextNode = next[0];
                int nextTime = next[1];
                if (time[nextNode] > currTime + nextTime) {
                    time[nextNode] = currTime + nextTime;
                    pq.offer(new int[]{nextNode, time[nextNode]});
                }
            }
        }

        return time;
    }

}
