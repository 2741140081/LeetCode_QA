package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1761 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/12 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1761 {

    /**
     * @Description:
     * 给你一个无向图，整数 n 表示图中节点的数目，edges 数组表示图中的边，其中 edges[i] = [ui, vi] ，表示 ui 和 vi 之间有一条无向边。
     * 一个 连通三元组 指的是 三个 节点组成的集合且这三个点之间 两两 有边。
     * 连通三元组的度数 是所有满足此条件的边的数目：一个顶点在这个三元组内，而另一个顶点不在这个三元组内。
     * 请你返回所有连通三元组中度数的 最小值 ，如果图中没有连通三元组，那么返回 -1 。
     * tips:
     * 2 <= n <= 400
     * edges[i].length == 2
     * 1 <= edges.length <= n * (n-1) / 2
     * 1 <= ui, vi <= n
     * ui != vi
     * 图中没有重复的边。
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/12 16:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTrioDegree(int n, int[][] edges) {
        int result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 6, edges = [[1,2],[1,3],[3,2],[4,1],[5,2],[3,6]]
     * 输出：3
     * 1. 需要找到的边的数目, 有效边的定义是, 在一个连通三元组中, 假设顶点1, 2, 3. 度数为 1 到除了 2, 3 以外的点的数目。
     * 那么 节点1的度数 graph[1].size() - 2, 即不包括2, 3. 并且一个三元组可以确定3个点的度数. 使用 int[] res; 初始值为 INF;
     * 2. 最小值为0, 即节点与其他两个节点构成三元组, 并且该节点无其他任何连接. 此时可以提前剪枝结束
     * 3. 判断3元组, int count = graph[i].size() >= 2, 然后从 count 中选取2个点, 判断是否连通, 用一个 Set<Integer> set;判断 key = i * n + j;
     * 4. 用一个优先队列, 存储度数和节点值. int[] outDegree; 存储节点的出度, 遍历outDegree, 获取出度大于2的节点. 添加到优先队列. 小根堆的优先队列, 基于入度值升序.
     * 5. 理解有问题, 还需要计算其他两个点的度数. int res = c1 - 2 + c2 - 2 + c3 - 2;
     * AC: 755ms/92.59MB
     * 优化 Set 和 优先队列
     * AC: 125ms/71.8MB
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/12 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        // 出度表
        int[] outDegree = new int[n];
        // Set 判断节点是否直连, 修改set, 改成二维boolean[][]
        boolean[][] g = new boolean[n][n];
        // 邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 构建邻接表
        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            graph[u].add(v);
            graph[v].add(u);
            outDegree[u]++;
            outDegree[v]++;
            g[u][v] = true;
            g[v][u] = true;
        }
        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int m = graph[i].size();
            for (int j = 0; j < m - 1; j++) {
                int c2 = graph[i].get(j);
                if (outDegree[c2] < 2) {
                    continue;
                }
                for (int k = j + 1; k < m; k++) {
                    int c3 = graph[i].get(k);
                    if (outDegree[c3] < 2) {
                        continue;
                    }

                    if (g[c2][c3]) {
                        int sum = outDegree[i] + outDegree[c2] + outDegree[c3] - 6;
                        if (sum < ans) {
                            ans = sum;
                        }
                    }
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

}
