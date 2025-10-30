package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/24 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_790 {

    /**
     * @Description:
     * 有两种形状的瓷砖：一种是 2 x 1 的多米诺形，另一种是形如 "L" 的托米诺形。两种形状都可以旋转。
     * 给定整数 n ，返回可以平铺 2 x n 的面板的方法的数量。返回对 109 + 7 取模 的值。
     *
     * 平铺指的是每个正方形都必须有瓷砖覆盖。两个平铺不同，当且仅当面板上有四个方向上的相邻单元中的两个，使得恰好有一个平铺有一个瓷砖占据两个正方形。
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/10/24 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numTilings(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * dp 解法
     * 针对第 i 列分为4种情况讨论
     * 一个正方形都没有被覆盖，记为状态 0；
     * 只有上方的正方形被覆盖，记为状态 1；
     * 只有下方的正方形被覆盖，记为状态 2；
     * 上下两个正方形都被覆盖，记为状态 3。
     *
     * 1. 第 i 列的上下正方形都没有被填充, dp[i][0] = dp[i - 1][3]
     * 2. 第 i 列只有上方的正方形没有被填充, dp[i][1] = dp[i - 1][0] + dp[i - 1][2]
     * 3. 第 i 列只有下方的正方形没有被填充, dp[i][2] = dp[i - 1][0] + dp[i - 1][1]
     * 4. 第 i 列的上下两个正方形都被填充, dp[i][3] = dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][3]
     *
     * @param n 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/24 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[][] dp = new int[n + 1][4];
        dp[0][3] = 1;
        int MOD = 1000000007;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][3];
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MOD;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MOD;
            dp[i][3] = (((dp[i - 1][0] + dp[i - 1][1]) % MOD + dp[i - 1][2]) % MOD + dp[i - 1][3]) % MOD;
        }
        
        return dp[n][3];
    }

}
