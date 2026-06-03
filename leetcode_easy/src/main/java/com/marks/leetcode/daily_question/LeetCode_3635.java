package com.marks.leetcode.daily_question;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3635 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/3 10:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3635 {

    /**
     * @Description:
     * 给你两种类别的游乐园项目：陆地游乐设施 和 水上游乐设施。
     * 陆地游乐设施
     * landStartTime[i] – 第 i 个陆地游乐设施最早可以开始的时间。
     * landDuration[i] – 第 i 个陆地游乐设施持续的时间。
     * 水上游乐设施
     * waterStartTime[j] – 第 j 个水上游乐设施最早可以开始的时间。
     * waterDuration[j] – 第 j 个水上游乐设施持续的时间。
     * 一位游客必须从 每个 类别中体验 恰好一个 游乐设施，顺序 不限 。
     *
     * 游乐设施可以在其开放时间开始，或 之后任意时间 开始。
     * 如果一个游乐设施在时间 t 开始，它将在时间 t + duration 结束。
     * 完成一个游乐设施后，游客可以立即乘坐另一个（如果它已经开放），或者等待它开放。
     * 返回游客完成这两个游乐设施的 最早可能时间 。
     *
     * tips:
     * 1 <= n, m <= 5 * 10^4
     * landStartTime.length == landDuration.length == n
     * waterStartTime.length == waterDuration.length == m
     * 1 <= landStartTime[i], landDuration[i], waterStartTime[j], waterDuration[j] <= 10^5
     * @param: landStartTime
     * @param: landDuration
     * @param: waterStartTime
     * @param: waterDuration
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int result;
        result = method_01(landStartTime, landDuration, waterStartTime, waterDuration);
        return result;
    }

    /**
     * @Description:
     * 1. 查找两次, 分别找到 陆地 + 水上最短时间, 和 水上 + 陆地最早离开时间
     * 2. 使用间接排序, 根据 landStartTime + landDuration 进行升序排序
     * 3. 忘记处理不存在的情况, 即 startTime >= minLandEndTime 条件不满足时的情况
     * AC: 217ms/98.01MB
     * 感觉时间复杂度有点高, 排序的复杂度时间复杂度是 O(nlogn)
     * @param: landStartTime
     * @param: landDuration
     * @param: waterStartTime
     * @param: waterDuration
     * @return int
     * @author marks
     * @CreateDate: 2026/06/03 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int m = landStartTime.length;
        int n = waterStartTime.length;
        Integer[] landIndex = new Integer[m];
        Integer[] waterIndex = new Integer[n];
        for (int i = 0; i < m; i++) {
            landIndex[i] = i;
        }
        for (int i = 0; i < n; i++) {
            waterIndex[i] = i;
        }
        Arrays.sort(landIndex, Comparator.comparingInt(i -> landStartTime[i] + landDuration[i]));
        Arrays.sort(waterIndex, Comparator.comparingInt(i -> waterStartTime[i] + waterDuration[i]));
        int ans = Integer.MAX_VALUE;
        int minLandEndTime = landStartTime[landIndex[0]] + landDuration[landIndex[0]];
        // 找到水上设施使得离开时间最早
        for (int i = 0; i < n; i++) {
            int startTime = waterStartTime[waterIndex[i]];
            if (startTime >= minLandEndTime) {
                ans = Math.min(ans, startTime + waterDuration[waterIndex[i]]);
                break;
            } else {
                ans = Math.min(ans, minLandEndTime + waterDuration[waterIndex[i]]);
            }
        }
        int minWaterEndTime = waterStartTime[waterIndex[0]] + waterDuration[waterIndex[0]];
        // 找到离开陆地时间最早
        for (int i = 0; i < m; i++) {
            int startTime = landStartTime[landIndex[i]];
            if (startTime >= minWaterEndTime) {
                ans = Math.min(ans, startTime + landDuration[landIndex[i]]);
                break;
            } else {
                ans = Math.min(ans, minWaterEndTime + landDuration[landIndex[i]]);
            }
        }

        return ans;
    }

}
