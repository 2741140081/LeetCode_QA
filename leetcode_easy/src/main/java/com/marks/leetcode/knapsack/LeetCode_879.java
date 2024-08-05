package com.marks.leetcode.knapsack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/5 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_879 {

    public final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [盈利计划]
     * 集团里有 n 名员工，他们可以完成各种各样的工作创造利润。
     * 第 i 种工作会产生 profit[i] 的利润，它要求 group[i] 名成员共同参与。如果成员参与了其中一项工作，就不能参与另一项工作。
     * 工作的任何至少产生 minProfit 利润的子集称为 盈利计划 。并且工作的成员总数最多为 n 。
     * 有多少种计划可以选择？因为答案很大，所以 返回结果模 10^9 + 7 的值。
     * tips:
     * 1 <= n <= 100
     * 0 <= minProfit <= 100
     * 1 <= group.length <= 100
     * 1 <= group[i] <= 100
     * profit.length == group.length
     * 0 <= profit[i] <= 100
     *
     * @param n
     * @param minProfit
     * @param group
     * @param profit
     * @return int
     * @author marks
     * @CreateDate: 2024/8/5 11:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int profitableSchemes(int n, int minProfit, int[] group, int[] profit) {
        int result = 0;
//        result = method_01(n, minProfit, group, profit);
        result = method_02(n, minProfit, group, profit);
        return result;
    }

    /**
     * @Description: [逆序遍历降维]
     *
     * @param n
     * @param minProfit
     * @param group
     * @param profit
     * @return int
     * @author marks
     * @CreateDate: 2024/8/5 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int minProfit, int[] group, int[] profit) {
        int len = group.length;
        int[][] dp = new int[n + 1][minProfit + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= len; i++) {
            int member = group[i - 1];
            int earn = profit[i - 1];
            for (int j = n; j >= member; j--) {
                for (int k = minProfit; k >= 0; k--) {
                    dp[j][k] = (dp[j][k] + dp[j - member][Math.max(0, k - earn)]) % MOD;
                }
            }
        }
        return dp[n][minProfit];
    }

    /**
     * @Description: [
     * 1. 最小达成的利润 minProfit
     * 2. 假设有充足的员工, 那么可以达成的最大利润是sumProfit
     * int[i][j][k] = i表示前i个项目,  j表示员工数, k表示最大的利润,
     * n = profit[i], x = group[i]
     * dp[i][j][k] = dp[i-1][j-x][k-n]
     * 对于第 i 个project
     * 取:
     * 1. 剩余项目的人数是否足够
     * group[i] +
     *
     * 先直接分析案例:
     * E1:
     * 输入：n = 10, minProfit = 5, group = [2,3,5], profit = [6,7,8]
     * 输出：7
     * 解释：至少产生 5 的利润，只要完成其中一种工作就行，所以该集团可以完成任何工作。
     * 有 7 种可能的计划：(0)，(1)，(2)，(0,1)，(0,2)，(1,2)，以及 (0,1,2) 。
     * int[][][] dp = new int[3][11][22]
     * 1. 如果n >= Math.sum(group) 即 员工充足
     * 在 profit[] 中找到目标的方案数
     * if(j >= minProfit)
     * dp[i][j] = dp[i-1][j-k] + 1
     * else
     * dp[i][j] = 1
     * ]
     * @param n
     * @param minProfit
     * @param group
     * @param profit
     * @return int
     * @author marks
     * @CreateDate: 2024/8/5 11:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int minProfit, int[] group, int[] profit) {
        if (n >= Arrays.stream(group).sum()) {
            // 如果员工充足, 只需要在 profit[] 中找到大于minProfit 即可
            int count = getCountByProfit(minProfit, profit);
            return count;
        }else {
            int len = group.length;
            int[][][] dp = new int[2][n + 1][minProfit + 1];
            dp[0][0][0] = 1;
            int curr = 0;
            for (int i = 1; i <= len; i++) {
                curr = i % 2;
                int pre = 1 - curr;
                int member = group[i - 1];
                int earn = profit[i - 1];
                for (int j = 0; j <= n; j++) {
                    for (int k = 0; k <= minProfit; k++) {
                        if (j < member) {
                            dp[curr][j][k] = dp[pre][j][k] % MOD;
                        }else {
                            dp[curr][j][k] = (dp[pre][j][k] + dp[pre][j - member][Math.max(0, k - earn)]) % MOD;
                        }
                    }
                }
            }
            int res = 0;
            for (int j = 0; j <= n; j++) {
                res = (res + dp[curr][j][minProfit]) % MOD;
            }

            return res;
        }
    }

    private int getCountByProfit(int minProfit, int[] profit) {
        int maxProfit = Arrays.stream(profit).sum();
        int n = profit.length;
        int[][] dp = new int[2][maxProfit + 1];
        int curr = 0;
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = profit[i - 1];
            for (int j = 0; j <= maxProfit; j++) {
                dp[curr][j] = dp[pre][j] % MOD;
                if (j >= temp) {
                    dp[curr][j] = (dp[curr][j] + dp[pre][j - temp]) % MOD;
                }
            }
        }
        int res = 0;
        for (int i = maxProfit; i >= minProfit; i--) {
            if (dp[curr][i] > 0) {
                res += dp[curr][i];
            }
        }

        return res;
    }
}
