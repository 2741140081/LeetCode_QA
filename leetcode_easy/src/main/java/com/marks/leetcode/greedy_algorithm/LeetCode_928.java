package com.marks.leetcode.greedy_algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_928 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/6 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_928 {

    /**
     * @Description:
     * 给定一个由 n 个节点组成的网络，用 n x n 个邻接矩阵 graph 表示。在节点网络中，只有当 graph[i][j] = 1 时，节点 i 能够直接连接到另一个节点 j。
     * 一些节点 initial 最初被恶意软件感染。只要两个节点直接连接，且其中至少一个节点受到恶意软件的感染，那么两个节点都将被恶意软件感染。
     * 这种恶意软件的传播将继续，直到没有更多的节点可以被这种方式感染。
     *
     * 假设 M(initial) 是在恶意软件停止传播之后，整个网络中感染恶意软件的最终节点数。
     * 我们可以从 initial 中 删除一个节点，并完全移除该节点以及从该节点到任何其他节点的任何连接。
     * 请返回移除后能够使 M(initial) 最小化的节点。如果有多个节点满足条件，返回索引 最小的节点 。
     * tips:
     * n == graph.length
     * n == graph[i].length
     * 2 <= n <= 300
     * graph[i][j] 是 0 或 1.
     * graph[i][j] == graph[j][i]
     * graph[i][i] == 1
     * 1 <= initial.length < n
     * 0 <= initial[i] <= n - 1
     *  initial 中每个整数都不同
     * @param: graph
     * @param: initial
     * @return int
     * @author marks
     * @CreateDate: 2026/01/06 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMalwareSpread(int[][] graph, int[] initial) {
        int result;
        result = method_01(graph, initial);
        return result;
    }

    /**
     * @Description:
     * 1. 考虑所有不在 initial 中的剩余节点，这些剩余节点组成一个图 G。
     * 2. 我们依次遍历 initial 中的节点，记当前遍历的节点为 v，将 v 加入图 G（仅在此次遍历有效），同时使用深度优先搜索算法标记从节点 v 可以访问到的节点集 infectedSet；
     * 如果节点 u∈infectedSet，那么我们将节点 v 加入到 infectedBy[u] 中。
     * 3. 遍历结束后，我们使用 count[v] 统计图 G 中只能被节点 v 感染到的所有节点数目：如果 infectedBy[u] 的大小为 1，
     * 且 infectedBy[u][0]=v，那么节点 u 就是图 G 中只能被节点 v 感染到的节点。
     * 最后遍历 count 数组，取使 count[v] 最大且下标值最小的节点 v 为答案。
     *
     * AC: 26ms/94.4MB
     * @param: graph
     * @param: initial
     * @return int
     * @author marks
     * @CreateDate: 2026/01/06 15:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] graph, int[] initial) {
        int n = graph.length; // 节点个数
        // set 存储从 initial 中删除的节点
        Set<Integer> initSet = new HashSet<>();
        for (int node : initial) {
            initSet.add(node);
        }
        // 记录节点 i 可以被感染的节点
        List<Integer>[] infectedBy = new List[n];
        for (int i = 0; i < n; i++) {
            infectedBy[i] = new ArrayList<>();
        }
        // 遍历 initial 中的节点, 执行深度优先搜索
        for (int v : initial) {
            boolean[] visited = new boolean[n];
            dfs(v, visited, initSet, graph);
            for (int i = 0; i < n; i++) {
                if (visited[i]) {
                    infectedBy[i].add(v);
                }
            }
        }
        int[] count = new int[n]; // 存储节点 i 可以唯一感染其他节点, 即被节点 i 唯一感染的节点数
        for (int i = 0; i < n; i++) {
            if (infectedBy[i].size() == 1) {
                count[infectedBy[i].get(0)]++;
            }
        }
        int ans = initial[0]; // 默认 initial[0]
        for (int u : initial) {
            if (count[u] > count[ans] || (count[u] == count[ans] && u < ans)) {
                ans = u;
            }
        }
        return ans;
    }

    private void dfs(int v, boolean[] visited,  Set<Integer> initSet, int[][] graph) {
        int n = visited.length;
        for (int i = 0; i < n; i++) {
            if (!visited[i] && graph[v][i] == 1 && !initSet.contains(i)) {
                visited[i] = true;
                // 继续深度
                dfs(i, visited, initSet, graph);
            }
        }
    }

}
