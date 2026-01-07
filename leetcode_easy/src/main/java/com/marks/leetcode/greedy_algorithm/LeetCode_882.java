package com.marks.leetcode.greedy_algorithm;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_882 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 9:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_882 {

    /**
     * @Description:
     * 给你一个无向图（原始图），图中有 n 个节点，编号从 0 到 n - 1 。你决定将图中的每条边 细分 为一条节点链，每条边之间的新节点数各不相同。
     * 图用由边组成的二维数组 edges 表示，其中 edges[i] = [ui, vi, cnti] 表示原始图中节点 ui 和 vi 之间存在一条边，cnti 是将边 细分 后的新节点总数。
     * 注意，cnti == 0 表示边不可细分。
     * 要 细分 边 [ui, vi] ，需要将其替换为 (cnti + 1) 条新边，和 cnti 个新节点。
     * 新节点为 x1, x2, ..., xcnti ，新边为 [ui, x1], [x1, x2], [x2, x3], ..., [xcnti-1, xcnti], [xcnti, vi] 。
     * 现在得到一个 新的细分图 ，请你计算从节点 0 出发，可以到达多少个节点？如果节点间距离是 maxMoves 或更少，则视为 可以到达 。
     * 给你原始图和 maxMoves ，返回 新的细分图中从节点 0 出发 可到达的节点数 。
     * tips:
     * 0 <= edges.length <= min(n * (n - 1) / 2, 10^4)
     * edges[i].length == 3
     * 0 <= ui < vi < n
     * 图中 不存在平行边
     * 0 <= cnti <= 10^4
     * 0 <= maxMoves <= 10^9
     * 1 <= n <= 3000
     * @param: edges
     * @param: maxMoves
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/01/07 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        int result;
        result = method_01(edges, maxMoves, n);
        return result;
    }

    /**
     * @Description:
     * 1. 创建一个二维数组，记录每个节点的相邻节点和距离。
     * 2. 怎么考虑节点及其细分节点是否由于深度优先搜索造成重复访问? 三维数组来存储节点及其细分节点信息 boolean[][][] visitedDetail = new boolean[n][n][max_cnt_i];
     * 三维数组的空间复杂度太高了, 应该是不适合的.
     * 3. 查看官方题解, 使用Dijkstra_algorithm
     * 4. 需要自己慢慢理解, 先CV一下官方的代码, need todo
     * AC: 47ms/58.23MB
     * @param: edges
     * @param: maxMoves
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/01/07 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] edges, int maxMoves, int n) {
        // 构建邻接表, 存储相邻节点, 及其分割节点信息
        List<int[]>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<int[]>();
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], nodes = edge[2];
            graph[u].add(new int[]{v, nodes});
            graph[v].add(new int[]{u, nodes});
        }
        Map<Integer, Integer> used = new HashMap<Integer, Integer>();
        // visited[i] 表示节点 i 是否被访问过
        Set<Integer> visited = new HashSet<Integer>();
        int reachableNodes = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0});

        while (!pq.isEmpty() && pq.peek()[0] <= maxMoves) {
            int[] pair = pq.poll();
            int step = pair[0], u = pair[1];
            if (!visited.add(u)) {
                continue;
            }
            reachableNodes++; // 所有能到达的节点数
            for (int[] next : graph[u]) {
                int v = next[0], nodes = next[1];
                if (nodes + step + 1 <= maxMoves && !visited.contains(v)) {
                    pq.offer(new int[]{nodes + step + 1, v});
                }
                used.put(encode(u, v, n), Math.min(nodes, maxMoves - step));
            }
        }

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], nodes = edge[2];
            // 所有能到达的细分节点数
            reachableNodes += Math.min(nodes, used.getOrDefault(encode(u, v, n), 0) + used.getOrDefault(encode(v, u, n), 0));
        }
        return reachableNodes;
    }

    private int encode(int u, int v, int n) {
        return u * n + v;
    }

}
