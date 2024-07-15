package com.marks.leetcode;

public class LeetCode_2320 {
    /**
     * 一条街道上共有 n * 2 个 地块 ，街道的两侧各有 n 个地块。每一边的地块都按从 1 到 n 编号。每个地块上都可以放置一所房子。
     * 现要求街道同一侧不能存在两所房子相邻的情况，请你计算并返回放置房屋的方式数目。由于答案可能很大，需要对 10^9 + 7 取余后再返回。
     * 注意，如果一所房子放置在这条街某一侧上的第 i 个地块，不影响在另一侧的第 i 个地块放置房子。
     *
     * E1:
     * 输入：n = 1
     * 输出：4
     * 解释：
     * 可能的放置方式：
     * 1. 所有地块都不放置房子。
     * 2. 一所房子放在街道的某一侧。
     * 3. 一所房子放在街道的另一侧。
     * 4. 放置两所房子，街道两侧各放置一所。
     *
     * E2:
     * 输入：n = 2
     * 输出：9
     * 解释：如上图所示，共有 9 种可能的放置方式。
     *
     * @param n
     * @return
     */
    public int countHousePlacements(int n) {
        int result = 0;
        result = method_01(n);
        return result;
    }

    /**
     * 对于地块k, 存在以下情况
     * 1. 不放置房屋, dp[i-1]
     * 2. 放置房屋, dp[i-2]
     * dp[i] = dp[i-1] + dp[i-2]
     * @param n
     * @return
     */
    private int method_01(int n) {
        final int MOD = 1000000007;
        long[] dp = new long[n+1];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i-1] + dp[i-2]) % MOD;
        }
        long result = (dp[n] * dp[n]) % MOD;
        return (int) result;
    }
}
