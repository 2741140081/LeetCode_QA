package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_103 {

    /**
     * @Description:
     * 给定不同面额的硬币 coins 和一个总金额 amount。
     * 编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
     * 如果没有任何一种硬币组合能组成总金额，返回 -1。
     *
     * 你可以认为每种硬币的数量是无限的。
     * @param coins 
     * @param amount
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int coinChange(int[] coins, int amount) {
        int result;
        result = method_01(coins, amount);
        return result;
        
    }

    
    /**
     * @Description:
     * dp[i] = Math.min(dp[i - coins[j]] + 1, dp[i])
     * AC: 13ms/43.45MB
     * @param coins 
     * @param amount 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            int temp = coins[i - 1];
            for (int j = 0; j <= amount; j++) {
                if (j >= temp) {
                    dp[j] = Math.min(dp[j], dp[j - temp] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

}
