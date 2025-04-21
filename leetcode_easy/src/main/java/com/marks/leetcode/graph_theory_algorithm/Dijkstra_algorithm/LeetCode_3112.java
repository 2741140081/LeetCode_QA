package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/31 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3112 {
    /**
     * @Description: [
     * <p>给你一个二维数组 edges 表示一个 n 个点的无向图，其中 edges[i] = [u_i, v_i, length_i] 表示节点 u_i 和节点 v_i 之间有一条需要 length_i 单位时间通过的无向边。
     * 同时给你一个数组 disappear ，其中 disappear[i] 表示节点 i 从图中消失的时间点，在那一刻及以后，你无法再访问这个节点。
     *
     * <p>注意，图有可能一开始是不连通的，两个节点之间也可能有多条边。
     * <p>请你返回数组 answer ，answer[i] 表示从节点 0 到节点 i 需要的 最少 单位时间。如果从节点 0 出发 无法 到达节点 i ，那么 answer[i] 为 -1 。
     * <p>tips:
     * 1 <= n <= 5 * 10^4
     * 0 <= edges.length <= 10^5
     * edges[i] == [ui, vi, length_i]
     * 0 <= ui, vi <= n - 1
     * 1 <= length_i <= 10^5
     * disappear.length == n
     * 1 <= disappear[i] <= 10^5
     * ]
     * @param n
     * @param edges
     * @param disappear
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/31 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        int[] result;
        result = method_01(n, edges, disappear);
        return result;
    }

    /**
     * @Description: [
     * 迪杰斯特拉算法
     * <p>E1:
     * 输入：n = 3, edges = [[0,1,2],[1,2,1],[0,2,4]], disappear = [1,1,5]
     * 输出：[0,-1,4]
     * 我们从节点 0 出发，目的是用最少的时间在其他节点消失之前到达它们。
     *
     * 对于节点 0 ，我们不需要任何时间，因为它就是我们的起点。
     * 对于节点 1 ，我们需要至少 2 单位时间，通过 edges[0] 到达。但当我们到达的时候，它已经消失了，所以我们无法到达它。
     * 对于节点 2 ，我们需要至少 4 单位时间，通过 edges[2] 到达。
     *
     * <p>1. 可以看到的是 节点数 n 与边数 m 相近, 所以可以近似看做一个稀疏图, 因此我采用小根堆的方式来解决
     * <p>2. 和之前存在的区别是, 本题存在一个disappear[] 数组, 表示当时间到 disappear[i] 后 节点i 会变成不可达状态, 需要特殊处理
     * <p>3. 还是关于disappear[i], 我们到达节点i的最短时间dist[i] < disappear[i], 这样才能到达
     * <p>4. 还是一个疑问是, 如果disappear[0] = 0, 是否能出发向一个节点?
     * <p>5. 我们是一个无向图, 因此节点两端都要添加到lists[] 中
     *
     * <p> 超时!!! Result: 531 / 534
     * <p> 查看题解, 加了一个判断在while中
     * if (t != answer[u]) {
     *     continue;
     * }
     * 不是很理解上面的判断添加, but 添加之后AC:97ms/117.14MB
     * ]
     * @param n
     * @param edges
     * @param disappear
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/31 14:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] edges, int[] disappear) {
        List<int[]>[] lists = new ArrayList[n];
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int start = edge[0], end = edge[1];
            lists[start].add(new int[]{end, edge[2]});
            lists[end].add(new int[]{start, edge[2]});
        }

        Arrays.fill(dist, -1);
        dist[0] = 0; // 起始点位于0处, 所以为0
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        queue.offer(new int[] {0, 0}); // 第一个0 表示dist[0], 第二个0 表示节点0

        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            int ds = curr_node[0];
            int curr_i = curr_node[1];
            if (dist[curr_i] != ds) {
                continue;
            }
            for (int[] next_node : lists[curr_i]) {
                int next_j = next_node[0];
                int spend = ds + next_node[1];
                if (spend < disappear[next_j] && (dist[next_j] == -1 || spend < dist[next_j])) {
                    dist[next_j] = spend;
                    queue.offer(new int[]{spend, next_j});
                }
            }
        }

        return dist;
    }
}
