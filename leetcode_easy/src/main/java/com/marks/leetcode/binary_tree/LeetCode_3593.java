package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/7 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3593 {

    /**
     * @Description:
     * 给你一个整数 n，以及一个无向树，该树以节点 0 为根节点，包含 n 个节点，节点编号从 0 到 n - 1。
     * 这棵树由一个长度为 n - 1 的二维数组 edges 表示，其中 edges[i] = [ui, vi] 表示节点 ui 和节点 vi 之间存在一条边。
     *
     * 每个节点 i 都有一个关联的成本 cost[i]，表示经过该节点的成本。
     * 路径得分 定义为路径上所有节点成本的总和。
     *
     * 你的目标是通过给任意数量的节点 增加 成本（可以增加任意非负值），使得所有从根节点到叶子节点的路径得分 相等 。
     * 返回需要增加成本的节点数的 最小值 。
     *
     * tips:
     * 2 <= n <= 10^5
     * edges.length == n - 1
     * edges[i] == [ui, vi]
     * 0 <= ui, vi < n
     * cost.length == n
     * 1 <= cost[i] <= 10^9
     * 输入保证 edges 表示一棵合法的树。
     * @param n
     * @param edges
     * @param cost
     * @return int
     * @author marks
     * @CreateDate: 2025/8/7 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minIncrease(int n, int[][] edges, int[] cost) {
        int result;
        result = method_01(n, edges, cost);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： n = 5, edges = [[0,4],[0,1],[1,2],[1,3]], cost = [3,4,1,1,7]
     * 输出： 1
     * 1. 构建邻接表, 深度优先遍历, 判断当前节点下的所有子节点的路径得分，求出路径得分的最大值, 和所有路径之和, 但是这种会导致数据溢出, 10^9 * 10^5 = 100亿 > Integer.MAX_VALUE;
     * 2. 这个不太对, 我们需要增加的不是父节点的值, 而是增加子节点的值, 因此计算 ans 时, 需要的是计算子节点的个数(不包括最大分数相同的节点个数), 需要一个Map<Integer, Integer> map。
     * 3. dfs 返回的 int 可能由于数据溢出导致问题, 修改返回值类型为 long
     * AC: 129ms(7.84%)/101.63MB(36.20%)
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     * @param n
     * @param edges
     * @param cost
     * @return int
     * @author marks
     * @CreateDate: 2025/8/7 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int ans = 0;
    private int method_01(int n, int[][] edges, int[] cost) {
        List<Integer>[] graph = buildGraph(edges);
        dfs(graph, 0, -1, cost);
        return ans;
    }

    private long dfs(List<Integer>[] graph, int node, int parent, int[] cost) {
        long max = 0;
        Map<Long, Integer> map = new HashMap<>();
        for (int next : graph[node]) {
            if (next != parent) {
                long temp = dfs(graph, next, node, cost);
                map.put(temp, map.getOrDefault(temp, 0) + 1);
                max = Math.max(max, temp);
            }
        }
        // 移除最大路径得分的节点
        map.remove(max);
        // 遍历map
        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            ans += entry.getValue();
        }
        return cost[node] + max;
    }

    private List<Integer>[] buildGraph(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        return graph;
    }
}
