package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3381 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 9:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3381 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k 。
     * 返回 nums 中一个 非空子数组 的 最大 和，要求该子数组的长度可以 被 k 整除。
     * tips:
     * 1 <= k <= nums.length <= 2 * 10^5
     * -10^9 <= nums[i] <= 10^9
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2025/11/27 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxSubarraySum(int[] nums, int k) {
        long result = 0;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1.
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2025/11/27 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] nums, int k) {
        int n = nums.length;
        long prefixSum = 0;
        long maxSum = Long.MIN_VALUE;
        long[] kSum = new long[k];
        for (int i = 0; i < k; i++) {
            kSum[i] = Long.MAX_VALUE / 2;
        }
        kSum[k - 1] = 0;
        for (int i = 0; i < n; i++) {
            prefixSum += nums[i];
            maxSum = Math.max(maxSum, prefixSum - kSum[i % k]);
            kSum[i % k] = Math.min(kSum[i % k], prefixSum);
        }
        return maxSum;
    }

    /**
     * @Description:
     * 先把数组处理为前缀和数组
     * 超时!(655/661)
     * 这种会重复计算, 例如 [1,2,3,4,5,6,7,8,9,10] k = 3
     * index = 0,3,6; 会重复计算后面的数值
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2025/11/27 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        long[] preSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }
        long ans = Long.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            for (int j = i + k - 1; j <= n; j += k) {
                ans = Math.max(ans, preSum[j] - preSum[i - 1]);
            }
        }
        return ans;
    }

}
