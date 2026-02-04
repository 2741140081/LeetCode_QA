package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_312 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/17 15:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_312 {

    /**
     * @Description:
     * 有 n 个气球，编号为0 到 n - 1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
     * 现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得 nums[i - 1] * nums[i] * nums[i + 1] 枚硬币。
     * 这里的 i - 1 和 i + 1 代表和 i 相邻的两个气球的序号。如果 i - 1或 i + 1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
     * 求所能获得硬币的最大数量。
     * tips:
     * n == nums.length
     * 1 <= n <= 300
     * 0 <= nums[i] <= 100
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/17 15:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCoins(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 将基于记忆化搜索的 method_01 优化为基于动态规划
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/03 17:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n + 2][n + 2];
        int[] arr = new int[n + 2];
        arr[0] = arr[n + 1] = 1;
        for (int i = 1; i <= n; i++) {
            arr[i] = nums[i - 1];
        }
        // 使用动态规划
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 2; j <= n + 1; j++) {
                for (int k = i + 1; k < j; k++) {
                    int sum = arr[i] * arr[k] * arr[j];
                    sum += dp[i][k] + dp[k][j];
                    dp[i][j] = Math.max(dp[i][j], sum);
                }
            }
        }

        return dp[0][n + 1];
    }

    /**
     * @Description:
     * 输入：nums = [3,1,5,8]
     * 输出：167
     * 解释：
     * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
     * 1.
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/17 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] val;
    private int[][] memo;
    private int method_01(int[] nums) {
        int n = nums.length;
        val = new int[n + 2];
        for (int i = 0; i < n; i++) {
            val[i + 1] = nums[i];
        }
        val[0] = val[n + 1] = 1;
        memo = new int[n + 2][n + 2];
        for (int i = 0; i < n + 2; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfsMaxC(0, n + 1);
    }

    private int dfsMaxC(int left, int right) {
        if (left >= right - 1) {
            // 区间小于3, 无法进行戳破
            return 0;
        }
        if (memo[left][right] != -1) {
            return memo[left][right];
        }
        for (int i = left + 1; i < right; i++) {
            int sum = val[left] * val[i] * val[right];
            sum += dfsMaxC(left, i) + dfsMaxC(i, right);
            memo[left][right] = Math.max(memo[left][right], sum);
        }
        return memo[left][right];
    }

}
