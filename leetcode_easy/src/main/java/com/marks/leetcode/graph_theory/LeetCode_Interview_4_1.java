package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_4_1 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 15:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_4_1 {

    /**
     * @Description:
     * 节点间通路。给定有向图，设计一个算法，找出两个节点之间是否存在一条路径。
     * tips:
     * 节点数量n在[0, 1e5]范围内。
     * 节点编号大于等于 0 小于 n。
     * 图中可能存在自环和平行边。
     * @param: n
     * @param: graph
     * @param: start
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/08 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean findWhetherExistsPath(int n, int[][] graph, int start, int target) {
        boolean result;
        result = method_01(n, graph, start, target);
        return result;
    }

    /**
     * @Description:
     * 1. 并查集能用于有向图吗? 不能使用, 基本的方法使用广度或者深度搜索来判断有向图路径是否存在
     * 2. 根据提示, 图存在自环和平行边(visited[] 解决, 防止重复访问)
     * AC: 13ms/115.23MB
     * spend time: 8min
     * @param: n
     * @param: graph
     * @param: start
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2026/01/08 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int n, int[][] graph, int start, int target) {
        // 构建邻接表
        List<Integer>[] graphList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graphList[i] = new ArrayList<>();
        }
        for (int[] ints : graph) {
            int u = ints[0];
            int v = ints[1];
            graphList[u].add(v);
        }
        boolean[] visited = new boolean[n];
        // 基于队列的广度优先搜索算法
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        while (!queue.isEmpty() && !visited[target]) {
            int u = queue.poll();
            for (int v : graphList[u]) {
                if (!visited[v]) {
                    queue.offer(v);
                    visited[v] = true;
                }
            }
        }
        return visited[target];
    }
}
