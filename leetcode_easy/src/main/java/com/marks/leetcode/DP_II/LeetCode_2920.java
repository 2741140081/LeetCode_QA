package com.marks.leetcode.DP_II;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2920 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/20 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2920 {

    /**
     * @Description:
     * 有一棵由 n 个节点组成的无向树，以 0  为根节点，节点编号从 0 到 n - 1 。
     * 给你一个长度为 n - 1 的二维 整数 数组 edges ，其中 edges[i] = [ai, bi] 表示在树上的节点 ai 和 bi 之间存在一条边。
     * 另给你一个下标从 0 开始、长度为 n 的数组 coins 和一个整数 k ，其中 coins[i] 表示节点 i 处的金币数量。
     *
     * 从根节点开始，你必须收集所有金币。要想收集节点上的金币，必须先收集该节点的祖先节点上的金币。
     * 节点 i 上的金币可以用下述方法之一进行收集：
     *
     * 收集所有金币，得到共计 coins[i] - k 点积分。如果 coins[i] - k 是负数，你将会失去 abs(coins[i] - k) 点积分。
     * 收集所有金币，得到共计 floor(coins[i] / 2) 点积分。如果采用这种方法，节点 i 子树中所有节点 j 的金币数 coins[j] 将会减少至 floor(coins[j] / 2) 。
     * 返回收集 所有 树节点的金币之后可以获得的最大积分。
     *
     * tips:
     * n == coins.length
     * 2 <= n <= 10^5
     * 0 <= coins[i] <= 10^4
     * edges.length == n - 1
     * 0 <= edges[i][0], edges[i][1] < n
     * 0 <= k <= 10^4
     * @param: edges
     * @param: coins
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/20 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumPoints(int[][] edges, int[] coins, int k) {
        int result;
        result = method_01(edges, coins, k);
        result = method_02(edges, coins, k);
        return result;
    }


    /**
     * @Description:
     * 查看官方题解, need todo: 需要自行理解
     * AC: 149ms/183.4MB
     * @param: edges
     * @param: coins
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/20 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] edges, int[] coins, int k) {
        int n = coins.length;
        List<List<Integer>> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            children.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            children.get(edge[0]).add(edge[1]);
            children.get(edge[1]).add(edge[0]);
        }
        int[][] memo = new int[n][14];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, -1, 0, coins, k, children, memo);
    }

    private int dfs(int node, int parent, int f, int[] coins, int k, List<List<Integer>> children, int[][] memo) {
        if (memo[node][f] != -1) {
            return memo[node][f];
        }
        int res0 = (coins[node] >> f) - k;
        int res1 = coins[node] >> (f + 1);
        for (int child : children.get(node)) {
            if (child == parent) {
                continue;
            }
            res0 += dfs(child, node, f, coins, k, children, memo);
            if (f + 1 < 14) {
                res1 += dfs(child, node, f + 1, coins, k, children, memo);
            }
        }
        memo[node][f] = Math.max(res0, res1);
        return memo[node][f];
    }


    /**
     * @Description:
     * 1. 动态规划问题
     * 2. 需要构建邻接表
     * 3. int[][] dp = new int[n][t], 其中 t 表示已经执行的操作2的次数, 最大次数为 14次
     * 4. 需要记录递归的深度, 使用优先队列
     * 5. int ans = 0; 当节点没有孩子
     * 6. 添加测试用例, 开始执行debug
     * 7. 存在问题, 由于当前我是找叶子节点的所有次数中的最大值, 但是这个最大值是会互相影响的, 例如 节点 i 有2个子节点, 分别是 i1, i2.
     * 如果当前 i 节点的最大深度是 d, 那么可能 i1 在 d1 下达到最大, 但是 i2 在 d2 下到达最大, 这样就会造成错误, 因为 i1, i2 是会相互影响的
     * @param: edges
     * @param: coins
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/20 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] edges, int[] coins, int k) {
        int n = coins.length;
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 添加一个入度表, 用来判断节点是否是叶子节点, 节点0是树的根节点
        int[] inDegree = new int[n];
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
            inDegree[v]++;
            inDegree[u]++;
        }
        // 先初始化 dp[][], 即根节点的初始化
        int[][] dp = new int[n][15];
        dp[0][0] = coins[0] - k; // 方案1
        dp[0][1] = coins[0] / 2; // 方案2
        boolean[] vis = new boolean[n];
        // 用普通队列即可
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        vis[0] = true;
        int depth = 1;
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int node = queue.poll();
                for (int next : graph[node]) {
                    if (!vis[next]) {
                        vis[next] = true;
                        // 更新dp
                        for (int j = 0; j <= depth; j++) {
                            // 对next 节点执行方案1操作, 方案2次数不变
                            dp[next][j] = dp[node][j] + coins[next] - k;
                            // 对next 节点执行方案2操作, 操作次数加1, 并且取较大值
                            if (depth <= 13) {
                                dp[next][j + 1] = Math.max(dp[next][j + 1], dp[node][j] + coins[next] / 2);
                            }
                        }
                        // 添加到队列中
                        queue.offer(next);
                    }
                }
                // 判断node 节点是否是叶子节点
                if (inDegree[node] == 1 && node != 0) {
                    // 找到当前depth 下的最大值
                    int max = Integer.MIN_VALUE;
                    for (int j = 0; j <= depth; j++) {
                        max = Math.max(max, dp[node][j]);
                    }
                    ans += max;
                }
            }
            depth++;
        }

        return ans;
    }

}
