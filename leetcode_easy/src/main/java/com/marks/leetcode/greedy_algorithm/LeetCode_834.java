package com.marks.leetcode.greedy_algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_834 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/6 10:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_834 {

    /**
     * @Description:
     * 给定一个无向、连通的树。树中有 n 个标记为 0...n-1 的节点以及 n-1 条边 。
     * 给定整数 n 和数组 edges ， edges[i] = [ai, bi]表示树中的节点 ai 和 bi 之间有一条边。
     * 返回长度为 n 的数组 answer ，其中 answer[i] 是树中第 i 个节点与所有其他节点之间的距离之和。
     * @param: n
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/06 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        int[] result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 看官方题解写的, 自己还不能完整写出, 需要后续自行理解或者下次在做一次
     * AC: 62ms/109.5MB
     * @param: n
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/06 10:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] ans;
    private int[] sz; // 节点的子树中节点的个数
    private int[] dp;
    private List<Integer>[] graph;
    private int[] method_01(int n, int[][] edges) {
        ans = new int[n];
        sz = new int[n];
        dp = new int[n];
        graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        dfs1(0, -1);
        dfs2(0, -1);
        return ans;
    }

    private void dfs1(int u, int f) {
        sz[u] = 1;
        dp[u] = 0;
        for (int v : graph[u]) {
            if (v == f) {
                continue;
            }
            dfs1(v, u);
            dp[u] += dp[v] + sz[v];
            sz[u] += sz[v];
        }
    }

    private void dfs2(int u, int f) {
        ans[u] = dp[u];
        for (int v : graph[u]) {
            if (v == f) {
                continue;
            }
            int pu = dp[u], pv = dp[v];
            int su = sz[u], sv = sz[v];
            dp[u] -= dp[v] + sz[v];
            sz[u] -= sz[v];
            dp[v] += dp[u] + sz[u];
            sz[v] += sz[u];

            dfs2(v, u); // 换根

            // 恢复
            dp[u] = pu;
            sz[u] = su;
            dp[v] = pv;
            sz[v] = sv;
        }
    }

}
