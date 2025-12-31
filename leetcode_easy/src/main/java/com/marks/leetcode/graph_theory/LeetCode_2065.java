package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2065 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/31 10:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2065 {

    /**
     * @Description:
     * 给你一张 无向 图，图中有 n 个节点，节点编号从 0 到 n - 1 （都包括）。
     * 同时给你一个下标从 0 开始的整数数组 values ，其中 values[i] 是第 i 个节点的 价值 。
     * 同时给你一个下标从 0 开始的二维整数数组 edges ，其中 edges[j] = [uj, vj, timej] 表示节点 uj 和 vj 之间有一条需要 timej 秒才能通过的无向边。
     * 最后，给你一个整数 maxTime 。
     * 合法路径 指的是图中任意一条从节点 0 开始，最终回到节点 0 ，且花费的总时间 不超过 maxTime 秒的一条路径。
     * 你可以访问一个节点任意次。一条合法路径的 价值 定义为路径中 不同节点 的价值 之和 （每个节点的价值 至多 算入价值总和中一次）。
     *
     * 请你返回一条合法路径的 最大 价值。
     * 注意：每个节点 至多 有 四条 边与之相连。
     * tips:
     * n == values.length
     * 1 <= n <= 1000
     * 0 <= values[i] <= 108
     * 0 <= edges.length <= 2000
     * edges[j].length == 3
     * 0 <= uj < vj <= n - 1
     * 10 <= timej, maxTime <= 100
     * [uj, vj] 所有节点对 互不相同 。
     * 每个节点 至多有四条 边。
     * 图可能不连通。
     * @param: values
     * @param: edges
     * @param: maxTime
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximalPathQuality(int[] values, int[][] edges, int maxTime) {
        int result;
        result = method_01(values, edges, maxTime);
        return result;
    }

    /**
     * @Description:
     * 1. DFS
     * AC: 126ms/46.98MB
     * @param: values
     * @param: edges
     * @param: maxTime
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans = 0;
    private int method_01(int[] values, int[][] edges, int maxTime) {
        // 构建邻接表
        int n = values.length;
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int time = edge[2];
            graph[u].add(new int[]{v, time});
            graph[v].add(new int[]{u, time});
        }
        boolean[] visited = new boolean[n]; // 节点值是否访问过
        // 深度优先搜索
        int[] curr = {0, 0, values[0]}; // 节点编号, 当前使用时间, 价值
        visited[0] = true;
        DFSGetMaxValue(graph, maxTime, curr, visited, values);
        return ans;
    }

    private void DFSGetMaxValue(List<int[]>[] graph, int maxTime, int[] parent, boolean[] visited, int[] values) {
        if (parent[1] > maxTime) {
            return;
        }
        if (parent[0] == 0) {
            ans = Math.max(ans, parent[2]);
        }

        for (int[] edge : graph[parent[0]]) {
            int v = edge[0];
            int time = edge[1];
            if (parent[1] + time <= maxTime) {
                if (!visited[v]) {
                    visited[v] = true;
                    DFSGetMaxValue(graph, maxTime, new int[]{v, parent[1] + time, parent[2] + values[v]}, visited, values);
                    visited[v] = false;
                } else {
                    DFSGetMaxValue(graph, maxTime, new int[]{v, parent[1] + time, parent[2]}, visited, values);
                }
            }
        }
    }
}
