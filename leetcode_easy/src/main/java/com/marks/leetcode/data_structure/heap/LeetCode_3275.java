package com.marks.leetcode.data_structure.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/8 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3275 {


    /**
     * @Description:
     * 有一个无限大的二维平面。
     * 给你一个正整数 k ，同时给你一个二维数组 queries ，包含一系列查询：
     * queries[i] = [x, y] ：在平面上坐标 (x, y) 处建一个障碍物，数据保证之前的查询 不会 在这个坐标处建立任何障碍物。
     * 每次查询后，你需要找到离原点第 k 近 障碍物到原点的 距离 。
     *
     * 请你返回一个整数数组 results ，其中 results[i] 表示建立第 i 个障碍物以后，离原地第 k 近障碍物距离原点的距离。如果少于 k 个障碍物，results[i] == -1 。
     *
     * 注意，一开始 没有 任何障碍物。
     *
     * 坐标在 (x, y) 处的点距离原点的距离定义为 |x| + |y| 。
     * @param queries
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/8 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] resultsArray(int[][] queries, int k) {
        int[] result;
        result = method_01(queries, k);
        return result;
    }

    /**
     * @Description:
     * AC:95ms/165.53MB
     * @param queries 
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/8 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] queries, int k) {
        int n = queries.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1); // 升序排序, 按照距离近到远排序
        for (int i = 0; i < queries.length; i++) {
            int value = Math.abs(queries[i][0]) + Math.abs(queries[i][1]);
            queue.offer(value);
            if (queue.size() > k) {
                queue.poll();
                ans[i] = queue.peek();
            } else if (queue.size() == k) {
                ans[i] = queue.peek();
            }
        }

        return ans;
    }
}
