package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_436 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/26 11:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_436 {

    /**
     * @Description:
     * 给你一个区间数组 intervals ，其中 intervals[i] = [starti, endi] ，且每个 starti 都 不同 。
     *
     * 区间 i 的 右侧区间 是满足 startj >= endi，且 startj 最小 的区间 j。注意 i 可能等于 j 。
     *
     * 返回一个由每个区间 i 对应的 右侧区间 下标组成的数组。如果某个区间 i 不存在对应的 右侧区间 ，则下标 i 处的值设为 -1 。
     * @param: intervals
     * @return int[]
     * @author marks
     * @CreateDate: 2025/11/26 11:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findRightInterval(int[][] intervals) {
        int[] result;
        result = method_01(intervals);
        return result;
    }

    /**
     * @Description:
     * AC: 12ms/53.38MB
     * @param: intervals
     * @return int[]
     * @author marks
     * @CreateDate: 2025/11/26 11:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] intervals) {
        int n = intervals.length;
        int[][] starts = new int[n][2];
        for (int i = 0; i < n; i++) {
            starts[i][0] = intervals[i][0];
            starts[i][1] = i;
        }
        Arrays.sort(starts, (a, b) -> a[0] - b[0]);
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int target = -1;
            int left = 0, right = n - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (starts[mid][0] >= intervals[i][1]) {
                    target = starts[mid][1];
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            ans[i] = target;
        }
        return ans;
    }

}
