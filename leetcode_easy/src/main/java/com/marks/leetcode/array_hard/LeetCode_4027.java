package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_4027 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/21 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_4027 {

    /**
     * @Description:
     * 给你一个整数 n 表示一栋建筑的楼层数，楼层编号从 0 到 n - 1 。
     * 同时给你一个整数 start ，表示电梯的起始楼层，以及一个二维整数数组 requests ，
     * 其中 requests[i] = [arrivali, floori] 表示在时间 arrivali 发出了一个前往楼层 floori 的请求。
     * 在时间 0 ，电梯在楼层 start 。
     * 每一秒钟，电梯可以 向上 移动一层、向下 移动一层，或者 停留 在当前楼层。
     * 一个请求 只能 在其到达时间或之后被处理；从请求到达时起，只要电梯在任意时刻位于该请求对应的楼层，该请求就会被 立即 处理。
     * 返回处理所有请求所需的 最短 时间。
     * tips:
     * 1 <= n <= 10^9
     * 1 <= requests.length <= 16
     * requests[i] == [arrivali, floori]
     * 0 <= arrivali <= 10^9
     * 0 <= start, floori <= n - 1
     * @param: n
     * @param: start
     * @param: requests
     * @return long
     * @author marks
     * @CreateDate: 2026/08/21 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long elevatorRequests(int n, int start, int[][] requests) {
        long result;
        result = method_01(n, start, requests);
        return result;
    }

    /**
     * @Description:
     * 1. 由于 request 很小, 所以应该采用dp + 状态压缩来处理
     * 2. 如何计算到达每一个请求层的时间, 即电梯前一个层级位于什么位置, 需要一个额外数组记录
     * int[][] dp = new int[2^m][m], dp[mask][j], mask 表示已经完成的请求, j 表示完成最后一个请求的下标位置.
     * 3. 状态转移方程: dp[p_mask ^ (1 << i)][i] = Math.min(dp[p_mask][j] + Math.abs(i - j))
     * 001 -> 2 010 -> 2, 011 -> 6 111 -> 8
     * AC: 314ms/81.05MB
     * @param: n
     * @param: start
     * @param: requests
     * @return long
     * @author marks
     * @CreateDate: 2026/08/21 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int start, int[][] requests) {
        int m = requests.length;
        int len = 1 << m;
        long INF = Long.MAX_VALUE / 2;
        long[][] dp = new long[len][m];
        for (int i = 0; i < len; i++) {
            Arrays.fill(dp[i], INF);
        }
        // 处理单个请求
        for (int i = 0; i < m; i++) {
            int[] curr = requests[i];
            dp[1 << i][i] = Math.max(Math.abs(start - curr[1]), curr[0]);
        }

        for (int mask = 0; mask < len; mask++) {
            for (int i = 0; i < m; i++) {
                if (dp[mask][i] == INF) {
                    continue;
                }

                // 处理下一个请求
                for (int j = 0; j < m; j++) {
                    if ((mask & (1 << j)) == 0) {
                        // 电梯从第 i 个请求的楼层移动到第 j 个请求的楼层所花的最小时间
                        long time = Math.max(Math.abs(requests[j][1] - requests[i][1]) + dp[mask][i], requests[j][0]);
                        dp[mask | (1 << j)][j] = Math.min(dp[mask | (1 << j)][j], time);
                    }
                }
            }
        }
        long ans = Long.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            ans = Math.min(dp[len - 1][i], ans);
        }

        return ans;
    }

}
