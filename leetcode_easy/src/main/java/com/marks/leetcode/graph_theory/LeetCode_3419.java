package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3419 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3419 {

    /**
     * @Description:
     * 给你两个整数 n 和 threshold ，同时给你一个 n 个节点的 有向 带权图，节点编号为 0 到 n - 1 。
     * 这个图用 二维 整数数组 edges 表示，其中 edges[i] = [Ai, Bi, Wi] 表示节点 Ai 到节点 Bi 之间有一条边权为 Wi的有向边。
     * 你需要从这个图中删除一些边（也可能 不 删除任何边），使得这个图满足以下条件：
     * 所有其他节点都可以到达节点 0 。
     * 图中剩余边的 最大 边权值尽可能小。
     * 每个节点都 至多 有 threshold 条出去的边。
     * 请你返回删除必要的边后，最大 边权的 最小值 为多少。如果无法满足所有的条件，请你返回 -1 。
     * tips:
     * 2 <= n <= 10^5
     * 1 <= threshold <= n - 1
     * 1 <= edges.length <= min(10^5, n * (n - 1) / 2).
     * edges[i].length == 3
     * 0 <= Ai, Bi < n
     * Ai != Bi
     * 1 <= Wi <= 10^6
     * 一对节点之间 可能 会有多条边，但它们的权值互不相同。
     * @param: n
     * @param: edges
     * @param: threshold
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMaxWeight(int n, int[][] edges, int threshold) {
        int result;
        result = method_01(n, edges, threshold);
        return result;
    }

    /**
     * @Description:
     * 1. 由于是有向图, 所以不能使用并查集判断图中节点的连通性.
     * 2. 构建邻接表时, 修改边的方向, 使得边的方向从 Ai 到 Bi 变为 Bi 到 Ai.
     * 3. 根据提示, 使用二分查找法来得到存在边的最大值, 对 edges 数组进行排序, 然后使用二分查找法来查找最大值.
     * 4. 0 ~ mid 下标的边能否构建一个从 0 到其他节点的路径, 如果可以返回 true, 否则返回 false.
     * 5. 重复上述过程, 找到最大值.
     * 6. 还需要注意 threshold 的限制, 如果节点的入度大于 threshold, 则无法满足要求.
     * 需要用一个入度 inDegree[] 数组存储节点的入度(在进行从节点0开始的广度优先搜索时, 记录相应节点的入度值)
     * 7. 没有必要要一个 threshold 数组, 因为visited[] 数组就表示只能访问一次.
     * AC: 257ms/276.47MB
     * @param: n
     * @param: edges
     * @param: threshold
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, int threshold) {
        // 先排序
        Arrays.sort(edges, (a, b) -> a[2] - b[2]); // 权值升序排序
        int left = 0;
        int right = edges.length - 1;
        if (!canBuildPath(n, edges, right, threshold)) {
            return -1;
        }
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canBuildPath(n, edges, mid, threshold)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans != -1 ? edges[ans][2] : ans;
    }

    private boolean canBuildPath(int n, int[][] edges, int mid, int threshold) {
        // 构建邻接表[0, mid]
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i <= mid; i++) {
            int u = edges[i][1];
            int v = edges[i][0];
            graph[u].add(v); // 没有必要添加边权值
        }
        boolean[] visited = new boolean[n];
        int[] inDegree = new int[n]; // 入度
        int count = 0;
        // 队列进行广度优先搜索
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        count++;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : graph[u]) {
                if (!visited[v] && inDegree[v] < threshold) {
                    visited[v] = true;
                    count++;
                    queue.offer(v);
                    inDegree[v]++;
                }
            }
        }
        return count == n;
    }

}
