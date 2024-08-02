package com.marks.leetcode.knapsack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/2 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3180 {
    /**
     * @Description: [
     * 给你一个整数数组 rewardValues，长度为 n，代表奖励的值。
     * 最初，你的总奖励 x 为 0，所有下标都是 未标记 的。你可以执行以下操作 任意次 ：
     * 从区间 [0, n - 1] 中选择一个 未标记 的下标 i。
     * 如果 rewardValues[i] 大于 你当前的总奖励 x，
     * 则将 rewardValues[i] 加到 x 上（即 x = x + rewardValues[i]），并 标记 下标 i。
     * 以整数形式返回执行最优操作能够获得的 最大 总奖励。
     *
     * tips:
     * 1 <= rewardValues.length <= 2000
     * 1 <= rewardValues[i] <= 2000
     * ]
     * @param rewardValues
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxTotalReward(int[] rewardValues) {
        int result = 0;
        result = method_01(rewardValues);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：rewardValues = [1, 2, 3, 4, 5, 6, 7, 8, 9]
     * 输出：11
     * 解释：
     * 依次标记下标 0、2 和 1。总奖励为 11，这是可获得的最大值。
     * 思路是:
     * 最大总和: sum
     * 找到数组中的最大值: 6
     * 然后剩余的可以取值的和 sum_1 < 6
     * 然后寻找第二大的值要求 sum_2 < 4
     *
     * dp[i][j]
     * ]
     * @param rewardValues
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 11:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] rewardValues) {
        Arrays.sort(rewardValues);
        int n = rewardValues.length;
        int m = 2 * rewardValues[n-1];
        // 使用滚动数组优化空间
        boolean[][] dp = new boolean[2][m];
        dp[0][0] = true;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = rewardValues[i-1];
            for (int j = 0; j < m; j++) {
                /*
                i表示前i个数(取/不取)，总和为j
                如果当前i不取, dp[i][j] = dp[i-1][j]
                如果取当前i, dp[i][j] = dp[i-1][j-temp]
                需要判断temp是否可以取得
                j - temp < temp
                j - temp >= 0
                 */
                dp[curr][j] = dp[pre][j] || (j >= temp && j < 2 * temp && dp[pre][j - temp]);
            }
        }
        for (int i = m-1; i >= 0; i--) {
            if (dp[curr][i]) {
                return i;
            }
        }
        return 0;
    }
}
