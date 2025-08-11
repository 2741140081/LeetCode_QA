package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/7 9:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3558 {
    private static final int MOD = (int) 1e9 + 7;

    /**
     * @Description:
     * 给你一棵 n 个节点的无向树，节点从 1 到 n 编号，树以节点 1 为根。
     * 树由一个长度为 n - 1 的二维整数数组 edges 表示，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间有一条边。
     *
     * 一开始，所有边的权重为 0。你可以将每条边的权重设为 1 或 2。
     * 两个节点 u 和 v 之间路径的 代价 是连接它们路径上所有边的权重之和。
     *
     * 选择任意一个 深度最大 的节点 x。
     * 返回从节点 1 到 x 的路径中，边权重之和为 奇数 的赋值方式数量。
     * 由于答案可能很大，返回它对 10^9 + 7 取模的结果。
     * 注意： 忽略从节点 1 到节点 x 的路径外的所有边。
     *
     * tips:
     * 2 <= n <= 10^5
     * edges.length == n - 1
     * edges[i] == [ui, vi]
     * 1 <= ui, vi <= n
     * edges 表示一棵合法的树。
     *
     * @param edges
     * @return int
     * @author marks
     * @CreateDate: 2025/8/7 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int assignEdgeWeights(int[][] edges) {
        int result;
        result = method_01(edges);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： edges = [[1,2],[1,3],[3,4],[3,5]]
     * 输出： 2
     * 1. 需要找到 最大深度的节点, 最大深度节点可能存在多个, 任取一个, 即节点x为最大深度节点之一, 并且深度为 d.
     * 2. 最开始的节点1到节点x的权重和为0, 即偶数, 如果需要权重和为奇数, 就需要将其中一条边的权重设为1, 其他边的权重可以为 0 或者 2, 即权重和为奇数.
     * 3. 假设我只有一条边权重设置为1, 可能的方式有 d 种, 剩余 d - 1 条边, 可能数为 2^(d - 1), 即 d + 2^(d -1)
     * 4. 可以枚举奇数条边的数量, 通过for循环枚举, i = 1; i < d; i += 2 , 组合数为 A(d, i) + 2^(d - i)
     * 5. 可以用上面的思路试试看, 首先通过 BFS 找到最大深度 d, 然后枚举 1 的数量来计算总的组合数。
     * 6. 看了一下, 必须要对边进行赋值操作, 也就是边权重为0不行, 只能设置为1或者2.
     * 7. 也就是说只需要计算边为1的组合数就行, 其他边全部会被赋值为2, 不需要处理
     * @param edges 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/7 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] edges) {
        List<Integer>[] graph = buildGraph(edges);
        boolean[] visited = new boolean[edges.length + 2];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        visited[1] = true;
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();
                for (int next : graph[cur]) {
                    if (!visited[next]) {
                        queue.add(next);
                        visited[next] = true;
                    }
                }
            }
            depth++;
        }
        // 最大深度减1
        depth -= 1;

        // 计算组合数, 奇数项之和等于2^(depth - 1)
        long result = pow(2, depth - 1);
        return (int) result;
    }

    /**
     * @Description:
     * 1. 数学原理‌：奇数项组合数之和等于 2^(n -1), 直接通过快速幂计算
     * 2. 使用快速幂计算处理大指数取模, 时间复杂度O(log n)
     * @param x
     * @param n
     * @return long
     * @author marks
     * @CreateDate: 2025/8/7 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }

    private List<Integer>[] buildGraph(int[][] edges) {
        int n = edges.length + 2;
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
