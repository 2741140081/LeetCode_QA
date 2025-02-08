package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

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
     * @param intervals 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minGroups(int[][] intervals) {
        int result;
        result = method_01(intervals);
        return result;
    }

    /**
     * @Description:
     * @param intervals 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] intervals) {
        PriorityQueue<int[]> curr = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        PriorityQueue<int[]> leave = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        PriorityQueue<int[]> set = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        return 0;
    }
}
