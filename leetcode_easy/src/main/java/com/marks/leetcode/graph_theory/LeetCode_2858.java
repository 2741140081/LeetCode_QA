package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2858 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/14 11:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2858 {

    /**
     * @Description:
     * 给你一个 n 个点的 简单有向图 （没有重复边的有向图），节点编号为 0 到 n - 1 。如果这些边是双向边，那么这个图形成一棵 树 。
     * 给你一个整数 n 和一个 二维 整数数组 edges ，其中 edges[i] = [ui, vi] 表示从节点 ui 到节点 vi 有一条 有向边 。
     * 边反转 指的是将一条边的方向反转，也就是说一条从节点 ui 到节点 vi 的边会变为一条从节点 vi 到节点 ui 的边。
     * 对于范围 [0, n - 1] 中的每一个节点 i ，你的任务是分别 独立 计算 最少 需要多少次 边反转 ，从节点 i 出发经过 一系列有向边 ，可以到达所有的节点。
     * 请你返回一个长度为 n 的整数数组 answer ，其中 answer[i]表示从节点 i 出发，可以到达所有节点的 最少边反转 次数。
     * tips:
     * 2 <= n <= 10^5
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ui == edges[i][0] < n
     * 0 <= vi == edges[i][1] < n
     * ui != vi
     * 输入保证如果边是双向边，可以得到一棵树。
     * @param: n
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/14 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] minEdgeReversals(int n, int[][] edges) {
        int[] result;
        result = method_01(n, edges);
        result = method_02(n, edges);
        return result;
    }

    /**
     * @return int[]
     * @Description:
     * 修改邻接表存储数据, 改成 int[] {next, 1/-1}
     * AC: 74ms/243.6MB
     * @param: n
     * @param: edges
     * @author marks
     * @CreateDate: 2026/01/14 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int n, int[][] edges) {
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(new int[]{v, 1}); // 存储有向边
            graph[v].add(new int[]{u, -1}); // 存储反转值
        }
        int[] dist = new int[n];
        dfs(0, -1, graph, dist);
        // 再次执行广度优先搜索, 即换根
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        boolean[] visited = new boolean[n];
        visited[0] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int[] t : graph[u]) {
                int v = t[0];
                int d = t[1];
                if (!visited[v]) {
                    queue.offer(v);
                    visited[v] = true;
                    dist[v] = dist[u] + (d > 0 ? 1 : -1);
                    // ans[v] = ans[u] + (set.contains(u * n + v) ? 1 : -1);
                }
            }
        }

        return dist;
    }

    private void dfs(int curr, int pa, List<int[]>[] graph, int[] dist) {
        for (int[] nt : graph[curr]) {
            int next = nt[0];
            int dir = nt[1];
            if (next != pa) {
                if (dir < 0) {
                    dist[0]++;
                }
                dfs(next, curr, graph, dist);
            }
        }
    }

    /**
     * @Description:
     * 1. 我隐约记得好像是需要进行两次dfs/bfs, 第一次求节点0到达其他节点的最小反转边数
     * 2. 然后依次判断节点0的相邻节点的最小反转边数, 即假设 节点0 的最小反转边数是 x条, 并且节点 1 是 节点0的相邻节点,
     * 如果存在边 1 -> 0, 那么 ans[1] = x - 1, 否则 ans[1] = x + 1, 这样就需要存储边的关系, 即有向边的关系
     * int key = u * n + v; 用Set 存储.
     * 3. 由于我需要从节点0 到达其他节点, 所以在构建邻接表时, 需要这个表是一个无向图, 即u -> v, v -> u, 但是我存储有向边。
     * 4. int[] dist 存储节点0 到其他节点的最小反转边数, 分析的差不多了. 开始写代码
     * 卡了一个测试用例: 420/421
     * 找到问题所在, 由于哈希冲突导致
     * return new int[] {d, u, v, set.contains(u * n + v) ? 0 : 1, u * n + v, map.get(u * n + v)[0], map.get(u * n + v)[1]};
     * [-1,9091,13059,0,909113059,94990,47651]
     * 关键问题：map.get(u * n + v)[0] = 94990 ≠ u = 9091，map.get(u * n + v)[1] = 47651 ≠ v = 13059
     * 不同的(u, v) 的哈希值相同.
     * @param: n
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/14 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] edges) {
        // Set 集合存储有向边
        Map<Integer, int[]> map = new HashMap<>();
        Set<Integer> set = new HashSet<>(); // 存储有向边
        // 构建邻接表
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(new int[]{v, 1});
            graph[v].add(new int[]{u, -1});
            set.add(u * n + v);
            map.put(u * n + v, edge);
        }

        // 广度优先搜索
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        int sum = 0; // 存储节点0 到其他节点的最小反转边数之和
        int sum2 = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int[] t : graph[u]) {
                int v = t[0];
                int d = t[1];
                if (!visited[v]) {
                    queue.offer(v);
                    visited[v] = true;
                    sum += (d < 0 ? 1 : 0);
                    sum2 += (set.contains(u * n + v) ? 0 : 1);
                    if (sum != sum2) {
                        // [-1,9091,13059,0,909113059,94990,47651]
                        return new int[] {d, u, v, set.contains(u * n + v) ? 0 : 1, u * n + v, map.get(u * n + v)[0], map.get(u * n + v)[1]};
                    }
                }
            }
        }
        int[] ans = new int[n];
        ans[0] = sum;

        // 再次执行广度优先搜索, 即换根
        queue.offer(0);
        visited = new boolean[n];
        visited[0] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int[] t : graph[u]) {
                int v = t[0];
                int d = t[1];
                if (!visited[v]) {
                    queue.offer(v);
                    visited[v] = true;
                    ans[v] = ans[u] + (d > 0 ? 1 : -1);
                    // ans[v] = ans[u] + (set.contains(u * n + v) ? 1 : -1);
                }
            }
        }
        return ans;
    }

}
