package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_539 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/6 17:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_539 {

    /**
     * @Description:
     * 给定一个 24 小时制（小时:分钟 "HH:MM"）的时间列表，找出列表中任意两个时间的最小时间差并以分钟数表示。
     * @param: timePoints
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 17:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMinDifference(List<String> timePoints) {
        int result;
        result = method_01(timePoints);
        return result;
    }

    /**
     * @Description:
     * 1. 将时间点转换成分钟数
     * 2. 排序时间点, 获取最小时间差
     * 3. 从下标1开始, 每次对比当前 i 的前后时间点, 获取时间差
     * 4. 这个数组是一个环形数组, 所以需要计算首尾时间点之间的时间差 "24:00" - last + first
     * AC: 9ms/47.26MB
     * @param: timePoints
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 17:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<String> timePoints) {
        int n = timePoints.size();
        int[] time = new int[n];
        for (int i = 0; i < n; i++) {
            time[i] = Integer.parseInt(timePoints.get(i).substring(0, 2)) * 60 + Integer.parseInt(timePoints.get(i).substring(3));
        }
        // 排序
        Arrays.sort(time);
        // 将 "24:00" - last + first 赋值给ans
        int ans = 24 * 60 - time[n - 1] + time[0];
        for (int i = 1; i < n; i++) {
            ans = Math.min(ans, time[i] - time[i - 1]);
        }

        return ans;
    }

}
