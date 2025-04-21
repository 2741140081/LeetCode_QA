package com.marks.leetcode.greedy_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/1 16:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3440 {
    /**
     * @Description:
     * 给你一个整数 eventTime 表示一个活动的总时长，这个活动开始于 t = 0 ，结束于 t = eventTime 。
     *
     * 同时给你两个长度为 n 的整数数组 startTime 和 endTime 。它们表示这次活动中 n 个时间 没有重叠 的会议，其中第 i 个会议的时间为 [startTime[i], endTime[i]] 。
     *
     * 你可以重新安排 至多 一个会议，安排的规则是将会议时间平移，且保持原来的 会议时长 ，你的目的是移动会议后 最大化 相邻两个会议之间的 最长 连续空余时间。
     *
     * 请你返回重新安排会议以后，可以得到的 最大 空余时间。
     *
     * 注意，会议 不能 安排到整个活动的时间以外，且会议之间需要保持互不重叠。
     *
     * 注意：重新安排会议以后，会议之间的顺序可以发生改变。
     * @param eventTime
     * @param startTime
     * @param endTime
     * @return int
     * @author marks
     * @CreateDate: 2025/4/1 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxFreeTime(int eventTime, int[] startTime, int[] endTime) {
        int result;
        result = method_01(eventTime, startTime, endTime);
        return result;
    }

    /**
     * @Description:
     * k = [i, j]
     * 1. 判断 k 是否可以移动到外部, k + pre[i - 1] + pre[j + 1], 如果不能, pre[i - 1] + pre[j + 1]
     * 2. 特殊情况 两个端点, 左侧端点只能左移, 右侧端点只能右移, 以便 k 最大
     * eventTime =405
     * startTime = [48,84,94,335]
     * endTime = [55,88,113,378]
     *
     * 0, 17_19, 24_25, 41
     *
     * AC: 5ms/59.50MB
     * @param eventTime
     * @param startTime
     * @param endTime
     * @return int
     * @author marks
     * @CreateDate: 2025/4/1 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int eventTime, int[] startTime, int[] endTime) {
        int n = startTime.length;
        int[] pre = new int[n + 1];
        pre[0] = startTime[0];
        for (int i = 1; i < n; i++) {
            pre[i] = startTime[i] - endTime[i - 1];
        }
        pre[n] = eventTime - endTime[n - 1];

        // 前缀 & 后缀 的最大值
        int[] pre_max = new int[n + 1];
        pre_max[0] = pre[0];
        for (int i = 1; i <= n; i++) {
            pre_max[i] = Math.max(pre_max[i - 1], pre[i]);
        }
        int[] suf_max = new int[n + 1];
        suf_max[n] = pre[n];
        for (int i = n - 1; i >= 0; i--) {
            suf_max[i] = Math.max(suf_max[i + 1], pre[i]);
        }

        // 判断两端分别移动过程中最大值, 这种仍然需要考虑将两端外移
        int ans = Math.max(pre[0] + pre[1], pre[n] + pre[n - 1]);

        int width_0 = endTime[0] - startTime[0];
        if (width_0 <= suf_max[2]) {
            ans = Math.max(ans, pre[0] + pre[1] + width_0);
        }

        int width_n = endTime[n - 1] - startTime[n - 1];
        if (width_n <= pre_max[n - 2]) {
            ans = Math.max(ans, pre[n] + pre[n - 1] + width_n);
        }

        // 处理中间的一般情况
        for (int i = 1; i < n - 1; i++) {
            int width = endTime[i] - startTime[i];
            if (pre_max[i - 1] >= width || suf_max[i + 2] >= width) {
                ans = Math.max(ans, width + pre[i] + pre[i + 1]);
            } else {
                ans = Math.max(ans, pre[i] + pre[i + 1]);
            }
        }
        return ans;
    }
}
