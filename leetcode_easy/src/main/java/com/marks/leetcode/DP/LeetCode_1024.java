package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1024 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/25 14:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1024 {

    /**
     * @Description:
     * 你将会获得一系列视频片段，这些片段来自于一项持续时长为 time 秒的体育赛事。这些片段可能有所重叠，也可能长度不一。
     *
     * 使用数组 clips 描述所有的视频片段，其中 clips[i] = [starti, endi] 表示：某个视频片段开始于 starti 并于 endi 结束。
     *
     * 甚至可以对这些片段自由地再剪辑：
     *
     * 例如，片段 [0, 7] 可以剪切成 [0, 1] + [1, 3] + [3, 7] 三部分。
     * 我们需要将这些片段进行再剪辑，并将剪辑后的内容拼接成覆盖整个运动过程的片段（[0, time]）。返回所需片段的最小数目，如果无法完成该任务，则返回 -1 。
     *
     *
     * @param: clips
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int videoStitching(int[][] clips, int time) {
        int result;
        result = method_01(clips, time);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：clips = [[0,2],[4,6],[8,10],[1,9],[1,5],[5,9]], time = 10
     * 输出：3
     * 1. 感觉可以先对片段进行排序, 按照片段的开始时间进行排序
     * @param: clips
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2025/11/25 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] clips, int time) {
        int[] dp = new int[time + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        for (int i = 1; i <= time; i++) {
            for (int[] clip : clips) {
                if (clip[0] < i && i <= clip[1]) {
                    dp[i] = Math.min(dp[i], dp[clip[0]] + 1);
                }
            }
        }
        return dp[time] == Integer.MAX_VALUE - 1 ? -1 : dp[time];
    }
}
