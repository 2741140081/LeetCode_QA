package com.marks.leetcode.knapsack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/6 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_956 {
    /**
     * @Description: [你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。
     * 你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。
     * 返回 广告牌的最大可能安装高度 。如果没法安装广告牌，请返回 0 。]
     * @param rods
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int tallestBillboard(int[] rods) {
        int result = 0;
        result = method_01(rods); // 错误, 待完善
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：[1,2,3,6]
     * 输出：6
     * 解释：我们有两个不相交的子集 {1,2,3} 和 {6}，它们具有相同的和 sum = 6。
     * 1. 求 rods[] 的sum值一半 target_sum, 求min(rods[i]) 即 min_1
     * 2. 最大高度是min_1 - target_sum 之间, 如果E1 [1 ~ 6]
     * 3. for i = 6; i >= 1; i--
     * 4. a.
     * // 以上思路错误
     * 观看题解后的思路
     * 定义dp[i][j] = k
     * i: rods[] 数组中前i根棍子
     * j: 两堆棍子的差值
     * k: 两堆棍子的公共高度
     *
     * ]
     * @param rods
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] rods) {
        int n = rods.length;
        int INF = Integer.MIN_VALUE / 2;
        int sum = Arrays.stream(rods).sum();
        int[][] dp = new int[2][sum + 1];
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;
        int prev;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            prev = 1 - curr;
            int rod = rods[i - 1];
            for (int j = 0; j <= sum; j++) {
                // 忽略第 i 根钢筋
                dp[curr][j] = dp[prev][j];
                // 将 i 添加到短的侧
                if (j + rod <= sum && dp[prev][j + rod] != INF) {
                    dp[curr][j] = Math.max(dp[curr][j], dp[prev][j + rod] + rod);
                }
                int abs = Math.abs(j - rod);
                // 将 i 添加到长的侧
                if (dp[prev][abs] != INF) { // j > rod, 防止从负数的非法转移
                    dp[curr][j] = Math.max(dp[curr][j], dp[prev][abs] + rod);
                }
            }
        }

        return dp[curr][0] / 2;
    }

}
