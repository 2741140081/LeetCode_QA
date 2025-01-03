package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/3 9:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3123 {
    /**
     * @Description: [
     * <p>给你一个 n 个节点的无向带权图，节点编号为 0 到 n - 1 。图中总共有 m 条边，用二维数组 edges 表示，其中 edges[i] = [ai, bi, wi] 表示节点 ai 和 bi 之间有一条边权为 wi 的边。
     *
     * <p>对于节点 0 为出发点，节点 n - 1 为结束点的所有最短路，你需要返回一个长度为 m 的 boolean 数组 answer ，
     * <p>如果 edges[i] 至少 在其中一条最短路上，那么 answer[i] 为 true ，否则 answer[i] 为 false 。
     *
     * <p>请你返回数组 answer 。
     *
     * <p>注意，图可能不连通。
     * <p>tips:
     * 2 <= n <= 5 * 10^4
     * m == edges.length
     * 1 <= m <= min(5 * 10^4, n * (n - 1) / 2)
     * 0 <= ai, bi < n
     * ai != bi
     * 1 <= wi <= 10^5
     * 图中没有重边。
     * ]
     * @param n 
     * @param edges
     * @return boolean[]
     * @author marks
     * @CreateDate: 2025/1/3 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean[] findAnswer(int n, int[][] edges) {
        boolean[] result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description: [
     * <p>E1:
     * 输入：n = 6, edges = [[0,1,4],[0,2,1],[1,3,2],[1,4,3],[1,5,1],[2,3,1],[3,5,3],[4,5,2]]
     * 输出：[true,true,true,false,true,true,true,false]
     * <p>思路:
     * <p>1. 我们需要找到节点0 到达节点 n - 1的最短距离, 不确定是否可以求出每一个节点到达节点 n - 1的最短距离
     * <p>2. 我们这个dist[]数组存放的是什么? 存放的是节点i 到达节点 0的最短距离
     * <p>3. 然后我们遍历整个n节点, 倒序, 使用队列或者栈进行BFS, 判断是否在最短路径上, 如果 (a, b) 在路径上, 然后通过map.get(key)得到index
     * 并且更新ans[index] = true;
     *
     * AC:118ms/84.88MB
     * ]
     * @param n
     * @param edges
     * @return boolean[]
     * @author marks
     * @CreateDate: 2025/1/3 9:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean[] method_01(int n, int[][] edges) {
        final int INF = Integer.MAX_VALUE / 2;
        int m = edges.length;
        int[] dist = new int[n];
        List<int[]>[] lists = new ArrayList[n];
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            dist[i] = INF;
            lists[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int x = edge[0];
            int y = edge[1];
            lists[x].add(new int[] {y, edge[2]});
            lists[y].add(new int[] {x, edge[2]});
            String key = Math.min(x, y) + "_" + Math.max(x, y);
            map.put(key, i);
        }
        boolean[] ans = new boolean[m];
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        dist[0] = 0;
        queue.offer(new int[] {0, 0});
        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            int ds = curr_node[0];
            int curr_i = curr_node[1];
            if (curr_i == n - 1) {
                break;
            }
            if (ds > dist[curr_i]) {
                continue;
            }
            for (int[] next_node : lists[curr_i]) {
                int next_j = next_node[0];
                int value = ds + next_node[1];
                if (value < dist[next_j]) {
                    dist[next_j] = value;
                    queue.offer(new int[] {value, next_j});
                }
            }
        }

        if (dist[n - 1] == INF) {
            Arrays.fill(ans, false);
            return ans;
        }
        Queue<Integer> que = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        que.offer(n - 1);
        visited[n - 1] = true;
        while (!que.isEmpty()) {
            int size = que.size();
            for (int i = 0; i < size; i++) {
                int curr_i = que.poll();
                for (int[] next_node : lists[curr_i]) {
                    int next_j = next_node[0];
                    int ds = dist[curr_i] - dist[next_j]; // ds 是 i -> j 的距离
                    if (ds == next_node[1]) {
                        String key = Math.min(curr_i, next_j) + "_" + Math.max(curr_i, next_j);
                        int index = map.get(key);
                        ans[index] = true;
                        if (!visited[next_j]) {
                            que.offer(next_j);
                            visited[next_j] = true;
                        }
                    }
                }
            }
        }
        return ans;
    }
}
