package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1824 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/5 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1824 {

    /**
     * @Description:
     * 给你一个长度为 n 的 3 跑道道路 ，它总共包含 n + 1 个 点 ，编号为 0 到 n 。
     * 一只青蛙从 0 号点第二条跑道 出发 ，它想要跳到点 n 处。然而道路上可能有一些障碍。
     * 给你一个长度为 n + 1 的数组 obstacles ，其中 obstacles[i] （取值范围从 0 到 3）表示在点 i 处的 obstacles[i] 跑道上有一个障碍。
     * 如果 obstacles[i] == 0 ，那么点 i 处没有障碍。任何一个点的三条跑道中 最多有一个 障碍。
     *
     * 比方说，如果 obstacles[2] == 1 ，那么说明在点 2 处跑道 1 有障碍。
     * 这只青蛙从点 i 跳到点 i + 1 且跑道不变的前提是点 i + 1 的同一跑道上没有障碍。
     * 为了躲避障碍，这只青蛙也可以在 同一个 点处 侧跳 到 另外一条 跑道（这两条跑道可以不相邻），但前提是跳过去的跑道该点处没有障碍。
     * 比方说，这只青蛙可以从点 3 处的跑道 3 跳到点 3 处的跑道 1 。
     * 这只青蛙从点 0 处跑道 2 出发，并想到达点 n 处的 任一跑道 ，请你返回 最少侧跳次数 。
     *
     * 注意：点 0 处和点 n 处的任一跑道都不会有障碍。
     * tips:
     * obstacles.length == n + 1
     * 1 <= n <= 5 * 10^5
     * 0 <= obstacles[i] <= 3
     * obstacles[0] == obstacles[n] == 0
     * @param: obstacles
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSideJumps(int[] obstacles) {
        int result;
        result = method_01(obstacles);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划 dp[i][j] 表示当前在点i 处, 第 j 个跑道上的最小侧跳次数
     * 2. 青蛙可以在同一点处跳到任意跑道上, 并且同一点处存在的障碍物 <= 1(必定可以到达点n).
     * 3. 当 for j(1~3) 时, 如果 j == obstacles[i]时, 此路不通, 需要在前一个 dp[i - 1][j] 进行侧跳,
     * 如果进行侧跳, 需要判断另外两条跑道是否存在障碍, 获取 int prev = obstacles[i - 1];
     * if prev != 0, for k(1~3) k != prev && k != j, dp[i][k] = Math.min(dp[i - 1][k], dp[i - 1][j] + 1); 好像有点问题, 先写代码看看吧
     * @param: obstacles
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] obstacles) {
        final int INF = Integer.MAX_VALUE / 2;
        int n = obstacles.length;
        int[][] dp = new int[n][3];
        Arrays.fill(dp[0], 1);
        dp[0][1] = 0;
        for (int i = 1; i < n; i++) {
            int minCost = INF; // 获取当前点 i 处, 最小侧跳次数
            for (int j = 0; j < 3; j++) {
                if (j == obstacles[i] - 1) {
                    dp[i][j] = INF;
                } else {
                    minCost = Math.min(minCost, dp[i - 1][j]);
                }
            }
            // 更新 dp[i][j]
            for (int j = 0; j < 3; j++) {
                if (j == obstacles[i] - 1) {
                    continue;
                }
                dp[i][j] = Math.min(dp[i - 1][j], minCost + 1);
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }
        return ans;
    }

}
