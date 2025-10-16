package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/14 10:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1621 {
    private static final int MOD = (int) 1e9 + 7;

    /**
     * @Description:
     * 给你一维空间的 n 个点，其中第 i 个点（编号从 0 到 n-1）位于 x = i 处，
     * 请你找到 恰好 k 个不重叠 线段且每个线段至少覆盖两个点的方案数。
     * 线段的两个端点必须都是 整数坐标 。这 k 个线段不需要全部覆盖全部 n 个点，且它们的端点 可以 重合。
     *
     * 请你返回 k 个不重叠线段的方案数。由于答案可能很大，请将结果对 10^9 + 7 取余 后返回。
     *
     * tips:
     * 2 <= n <= 1000
     * 1 <= k <= n-1
     * @param n 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSets(int n, int k) {
        int result;
        result = method_01(n, k);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：n = 4, k = 2
     * 输出：5
     * 1. dp[k][l][r] = dp[k - 1][0][l] + 1;
     * 2. dp[1][0][1]
     * need todo again, 当前是查看题解的代码, 之后再去尝试
     * @param n 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/14 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int k) {
        int[] dp = new int[n];
        int[] sums = new int[n];
        int p = 1000000007;
        dp[0] = sums[0] = 1;
        for (int i = 0; i <= k; i++) { // i个线段
            for (int j = 0; j < n; j++) {
                if (j < i) dp[j] = 0;
                else if (j == i || i == 0) dp[j] = 1;
                else dp[j] = (sums[j - 1] + dp[j - 1]) % p;
            }
            sums[0] = dp[0];
            for (int j = 1; j < n; j++) {
                sums[j] = (dp[j] + sums[j - 1]) % p;
            }
        }
        return dp[n - 1];
    }

}
