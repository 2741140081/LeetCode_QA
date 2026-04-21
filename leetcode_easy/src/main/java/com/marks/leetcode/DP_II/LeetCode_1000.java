package com.marks.leetcode.DP_II;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1000 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/21 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1000 {

    /**
     * @Description:
     * 有 n 堆石头排成一排，第 i 堆中有 stones[i] 块石头。
     * 每次 移动 需要将 连续的 k 堆石头合并为一堆，而这次移动的成本为这 k 堆中石头的总数。
     * 返回把所有石头合并成一堆的最低成本。如果无法合并成一堆，返回 -1 。
     *
     * tips:
     * n == stones.length
     * 1 <= n <= 30
     * 1 <= stones[i] <= 100
     * 2 <= k <= 30
     * @param: stones
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/21 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int mergeStones(int[] stones, int k) {
        int result;
        result = method_01(stones, k);
        return result;
    }

    private int[][][] dp;
    private int INF;
    private int k;

    /**
     * @Description:
     * 1. 判断能否合并成一堆, 假设可以执行 x 次合并操作后, 能合并成一堆石头, 每次合并会减少 k - 1个石头堆:
     * n - (t * (k - 1)) = 1 => t = (n - 1) / (k - 1) => (n - 1) % (k - 1) == 0, 即可合并成一堆石头.
     * 2. 定义一个dp数组, dp[i][j][t], dp[i][j][t] 表示从 i 到 j 的石头合并 t 次后的最小成本.
     * 3. 转移方程, dp[i][j][t] = Math.min(dp[i][j][t], dp[i][m][t - 1] + dp[m + 1][j][1]) + sum[j] - sum[i - 1];
     * 4. 使用记忆化搜索来处理
     * 直接看题解, need todo
     * AC: 2ms/43.64MB
     * @param: stones
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/21 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] stones, int k) {
        int n = stones.length;
        if ((n - 1) % (k - 1) != 0) {
            // 无法合并成一堆石头
            return -1;
        }
        int[] sum = new int[n]; // 前缀和
        INF = Integer.MAX_VALUE;
        this.k = k;

        dp = new int[n][n][k + 1];
        // 初始化dp
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            count += stones[i];
            dp[i][i][1] = 0;
            sum[i] = count;
        }

        return getMinCost(sum, 0, n - 1, 1);
    }

    private int getMinCost(int[] sum, int left, int right, int t) {
        // 判断是否已经求解, 如果已经求解过, 返回结果
        if (dp[left][right][t] != -1) {
            return dp[left][right][t];
        }
        if (t > right - left + 1) {
            return INF;
        }
        if (t == 1) {
            int ans = getMinCost(sum, left, right, k);
            if (ans == INF) {
                return dp[left][right][t] = ans;
            }
            return dp[left][right][t] = ans + sum[right] - (left == 0 ? 0 : sum[left - 1]);
        }
        int ans = INF;
        for (int i = left; i < right; i += k - 1) {
            ans = Math.min(ans, getMinCost(sum, left, i, 1) + getMinCost(sum, i + 1, right, t - 1));
        }
        return dp[left][right][t] = ans;
    }

}
