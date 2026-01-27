package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3015 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/23 9:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3015 {

    /**
     * @Description:
     * 给你三个 正整数 n 、x 和 y 。
     * 在城市中，存在编号从 1 到 n 的房屋，由 n 条街道相连。
     * 对所有 1 <= i < n ，都存在一条街道连接编号为 i 的房屋与编号为 i + 1 的房屋。
     * 另存在一条街道连接编号为 x 的房屋与编号为 y 的房屋。
     * 对于每个 k（1 <= k <= n），你需要找出所有满足要求的 房屋对 [house1, house2] ，即从 house1 到 house2 需要经过的 最少 街道数为 k 。
     * 返回一个下标从 1 开始且长度为 n 的数组 result ，其中 result[k] 表示所有满足要求的房屋对的数量，即从一个房屋到另一个房屋需要经过的 最少 街道数为 k 。
     * 注意，x 与 y 可以 相等 。
     * tips:
     * 2 <= n <= 100
     * 1 <= x, y <= n
     * @param: n
     * @param: x
     * @param: y
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/23 9:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countOfPairs(int n, int x, int y) {
        int[] result;
        result = method_01(n, x, y);
        return result;
    }

    /**
     * @Description:
     * E1: n = 5, x = 2, y = 4
     * 1. x == y, 那么就是一个x的自环
     * 2. 求解节点直接的最短路, 使用 Dijkstra 算法, 如果是求最短距离, 那么自环可以忽略(因为自环除了给自身增加距离没有任何用处)
     * 3. 假设 int[] dist; 为节点0 (实际节点1) 到达其他节点的最短距离
     * 4. 然后执行换根操作, 即从节点0 -> 节点1 的换根, 并且更新 dist 数组, 不能用换根解决, 因为子集与最短路径的关系无法确定
     * 5. 直接暴力吧, 求解每个节点下的最短距离, 然后将结果相加
     * AC: 55ms/46.2MB
     * @param: n
     * @param: x
     * @param: y
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/23 9:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int x, int y) {
        int[] ans = new int[n];
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < n - 1; i++) {
            int u = i;
            int v = i + 1;
            graph[u].add(v);
            graph[v].add(u);
        }
        // 特殊节点处理
        x--;
        y--;
        if (x == y) {
            graph[x].add(y);
        } else {
            graph[x].add(y);
            graph[y].add(x);
        }

        for (int i = 0; i < n; i++) {
            dfsChangeRoot(i, graph, ans);
        }

        return ans;
    }

    private void dfsChangeRoot(int i, List<Integer>[] graph, int[] ans) {
        int n = graph.length;
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[i] = 0;

        // 基于优先队列的Dijkstra算法
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        queue.offer(new int[]{0, i}); // 距离 和 节点编号
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curDist = cur[0];
            int curNode = cur[1];
            if (curDist > dist[curNode]) {
                continue;
            }
            for (int nextNode : graph[curNode]) {
                int nextDist = curDist + 1;
                if (nextDist < dist[nextNode]) {
                    dist[nextNode] = nextDist;
                    queue.offer(new int[]{nextDist, nextNode});
                }
            }
        }

        // 先给ans 赋值
        for (int index : dist) {
            if (index > 0) {
                ans[index - 1]++;
            }
        }
    }

}
