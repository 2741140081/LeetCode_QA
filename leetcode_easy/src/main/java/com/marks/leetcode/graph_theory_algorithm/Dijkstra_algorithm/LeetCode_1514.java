package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/31 17:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1514 {
    /**
     * @param n
     * @param edges
     * @param succProb
     * @param start_node
     * @param end_node
     * @return double
     * @Description: [
     * <p>给你一个由 n 个节点（下标从 0 开始）组成的无向加权图，该图由一个描述边的列表组成，其中 edges[i] = [a, b] 表示连接节点 a 和 b 的一条无向边，
     * 且该边遍历成功的概率为 succProb[i] 。
     *
     * <p>指定两个节点分别作为起点 start 和终点 end ，请你找出从起点到终点成功概率最大的路径，并返回其成功概率。
     *
     * <p>如果不存在从 start 到 end 的路径，请 返回 0 。只要答案与标准答案的误差不超过 1e-5 ，就会被视作正确答案。
     * tips:
     * 2 <= n <= 10^4
     * 0 <= start, end < n
     * start != end
     * 0 <= a, b < n
     * a != b
     * 0 <= succProb.length == edges.length <= 2*10^4
     * 0 <= succProb[i] <= 1
     * 每两个节点之间最多有一条边
     * <p>
     * 1. Multiplying probabilities will result in precision errors.
     * 1.概率相乘将导致精度误差。
     * <p>
     * 2. Take log probabilities to sum up numbers instead of multiplying them.
     * 2.采用对数概率来求和数字，而不是将它们相乘。
     * <p>
     * 3. Use Dijkstra's algorithm to find the minimum path between the two nodes after negating all costs.
     * 3.使用Dijkstra算法在求出所有代价后，找到两个节点之间的最小路径。
     * ]
     * @author marks
     * @CreateDate: 2025/1/2 9:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        double result;
        result = method_01(n, edges, succProb, start_node, end_node);
        return result;
    }

    /**
     * @Description: [
     * 1. 查看题解的tips, 然后了解了一下对数函数, 即以自然数 底数 e(约为2.71)
     * double total = Math.log(0.5 * 0.5) = Math.log(0.5) + Math.log(0.5);
     * 2. 当概率越小时, Math.log(x) = y, 即 y 的值也越小, 题目需要我们找到最大的
     * 3. 通过观察, 本题节点n, 与边数m想接近, 因此使用小根堆
     * 4. 无向图, 因此节点两端都需要填到lists中
     * 5. 我需要由start开始找
     *
     * AC:46ms/56.60MB
     *
     * 使用 continue 优化一下
     * AC:42ms/57.52MB
     *
     * 在用if 提前剪枝
     * AC:31ms/54.95MB
     * ]
     * @param n
     * @param edges
     * @param succProb
     * @param start_node
     * @param end_node
     * @return double
     * @author marks
     * @CreateDate: 2025/1/2 9:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int n, int[][] edges, double[] succProb, int start_node, int end_node) {
        List<double[]>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            succProb[i] = Math.log(succProb[i]);

            int[] edge = edges[i];
            int x = edge[0];
            int y = edge[1];
            double value = succProb[i];
            lists[x].add(new double[] {y, value});
            lists[y].add(new double[] {x, value});

        }
        double[] dist = new double[n];
        boolean[] visited = new boolean[n];
        Queue<double[]> queue = new PriorityQueue<>(Comparator.comparingDouble((double[] o) -> o[0]).reversed());
        queue.offer(new double[] {0, start_node});
        while (!queue.isEmpty()) {
            double[] curr_node = queue.poll();
            double value = curr_node[0];
            int curr_i = (int) curr_node[1];
            if (curr_i == end_node) {
                return Math.exp(dist[end_node]);
            }
            if (value < dist[curr_i]) {
                // 已经有一条概率更大的路径到达 curr_i 节点了
                continue;
            }
            visited[curr_i] = true;
            for (double[] next_node : lists[curr_i]) {
                int next_j = (int) next_node[0];
                double poss = value + next_node[1];
                if (poss > dist[next_j] || (!visited[next_j] && dist[next_j] == 0)) {
                    dist[next_j] = poss;
                    queue.offer(new double[] {poss, next_j});
                }
            }
        }

        return dist[end_node] == 0 ? dist[end_node] : Math.exp(dist[end_node]);
    }
}
