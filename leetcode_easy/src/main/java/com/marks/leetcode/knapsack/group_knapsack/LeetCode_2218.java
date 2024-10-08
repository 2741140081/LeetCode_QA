package com.marks.leetcode.knapsack.group_knapsack;

import java.util.List;

public class LeetCode_2218 {
    /**
     * 从栈中取出 K 个硬币的最大面值和
     * 一张桌子上总共有 n 个硬币 栈 。每个栈有 正整数 个带面值的硬币。
     * 每一次操作中，你可以从任意一个栈的 顶部 取出 1 个硬币，从栈中移除它，并放入你的钱包里。
     * 给你一个列表 piles ，其中 piles[i] 是一个整数数组，分别表示第 i 个栈里 从顶到底 的硬币面值。
     * 同时给你一个正整数 k ，请你返回在 恰好 进行 k 次操作的前提下，你钱包里硬币面值之和 最大为多少 。
     *
     * n == piles.length
     * 1 <= n <= 1000
     * 1 <= piles[i][j] <= 10^5
     * 1 <= k <= sum(piles[i].length) <= 2000
     * @param piles
     * @param k
     * @return
     */
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int result = 0;
        result = method_01(piles, k);
        return result;

    }

    /**
     * 输入：piles = [[1,100,3],[7,8,9]], k = 2
     * 输出：101
     * 解释：
     * 上图展示了几种选择 k 个硬币的不同方法。
     * 我们可以得到的最大面值为 101 。
     *
     * dp[i][j] =
     * @param piles
     * @param k
     * @return
     */
    private int method_01(List<List<Integer>> piles, int k) {

        int[] dp = new int[k + 1];
        dp[0] = 0;
        for (List<Integer> pile : piles) {
            int n = pile.size();
            for (int i = 1; i < n; i++) {
                // 前缀和
                pile.set(i, pile.get(i) + pile.get(i - 1));
            }
            for (int j = k; j > 0; j--) {
                for (int g = 0; g < Math.min(n, j); g++) {
                    dp[j] = Math.max(dp[j], dp[j - g - 1] + pile.get(g));
                }
            }

        }
        return dp[k];
    }
}
