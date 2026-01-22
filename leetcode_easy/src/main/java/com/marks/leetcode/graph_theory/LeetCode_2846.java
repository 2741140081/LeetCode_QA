package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2846 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 11:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2846 {

    /**
     * @Description:
     * 现有一棵由 n 个节点组成的无向树，节点按从 0 到 n - 1 编号。
     * 给你一个整数 n 和一个长度为 n - 1 的二维整数数组 edges ，
     * 其中 edges[i] = [ui, vi, wi] 表示树中存在一条位于节点 ui 和节点 vi 之间、权重为 wi 的边。
     * 另给你一个长度为 m 的二维整数数组 queries ，其中 queries[i] = [ai, bi] 。
     * 对于每条查询，请你找出使从 ai 到 bi 路径上每条边的权重相等所需的 最小操作次数 。
     * 在一次操作中，你可以选择树上的任意一条边，并将其权重更改为任意值。
     * 注意：
     * 查询之间 相互独立 的，这意味着每条新的查询时，树都会回到 初始状态 。
     * 从 ai 到 bi的路径是一个由 不同 节点组成的序列，从节点 ai 开始，到节点 bi 结束，且序列中相邻的两个节点在树中共享一条边。
     * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是第 i 条查询的答案。
     * tips:
     * 1 <= n <= 10^4
     * edges.length == n - 1
     * edges[i].length == 3
     * 0 <= ui, vi < n
     * 1 <= wi <= 26
     * 生成的输入满足 edges 表示一棵有效的树
     * 1 <= queries.length == m <= 2 * 10^4
     * queries[i].length == 2
     * 0 <= ai, bi < n
     * @param: n
     * @param: edges
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/22 11:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        int[] result;
        result = method_01(n, edges, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 如何获取任意两个节点的最近公共父节点(LCA - Lowest Common Ancestor), 使用倍增法(binary lifting)
     * 2. int[][] count; 记录路径上0到其他每个节点的权重数量, 假设0 -> 节点1有3条1的边权, 2条2的边权 count[1][0] = 3, count[1][1] = 2
     * 3. 执行深度优先搜索, 从节点0开始,
     * AC: 73ms/85.16MB
     * @param: n
     * @param: edges
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/22 11:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] depth;
    private int[][] parent;
    private int maxLog;

    private int[] method_01(int n, int[][] edges, int[][] queries) {
        depth = new int[n];
        this.maxLog = (int)(Math.log(n) / Math.log(2)) + 1;
        parent = new int[n][maxLog];
        // 初始化根节点
        for (int i = 0; i < n; i++) {
            Arrays.fill(parent[i], -1);
        }
        // 构建邻接表
        List<int[]>[] matrix = new List[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2] - 1; // 记录 0 ~ 25 的权值
            matrix[u].add(new int[]{v, w});
            matrix[v].add(new int[]{u, w});
        }
        int[][] count = new int[n][26]; // 记录路径上0到其他每个节点的权重数量
        dfs(0, -1, 0, matrix, count);
        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            int lca = lca(u, v);
            int pathLen = depth[u] + depth[v] - 2 * depth[lca];
            int max = -1;
            for (int j = 0; j < 26; j++) {
                int temp = count[u][j] + count[v][j] - 2 * count[lca][j];
                max = Math.max(max, temp);
            }
            ans[i] = pathLen - max;
        }
        return ans;
    }

    private void dfs(int i, int fa, int dep, List<int[]>[] matrix, int[][] count) {
        parent[i][0] = fa;
        depth[i] = dep;

        // 构建倍增数组
        for (int j = 1; j < maxLog; j++) {
            if (parent[i][j - 1] != -1) {
                parent[i][j] = parent[parent[i][j - 1]][j - 1];
            } else {
                parent[i][j] = -1;
            }
        }

        // 处理下一个节点 dfs
        for (int[] edge : matrix[i]) {
            int next = edge[0];
            int w = edge[1];
            if (next == fa) {
                continue;
            }
            // 更新权值
            for (int j = 0; j < 26; j++) {
                count[next][j] = count[i][j];
            }
            count[next][w]++;
            dfs(next, i, dep + 1, matrix, count);
        }
    }


    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // 将 u 提升到与 v 相同的深度
        int diff = depth[u] - depth[v];
        for (int i = 0; i < maxLog; i++) {
            if ((diff & (1 << i)) != 0) {
                u = parent[u][i];
            }
        }
        // 如果 v 就是 LCA
        if (u == v) {
            return u;
        }
        // 同时向上跳跃寻找LCA
        for (int i = maxLog - 1; i >= 0; i--) {
            if (parent[u][i] != -1 && parent[u][i] != parent[v][i]) {
                u = parent[u][i];
                v = parent[v][i];
            }
        }
        return parent[u][0];
    }
}
