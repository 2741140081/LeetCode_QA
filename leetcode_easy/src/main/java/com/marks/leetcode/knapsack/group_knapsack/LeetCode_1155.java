package com.marks.leetcode.knapsack.group_knapsack;

import java.util.Arrays;

/**
 * <p>项目名称: 分组背包 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 16:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1155 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [功能描述:
     * 这里有 n 个一样的骰子，每个骰子上都有 k 个面，分别标号为 1 到 k 。
     * 给定三个整数 n、k 和 target，请返回投掷骰子的所有可能得到的结果（共有 k^n 种方式），使得骰子面朝上的数字总和等于 target。
     * 由于答案可能很大，你需要对 10^9 + 7 取模。
     * tips:
     * 1 <= n, k <= 30
     * 1 <= target <= 1000
     * ]
     * @param n
     * @param k
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 16:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numRollsToTarget(int n, int k, int target) {
        int result = 0;
        result = method_01(n, k, target);
        return result;
    }
    /**
     * @Description: [功能描述
     * 输入：n = 2, k = 6, target = 7
     * 输出：6
     * 解释：你掷了两个骰子，每个骰子有 6 个面。
     * 有 6 种方式得到总和为 7 的结果: 1+6, 2+5, 3+4, 4+3, 5+2, 6+1。
     * 1. 不能用多重背包来看待, 因为1,6 和6,1 不是同一种方案
     * int[][] dp = new int[][8]
     * dp[i][j] =
     * 忽略上面
     * int[][][] dp = new int[n + 1][target + 1][k + 1]
     * dp[0][0][0] = 1
     *
     * dp[i][j][k] += K * dp[i - 1][j - k][1]
     * dp[2][7][1] + dp[2][7][2]...... + dp[2][7][6] = res
     * dp[1][6][6] + dp[1][5][5]...... + dp[1][1][1]
     * dp[i][j][k] = dp[i - 1][j - k][j - k] * i
     *
     * dp[2][7] = dp[1][1] + dp[1][2] + ..... + dp[1][6]
     * ]
     * @param n
     * @param k
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 16:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int k, int target) {
        int[][] dp = new int[2][target + 1];
        dp[0][0] = 1;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            for (int j = 0; j <= target; j++) {
                dp[curr][j] = 0;
                for (int l = 1; l <= k; l++) {
                    if (j >= l) {
                        dp[curr][j] =(dp[curr][j] + dp[pre][j - l]) % MOD;
                    }
                }
            }

        }
        return dp[curr][target];
    }
}
