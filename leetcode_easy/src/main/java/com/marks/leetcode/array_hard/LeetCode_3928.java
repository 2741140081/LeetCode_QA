package com.marks.leetcode.array_hard;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3928 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/10 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3928 {

    /**
     * @Description:
     * 给你一个整数 n 和一个长度为 n 的整数数组 prices，其中 prices[i] 表示商店 i 中苹果的价格。
     * 另给定一个二维整数数组 roads，其中 roads[i] = [ui, vi, costi, taxi] 表示一条 双向 道路：
     * ui 和 vi 是该道路连接的两个商店。
     * costi 表示在 不携带苹果 时通过该道路的花费。
     * taxi 表示在 携带苹果 时，该道路费用相对于 costi 的乘数。
     * 对于每个商店 i，你可以选择其中之一：
     * 直接在商店 i 购买苹果，花费为 prices[i]。
     * 以 空手 状态，通过 任意数量 的道路前往任意一家商店 j，以 prices[j] 的价格购买苹果，然后携带苹果返回商店 i。
     * 返回途中，每条道路的费用为 cost * tax。
     * 前往商店时（空手）和返回时（携带苹果）所经过的路径可以 不同。
     * 返回一个长度为 n 的整数数组 ans，其中 ans[i] 表示从商店 i 出发购买到苹果所需的 最小 总花费。
     *
     * tips:
     * 1 <= n <= 1000
     * prices.length == n
     * 1 <= prices[i] <= 10^9
     * 0 <= roads.length <= min(n × (n - 1) / 2, 2000)
     * roads[i] = [ui, vi, costi, taxi]
     * 0 <= ui, vi <= n - 1
     * ui != vi
     * 1 <= costi <= 10^9
     * 1 <= taxi <= 100
     * 不存在重复边。
     * @param: n
     * @param: prices
     * @param: roads
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/10 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] minCost(int n, int[] prices, int[][] roads) {
        int[] result;
        result = method_01(n, prices, roads);
        return result;
    }

    private int n;
    private long INF;

    /**
     * @Description:
     * 1. 先对 roads 构建邻接表, 然后对 prices 进行升序排序,
     * 2. 排序之后, price[i] <= prices[i + 1], 所以 i 只能选择到 [0 ~ i - 1] 的商店中购买,
     * 才有机会得到更小值的花费
     * 3. 构建一个 dist[][], dist[i][j] 表示从商店 i 到商店 j 的最小路程费用, 并且还需要一个数组, 记录
     * 携带苹果后的路程花费 distWithApple[i][j]
     * 4. 计算节点之间的最短路程费用, 使用 Dijkstra 算法
     * 超时: 999/1000
     * 5. 添加剪枝操作, 减少无用的道路
     * AC: 1294ms/282.1MB
     * 6. 如果继续优化, 还行继续对道路和节点进行剪枝
     * @param: n
     * @param: prices
     * @param: roads
     * @return int[]
     * @author marks
     * @CreateDate: 2026/09/10 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[] prices, int[][] roads) {
        this.n = n;
        INF = Long.MAX_VALUE / 2;
        // 先对 prices 进行间接排序
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, Comparator.comparingInt(a -> prices[a]));
        int maxPrice = prices[idx[n - 1]];
        // 构建邻接表
        List<int[]>[] adj = new List[n];
        List<int[]>[] adjWithApple = new List[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            adjWithApple[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            int u = road[0];
            int v = road[1];
            int cost = road[2];
            int tax = road[3];
            // 需要进行剪枝, 删除不必要的道路
            if (cost < maxPrice) {
                adj[u].add(new int[]{v, cost, tax});
                adj[v].add(new int[]{u, cost, tax});
            }
            if (((long) cost * tax) < maxPrice) {
                adjWithApple[u].add(new int[]{v, cost, tax});
                adjWithApple[v].add(new int[]{u, cost, tax});
            }
        }


        // 使用 Dijkstra 算法, 得到从商店 i 到商店 j 的最小路程费用
        long[][] dist = new long[n][n];
        long[][] distWithApple = new long[n][n];
        // 初始化 -1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = INF;
                distWithApple[i][j] = INF;
            }
        }
        for (int i = 0; i < n; i++) {
            // 创建 Dijkstra 算法
            getMinDist(adj, i, dist, false);
            getMinDist(adjWithApple, i, distWithApple, true);
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int from = idx[i];
            long minCost = prices[from];
            for (int j = 0; j < i; j++) {
                int to = idx[j]; // 当前在 from 商店, 需要前往 to 商店购买苹果并返回
                if (dist[from][to] == INF || distWithApple[to][from] == INF) {
                    continue;
                }
                minCost = Math.min(minCost, dist[from][to] + distWithApple[to][from] + prices[to]);
            }
            ans[from] = (int) minCost;
        }

        return ans;
    }

    private void getMinDist(List<int[]>[] adj, int start, long[][] dist, boolean isTaxApple) {
        long[] curr = new long[n];
        Arrays.fill(curr, INF);
        curr[start] = 0;
        // 构建优先队列
        PriorityQueue<long[]> queue = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        queue.add(new long[]{0, start});
        while (!queue.isEmpty()) {
            long[] currNode = queue.poll();
            long distTo = currNode[0];
            int node = (int) currNode[1];
            if (distTo > curr[node]) {
                continue;
            }
            for (int[] neighbor : adj[node]) {
                int nextNode = neighbor[0];
                int cost = neighbor[1];
                int tax = neighbor[2];
                long distToNextNode = isTaxApple ? distTo + (long) cost * tax : distTo + cost;
                if (distToNextNode < curr[nextNode]) {
                    curr[nextNode] = distToNextNode;
                    queue.add(new long[]{distToNextNode, nextNode});
                }
            }
        }
        dist[start] = curr;
    }

}
