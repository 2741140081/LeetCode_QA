package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/13 16:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3203 {


    /**
     * @Description:
     * 给你两棵 无向 树，分别有 n 和 m 个节点，节点编号分别为 0 到 n - 1 和 0 到 m - 1 。
     * 给你两个二维整数数组 edges1 和 edges2 ，长度分别为 n - 1 和 m - 1 ，其中 edges1[i] = [ai, bi] 表示在第一棵树中节点 ai 和 bi 之间有一条边，
     * edges2[i] = [ui, vi] 表示在第二棵树中节点 ui 和 vi 之间有一条边。
     *
     * 你必须在第一棵树和第二棵树中分别选一个节点，并用一条边连接它们。
     * 请你返回添加边后得到的树中，最小直径 为多少。
     *
     * 一棵树的 直径 指的是树中任意两个节点之间的最长路径长度。
     *
     * tips:
     * 1 <= n, m <= 10^5
     * edges1.length == n - 1
     * edges2.length == m - 1
     * edges1[i].length == edges2[i].length == 2
     * edges1[i] = [ai, bi]
     * 0 <= ai, bi < n
     * edges2[i] = [ui, vi]
     * 0 <= ui, vi < m
     * 输入保证 edges1 和 edges2 分别表示一棵合法的树。
     *
     * @param edges1
     * @param edges2
     * @return int
     * @author marks
     * @CreateDate: 2025/8/13 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumDiameterAfterMerge(int[][] edges1, int[][] edges2) {
        int result;
        result = method_01(edges1, edges2);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：edges1 = [[0,1],[0,2],[0,3],[2,4],[2,5],[3,6],[2,7]], edges2 = [[0,1],[0,2],[0,3],[2,4],[2,5],[3,6],[2,7]]
     * 输出：5
     *
     * 1. 要找到最小直径, 即树1和树2的节点 x, y; 到其他节点的距离最小, 那么 x, y 之间的距离就是最小直径
     * 2. 树1和树2的节点 x, y; 那么该怎么找到这个节点x, y,
     * 3. 直接遍历树1, dfs, 时间复杂度是 O(n^2) = 10^10, 大概率超时!
     * 4. 修改思路, 通过dfs遍历树1, 找到树1的直径, 然后再遍历树2, 找到树2的直径, 然后再计算两棵树的半径, 然后返回三者中的最大值
     * AC: 132ms(14.03%)/112.18MB(87.72%)
     * @param edges1
     * @param edges2
     * @return int
     * @author marks
     * @CreateDate: 2025/8/13 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans; // 存储树的最大直径
    private int method_01(int[][] edges1, int[][] edges2) {
        List<Integer>[] graph1 = buildGraph(edges1);
        List<Integer>[] graph2 = buildGraph(edges2);

        // 计算第一棵树的直径
        ans = 0;
        dfs(graph1, 0, -1);
        int diameter1 = ans;

        // 计算第二棵树的直径
        ans = 0;
        dfs(graph2, 0, -1);
        int diameter2 = ans;

        // 计算两棵树的半径
        int radius1 = (diameter1 + 1) / 2;
        int radius2 = (diameter2 + 1) / 2;

        // 连接后新树的直径是三者中的最大值
        return Math.max(Math.max(diameter1, diameter2), radius1 + radius2 + 1);
    }

    private int dfs(List<Integer>[] graph, int node, int parent) {
        int max1 = 0;
        int max2 = 0;
        for (int next : graph[node]) {
            if (next != parent) {
                int childValue = dfs(graph, next, node);
                if (childValue > max1) {
                    max2 = max1;
                    max1 = childValue;
                } else if (childValue > max2) {
                    max2 = childValue;
                }
            }
        }
        ans = Math.max(ans, max1 + max2);
        return max1 + 1;
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
