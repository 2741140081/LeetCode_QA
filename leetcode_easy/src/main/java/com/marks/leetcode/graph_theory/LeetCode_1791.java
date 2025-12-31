package com.marks.leetcode.graph_theory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1791 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/31 9:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1791 {

    /**
     * @Description:
     * 有一个无向的 星型 图，由 n 个编号从 1 到 n 的节点组成。
     * 星型图有一个 中心 节点，并且恰有 n - 1 条边将中心节点与其他每个节点连接起来。
     * 给你一个二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示在节点 ui 和 vi 之间存在一条边。
     * 请你找出并返回 edges 所表示星型图的中心节点。
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 9:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findCenter(int[][] edges) {
        int result;
        result = method_01(edges);
        return result;
    }

    /**
     * @Description:
     * AC: 21ms/136.58MB
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 9:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] edges) {
        int n = edges.length + 1;
        if (n <= 1) {
            // 只有一个节点
            return 1;
        }
        // 构建邻接矩阵
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 入度
        int[] inDegree = new int[n];
        for (int[] edge : edges) {
            int x = edge[0] - 1;
            int y = edge[1] - 1;
            graph[x].add(y);
            graph[y].add(x);
            inDegree[x]++;
            inDegree[y]++;
        }
        // 广度优先搜索
        Queue<Integer> queue = new ArrayDeque<>();
        // 删除入度为1的节点
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 1) {
                queue.offer(i);
            }
        }
        while (queue.size() > 1) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();
                inDegree[cur]--;
                for (int next : graph[cur]) {
                    inDegree[next]--;
                    if (inDegree[next] == 1) {
                        queue.offer(next);
                    }
                }
            }
        }
        return queue.peek() + 1;
    }

}
