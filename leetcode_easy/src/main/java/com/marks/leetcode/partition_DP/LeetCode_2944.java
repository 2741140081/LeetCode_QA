package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/8 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2944 {
    /**
     * @Description: [
     * 你在一个水果超市里，货架上摆满了玲琅满目的奇珍异果。
     * 给你一个下标从 1 开始的数组 prices ，其中 prices[i] 表示你购买第 i 个水果需要花费的金币数目。
     * 水果超市有如下促销活动：
     * 如果你花费 price[i] 购买了下标为 i 的水果，那么你可以免费获得下标范围在 [i + 1, i + i] 的水果。
     * 注意 ，即使你 可以 免费获得水果 j ，你仍然可以花费 prices[j] 个金币去购买它以获得它的奖励。
     *
     * 请你返回获得所有水果所需要的 最少 金币数。
     *
     * tips:
     * 1 <= prices.length <= 1000
     * 1 <= prices[i] <= 105
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/10/8 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumCoins(int[] prices) {
        int result = 0;
//        result = method_01(prices);
//        result = method_02(prices);
        result = method_03(prices);
        return result;
    }
    /**
     * @Description: [
     * AC:8ms/43.27MB
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 10:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] prices) {
        int n = prices.length;
        int[] dp = new int[n + 1];
        dp[1] = prices[0];
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + prices[i - 1];
            for (int j = i - 2; j >= 0 && 2 * j + 2 >= i ; j--) {
                dp[i] = Math.min(dp[i], dp[j] + prices[j]);
            }
        }
        return dp[n];
    }

    /**
     * @Description: [
     * 使用双端队列解决:
     * 在队列的前端，如果上次购买的时间到现在已经超过了免费的时间，那么我们把这些购买从队列中弹出。
     * 在队列的后端，如果队列里的费用比最新的费用还高或相等，那么把这些费用从队列里弹出
     *
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] prices) {
        int n = prices.length;
        int[] dp = new int[n + 1];
        int[] que = new int[n + 1];
        int read = 0, write = 0;
        dp[0] = prices[0];

        for (int i = 1; i <= n; i++) {
            while (read < write && que[read] < (i - 1) / 2) {
                read++;
            }
            int min = dp[read] + (i < n ? prices[i] : 0);

            while (read < write && dp[write] >= min) {
                write--;
            }

            que[++write] = i;
            dp[write] = min;
        }
        return dp[write];
    }

    /**
     * @Description: [
     * E1:
     * 输入：prices = [26,18,6,12,49,7,45,45]
     * 输出：39
     *
     * 用 prices[0] = 26 个金币购买第 1 个水果，你可以免费获得第 2 个水果。
     * 免费获得第 2 个水果。
     * 用 prices[2] = 6 个金币购买第 3 个水果，你可以免费获得第 4，5，6（接下来的三个）水果。
     * 免费获得第 4 个水果。
     * 免费获得第 5 个水果。
     * 用 prices[5] = 7 个金币购买第 6 个水果，你可以免费获得第 7 和 第 8 个水果。
     * 免费获得第 7 个水果。
     * 免费获得第 8 个水果。
     * 请注意，即使您可以免费获得第 6 个水果作为购买第 3 个水果的奖励，但您购买它是为了获得其奖励，这是更优化的。
     *
     * AC:3ms/43.10MB
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/10/8 16:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] prices) {
        int n = prices.length;
        int[] dp = new int[n];
        int min = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = min + prices[i];
            min = Integer.MAX_VALUE;
            for (int j = i / 2; j <= i; j++) {
                min = Math.min(dp[j], min);
            }
        }
        return min;
    }
}
