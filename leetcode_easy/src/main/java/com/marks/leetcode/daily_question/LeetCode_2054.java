package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2054 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/23 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2054 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的二维整数数组 events ，其中 events[i] = [startTimei, endTimei, valuei] 。
     * 第 i 个活动开始于 startTimei ，结束于 endTimei ，如果你参加这个活动，那么你可以得到价值 valuei 。
     * 你 最多 可以参加 两个时间不重叠 活动，使得它们的价值之和 最大 。
     *
     * 请你返回价值之和的 最大值 。
     * 注意，活动的开始时间和结束时间是 包括 在活动时间内的，也就是说，你不能参加两个活动且它们之一的开始时间等于另一个活动的结束时间。
     * 更具体的，如果你参加一个活动，且结束时间为 t ，那么下一个活动必须在 t + 1 或之后的时间开始。
     * @param: events
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxTwoEvents(int[][] events) {
        int result;
        result = method_01(events);
        return result;
    }

    /**
     * @Description:
     * 1. 使用二分查找法, 对events数组进行排序, 按照startTime进行升序排序, 如果startTime相同则按照value进行降序排序
     * 2. 取当前活动为第一个活动, 值为v1 = events[i][2], left = i + 1, right = n - 1, 找到满足条件的
     * events[mid][0] > events[i][1]
     * 3. 是不是应该先预处理, startTime[i] 之后能获取的最大价值, 用一个pre[]数组保存, pre[i] = max(pre[i + 1], events[i][2]), 倒序处理
     * AC: 47ms/167.57MB
     * @param: events
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] events) {
        // 对events数组进行排序
        Arrays.sort(events, (a, b) -> a[0] == b[0] ? b[2] - a[2] : a[0] - b[0]);
        int n = events.length;
        int[] prev = new int[n];
        prev[n - 1] = events[n - 1][2];
        for (int i = n - 2; i >= 0; i--) {
            prev[i] = Math.max(prev[i + 1], events[i][2]);
        }
        int ans = 0;
        // 二分查找
        for (int i = 0; i < n; i++) {
            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int mid = left + (right - left) / 2;
                if (events[mid][0] > events[i][1]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            // ans 与当前单个活动的值进行比较, 取最大值
            ans = Math.max(ans, events[i][2]);
            // 需要判断left < n
            if (left < n && events[left][0] > events[i][1]) {
                ans = Math.max(ans, events[i][2] + prev[left]);
            }

        }
        return ans;
    }

}
