package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/30 17:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_743 {
    /**
     * @Description: [
     * 有 n 个网络节点，标记为 1 到 n。
     * 给你一个列表 times，表示信号经过 有向 边的传递时间。
     * times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间。
     * 现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。
     * ]
     * @param times
     * @param n
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/12/30 17:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        int result;
        result = method_01(times, n, k);
        result = method_02(times, n, k);
        return result;
    }

    /**
     * @Description: [
     * 官方题解: Dijkstra_algorithm
     * E1:times = [[1,2,1],[2,3,7],[1,3,4],[2,1,2]], n = 3, k = 2
     * Result = 7, C_Result = 6
     * [N, 1, 4]
     * [2, N, 7]
     * [N, N, N]
     *
     * dist[] = [N, 0, N]
     *
     * i = 0, x = -1, j = 0
     * => x = 0, j = 1, d[x] > d[1]
     * => x = 1, j = 2, d[x] < d[2]
     *
     * AC:3ms/48.39MB
     * ]
     * @param times
     * @param n
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/12/31 9:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2;
        int[][] gird = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(gird[i], INF);
        }

        // 将times[][] 数组中的元素存储到二维表中
        for (int[] time : times) {
            int x = time[0] - 1, y = time[1] - 1;
            gird[x][y] = time[2];
        }

        int[] dist = new int[n]; // 存储节点k 到所有节点的最短距离(时间/值)
        Arrays.fill(dist, INF);
        dist[k - 1] = 0;
        boolean[] visited = new boolean[n]; // 是否已遍历
        for (int i = 0; i < n; i++) {
            int x = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (x == -1 || dist[j] < dist[x])) {
                    x = j;
                }
            }
            visited[x] = true;
            for (int j = 0; j < n; j++) {
                dist[j] = Math.min(dist[j], dist[x] + gird[x][j]);
            }
        }
        int ans = Arrays.stream(dist).max().getAsInt();
        return ans == INF ? -1 : ans;
    }

    /**
     * @Description: [
     * 解答错误: 存在问题, 我对 Dijkstra 算法不清楚,
     * 它可能不是一个通过BFS来计算的, 还是看看官解吧
     * Result: 51/53
     *
     * 查看其他人题解后修改成小根堆,
     * AC:11ms/48.04MB
     *
     * tips: 值得注意的是，由于本题边数远大于点数，是一张稠密图，因此在运行时间上，枚举写法要略快于堆的写法。
     * ]
     * @param times
     * @param n
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/12/30 18:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] times, int n, int k) {
        final int INF = Integer.MAX_VALUE / 2;
        List<int[]>[] lists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int[] time : times) {
            int start = time[0] - 1;
            int end = time[1] - 1;
            int value = time[2];
            lists[start].add(new int[]{end, value});
        }

        int[] dist = new int[n];            // 存储节点k 到达 dist[i] 的时间最小值
        Arrays.fill(dist, INF);             // 取最大值

        // 试试BFS, 错误, 不是BFS, 而是小根堆
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        queue.offer(new int[]{0, k - 1});
        dist[k - 1] = 0;
        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            int value = curr_node[0];
            int curr_i = curr_node[1];
            for (int[] array : lists[curr_i]) {
                int end = array[0];
                int ds = array[1] + value;
                if (ds < dist[end]) {
                    dist[end] = ds;                   // 新的距离更短，就更新节点v的最短路径
                    queue.offer(new int[] {ds, end}); // 路径一旦更新就要入队，从而得到正确的最短路径节点
                }
            }
        }

        int ans = Arrays.stream(dist).max().getAsInt();
        return ans == INF ? -1 : ans;
    }
}
