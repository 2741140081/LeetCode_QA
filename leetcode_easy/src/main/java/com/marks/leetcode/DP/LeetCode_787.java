package com.marks.leetcode.DP;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_787 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/6 16:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_787 {

    /**
     * @Description:
     * 有 n 个城市通过一些航班连接。给你一个数组 flights ，
     * 其中 flights[i] = [from_i, to_i, price_i] ，表示该航班都从城市 from_i 开始，以价格 price_i 抵达 to_i。
     *
     * 现在给定所有的城市和航班，以及出发城市 src 和目的地 dst，
     * 你的任务是找到出一条最多经过 k 站中转的路线，使得从 src 到 dst 的 价格最便宜 ，并返回该价格。
     * 如果不存在这样的路线，则输出 -1。
     * @param: n
     * @param: flights
     * @param: src
     * @param: dst
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/11/06 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int result;
        result = method_01(n, flights, src, dst, k);
        return result;
    }

    /**
     * @Description:
     * 输入: n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
     * 输出: 200
     * bfs超时!!!
     * 需要剪枝优化,
     * 1. 如果当前节点是目标节点, 则返回
     * 2. 如果当前的价格大于minPrice[i], 则返回
     * 3. 如果当前curr_k 大于k, 则返回
     * AC: 3ms/45.69MB
     * @param: n
     * @param: flights
     * @param: src
     * @param: dst
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/11/06 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] flights, int src, int dst, int k) {
        // 构建邻接矩阵
        int[][] graph = new int[n][n];
        for (int[] flight : flights) {
            int from = flight[0];
            int to = flight[1];
            int price = flight[2];
            graph[from][to] = price;
        }
        int[] minPrice = new int[n];
        Arrays.fill(minPrice, Integer.MAX_VALUE);
        minPrice[src] = 0;

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{src, 0, 0});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int curr_node = poll[0];
            int curr_price = poll[1];
            int curr_k = poll[2];
            if (curr_node == dst) {
                continue;
            }

            for (int i = 0; i < n; i++) {
                if (graph[curr_node][i] != 0 && curr_k <= k && curr_price + graph[curr_node][i] < minPrice[i]) {
                    minPrice[i] = curr_price + graph[curr_node][i];
                    queue.offer(new int[]{i, curr_price + graph[curr_node][i], curr_k + 1});
                }
            }
        }


        return minPrice[dst] == Integer.MAX_VALUE ? -1 : minPrice[dst];
    }

}
