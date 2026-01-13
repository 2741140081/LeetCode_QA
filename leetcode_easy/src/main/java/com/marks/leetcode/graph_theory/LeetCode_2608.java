package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2608 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/13 15:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2608 {

    /**
     * @Description:
     * 现有一个含 n 个顶点的 双向 图，每个顶点按从 0 到 n - 1 标记。
     * 图中的边由二维整数数组 edges 表示，其中 edges[i] = [ui, vi] 表示顶点 ui 和 vi 之间存在一条边。
     * 每对顶点最多通过一条边连接，并且不存在与自身相连的顶点。
     * 返回图中 最短 环的长度。如果不存在环，则返回 -1 。
     * 环 是指以同一节点开始和结束，并且路径中的每条边仅使用一次。
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/13 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findShortestCycle(int n, int[][] edges) {
        int result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 无向图, 没有平行边和自环, 构建邻接表
     * 2. 拓扑排序, 删除不在环路上的点/边
     * 3. 计算环的最小长度
     * 4. 怎么处理类似与'8' 字的环?
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/13 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        int[] inDegree = new int[n]; // 节点的入度
        // 邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
            inDegree[u]++;
            inDegree[v]++;
        }
        // 拓扑排序, 队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 1) {
                // 无向图, 只有入度为1的点, 才能作为起点
                queue.offer(i);
            }
        }
        boolean[] visited = new boolean[n];
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            visited[curr] = true;
            for (int next : graph[curr]) {
                inDegree[next]--;
                if (inDegree[next] == 1) {
                    queue.offer(next);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                // BFS
                ans = Math.min(ans, bfs(graph, i));
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private int bfs(List<Integer>[] graph, int start) {
        int[] dist = new int[graph.length];
        Arrays.fill(dist, -1);
        dist[start] = 0;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{start, -1});
        int ans = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int currNode = curr[0];
            int currParent = curr[1];
            for (int next : graph[currNode]) {
                if (dist[next] == -1) {
                    dist[next] = dist[currNode] + 1;
                    queue.offer(new int[]{next, currNode});
                } else if (next != currParent){
                    ans = Math.min(ans, dist[currNode] + dist[next] + 1);
                }
            }
        }
        return ans;
    }

}
