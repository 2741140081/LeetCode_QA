package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/8 15:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1335 {
    /**
     * @Description: [
     * 你需要制定一份 d 天的工作计划表。工作之间存在依赖，要想执行第 i 项工作，你必须完成全部 j 项工作（ 0 <= j < i）。
     * 你每天 至少 需要完成一项任务。工作计划的总难度是这 d 天每一天的难度之和，而一天的工作难度是当天应该完成工作的最大难度。
     * 给你一个整数数组 jobDifficulty 和一个整数 d，分别代表工作难度和需要计划的天数。第 i 项工作的难度是 jobDifficulty[i]。
     * 返回整个工作计划的 最小难度 。如果无法制定工作计划，则返回 -1 。
     *
     * tips:
     * 1 <= jobDifficulty.length <= 300
     * 0 <= jobDifficulty[i] <= 1000
     * 1 <= d <= 10
     * ]
     * @param jobDifficulty
     * @param d
     * @return int
     * @author marks
     * @CreateDate: 2024/10/8 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDifficulty(int[] jobDifficulty, int d) {
        int result = 0;
        result = method_01(jobDifficulty, d);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：jobDifficulty = [6,5,4,3,2,1], d = 2
     * 输出：7
     * 解释：第一天，您可以完成前 5 项工作，总难度 = 6.
     * 第二天，您可以完成最后一项工作，总难度 = 1.
     * 计划表的难度 = 6 + 1 = 7
     *
     * 将数组job划分成d份, 使得和最小, minResult = max_1 +.... + max_d;
     *
     * 预处理 jobDifficulty, pre[i][j] 表示jobD[i] ~ jobD[j] 之间的最大值
     * AC:12ms/43.12MB
     * ]
     * @param jobDifficulty
     * @param d
     * @return int
     * @author marks
     * @CreateDate: 2024/10/8 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] jobDifficulty, int d) {
        int n = jobDifficulty.length;
        int sum = Arrays.stream(jobDifficulty).sum();
        if (d > n) {
            //无法制定工作计划, 因为每一天都必须有一个工作, 相当于不能划分一个空数组
            return -1;
        }
        int[][] pre = new int[n][n];
        for (int i = 0; i < n; i++) {
            int ans = jobDifficulty[i];
            pre[i][i] = ans;
            for (int j = i + 1; j < n; j++) {
                ans = Math.max(ans, jobDifficulty[j]);
                pre[i][j] = ans;
            }
        }
        int[][] dp = new int[n + 1][d + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], sum);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, d); j++) {
                for (int k = 0; k < i; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[k][j - 1] + pre[k][i - 1]);
                }
            }
        }
        return dp[n][d];
    }
}
