package com.marks.leetcode.graph_theory;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2699 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 11:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2699 {

    /**
     * @Description:
     * 给你一个 n 个节点的 无向带权连通 图，节点编号为 0 到 n - 1 ，
     * 再给你一个整数数组 edges ，其中 edges[i] = [ai, bi, wi] 表示节点 ai 和 bi 之间有一条边权为 wi 的边。
     * 部分边的边权为 -1（wi = -1），其他边的边权都为 正 数（wi > 0）。
     * 你需要将所有边权为 -1 的边都修改为范围 [1, 2 * 10^9] 中的 正整数 ，使得从节点 source 到节点 destination 的 最短距离 为整数 target 。
     * 如果有 多种 修改方案可以使 source 和 destination 之间的最短距离等于 target ，你可以返回任意一种方案。
     *
     * 如果存在使 source 到 destination 最短距离为 target 的方案，请你按任意顺序返回包含所有边的数组（包括未修改边权的边）。
     * 如果不存在这样的方案，请你返回一个 空数组 。
     * 注意：你不能修改一开始边权为正数的边。
     * tips:
     * 1 <= n <= 100
     * 1 <= edges.length <= n * (n - 1) / 2
     * edges[i].length == 3
     * 0 <= ai, bi < n
     * wi = -1 或者 1 <= wi <= 10^7
     * ai != bi
     * 0 <= source, destination < n
     * source != destination
     * 1 <= target <= 10^9
     * 输入的图是连通图，且没有自环和重边。
     * @param: n
     * @param: edges
     * @param: source
     * @param: destination
     * @param: target
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/15 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {
        int[][] result;
        result = method_01(n, edges, source, destination, target);
        return result;
    }

    /**
     * @Description:
     * 1. 需要修改所有为负数的边的边权, 并且不能修改正数边权
     * 2. 找最短路径, 使用 Dijkstra 算法, 找到最短距离
     * 3. 什么情况下返回空数组? 假设我们将所有负边权修改为1. 此时可以得到 s -> d 的最短距离 dist[d1], 假设 dist[d1] > target, 返回空数组
     * 4. 或者将负边修改为 2 * 10^9, 此时可以得到 s -> d 的最短距离 dist[d2], 假设 dist[d2] < target, 同样返回空数组
     * 5. 上面两个dist[d] 相当于是最小值和最大值, min = dist[d1], max = dist[d2], 如果 min <= target <= max, 则存在可能性, 否则返回空数组
     * 6. 假设存在可行方案, 如何针对每一条边给出值? int sub = target - min;
     * 7. 然后就需要考虑 edge[x][y] = -1, x,y 直接存在一条负边,  dist[x] = a, dist[y] = b. edge[x][y] = 1.int s = Math.abs(a - b) == 1. 说明这条边被使用了
     * 8. 然后是枚举所有负边进行修改, int[] adj = {1,1,1,...,1} => 到 int[] adj = {sub,sub,sub,....,sub} 从这些可能性中找到任意一种可能性使得 dist[d] = target
     * 9. 直接看官方题解吧, 很难想出来， need todo.
     * AC: 127ms/55.54MB
     * @param: n
     * @param: edges
     * @param: source
     * @param: destination
     * @param: target
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/15 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int n, int[][] edges, int source, int destination, int target) {
        int k = 0;
        for (int[] e : edges) {
            if (e[2] == -1) {
                ++k;
            }
        }

        // 判断无解情况
        // 1. 负边全部设置为1
        if (dijkstra(source, destination, construct(n, edges, 0, target)) > target) {
            return new int[0][];
        }
        // 2. 负边全部设置为 target
        if (dijkstra(source, destination, construct(n, edges, (long) k * (target - 1), target)) < target) {
            return new int[0][];
        }

        long left = 0, right = (long) k * (target - 1), ans = 0;
        while (left <= right) {
            long mid = (left + right) / 2;
            if (dijkstra(source, destination, construct(n, edges, mid, target)) >= target) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // 得到使得最短距离为 target 的方案 的 ans, 修改edges[][]
        for (int[] e : edges) {
            if (e[2] == -1) {
                if (ans >= target - 1) {
                    e[2] = target;
                    ans -= target - 1;
                } else {
                    e[2] = (int) (1 + ans);
                    ans = 0;
                }
            }
        }

        return edges;
    }

    public long dijkstra(int source, int destination, int[][] adjMatrix) {
        // 朴素的 dijkstra 算法
        // adjMatrix 是一个邻接矩阵
        int n = adjMatrix.length;
        long[] dist = new long[n];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        boolean[] used = new boolean[n];
        dist[source] = 0;

        for (int round = 0; round < n - 1; ++round) {
            int u = -1;
            for (int i = 0; i < n; ++i) {
                if (!used[i] && (u == -1 || dist[i] < dist[u])) {
                    u = i;
                }
            }
            used[u] = true;
            for (int v = 0; v < n; ++v) {
                if (!used[v] && adjMatrix[u][v] != -1) {
                    dist[v] = Math.min(dist[v], dist[u] + adjMatrix[u][v]);
                }
            }
        }

        return dist[destination];
    }

    /**
     * @Description:
     * 1. 按照类似与这种方式修改负边的值，使得最短距离为 target, 并且由于增加值是一个单调的过程
     * 2. 即 dp[i] = {dp[i - 1], dp[i - 1] + 1}, 给一条边增加1, 则如果对最终结果有影响, 则最终结果加1, 如果没有影响，则最终结果不变
     * [1, 1, 1,⋯,1]
     * [2,1,1,⋯,1]
     * [3,1,1,⋯,1]
     * ⋯
     * [D,1,1,⋯,1]
     * [D,2,1,⋯,1]
     * [D,3,1,⋯,1]
     * ⋯
     * [D,D,D,⋯,D]
     * @param: n
     * @param: edges
     * @param: idx
     * @param: target
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/01/15 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] construct(int n, int[][] edges, long idx, int target) {
        // 需要构造出第 idx 种不同的边权情况，返回一个邻接矩阵
        int[][] adjMatrix = new int[n][n];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(adjMatrix[i], -1);
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            if (w != -1) {
                adjMatrix[u][v] = adjMatrix[v][u] = w;
            } else {
                if (idx >= target - 1) {
                    adjMatrix[u][v] = adjMatrix[v][u] = target;
                    idx -= (target - 1);
                } else {
                    adjMatrix[u][v] = adjMatrix[v][u] = (int) (1 + idx);
                    idx = 0;
                }
            }
        }
        return adjMatrix;
    }

}
