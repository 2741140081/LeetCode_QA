package com.marks.utils;

import java.util.*;

/**
 * <p>项目名称: 2642. 设计可以求最短路径的图类 </p>
 * <p>文件名称: LeetCode_2642 </p>
 * <p>描述:
 * <p>给你一个有 n 个节点的 有向带权 图，节点编号为 0 到 n - 1 。图中的初始边用数组 edges 表示，其中 edges[i] = [fromi, toi, edgeCosti] 表示从 fromi 到 toi 有一条代价为 edgeCosti 的边。
 *
 * <p>请你实现一个 Graph 类：
 *
 * <p>Graph(int n, int[][] edges) 初始化图有 n 个节点，并输入初始边。
 * <p>addEdge(int[] edge) 向边集中添加一条边，其中 edge = [from, to, edgeCost] 。数据保证添加这条边之前对应的两个节点之间没有有向边。
 * <p>int shortestPath(int node1, int node2) 返回从节点 node1 到 node2 的路径 最小 代价。如果路径不存在，返回 -1 。一条路径的代价是路径中所有边代价之和。
 *
 * <p>AC: 96ms/54.80MB </>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/31 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Graph {
    private int n;
    private List<int[]>[] lists;

    private final int INF = -1;

    public Graph(int n, int[][] edges) {
        this.n = n;
        this.lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            addEdge(edge);
        }
    }

    public void addEdge(int[] edge) {
        int start = edge[0], end = edge[1];
        lists[start].add(new int[] {end, edge[2]});
    }

    public int shortestPath(int node1, int node2) {
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[node1] = 0;
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.offer(new int[] {0, node1});
        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            int ds = curr_node[0];
            int curr_i = curr_node[1];
            if (curr_i != node2) {
                return ds;
            }
            for (int[] next_node : lists[curr_i]) {
                int next_j = next_node[0], spend = ds + next_node[1];
                if (spend < dist[next_j] || dist[next_j] == INF) {
                    dist[next_j] = spend;
                    queue.offer(new int[] {spend, next_j});
                }
            }
        }
        return -1;
    }
}
