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
 * @date 2025/8/8 14:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2872 {
    private int ans = 0;

    /**
     * @Description:
     * 给你一棵 n 个节点的无向树，节点编号为 0 到 n - 1 。
     * 给你整数 n 和一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 有一条边。
     * 同时给你一个下标从 0 开始长度为 n 的整数数组 values ，其中 values[i] 是第 i 个节点的 值 。
     * 再给你一个整数 k 。
     * 你可以从树中删除一些边，也可以一条边也不删，得到若干连通块。一个 连通块的值 定义为连通块中所有节点值之和。如果所有连通块的值都可以被 k 整除，那么我们说这是一个 合法分割 。
     *
     * 请你返回所有合法分割中，连通块数目的最大值 。
     *
     * tips:
     * 1 <= n <= 3 * 10^4
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * values.length == n
     * 0 <= values[i] <= 10^9
     * 1 <= k <= 10^9
     * values 之和可以被 k 整除。
     * 输入保证 edges 是一棵无向树。
     * @param n
     * @param edges
     * @param values
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/8/8 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        int result;
        result = method_01(n, edges, values, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 7, edges = [[0,1],[0,2],[1,3],[1,4],[2,5],[2,6]], values = [3,0,6,1,5,2,1], k = 3
     * 输出：3
     * 1. 根据已知条件, sum(values) % k == 0, 通过自底向上的深度优先遍历, 判断当前节点的值 + 子树节点的值之和 % k == 0, 则当前节点合法, ans++。
     * 2. 深度优先遍历 dfs 的过程中, 返回的是子树节点值之和 + 当前节点值, 用于返回上层节点。不需要对合法的节点值进行重置为0。
     * 3. 原因是, 假设当前节点之和为 sum, 左右子树各有一个合法节点, 值都是 k, 剩余的节点之和为 sum - 2 * k, 则 (sum - 2 * k) % k == 0, 剩余的节点合法。
     * 4. 还有一个问题, 节点的值可能为0, 这就导致节点值为0的节点合法。但是我们都知道节点值为0是不合法的, 对于这些节点值为0的节点, 我们需要直接跳过。
     * 5. 最大节点之和 = 3* 10^13, 这个值超过了Integer.MAX_VALUE, 所以我们需要使用long类型。
     * 6. 等等, 需要对节点值为0的节点进行特殊判断, 假设节点值为0的节点的左右子树都是合法的, 那么节点值为0的节点可以跳过,
     * 但是如果左右子树都不合法, 那么节点值为0的节点就是一个连接点的作用, 此时不能跳过, 则 ans++;
     * 7. 调整一下返回的值, 返回 sum % k 取模后的值.
     * 8. 这个整除我的理解错误了, 节点值为0的节点是合法的, 因为0 / k = 0, 节点值为0的节点合法。我的理解是节点之和是k的倍数, 所以我错了。
     * AC: 30ms(8.33%)/60.30MB(58.33%)
     * @param n
     * @param edges
     * @param values
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/8/8 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, int[] values, int k) {
        List<Integer>[] graph = buildGraph(edges);

        dfs(0, -1, graph, values, k);
        return ans;
    }

    private long dfs(int node, int parent, List<Integer>[] graph, int[] values, int k) {
        long sum = values[node];

        for (int next : graph[node]) {
            if (next != parent) {
                long childValue = dfs(next, node, graph, values, k);
                sum += childValue;
            }
        }

        if (sum % k == 0 ) {
            ans++;
        }
        return sum % k;
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
