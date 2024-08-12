package com.marks.leetcode.classic_linear_DP.LCS;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/12 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1458 {
    /**
     * @Description: [给你两个数组 nums1 和 nums2 。
     *
     * 请你返回 nums1 和 nums2 中两个长度相同的 非空 子序列的最大点积。
     *
     * 数组的非空子序列是通过删除原数组中某些元素（可能一个也不删除）后剩余数字组成的序列，但不能改变数字间相对顺序。
     * 比方说，[2,3,5] 是 [1,2,3,4,5] 的一个子序列而 [1,5,3] 不是。]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/12 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int result = 0;
//        result = method_01(nums1, nums2);
        result = method_02(nums1, nums2);
        return result;
    }

    /**
     * @Description: [基于Method_01, 使用滚动数组优化空间复杂度]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/12 14:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[][] dp = new int[2][n];
        int curr = 0;
        for (int i = 0; i < m; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            for (int j = 0; j < n; j++) {
                int temp = nums1[i] * nums2[j];
                dp[curr][j] = temp;
                if (i > 0) {
                    dp[curr][j] = Math.max(dp[curr][j], dp[pre][j]);
                }
                if (j > 0) {
                    dp[curr][j] = Math.max(dp[curr][j], dp[curr][j - 1]);
                }
                if (i > 0 && j > 0) {
                    dp[curr][j] = Math.max(dp[curr][j], dp[pre][j - 1] + temp);
                }
            }
        }
        return dp[curr][n - 1];
    }

    private int method_01(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = nums1[i] * nums2[j];
                dp[i][j] = temp;
                if (i > 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }
                if (j > 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                }
                if (i > 0 && j > 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + temp);
                }
            }
        }
        return dp[m - 1][n - 1];
    }
}
