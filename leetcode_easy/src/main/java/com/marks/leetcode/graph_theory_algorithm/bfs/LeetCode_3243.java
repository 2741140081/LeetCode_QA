package com.marks.leetcode.graph_theory_algorithm.bfs;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/23 17:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3243 {
    /**
     * @Description: [
     * <p>给你一个整数 n 和一个二维整数数组 queries。
     * <p>有 n 个城市，编号从 0 到 n - 1。初始时，每个城市 i 都有一条单向道路通往城市 i + 1（ 0 <= i < n - 1）。
     * <p>queries[i] = [ui, vi] 表示新建一条从城市 ui 到城市 vi 的单向道路。每次查询后，你需要找到从城市 0 到城市 n - 1 的最短路径的长度。
     * <p>返回一个数组 answer，对于范围 [0, queries.length - 1] 中的每个 i，answer[i] 是处理完前 i + 1 个查询后，从城市 0 到城市 n - 1 的最短路径的长度。
     *
     * <p>tips:
     * 3 <= n <= 500
     * 1 <= queries.length <= 500
     * queries[i].length == 2
     * 0 <= queries[i][0] < queries[i][1] < n
     * 1 < queries[i][1] - queries[i][0]
     * 查询中没有重复的道路。
     * ]
     * @param n 
     * @param queries 
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/23 17:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        int[] result;
        result = method_01(n, queries);
        return result;
    }

    /**
     * @Description: [
     * 超时!!! 因该是有重复添加的情况
     * 958/972
     * 添加额外数组, 防止重复添加
     * AC:467ms/44.82MB
     * 时间复杂度太高了!!!
     * 其实我们重复计算了太多次路径, 感觉只需要每次更新queries[i]到路径中即可
     * E1:
     * n = 5, queries = [[2, 4], [0, 2], [0, 4]]
     * [4, 3, 2, 1, 0]
     * []
     *
     * ]
     * @param n
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2024/12/23 17:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] queries) {
        List<Integer>[] list = new ArrayList[n];
        int m = queries.length;
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
            if (i < n - 1) {
                list[i].add(i + 1);
            }
        }
        Queue<int[]> queue = new LinkedList<>();
        int[] ans = new int[m];
        Arrays.fill(ans, n - 1);

        for (int i = 0; i < m; i++) {

            int start = queries[i][0], end = queries[i][1];
            list[start].add(end);

            ans[i] = bfsMaxArea(n, list);
        }
        return ans;
    }

    private int bfsMaxArea(int n, List<Integer>[] list) {
        int[] pre = new int[n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int curr_i = array[0];
            int deep = array[1];
            pre[curr_i] = 1;
            if (curr_i == n - 1) {
                return deep;
            }
            for (int j : list[curr_i]) {
                if (pre[j] == 0) {
                    queue.offer(new int[]{j, deep + 1});
                }
            }
        }
        return n - 1;
    }
}
