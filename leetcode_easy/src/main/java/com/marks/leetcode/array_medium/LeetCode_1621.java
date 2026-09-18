package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1621 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/16 14:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1621 {

    /**
     * @Description:
     * 给你一维空间的 n 个点，其中第 i 个点（编号从 0 到 n-1）位于 x = i 处，
     * 请你找到 恰好 k 个不重叠 线段且每个线段至少覆盖两个点的方案数。
     * 线段的两个端点必须都是 整数坐标 。
     * 这 k 个线段不需要全部覆盖全部 n 个点，且它们的端点 可以 重合。
     * 请你返回 k 个不重叠线段的方案数。由于答案可能很大，请将结果对 10^9 + 7 取余 后返回。
     *
     * tips:
     * 2 <= n <= 1000
     * 1 <= k <= n-1
     * @param: n
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/16 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSets(int n, int k) {
        int result;
        result = method_01(n, k);
        return result;
    }


    private static final int MOD = 1000000007;
    /**
     * @Description:
     * 1. 动态规划, dp[i][j] 表示前 i 个点，恰好 j 个线段的方案数
     * 2. 状态转移方程, dp[i][j] = Math.max(dp[i - 1][j], dp[k][j - 1] * (i - k))， dp[0][0] = 1
     * 3. case: n = 4, k = 2, 第一段 [0,1], 第二段 dp[3][2] = dp[1][1] * 2 = 2, dp[1][1] = 1
     * 4. [0,1] [1,2]; [0,1] [2,3]; [0,1] [1,3]; [0,2] [2,3]; [1,2] [2,3]
     * 5. 假设以3为结尾, 并且构成两个线段, 需要 sum = dp[2][1] + dp[1][1] + dp[2][2]; dp[2][1] = dp[1][1] + dp[0][0] * 2
     * dp[2][2] = dp[1][1] = 1, sum = 2 + 2 + 1 = 5
     *
     * todo: 需要通过模拟case 来理解原理
     * @param: n
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/16 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int k) {
        int[] dp = new int[n];
        int[] prefixSums = new int[n + 1];
        // 初始化 j=0: 0 条线段, 任何位置都只有 1 种方案 (什么都不放)
        for (int j = 0; j < n; j++) {
            dp[j] = 1;
            prefixSums[j + 1] = (prefixSums[j] + dp[j]) % MOD;
        }
        // 逐轮计算 j = 1, 2, ..., k 条线段的方案数
        for (int i = 1; i <= k; i++) {
            dp[0] = 0; // 1 个点无法构成任何线段
            // 核心转移: dp[i] = dp[i-1] + prefixSums[i]
            // dp[i-1]: 第 i 条线段右端惰性延伸到当前位置
            // prefixSums[i]: 第 i 条线段以当前位置为新右端点, 枚举所有左端点的前缀和
            for (int j = 1; j < n; j++) {
                dp[j] = (dp[j - 1] + prefixSums[j]) % MOD;
            }
            // 根据新的 dp 数组重建前缀和, 供下一轮使用
            for (int j = 0; j < n; j++) {
                prefixSums[j + 1] = (prefixSums[j] + dp[j]) % MOD;
            }
        }
        return dp[n - 1];
    }

}
