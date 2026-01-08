package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3604 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 16:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3604 {

    /**
     * @Description:
     * 给你一个整数 n 和一个 有向 图，图中有 n 个节点，编号从 0 到 n - 1。
     * 图由一个二维数组 edges 表示，其中 edges[i] = [ui, vi, starti, endi] 表示从节点 ui 到 vi 的一条边，该边 只能 在满足 starti <= t <= endi 的整数时间 t 使用。
     * 你在时间 0 从在节点 0 出发。
     * 在一个时间单位内，你可以：
     * 停留在当前节点不动，或者
     * 如果当前时间 t 满足 starti <= t <= endi，则从当前节点沿着出边的方向移动。
     * 返回到达节点 n - 1 所需的 最小 时间。如果不可能，返回 -1。
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTime(int n, int[][] edges) {
        int result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 节点之间的移动需要消耗一个单位时间 即 1s, 并且只能在特点时间段进行移动操作
     * 2. 有向图求解最小到达时间, 使用Dijkstra算法
     * AC: 53ms/227.11MB
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 16:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        final int INF = Integer.MAX_VALUE / 2;
        // 构建邻接表
        List<int[]>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int start = edge[0];
            int end = edge[1];
            int startTime = edge[2];
            int endTime = edge[3];
            lists[start].add(new int[]{end, startTime, endTime});
        }
        int[] time = new int[n]; // 存储节点 0 到其他节点的最小距离
        Arrays.fill(time, INF);
        time[0] = 0; // 节点 0 到自己为 0

        // 创建优先队列
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        queue.offer(new int[]{0, 0}); // 时间 和 节点编号

        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            int t = curr_node[0];
            int curr = curr_node[1];
            for (int[] next : lists[curr]) {
                int next_node = next[0];
                int startTime = next[1];
                int endTime = next[2];
                if (t <= endTime) {
                    int  nextT = Math.max(t, startTime) + 1; // 到达下一个节点的时间
                    if (nextT < time[next_node]) {
                        time[next_node] = nextT;
                        queue.offer(new int[]{nextT, next_node});
                    }
                }
            }
        }
        return time[n - 1] == INF ? -1 : time[n - 1];
    }

}
