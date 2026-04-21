package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1235 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/16 17:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1235 {

    /**
     * @Description:
     * 你打算利用空闲时间来做兼职工作赚些零花钱。
     * 这里有 n 份兼职工作，每份工作预计从 startTime[i] 开始到 endTime[i] 结束，报酬为 profit[i]。
     * 给你一份兼职工作表，包含开始时间 startTime，结束时间 endTime 和预计报酬 profit 三个数组，请你计算并返回可以获得的最大报酬。
     * 注意，时间上出现重叠的 2 份工作不能同时进行。
     * 如果你选择的工作在时间 X 结束，那么你可以立刻进行在时间 X 开始的下一份工作。
     *
     * tips:
     * 1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
     * 1 <= startTime[i] < endTime[i] <= 10^9
     * 1 <= profit[i] <= 10^4
     * @param: startTime
     * @param: endTime
     * @param: profit
     * @return int
     * @author marks
     * @CreateDate: 2026/04/16 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int result;
        result = method_01(startTime, endTime, profit);
        return result;
    }

    /**
     * @Description:
     * 1. 应该使用 dp[i], 选择取第 i 份工作能获取的最大报酬 dp[i] = dp[j] + profit[i]; dp[j] 表示第 j 份工作能获取的最大报酬, j < i && endTime[j] <= startTime[i]
     * 2. 状态转移方程: dp[i] = max(dp[i-1], dp[j] + profit[i]); j < i && endTime[j] <= startTime[i]
     * 3. 考虑一下时间复杂度, 时间复杂度为 O(n^2), 这样会导致时间超时, 需要使用二分查找进行优化, 但是二分查找需要一个有序的数组, 所以需要对endTime进行排序
     * 4. 这样时间复杂度可以降低为O(nlogn)
     * AC: 32ms/67.7MB
     * @param: startTime
     * @param: endTime
     * @param: profit
     * @return int
     * @author marks
     * @CreateDate: 2026/04/16 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i][0] = startTime[i];
            jobs[i][1] = endTime[i];
            jobs[i][2] = profit[i];
        }
        // 对 endTime 进行排序
        Arrays.sort(jobs, (a, b) -> a[1] - b[1]);
        int[] dp = new int[n]; // dp[i] 表示第 i 份工作能获取的最大报酬
        dp[0] = jobs[0][2];
        for (int i = 1; i < n; i++) {
            int targetTime = jobs[i][0];
            int j = binarySearch(jobs, targetTime, i);
            // 判断 j 是否有效
            if (j != -1) {
                dp[i] = Math.max(dp[i - 1], dp[j] + jobs[i][2]);
            } else {
                // j = -1 时
                dp[i] = Math.max(dp[i - 1], jobs[i][2]);
            }
        }

        return dp[n - 1];
    }

    private int binarySearch(int[][] jobs, int targetTime, int range) {
        int left = 0;
        int right = range - 1;
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (jobs[mid][1] <= targetTime) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

}
