package com.marks.leetcode.array_hard;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1851 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/4 10:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1851 {

    /**
     * @Description:
     * 给你一个二维整数数组 intervals ，其中 intervals[i] = [lefti, righti] 表示第 i 个区间开始于 lefti 、结束于 righti（包含两侧取值，闭区间）。
     * 区间的 长度 定义为区间中包含的整数数目，更正式地表达是 righti - lefti + 1 。
     * 再给你一个整数数组 queries 。
     * 第 j 个查询的答案是满足 lefti <= queries[j] <= righti 的 长度最小区间 i 的长度 。
     * 如果不存在这样的区间，那么答案是 -1 。
     * 以数组形式返回对应查询的所有答案。
     *
     * tips:
     * 1 <= intervals.length <= 10^5
     * 1 <= queries.length <= 10^5
     * intervals[i].length == 2
     * 1 <= lefti <= righti <= 10^7
     * 1 <= queries[j] <= 10^7
     * @param: intervals
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/04 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] minInterval(int[][] intervals, int[] queries) {
        int[] result;
        result = method_01(intervals, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 由于 queries 的查询需要 O(n), 所以仅剩余 O(log n)的剩余复杂度来操作 intervals 中查询符合要求的最小区间大小
     * 2. 还是使用优先队列, 之前的想法是由于优先队列在循环体中, 感觉是m * nlog n, 但是实际是对 intervals[] 仅存取1次
     * @param: intervals
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/08/04 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] intervals, int[] queries) {
        int n = intervals.length, m = queries.length;
        // 根据 right 进行升序排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        // 间接排序
        Integer[] index = new Integer[m];
        for (int i = 0; i < m; i++) {
            index[i] = i;
        }
        Arrays.sort(index, (a, b) -> queries[a] - queries[b]);
        // 创建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int left = 0; // 待添加的下标
        int[] ans = new int[m];
        for (int i = 0; i < m; i++) {
            int idx = index[i];
            int target = queries[idx];
            while (left < n && intervals[left][0] <= target) {
                pq.offer(new int[]{intervals[left][1] - intervals[left][0] + 1, intervals[left][0], intervals[left][1]});
                left++;
            }

            // 取出无效值
            while (!pq.isEmpty() && pq.peek()[2] < target) {
                pq.poll();
            }
            ans[idx] = pq.isEmpty() ? -1 : pq.peek()[0];
        }

        return ans;
    }

}
