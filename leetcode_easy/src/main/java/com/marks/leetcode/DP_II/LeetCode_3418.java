package com.marks.leetcode.DP_II;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3418 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/21 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3418 {

    /**
     * @Description:
     * 给你一个 m x n 的网格。一个机器人从网格的左上角 (0, 0) 出发，目标是到达网格的右下角 (m - 1, n - 1)。在任意时刻，机器人只能向右或向下移动。
     * 网格中的每个单元格包含一个值 coins[i][j]：
     * 如果 coins[i][j] >= 0，机器人可以获得该单元格的金币。
     * 如果 coins[i][j] < 0，机器人会遇到一个强盗，强盗会抢走该单元格数值的 绝对值 的金币。
     * 机器人有一项特殊能力，可以在行程中 最多感化 2个单元格的强盗，从而防止这些单元格的金币被抢走。
     *
     * 注意：机器人的总金币数可以是负数。
     * 返回机器人在路径上可以获得的 最大金币数 。
     *
     * tips:
     * m == coins.length
     * n == coins[i].length
     * 1 <= m, n <= 500
     * -1000 <= coins[i][j] <= 1000
     * @param: coins
     * @return int
     * @author marks
     * @CreateDate: 2026/04/21 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumAmount(int[][] coins) {
        int result;
        result = method_01(coins);
        return result;
    }

    /**
     * @Description:
     * 1. 正常的动态规划, 但是需要额外定义一个数据, int[][][] dp[i][j][k], k 表示感化的次数, 最大值为 2, k <= 2
     * 2. 转移方程: 假设位于 (i, j) 的单元格, 判断
     * coins[i][j] >= 0, 可以从上或者左进行转移, 取两者的较大值 + coins[i][j], dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i][j - 1][k]) + coins[i][j];
     * coins[i][j] < 0, 可以选择感化或者不感化, 不感化与 >=0 的转移方程一样
     * 感化情况, 假设位于 (i, j) 的单元格, dp[i][j][k] = Math.max(dp[i - 1][j][k - 1], dp[i][j - 1][k - 1]); k 的取值范围是 1 到 2
     * 3. 初始化, 设置一个 INF, 需要初始化第一行和第一列
     * 4. 情况分析完成, 整体时间复杂度为 O(m * n * k), 其中k 为 2, 所以时间复杂度 和 空间复杂度 都为 O(m * n)
     * AC: 90ms/233.02MB
     * @param: coins
     * @return int
     * @author marks
     * @CreateDate: 2026/04/21 15:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] coins) {
        int m = coins.length;
        int n = coins[0].length;
        int INF = Integer.MIN_VALUE / 2;
        int[][][] dp = new int[m][n][3];
        // 初始化 dp
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        // 初始化(0, 0) 节点
        dp[0][0][0] = coins[0][0];
        if (coins[0][0] < 0) {
            // 不需要扣钱
            dp[0][0][1] = 0;
        }
        // 初始化第一行
        for (int j = 1; j < n; j++) {
            if (coins[0][j] >= 0) {
                for (int k = 0; k < 3; k++) {
                    dp[0][j][k] = dp[0][j - 1][k] + coins[0][j];
                }
            } else {
                // 不感化
                for (int k = 0; k < 3; k++) {
                    dp[0][j][k] = dp[0][j - 1][k] + coins[0][j];
                }
                // 感化
                for (int k = 1; k < 3; k++) {
                    dp[0][j][k] = Math.max(dp[0][j - 1][k - 1], dp[0][j][k]);
                }
            }
        }
        // 初始化第一列
        for (int i = 1; i < m; i++) {
            if (coins[i][0] >= 0) {
                for (int k = 0; k < 3; k++) {
                    dp[i][0][k] = dp[i - 1][0][k] + coins[i][0];
                }
            } else {
                // 不感化
                for (int k = 0; k < 3; k++) {
                    dp[i][0][k] = dp[i - 1][0][k] + coins[i][0];
                }
                // 感化
                for (int k = 1; k < 3; k++) {
                    dp[i][0][k] = Math.max(dp[i - 1][0][k - 1], dp[i][0][k]);
                }
            }
        }
        // 执行动态规划
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (coins[i][j] >= 0) {
                    for (int k = 0; k < 3; k++) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i][j - 1][k]) + coins[i][j];
                    }
                } else {
                    // 不感化
                    for (int k = 0; k < 3; k++) {
                        dp[i][j][k] = Math.max(dp[i - 1][j][k], dp[i][j - 1][k]) + coins[i][j];
                    }
                    // 感化
                    for (int k = 1; k < 3; k++) {
                        // 还需要与 不感化的情况 进行比较, 取较大的值
                        dp[i][j][k] = Math.max(dp[i][j][k], Math.max(dp[i - 1][j][k - 1], dp[i][j - 1][k - 1]));
                    }
                }
            }
        }
        int ans = INF;
        for (int k = 0; k < 3; k++) {
            ans = Math.max(ans, dp[m - 1][n - 1][k]);
        }


        return ans;
    }

}
