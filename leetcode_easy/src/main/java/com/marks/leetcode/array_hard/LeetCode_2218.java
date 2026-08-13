package com.marks.leetcode.array_hard;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2218 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/12 14:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2218 {

    /**
     * @Description:
     * 一张桌子上总共有 n 个硬币 栈 。每个栈有 正整数 个带面值的硬币。
     * 每一次操作中，你可以从任意一个栈的 顶部 取出 1 个硬币，从栈中移除它，并放入你的钱包里。
     * 给你一个列表 piles ，其中 piles[i] 是一个整数数组，分别表示第 i 个栈里 从顶到底 的硬币面值。
     * 同时给你一个正整数 k ，请你返回在 恰好 进行 k 次操作的前提下，你钱包里硬币面值之和 最大为多少 。
     * tips:
     * n == piles.length
     * 1 <= n <= 1000
     * 1 <= piles[i][j] <= 10^5
     * 1 <= k <= sum(piles[i].length) <= 2000
     * @param: piles
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/12 14:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int result;
        result = method_01(piles, k);
        return result;
    }

    /**
     * @Description:
     * 1. 使用动态规划 + 前缀和预处理, dp[i][j] 表示在前 i 个栈中取了 j 个数的最大值.
     * 2. 如果要处理第 i 个栈, 第 k 个栈可以取 [0 ~ Math.min(k, m)], m 为当前栈的大小,
     * dp[i][0] = dp[i - 1][0], dp[i][j] = dp[i - 1][j - x] + preSum[i][x]
     * AC: 65ms/54.9MB
     * @param: piles
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/12 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<List<Integer>> piles, int k) {
        int n = piles.size();
        int[][] dp = new int[n + 1][k + 1];
        // 构建前缀和
        for (int i = 0; i < n; i++) {
            // 构建前缀和
            int m = piles.get(i).size();
            int[] preSum = new int[m + 1];
            for (int j = 0; j < m; j++) {
                preSum[j + 1] = preSum[j] + piles.get(i).get(j);
            }
            // 假设当前已经取值为 x
            for (int x = 0; x <= k; x++) {
                // 当前栈可以取的数
                for (int y = 0; y <= Math.min(x, m); y++) {
                    dp[i + 1][x] = Math.max(dp[i + 1][x], dp[i][x - y] + preSum[y]);
                }
            }
        }

        return dp[n][k];
    }

}
