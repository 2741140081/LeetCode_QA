package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1986 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1986 {

    /**
     * @Description:
     * 你被安排了 n 个任务。任务需要花费的时间用长度为 n 的整数数组 tasks 表示，第 i 个任务需要花费 tasks[i] 小时完成。
     * 一个 工作时间段 中，你可以 至多 连续工作 sessionTime 个小时，然后休息一会儿。
     *
     * 你需要按照如下条件完成给定任务：
     *
     * 如果你在某一个时间段开始一个任务，你需要在 同一个 时间段完成它。
     * 完成一个任务后，你可以 立马 开始一个新的任务。
     * 你可以按 任意顺序 完成任务。
     * 给你 tasks 和 sessionTime ，请你按照上述要求，返回完成所有任务所需要的 最少 数目的 工作时间段 。
     *
     * 测试数据保证 sessionTime 大于等于 tasks[i] 中的 最大值 。
     * tips:
     * n == tasks.length
     * 1 <= n <= 14
     * 1 <= tasks[i] <= 10
     * max(tasks[i]) <= sessionTime <= 15
     * @param: tasks
     * @param: sessionTime
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSessions(int[] tasks, int sessionTime) {
        int result;
//        result = method_01(tasks, sessionTime);
        result = method_02(tasks, sessionTime);
        return result;
    }

    /**
     * @Description:
     * 状态压缩
     * AC: 120ms/43.50MB
     * @param: tasks
     * @param: sessionTime
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 16:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] tasks, int sessionTime) {
        int n = tasks.length;
        int m = 1 << n;
        int[] dp = new int[m];
        boolean[] vis = new boolean[m];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        for (int mask = 1; mask < m; mask++) {
            int needTime = 0;
            for (int i = 0; i < n; i++) {
                if (((mask >> i) & 1) == 1) {
                    needTime += tasks[i];
                }
            }
            if (needTime <= sessionTime) {
                vis[mask] = true;
            }
        }
        dp[0] = 0;
        for (int mask = 1; mask < m; mask++) {
            for (int i = mask; i > 0; i = (i - 1) & mask) {
                if (vis[i]) {
                    dp[mask] = Math.min(dp[mask], dp[mask ^ i] + 1);
                }
            }
        }

        return dp[m - 1];
    }

    /**
     * @Description:
     *
     * @param: tasks
     * @param: sessionTime
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] tasks, int sessionTime) {
        int n = tasks.length, m = 1 << n;
        final int INF = 20;
        int[] dp = new int[m];
        Arrays.fill(dp, INF);

        // 预处理每个状态，合法状态预设为 1
        for (int i = 1; i < m; i++) {
            int state = i, idx = 0;
            int spend = 0;
            while (state > 0) {
                int bit = state & 1;
                if (bit == 1) {
                    spend += tasks[idx];
                }
                state >>= 1;
                idx++;
            }
            if (spend <= sessionTime) {
                dp[i] = 1;
            }
        }

        // 对每个状态枚举子集，跳过已经有最优解的状态
        for (int i = 1; i < m; i++) {
            if (dp[i] == 1) {
                continue;
            }
            int split = i >> 1;
            // 由于转移是由子集与补集得来，因此可以将子集分割为两块，避免重复枚举
            for (int j = (i - 1) & i; j > split; j = (j - 1) & i) {
                // i 状态的最优解可能由当前子集 j 与子集 j 的补集得来
                dp[i] = Math.min(dp[i], dp[j] + dp[i ^ j]);
            }
        }

        return dp[m - 1];
    }
}
