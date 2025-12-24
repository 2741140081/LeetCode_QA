package com.marks.leetcode.bfs_algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_847 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/24 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_847 {

    /**
     * @Description:
     * 存在一个由 n 个节点组成的无向连通图，图中的节点按从 0 到 n - 1 编号。
     * 给你一个数组 graph 表示这个图。其中，graph[i] 是一个列表，由所有与节点 i 直接相连的节点组成。
     * 返回能够访问所有节点的最短路径的长度。你可以在任一节点开始和停止，也可以多次重访节点，并且可以重用边。
     * @param: graph
     * @return int
     * @author marks
     * @CreateDate: 2025/12/24 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shortestPathLength(int[][] graph) {
        int result;
        result = method_01(graph);
        return result;
    }

    /**
     * @Description:
     * 管堵优先遍历 + 状态压缩
     * AC: 10ms/45.52MB
     * @param: graph
     * @return int
     * @author marks
     * @CreateDate: 2025/12/24 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] graph) {
        int n = graph.length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][1 << n];
        for (int i = 0; i < n; i++) {
            queue.offer(new int[]{i, 1 << i, 0}); // 当前节点，当前节点状态，当前节点到当前节点状态的距离
            visited[i][1 << i] = true; // 节点状态
        }
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int cur = poll[0];
            int state = poll[1];
            int distance = poll[2];
            if (state == (1 << n) - 1) {
                // 访问所有节点
                return distance;
            }
            for (int next : graph[cur]) {
                int nextState = state | (1 << next); // 当前节点状态, 将next位置设置为1
                if (!visited[next][nextState]) {
                    queue.offer(new int[]{next, nextState, distance + 1});
                    visited[next][nextState] = true;
                }
            }
        }
        return 0;
    }

}
