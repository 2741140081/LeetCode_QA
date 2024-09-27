package com.marks.leetcode.partition_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/27 11:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1043 {
    /**
     * @Description: [
     * 给你一个整数数组 arr，请你将该数组分隔为长度 最多 为 k 的一些（连续）子数组。
     * 分隔完成后，每个子数组的中的所有值都会变为该子数组中的最大值。
     *
     * 返回将数组分隔变换后能够得到的元素最大和。本题所用到的测试用例会确保答案是一个 32 位整数。
     * tips:
     * 1 <= arr.length <= 500
     * 0 <= arr[i] <= 10^9
     * 1 <= k <= arr.length
     * ]
     * @param arr
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/27 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int result = 0;
        result = method_01(arr, k);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：arr = [1,15,7,9,2,5,10], k = 3
     * 输出：84
     * 解释：数组变为 [15,15,15,9,10,10,10]
     * 假设前i个已划分
     * 对于nums[i + 1], nums[i + k]
     * dp[0] = 1,
     * dp[1] = 15 * 2,
     * dp[2] = 15 * 3,
     * dp[3] = max(1 + 15 * 3, 15 * 2 + 9 * 2, 15 * 3 + 9)
     * AC:6ms/41.22MB
     * ]
     * @param arr
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/27 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int maxValue = arr[i - 1];
            for (int j = i - 1; j >= 0 && j >= i - k; j--) {
                dp[i] = Math.max(dp[i], dp[j] + maxValue * (i - j));
                if (j > 0) {
                    maxValue = Math.max(maxValue, arr[j - 1]);
                }
            }
        }
        return dp[n];
    }
}
