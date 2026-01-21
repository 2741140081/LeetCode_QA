package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2045 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/19 15:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2045 {

    /**
     * @Description:
     * 城市用一个 双向连通 图表示，图中有 n 个节点，从 1 到 n 编号（包含 1 和 n）。
     * 图中的边用一个二维整数数组 edges 表示，其中每个 edges[i] = [ui, vi] 表示一条节点 ui 和节点 vi 之间的双向连通边。
     * 每组节点对由 最多一条 边连通，顶点不存在连接到自身的边。穿过任意一条边的时间是 time 分钟。
     * 每个节点都有一个交通信号灯，每 change 分钟改变一次，从绿色变成红色，再由红色变成绿色，循环往复。
     * 所有信号灯都 同时 改变。你可以在 任何时候 进入某个节点，但是 只能 在节点 信号灯是绿色时 才能离开。
     * 如果信号灯是  绿色 ，你 不能 在节点等待，必须离开。
     * 第二小的值 是 严格大于 最小值的所有值中最小的值。
     * 例如，[2, 3, 4] 中第二小的值是 3 ，而 [2, 2, 4] 中第二小的值是 4 。
     * 给你 n、edges、time 和 change ，返回从节点 1 到节点 n 需要的 第二短时间 。
     * 注意：
     * 你可以 任意次 穿过任意顶点，包括 1 和 n 。
     * 你可以假设在 启程时 ，所有信号灯刚刚变成 绿色 。
     * tips:
     * 2 <= n <= 10^4
     * n - 1 <= edges.length <= min(2 * 10^4, n * (n - 1) / 2)
     * edges[i].length == 2
     * 1 <= ui, vi <= n
     * ui != vi
     * 不含重复边
     * 每个节点都可以从其他节点直接或者间接到达
     * 1 <= time, change <= 10^3
     * @param: n
     * @param: edges
     * @param: time
     * @param: change
     * @return int
     * @author marks
     * @CreateDate: 2026/01/19 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        int result;
        result = method_01(n, edges, time, change);
        return result;
    }

    /**
     * @Description:
     * 1. 找最短(第二短距离) 使用 Dijkstra 算法
     * 2. 可以在任何时候进入下一个节点, 但是只能在绿灯时才能离开当前节点, 并且所有节点的变换周期时间间隔是 change, 一样的 int type = time / change;
     * if type % 2 == 0; 绿灯, 否则为红灯, 红灯只能等待, 需要多少时间 int wait = change - time % change;
     * 3. 使用一个 Set 记录到达节点 n - 1(实际是n)的时间, set.size() == 2; 返回结果
     * 4. 应该是一个稀疏图, 因为 n <= 10^4, V <= Math.min(2 * 10^4, n^2)
     * AC: 92ms/93.6MB
     * @param: n
     * @param: edges
     * @param: time
     * @param: change
     * @return int
     * @author marks
     * @CreateDate: 2026/01/19 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, int time, int change) {
        final int INF = Integer.MAX_VALUE / 2;
        // 构建邻接表(无向图)
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            graph[u].add(v);
            graph[v].add(u);
        }
        // 是不是应该记录2个值
        int[][] dist = new int[n][2]; // 记录最短和第二短
        for (int i = 0; i < n; i++) {
            dist[i][0] = INF;
            dist[i][1] = INF;
        }
        Set<Integer> ans = new HashSet<>();
        dist[0][0] = 0;
        // 优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0}); // 时间 , 编号
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cTime = cur[0];
            int node = cur[1];
            if (node == n - 1) {
                ans.add(cTime);
                if (ans.size() == 2) {
                    return cTime;
                }
            }
            // 在当前节点判断能否出发时是否是红灯, 而不是在next 节点出判断
            int type = cTime / change;
            if (type % 2 == 1) {
                // 红灯
                cTime += change - cTime % change;
            }
            for (int next : graph[node]) {
                int aTime = cTime + time; // 到达下一个节点的时间
                if (aTime < dist[next][0]) {
                    // 判断是否需要更新 dist[next][1]
                    if (dist[next][0] != INF) {
                        dist[next][1] = Math.min(dist[next][1], dist[next][0]);
                    }
                    dist[next][0] = aTime;
                    pq.offer(new int[]{aTime, next});
                } else if (aTime > dist[next][0]){
                    // aTime > dist[next][0]
                    if (aTime < dist[next][1]) {
                        dist[next][1] = aTime;
                        pq.offer(new int[]{aTime, next});
                    }
                }

            }

        }
        return 0;
    }

}
