package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_886 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/16 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_886 {

    /**
     * @Description:
     * 给定一组 n 人（编号为 1, 2, ..., n）， 我们想把每个人分进任意大小的两组。每个人都可能不喜欢其他人，那么他们不应该属于同一组。
     * 给定整数 n 和数组 dislikes ，其中 dislikes[i] = [ai, bi] ，表示不允许将编号为 ai 和  bi的人归入同一组。
     * 当可以用这种方法将所有人分进两组时，返回 true；否则返回 false。
     * @param: n
     * @param: dislikes
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/16 10:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean possibleBipartition(int n, int[][] dislikes) {
        boolean result;
        result = method_01(n, dislikes);
        return result;
    }

    /**
     * @Description:
     * 1. 明确这不是一个贪心问题, 这是一个图的问题, 假设存在如下关系 a - b - c - a
     * 2. 这种构成环路的关系就无法满足分割成两组, 那么就可以基于染色法来求解
     * 3. 使用广度优先搜索的染色法, int[] color = new int[n]; 1: 表示组1, 2: 表示组2, 0: 表示未染色
     * AC: 17ms/54.65MB
     * @param: n
     * @param: dislikes
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/16 10:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int n, int[][] dislikes) {
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] dislike : dislikes) {
            int u = dislike[0] - 1;
            int v = dislike[1] - 1;
            graph[u].add(v);
            graph[v].add(u);
        }
        int[] color = new int[n]; // 0: 未染色, 1: 组1, 2: 组2
        for (int i = 0; i < n; i++) {
            // 从节点0开始染色
            if (color[i] == 0) {
                if (!BFSSetColor(graph, color, i, 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean BFSSetColor(List<Integer>[] graph, int[] color, int i, int k) {
        color[i] = k;
        // 队列
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, k});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curNode = cur[0];
            int curColor = cur[1];
            int nextColor = curColor == 1 ? 2 : 1;
            for (int nextNode : graph[curNode]) {
                if (color[nextNode] == 0) {
                    color[nextNode] = nextColor;
                    queue.offer(new int[]{nextNode, nextColor});
                } else if (color[nextNode] == curColor) {
                    return false;
                }
            }
        }
        return true;
    }

}
