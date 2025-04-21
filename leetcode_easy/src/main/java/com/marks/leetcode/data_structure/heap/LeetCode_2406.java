package com.marks.leetcode.data_structure.heap;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/8 15:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2406 {
    /**
     * @Description:
     * 给你一个二维整数数组 intervals ，其中 intervals[i] = [lefti, righti] 表示 闭 区间 [lefti, righti] 。
     * 你需要将 intervals 划分为一个或者多个区间 组 ，每个区间 只 属于一个组，且同一个组中任意两个区间 不相交 。
     * 请你返回 最少 需要划分成多少个组。
     * 如果两个区间覆盖的范围有重叠（即至少有一个公共数字），那么我们称这两个区间是 相交 的。比方说区间 [1, 5] 和 [5, 8] 相交。
     *
     * tips:
     * 1 <= intervals.length <= 10^5
     * intervals[i].length == 2
     * 1 <= lefti <= righti <= 10^6
     * @param intervals 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minGroups(int[][] intervals) {
        int result;
        result = method_01(intervals);
        result = method_02(intervals);
        return result;
    }

    /**
     * @Description:
     * AC: 103ms/87.86MB
     * @param intervals
     * @return int
     * @author marks
     * @CreateDate: 2025/2/10 14:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        for (int[] p : intervals) {
            if (!pq.isEmpty() && pq.peek() < p[0]) pq.poll();
            pq.offer(p[1]);
        }
        return pq.size();
    }

    /**
     * @Description:
     * 1. 对intervals[][] 进行排序
     * 超时: TLE
     * @param intervals 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] intervals) {
        PriorityQueue<int[]> curr = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        PriorityQueue<int[]> leave = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        Deque<Integer> stack = new ArrayDeque<>(); // 可以直接用stack

        for (int[] interval : intervals) {
            curr.offer(interval);
        }
        int ans = 1;
        while (!curr.isEmpty()) {
            int size = curr.size();
            for (int i = 0; i < size; i++) {
                int[] curr_node = curr.poll();
                if (stack.isEmpty() || curr_node[0] > stack.peek()) {
                    stack.push(curr_node[1]);
                } else {
                    leave.offer(curr_node);
                }
            }
            if (leave.size() > 0) {
                ans++;
                curr = leave;
                stack.clear();
                leave = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
            }
        }
        return ans;
    }
}
