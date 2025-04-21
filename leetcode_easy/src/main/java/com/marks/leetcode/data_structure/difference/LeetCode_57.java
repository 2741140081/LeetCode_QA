package com.marks.leetcode.data_structure.difference;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/18 14:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_57 {
    /**
     * @Description:
     * 给你一个 无重叠的 ，按照区间起始端点排序的区间列表 intervals，其中 intervals[i] = [start_i, end_i] 表示第 i 个区间的开始和结束，
     * 并且 intervals 按照 start_i 升序排列。
     * 同样给定一个区间 newInterval = [start, end] 表示另一个区间的开始和结束。
     * 在 intervals 中插入区间 newInterval，使得 intervals 依然按照 start_i 升序排列，且区间之间不重叠（如果有必要的话，可以合并区间）。
     * 返回插入之后的 intervals。
     * 注意 你不需要原地修改 intervals。你可以创建一个新数组然后返回它。
     * @param intervals
     * @param newInterval
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/2/18 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int[][] result;
        result = method_01(intervals, newInterval);
        return result;
    }

    /**
     * @Description:
     * @see LeetCode_56#merge(int[][]) 基本一致, 只是加了一个newInterval
     * AC: 1ms/44.11MB
     * @param intervals
     * @param newInterval
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/2/18 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] intervals, int[] newInterval) {
        int maxValue = newInterval[1];
        for (int[] interval : intervals) {
            maxValue = Math.max(maxValue, interval[1]);
        }

        int[] diff = new int[maxValue * 2 + 2];
        diff[newInterval[0] * 2]++;
        diff[newInterval[1] * 2 + 1]--;
        for (int[] interval : intervals) {
            int left = interval[0] * 2, right = interval[1] * 2;
            diff[left]++;
            diff[right + 1]--;
        }

        List<int[]> list = new ArrayList<>();
        int count = 0;
        boolean reStart = true;
        int left = -1, right = -1;
        for (int i = 0; i <= maxValue * 2 + 1; i++) {
            count += diff[i];
            if (count > 0 && reStart) {
                reStart = false;
                left = i / 2;
            } else if (count == 0 && !reStart) {
                reStart = true;
                right = (i - 1) / 2;
                list.add(new int[] {left, right});
            }
        }

        return list.toArray(new int[0][]);
    }
}
