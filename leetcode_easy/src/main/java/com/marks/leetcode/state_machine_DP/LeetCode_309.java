package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/26 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_309 {
    /**
     * @Description: [
     * 给定一个整数数组prices，其中第  prices[i] 表示第 i 天的股票价格 。
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     *
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int[] prices) {
        int result = 0;
//        result = method_01(prices);
//        int result_2 = method_02(prices);
        int result_3 = method_03(prices);
//        result = method_04(prices);
        result = method_05(prices);
        return result == result_3 ? result : result_3;
    }

    /**
     * @Description: [官方题解: 动态规划之空间优化]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/27 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_05(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[][] dp = new int[n][3];
        int dp_0 = -prices[0];
        int dp_1 = 0;
        int dp_2 = 0;
        dp[0][0] = -prices[0];
        for (int i = 1; i < n; i++) {
            int pre_0 = dp_0;
            int pre_1 = dp_1;
            int pre_2 = dp_2;
            dp_0 = Math.max(pre_0, pre_2 - prices[i]);
            dp_1 = pre_0 + prices[i];
            dp_2 = Math.max(pre_2, pre_1);
        }
        return Math.max(dp_1, dp_2);
    }

    /**
     * @Description: [官方题解
     * // dp[i][0]: 手上持有股票的最大收益
     * // dp[i][1]: 手上不持有股票，并且处于冷冻期中的累计最大收益
     * // dp[i][2]: 手上不持有股票，并且不在冷冻期中的累计最大收益
     * 动态规划: 由于dp[i] 只与dp[i - 1]存在关系, 且无后效性
     * 可以使用空间优化 第一维空间, 优化后为dp[3] 即常数的空间复杂度
     * 可以使用3个变量来记录值 method_05: 空间优化
     *
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/27 15:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_04(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int[][] dp = new int[n][3];
        dp[0][0] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][2] - prices[i]);
            dp[i][1] = dp[i - 1][0] + prices[i];
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1]);
        }
        return Math.max(dp[n - 1][1], dp[n - 1][2]);
    }

    /**
     * @Description: [
     * 动态规划: 滚动数组
     * AC:15ms/40.95MB
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/27 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int k = n / 3 + (n % 3 >= 2 ? 1 : 0);
        int[][][] dp = new int[2][k + 2][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <= k + 1; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE / 2);
            }
        }
        for (int j = 1; j <= k + 1; j++) {
            dp[0][j][0] = 0;
        }
        int curr = 0;
        for (int i = 0; i < n; i++) {
            int pre = i % 2;
            curr = 1 - pre;
            for (int j = 1; j <= k + 1; j++) {
                dp[curr][j][0] = Math.max(dp[pre][j][0], dp[pre][j][1]);
                dp[curr][j][1] = dp[pre][j][2] + prices[i];
                dp[curr][j][2] = Math.max(dp[pre][j][2], dp[pre][j - 1][0] - prices[i]);
            }
        }
        return Math.max(dp[curr][k + 1][0], dp[curr][k + 1][1]);
    }

    /**
     * @Description: [
     * 基于method_01的空间优化
     * 由于dp[i] 只与dp[i - 1]有关, 可以优化这一维空间
     * 开干！！！
     * 存在问题 dp[j][2] = Math.max(temp_2, dp[j - 1][0] - prices[i]);
     * 这一步会被循环中的这个dp[j][0] = Math.max(temp_0, temp_1);修改值，导致拿不到dp[i - 1][j - 1][0]的值
     * 或者说拿到的值是被修改了的值，导致改错误发生
     * 导致改错误发生，所以无法进行空间优化，使用滚动数组优化method_03
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/27 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int k = n / 3 + (n % 3 >= 2 ? 1 : 0);
        int[][] dp = new int[k + 2][3];

        for (int j = 0; j <= k + 1; j++) {
            Arrays.fill(dp[j], Integer.MIN_VALUE / 2);
        }
        for (int j = 1; j <= k + 1; j++) {
            dp[j][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k + 1; j++) {
                int temp_0 = dp[j][0];
                int temp_1 = dp[j][1];
                int temp_2 = dp[j][2];
                dp[j][0] = Math.max(temp_0, temp_1);
                dp[j][1] = temp_2 + prices[i];
                dp[j][2] = Math.max(temp_2, dp[j - 1][0] - prices[i]);
            }
        }
        return Math.max(dp[k + 1][0], dp[k + 1][1]);
    }

    /**
     * @Description: [
     * 假设我们按照LeetCode_188.java 的方法, 并且通过计算得到k的最大值, 即最大交易次数 k = n / 3 + (n % 3 >= 2 ? 1 : 0)
     * 如何解决冷静期1天的问题
     *
     * dp[i + 1][j][0] = Math.max(dp[i][j][0], dp[i][j][1] + price[i])
     * 我该如何确定前一天是否卖出股票
     * dp[i + 1][j][0] -> dp[i][j][1] + price[i] 这是卖出股票, 钱增加了
     * 买入
     * dp[i + 1][j][1] = Math.max(dp[i][j][1], dp[i][j][0] - prices[i])
     *
     * dp[i + 1][j][1] -> dp[i][j][0] - prices[i] 这是买入
     *
     * 判断 是否发生卖出股票
     * if(dp[i + 1][j][0] > dp[i][j][0]) {
     *     dp[i + 1][j][1] = dp[i][j][1]
     * }else{
     *     dp[i + 1][j][1] = Math.max(dp[i][j][1], dp[i][j][0] - prices[i])
     * }
     *
     * 继上一次失败继续分析
     * 查看官方题解, 他包含3个状态,
     * 如果我们将原本的状态进行添加
     * 之前是0表示不持有股票，1表示持有股票
     * 进行扩展
     * 1.当前不持有股票, 并且不在冷冻期
     * 2.当前不持有股票，并且处于冷冻期
     * 3.当前持有一份股票
     * 1 -> 3
     * 2 -> 1
     * 3 -> 2
     * 1. -> dp[0]
     * 2. -> dp[1]
     * 3. -> dp[2]
     *
     * 状态转移:
     * 1 -> 3
     * dp[i + 1][j][2] = Math.max(dp[i][j][2], dp[i][j - 1][0] - prices[i])
     * 2 -> 1
     * dp[i + 1][j][0] = Math.max(dp[i][j][0], dp[i][j][1])
     * 3 -> 2
     * dp[i + 1][j][1] = dp[i][j][2] + prices[i]
     *
     * 成功！！！ 虽然复杂度感人，但是毕竟是通过的AC is Accepted
     * AC:206ms/264.1MB
     * Accepted，简称AC，意味着通过了测试样例（答案正确），通常在信息学竞赛在线评测系统（Online Judge）显示，是信息学竞赛常用的术语之一。
     * 动态规划: 优化空间 method_02
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/26 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int k = n / 3 + (n % 3 >= 2 ? 1 : 0);
        int[][][] dp = new int[n + 1][k + 2][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k + 1; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE / 2);
            }
        }

        for (int j = 1; j <= k + 1; j++) {
            dp[0][j][0] = 0;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= k + 1; j++) {
                dp[i + 1][j][0] = Math.max(dp[i][j][0], dp[i][j][1]);
                dp[i + 1][j][1] = dp[i][j][2] + prices[i];
                dp[i + 1][j][2] = Math.max(dp[i][j][2], dp[i][j - 1][0] - prices[i]);
            }
        }
        return Math.max(dp[n][k + 1][0], dp[n][k + 1][1]);
    }
}
