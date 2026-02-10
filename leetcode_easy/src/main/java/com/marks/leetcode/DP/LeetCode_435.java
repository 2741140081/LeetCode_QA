package com.marks.leetcode.DP;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_435 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 16:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_435 {

    /**
     * @Description:
     * 给定一个区间的集合 intervals ，其中 intervals[i] = [starti, endi] 。返回 需要移除区间的最小数量，使剩余区间互不重叠 。
     * 注意 只在一点上接触的区间是 不重叠的。例如 [1, 2] 和 [2, 3] 是不重叠的。
     * tips:
     * 1 <= intervals.length <= 10^5
     * intervals[i].length == 2
     * -5 * 104 <= starti < endi <= 5 * 10^4
     * @param: intervals
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        int result;
        result = method_01(intervals);
        result = method_02(intervals);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官解, 使用贪心
     * AC: 53ms/113.48MB
     * @param: intervals
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 16:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] intervals) {
        int n = intervals.length;
        if (n == 0) {
            return 0;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // 对右节点进行升序排序
        int ans = 0;
        int right = intervals[0][1];
        for (int i = 1; i < n; i++) {
            if (intervals[i][0] >= right) {
                right = intervals[i][1];
                ans++;
            }
        }

        return n - ans;
    }

    /**
     * @Description:
     * 1. 动态规划, 需要移除的最小数量, 即为总的数量减去组成无重复区间的最大数量, 即为待移除的最小数量
     * 超时, 55/59 通过
     * @param: intervals
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] intervals) {
        int n = intervals.length;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // 升序排序
        int[] dp = new int[n]; // dp[i] 表示以 intervals[i] 结尾的区间，最多能组成的无重叠区间数量
        Arrays.fill(dp, 1);
        int ans = 1;
        for (int i = 1; i < n; i++) {
            int left = intervals[i][0];
            for (int j = 0; j < i; j++) {
                if (left >= intervals[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            ans = Math.max(ans, dp[i]);
        }

        return n - ans;
    }

}
