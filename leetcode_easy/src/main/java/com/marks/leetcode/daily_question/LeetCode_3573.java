package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3573 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/17 17:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3573 {

    /**
     * @Description:
     * 给你一个整数数组 prices，其中 prices[i] 是第 i 天股票的价格（美元），以及一个整数 k。
     * 你最多可以进行 k 笔交易，每笔交易可以是以下任一类型：
     * 普通交易：在第 i 天买入，然后在之后的第 j 天卖出，其中 i < j。你的利润是 prices[j] - prices[i]。
     * 做空交易：在第 i 天卖出，然后在之后的第 j 天买回，其中 i < j。你的利润是 prices[i] - prices[j]。
     * 注意：你必须在开始下一笔交易之前完成当前交易。此外，你不能在已经进行买入或卖出操作的同一天再次进行买入或卖出操作。
     * 通过进行 最多 k 笔交易，返回你可以获得的最大总利润。
     * @param: prices
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2025/12/17 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumProfit(int[] prices, int k) {
        long result;
//        result = method_01(prices, k);
        result = method_02(prices, k);
        return result;
    }

    /**
     * @Description: 
     * 优化空间复杂度, 由于 i 只与 i - 1 有关，所以 i 的空间复杂度可以优化
     * @param: prices
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2025/12/18 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] prices, int k) {
        int n = prices.length;
        long[][] dp = new long[k + 1][3];

        // 初始化第0天的状态
        for (int j = 1; j <= k; j++) {
            dp[j][1] = -prices[0];
            dp[j][2] = prices[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = k; j > 0; j--) {
                dp[j][0] = Math.max(dp[j][0],
                        Math.max(dp[j][1] + prices[i], dp[j][2] - prices[i]));
                dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i]);
                dp[j][2] = Math.max(dp[j][2], dp[j - 1][0] + prices[i]);
            }
        }
        return dp[k][0];
    }

    /**
     * @Description:
     * 1. 创建一个三维数组 dp[i][j][s]，其中 i 表示第 i 天，j 表示进行 j 笔交易,
     * s表示0表示交易已完成, 1表示当前存在股票待卖出, 2 表示当前存在股票待买入
     * 2. 初始化 dp 数组，将 dp[i][j][s] 初始化为 Integer.MIN_VALUE / 2。
     * 3. dp[i][j][0] = Math.max(dp[i - 1][j][0],
     * Math.max(dp[i - 1][j - 1][1] + prices[i], dp[i - 1][j - 1][2] - prices[i]));
     * dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j][0] - prices[i]); // 普通交易的买入操作
     * dp[i][j][2] = Math.max(dp[i - 1][j][2], dp[i - 1][j][0] + prices[i]); // 做空交易的卖出操作
     * @param: prices
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2025/12/17 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] prices, int k) {
        int n = prices.length;
        long[][][] dp = new long[n][k + 1][3];

        // 初始化第0天的状态
        for (int j = 1; j <= k; j++) {
            dp[0][j][1] = -prices[0];
            dp[0][j][2] = prices[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0],
                        Math.max(dp[i - 1][j][1] + prices[i], dp[i - 1][j][2] - prices[i]));
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
                dp[i][j][2] = Math.max(dp[i - 1][j][2], dp[i - 1][j - 1][0] + prices[i]);
            }
        }
        return dp[n - 1][k][0];
    }

}
