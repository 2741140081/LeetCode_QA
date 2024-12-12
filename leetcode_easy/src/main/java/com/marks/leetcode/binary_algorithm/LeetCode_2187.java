package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/20 9:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2187 {
    /**
     * @Description: [
     * 给你一个数组 time ，其中 time[i] 表示第 i 辆公交车完成 一趟旅途 所需要花费的时间。
     *
     * 每辆公交车可以 连续 完成多趟旅途，也就是说，一辆公交车当前旅途完成后，可以 立马开始 下一趟旅途。每辆公交车 独立 运行，也就是说可以同时有多辆公交车在运行且互不影响。
     *
     * 给你一个整数 totalTrips ，表示所有公交车 总共 需要完成的旅途数目。请你返回完成 至少 totalTrips 趟旅途需要花费的 最少 时间。
     *
     * tips:
     * 1 <= time.length <= 10^5
     * 1 <= time[i], totalTrips <= 10^7
     * ]
     * @param time 
     * @param totalTrips
     * @return long
     * @author marks
     * @CreateDate: 2024/11/20 9:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimumTime(int[] time, int totalTrips) {
        long result;
        result = method_01(time, totalTrips);
        return result;
    }

    /**
     * @Description: [
     * 输入：time = [1,2,3], totalTrips = 5
     * 输出：3
     * 解释：
     * - 时刻 t = 1 ，每辆公交车完成的旅途数分别为 [1,0,0] 。
     *   已完成的总旅途数为 1 + 0 + 0 = 1 。
     * - 时刻 t = 2 ，每辆公交车完成的旅途数分别为 [2,1,0] 。
     *   已完成的总旅途数为 2 + 1 + 0 = 3 。
     * - 时刻 t = 3 ，每辆公交车完成的旅途数分别为 [3,1,1] 。
     *   已完成的总旅途数为 3 + 1 + 1 = 5 。
     * 所以总共完成至少 5 趟旅途的最少时间为 3 。
     *
     * AC:72ms/56.52MB
     * ]
     * @param time
     * @param totalTrips
     * @return long
     * @author marks
     * @CreateDate: 2024/11/20 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] time, int totalTrips) {
        long minTime = (long)Arrays.stream(time).min().getAsInt();
        if (time.length < 2) {
            return minTime * totalTrips;
        }
        long left = 1;
        long right = minTime * totalTrips;

        while (left < right) {
            long mid = (right - left) / 2 + left;
            long total = getTotalTripsByTargetTime(time, mid);
            if (total >= totalTrips) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private long getTotalTripsByTargetTime(int[] time, long mid) {
        long sum = 0;
        for (int t : time) {
            sum += mid / t;
        }
        return sum;
    }
}
