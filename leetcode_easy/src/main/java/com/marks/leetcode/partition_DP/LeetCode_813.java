package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/8 9:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_813 {
    /**
     * @Description: [
     * 给定数组 nums 和一个整数 k 。我们将给定的数组 nums 分成 最多 k 个非空子数组，且数组内部是连续的 。 分数 由每个子数组内的平均值的总和构成。
     *
     * 注意我们必须使用 nums 数组中的每一个数进行分组，并且分数不一定需要是整数。
     *
     * 返回我们所能得到的最大 分数 是多少。答案误差在 10^-6 内被视为是正确的。
     * tips:
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 10^4
     * 1 <= k <= nums.length
     * ]
     * @param nums
     * @param k
     * @return double
     * @author marks
     * @CreateDate: 2024/10/8 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double largestSumOfAverages(int[] nums, int k) {
        double result = 0;
        result = method_01(nums, k);
//        result = method_02(nums, k);
        return result;
    }



    /**
     * @Description: [
     * 输入: nums = [9,1,2,3,9], k = 3
     * 输出: 20.00000
     * 解释:
     * nums 的最优分组是[9], [1, 2, 3], [9]. 得到的分数是 9 + (1 + 2 + 3) / 3 + 9 = 20.
     * 我们也可以把 nums 分成[9, 1], [2], [3, 9].
     * 这样的分组得到的分数为 5 + 2 + 6 = 13, 但不是最大值.
     * 基于method_02的空间优化, 由于j只与j - 1存在关联, 所以优化成一维空间复杂度
     * AC:6ms/40.36MB
     * ]
     * @param nums
     * @param k
     * @return double
     * @author marks
     * @CreateDate: 2024/10/8 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[] nums, int k) {
        int n = nums.length;
        double[] pre = new double[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];
        }
        double[] dp = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = pre[i] / i;
        }
        for (int j = 2; j <= k; j++) {
            for (int i = n; i >= 1; i--) {
                for (int l = j - 1; l < i; l++) {
                    dp[i] = Math.max(dp[i], dp[l] + (pre[i] - pre[l])/(i - l));
                }
            }
        }
        return dp[n];
    }
    /**
     * @Description: [
     * 感觉method_01不是很适合, 还是动态规划来解决
     * 1. 预处理nums[], 求得pre[],即数组元素和
     * AC:13ms/40.48MB
     * ]
     * @param nums
     * @param k
     * @return double
     * @author marks
     * @CreateDate: 2024/10/8 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_02(int[] nums, int k) {
        int n = nums.length;
        double[] pre = new double[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + nums[i];

        }
        double[][] dp = new double[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][1] = pre[i] / i;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 2; j <= Math.min(i, k); j++) {
                for (int l = 0; l < i; l++) {
                    dp[i][j] = Math.max(dp[i][j], dp[l][j - 1] + (pre[i] - pre[l])/(i - l));
                }

            }
        }
        return dp[n][k];
    }
}
