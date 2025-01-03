package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/3 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1976 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * <p>你在一个城市里，城市由 n 个路口组成，路口编号为 0 到 n - 1 ，某些路口之间有 双向 道路。
     * 输入保证你可以从任意路口出发到达其他任意路口，且任意两个路口之间最多有一条路。
     *
     * <p>给你一个整数 n 和二维整数数组 roads ，其中 roads[i] = [ui, vi, time_i] 表示在路口 ui 和 vi 之间有一条需要花费 time_i 时间才能通过的道路。
     * 你想知道花费 最少时间 从路口 0 出发到达路口 n - 1 的方案数。
     *
     * <p>请返回花费 最少时间 到达目的地的 路径数目 。由于答案可能很大，将结果对 109 + 7 取余 后返回。
     * <p>tips:
     * 1 <= n <= 200
     * n - 1 <= roads.length <= n * (n - 1) / 2
     * roads[i].length == 3
     * 0 <= ui, vi <= n - 1
     * 1 <= time_i <= 10^9
     * ui != vi
     * 任意两个路口之间至多有一条路。
     * 从任意路口出发，你能够到达其他任意路口。
     * ]
     * @param n 
     * @param roads 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/3 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPaths(int n, int[][] roads) {
        int result;
        result = method_01(n, roads);
        return result;
    }

    /**
     * @Description: [
     * 思路
     * <p> 1. 先求出0 -> n - 1最短的路径, 通过最短路径, 计算路径总数
     * <p> Result: 16/55 存在问题, A -> B, A -> C, 然后 C -> B, 类似于这种, 我用的方式会漏算
     * <p> 查看官解题解, 也是使用DP, 但是他使用的很巧妙, 直接在 求解最短路径时, 进行DP的计算
     * <p> if (spend < dist[next_j]) {
     * <p>   dist[next_j] = spend;
     * <p>   dp[next_j] = dp[curr_i];
     * <p>   listQueue.offer(new long[] {spend, next_j});
     * <p>} else if (spend == dist[next_j]) {
     * <p>    dp[next_j] = (dp[next_j] + dp[curr_i]) % MOD;
     * <p>}
     *
     * ]
     * @param n 
     * @param roads 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/3 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] roads) {
        final long INF = Long.MAX_VALUE / 2;
        long[] dist = new long[n];
        List<int[]>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            dist[i] = INF;
            lists[i] = new ArrayList<>();
        }
        for (int[] road : roads) {
            int x = road[0];
            int y = road[1];
            int time = road[2];
            lists[x].add(new int[] {y, time});
            lists[y].add(new int[] {x, time});
        }
        int[] dp = new int[n];
        dp[0] = 1;
        Queue<long[]> listQueue = new PriorityQueue<>(Comparator.comparingLong(o -> o[0]));
        dist[0] = 0;
        listQueue.offer(new long[] {0, 0});
        while (!listQueue.isEmpty()) {
            long[] curr_node = listQueue.poll();
            long ds = curr_node[0];
            int curr_i = (int) curr_node[1];

            if (ds > dist[curr_i]) {
                continue;
            }

            for (int[] next_node : lists[curr_i]) {
                int next_j = next_node[0];
                long spend = next_node[1] + ds;
                if (spend < dist[next_j]) {
                    dist[next_j] = spend;
                    dp[next_j] = dp[curr_i];
                    listQueue.offer(new long[] {spend, next_j});
                } else if (spend == dist[next_j]) {
                    dp[next_j] = (dp[next_j] + dp[curr_i]) % MOD;
                }
            }
        }

        return dp[n - 1];
    }
}
