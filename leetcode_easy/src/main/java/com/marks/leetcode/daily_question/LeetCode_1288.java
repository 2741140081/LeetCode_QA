package com.marks.leetcode.daily_question;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1288 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/6 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1288 {

    /**
     * @Description:
     * 给你一个区间列表，请你删除列表中被其他区间所覆盖的区间。
     * 只有当 c <= a 且 b <= d 时，我们才认为区间 [a,b) 被区间 [c,d) 覆盖。
     * 在完成所有删除操作后，请你返回列表中剩余区间的数目。
     *
     * tips:
     * 1 <= intervals.length <= 1000
     * 0 <= intervals[i][0] < intervals[i][1] <= 10^5
     * 对于所有的 i != j：intervals[i] != intervals[j]
     * @param: intervals
     * @return int
     * @author marks
     * @CreateDate: 2026/07/06 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int removeCoveredIntervals(int[][] intervals) {
        int result;
        result = method_01(intervals);
        result = method_02(intervals);
        return result;
    }

    /**
     * @Description:
     * 1. 对 intervals 进行排序, 按照 intervals[0] 进行升序排序, 然后对 intervals[1] 进行升序排序
     * AC: 5ms/45.68MB
     * @param: intervals
     * @return int
     * @author marks
     * @CreateDate: 2026/07/06 11:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] intervals) {
        int n = intervals.length;
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int ans = 0;
        int rMax = intervals[0][1];
        for (int i = 1; i < n; i++) {
            if (intervals[i][1] <= rMax) {
                ans++;
            } else {
                rMax = Math.max(rMax, intervals[i][1]);
            }
        }

        return n - ans;
    }

    /**
     * @Description:
     * 1. 应该会有一个默认条件是保留最多的剩余区间, 否则完全可以删除 n - 1 个区间, 仅保留一个区间
     * 2. 需要对 intervals 进行排序, 按照 intervals[1] 进行升序排序.
     * 3. 对于 intervals[i], 可以使用动态规划, 有两种情况:
     * 3.1 保留当前区间, 然后找到之前的一个区间 j, 要求区间intervals[j] 满足以下条件, 满足 intervals[j][1] < intervals[i][0],
     * dp[i][1] = Max(dp[j][1], dp[j][0]) + 1;
     * 3.2 舍弃当前区间, dp[i][0] = Max(dp[i-1][0], dp[i-1][1]);
     * 4. 找 j 的方法可以使用二分查找法, 整体时间复杂度是 O(nlogn), 空间复杂度是 O(n)
     * 5. 理解错误, 覆盖的意思是 [a, b] [c, d] 要求 c <= a && d >= b, 即 [a, b] 是 [c, d] 的内部区间一种包含关系,
     * 需要删除被覆盖的区间, 即删除 [a, b] 区间.
     * 6. 直接暴力 for 循环, 判断区间 i 与区间 j (j < i), 是否存在覆盖关系, 如果存在, 则删除 j. 并且不能重复删除, 所以需要 boolean[] 记录是否删除过
     * 7. int ans 记录删除的数量.
     * AC: 21ms/46.01MB, 时间复杂度 O(n^2), 空间复杂度 O(n)
     * @param: intervals
     * @return int
     * @author marks
     * @CreateDate: 2026/07/06 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] intervals) {
        int n = intervals.length;
        int ans = 0;
        boolean[] isDelete = new boolean[n];
        // 对 intervals 进行排序, 对 intervals[1] 进行升序排序, 对 intervals[0] 进行降序排序
        Arrays.sort(intervals, (a, b) -> a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (intervals[j][0] >= intervals[i][0] && !isDelete[j]) {
                    ans++;
                    isDelete[j] = true;
                }
            }
        }

        return n - ans;
    }



}
