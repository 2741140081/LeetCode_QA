package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1786 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/16 10:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1786 {

    /**
     * @Description:
     * 现有一个加权无向连通图。给你一个正整数 n ，表示图中有 n 个节点，并按从 1 到 n 给节点编号；
     * 另给你一个数组 edges ，其中每个 edges[i] = [ui, vi, weighti] 表示存在一条位于节点 ui 和 vi 之间的边，这条边的权重为 weighti 。
     * 从节点 start 出发到节点 end 的路径是一个形如 [z0, z1, z2, ..., zk] 的节点序列，
     * 满足 z0 = start 、zk = end 且在所有符合 0 <= i <= k-1 的节点 zi 和 zi+1 之间存在一条边。
     *
     * 路径的距离定义为这条路径上所有边的权重总和。用 distanceToLastNode(x) 表示节点 n 和 x 之间路径的最短距离。
     * 受限路径 为满足 distanceToLastNode(zi) > distanceToLastNode(zi+1) 的一条路径，其中 0 <= i <= k-1 。
     * 返回从节点 1 出发到节点 n 的 受限路径数 。由于数字可能很大，请返回对 10^9 + 7 取余 的结果。
     * tips:
     * 1 <= n <= 2 * 10^4
     * n - 1 <= edges.length <= 4 * 10^4
     * edges[i].length == 3
     * 1 <= ui, vi <= n
     * ui != vi
     * 1 <= weighti <= 105
     * 任意两个节点之间至多存在一条边
     * 任意两个节点之间至少存在一条路径
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countRestrictedPaths(int n, int[][] edges) {
        int result;
//        result = method_01(n, edges);
        result = method_02(n, edges);
        return result;
    }

    /**
     * @Description:
     * 使用dp计算受阻路径数
     * AC: 69ms/129.31MB
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 11:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[][] edges) {
        final int MOD = 1000000007;
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            graph[u].add(new int[]{v, edge[2]});
            graph[v].add(new int[]{u, edge[2]});
        }
        int[] dist = new int[n]; // 存储从节点 n - 1 到其他节点的最短距离
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[n - 1] = 0;
        extracted(n, dist, graph);
        // dp 过程
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i] = new int[]{i, dist[i]}; // 点编号，点距离
        }
        Arrays.sort(arr, (a, b)->a[1]-b[1]); // 根据点距离从小到大排序

        // 定义 f(i) 为从第 i 个点到结尾的受限路径数量
        // 从 f[n] 递推到 f[1]
        int[] f = new int[n];
        f[n - 1] = 1;
        for (int i = 0; i < n; i++) {
            int idx = arr[i][0], cur = arr[i][1];
            for (int[] next : graph[idx]) {
                if (cur > dist[next[0]]) {
                    f[idx] += f[next[0]];
                    f[idx] %= MOD;
                }
            }
            // 第 1 个节点不一定是距离第 n 个节点最远的点，但我们只需要 f[1]，可以直接跳出循环
            if (idx == 0) break;
        }
        return f[0];
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 5, edges = [[1,2,3],[1,3,3],[2,3,1],[1,4,2],[5,2,2],[3,5,1],[5,4,10]]
     * 输出：3
     * 解释：每个圆包含黑色的节点编号和蓝色的 distanceToLastNode 值。三条受限路径分别是：
     * 1) 1 --> 2 --> 5
     * 2) 1 --> 2 --> 3 --> 5
     * 3) 1 --> 3 --> 5
     * 1. 通过Dijkstra算法，求节点 n - 1(实际是编号 n) 到其他节点(0 ~ n - 2)的最短距离, int[] dist;
     * 2. 然后需要逆向遍历, 从节点 n - 1 开始, 进行广度优先搜索, step 表示步数, 并且下一步需要满足 dist[nextStep] > dist[currStep]
     * 否则不能算是一条受阻路径
     * 3. 并且终止点在节点0(实际编号1) 处停止, 计算count++
     * 4. 这是一个稀疏图, 所以采用基于优先队列的迪杰斯特拉算法
     * 5. 超时!!! (52/77)
     * 6. 超时的原因是计算受阻路径的时间复杂度太高了, 怎么优化
     * 7. 想到的方法是使用递归求解, 试试看, 还是不行, 看看题解吧
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/16 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            graph[u].add(new int[]{v, edge[2]});
            graph[v].add(new int[]{u, edge[2]});
        }
        int[] dist = new int[n]; // 存储从节点 n - 1 到其他节点的最短距离
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[n - 1] = 0;
        extracted(n, dist, graph);
        // 计算受阻路径数, 递归还是超时, 需要重构一个新的邻接表
        int max = dist[0], min = dist[n - 1]; // 受阻路径中的节点的范围必须在 [min, max]
        // 非法节点
        Set<Integer> illegal = new HashSet<>();
        for (int i = 1; i < n - 1; i++) {
            if (dist[i] <= min || dist[i] >= max) {
                illegal.add(i);
            }
        }
        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            if (!illegal.contains(u) && !illegal.contains(v)) {
                graph[u].add(new int[]{v, edge[2]});
                graph[v].add(new int[]{u, edge[2]});
            }

        }
        int ans = recursion(n, 0, -1, graph, dist);
        return ans;
    }

    private int recursion(int n, int curr, int pa, List<int[]>[] graph, int[] dist) {
        final int MOD = 1000000007;
        int count = 0;
        if (curr == n - 1) {
            return 1;
        }
        for (int[] neighbor : graph[curr]) {
            int next = neighbor[0];
            if (next != pa && dist[curr] > dist[next]) {
                count = (count + recursion(n, next, curr, graph, dist)) % MOD;
            }
        }
        return count;
    }

    private void extracted(int n, int[] dist, List<int[]>[] graph) {
        // 构建优先队列, 存储节点编号和距离
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.offer(new int[]{n - 1, 0});
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currNode = curr[0];
            int currDist = curr[1];
            if (currDist > dist[currNode]) {
                continue;
            }
            for (int[] neighbor : graph[currNode]) {
                int nextNode = neighbor[0];
                int nextDist = neighbor[1];
                if (dist[nextNode] > dist[currNode] + nextDist) {
                    dist[nextNode] = dist[currNode] + nextDist;
                    pq.offer(new int[]{nextNode, dist[nextNode]});
                }
            }
        }
    }

}
