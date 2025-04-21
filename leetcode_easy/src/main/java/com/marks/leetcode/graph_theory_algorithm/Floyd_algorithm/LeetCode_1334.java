package com.marks.leetcode.graph_theory_algorithm.Floyd_algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/6 16:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1334 {
    /**
     * @Description: [
     * 有 n 个城市，按从 0 到 n-1 编号。给你一个边数组 edges，
     * 其中 edges[i] = [fromi, toi, weighti] 代表 fromi 和 toi 两个城市之间的双向加权边，距离阈值是一个整数 distanceThreshold。
     *
     * 返回在路径距离限制为 distanceThreshold 以内可到达城市最少的城市。如果有多个这样的城市，则返回编号最大的城市。
     *
     * 注意，连接城市 i 和 j 的路径的距离等于沿该路径的所有边的权重之和。
     * ]
     * @param n 
     * @param edges 
     * @param distanceThreshold
     * @return int
     * @author marks
     * @CreateDate: 2025/1/6 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int result;
        result = method_01(n, edges, distanceThreshold);
        return result;
    }

    /**
     * @Description: [
     * AC:8ms/43.40MB
     * ]
     * @param n
     * @param edges
     * @param distanceThreshold
     * @return int
     * @author marks
     * @CreateDate: 2025/1/6 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, int distanceThreshold) {
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            dp[x][y] = edge[2];
            dp[y][x] = edge[2];
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }
        Queue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o2[1] - o1[1]; // 1列降序, 找到最大的下标
            } else {
                return o1[0] - o2[0]; // 0列升序, 找到最少的邻居
            }

        });
        int[] count = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] <= distanceThreshold) {
                    count[i]++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            queue.offer(new int[] {count[i], i});
        }
        return queue.poll()[1];
    }
}
