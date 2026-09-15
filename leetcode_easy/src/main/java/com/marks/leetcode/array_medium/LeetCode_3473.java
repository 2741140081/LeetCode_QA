package com.marks.leetcode.array_medium;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3473 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/15 14:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3473 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和两个整数 k 和 m。
     * 返回数组 nums 中 k 个不重叠子数组的 最大 和，其中每个子数组的长度 至少 为 m。
     * 子数组 是数组中的一个连续序列。
     *
     * tips:
     * 1 <= nums.length <= 2000
     * -10^4 <= nums[i] <= 10^4
     * 1 <= k <= floor(nums.length / m)
     * 1 <= m <= 3
     * @param: nums
     * @param: k
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/09/15 14:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSum(int[] nums, int k, int m) {
        int result;
        result = method_01(nums, k, m);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划, 状态转移方程 dp[i][j] = Math.max(dp[i][j], dp[i-1][k] + sum(k+1, j))
     * 2. 前缀和数组 prefixSum[i] = nums[0] + nums[1] + ... + nums[i-1]
     * 3. 动态规格的定义是, dp[i][j], i 表示 [0, i] 个元素, j 表示构成 j 个子数组, value = dp[i][j], 构成 j 个子数组的最大值。
     * 4. maxK = i / m, 即前 i 个元素最多可以构成 [0 ~ maxK] 个子数组, 感觉不太对.
     * 5. 假设 i 是第 i 个子数组, 并且 j 是第 i 个子数组的结尾下标,
     * todo: 当前查看题解, 需要自行理解
     *
     * @param: nums
     * @param: k
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/09/15 14:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k, int m) {
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }
        int[][] dp = new int[k + 1][n + 1];
        int INF = Integer.MIN_VALUE / 2;
        // dp[0][j] = 0
        for (int i = 1; i <= k; i++) {
            Arrays.fill(dp[i], INF);
        }
        for (int i = 1; i <= k; i++) {
            int max = INF;
            for (int j = i * m; j <= (n - (k - i) * m) ; j++) {
                max = Math.max(max, dp[i - 1][j - m] - prefixSum[j - m]);
                dp[i][j] = Math.max(dp[i][j - 1], max + prefixSum[j]);
            }
        }

        return dp[k][n];
    }

}
