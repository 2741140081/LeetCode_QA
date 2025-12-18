package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1857 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/18 15:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1857 {

    /**
     * @Description:
     * 给你一个 有向图 ，它含有 n 个节点和 m 条边。节点编号从 0 到 n - 1 。
     * 给你一个字符串 colors ，其中 colors[i] 是小写英文字母，表示图中第 i 个节点的 颜色 （下标从 0 开始）。
     * 同时给你一个二维数组 edges ，其中 edges[j] = [aj, bj] 表示从节点 aj 到节点 bj 有一条 有向边 。
     * 图中一条有效 路径 是一个点序列 x1 -> x2 -> x3 -> ... -> xk ，对于所有 1 <= i < k ，
     * 从 xi 到 xi+1 在图中有一条有向边。路径的 颜色值 是路径中 出现次数最多 颜色的节点数目。
     *
     * 请你返回给定图中有效路径里面的 最大颜色值 。如果图中含有环，请返回 -1 。
     * @param: colors
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/18 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestPathValue(String colors, int[][] edges) {
        int result;
        result = method_01(colors, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 将edges构建成邻接表, 通过邻接表判断是否存在环
     * 2. 感觉主要就是判断环, 以及需要找到一条有效的路径, 使用Math.max() 比较而不是相加
     * AC: 89ms/150.97MB
     * @param: colors
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/18 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String colors, int[][] edges) {
        int n = colors.length();
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 构建入度表
        int[] inDegree = new int[n];
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            inDegree[edge[1]]++;
        }
        int[] visited = new int[n];
        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                if (hasCycle(graph, visited, i)) {
                    return -1;
                }
            }
        }
        int[][] count = new int[n][26];
        int[] maxCount = new int[26];
        // 广度优先搜索, 添加队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            count[cur][colors.charAt(cur) - 'a']++;
            for (int next : graph[cur]) {
                for (int i = 0; i < 26; i++) {
                    count[next][i] = Math.max(count[next][i], count[cur][i]); // 有效路径, 只能选择最大的一条
                }
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                ans = Math.max(ans, count[i][j]);
            }
        }
        return ans;
    }

    private boolean hasCycle(List<Integer>[] graph, int[] visited, int i) {
        visited[i] = 1;

        for (int next : graph[i]) {
            if (visited[next] == 1) {
                return true;
            }
            if (visited[next] == 0 && hasCycle(graph, visited, next)) {
                return true;
            }
        }
        visited[i] = 2;
        return false;
    }

}
