package com.marks.leetcode.knapsack.complete_knapsack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/6 17:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_279 {
    /**
     * @Description: [给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。
     * 例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
     * ]
     * @param n
     * @return int
     * @author marks
     * @link LeetCode_322.java
     * @CreateDate: 2024/8/6 17:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSquares(int n) {
        int result = 0;
        result = method_01(n);
        return result;
    }

    /**
     * @Description: [功能描述: 求最少数量
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/8/6 17:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int target = (int) Math.floor(Math.sqrt(n));
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 1);
        dp[0] = 0;
        for (int i = 1; i <= target; i++) {
            int temp = (int) Math.pow(i, 2);
            for (int j = 0; j <= n; j++) {
                if (j >= temp) {
                    dp[j] = Math.min(dp[j], dp[j - temp] + 1);
                }

            }
        }
        return dp[n];
    }
}
