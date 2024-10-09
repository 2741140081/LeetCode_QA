package com.marks.leetcode.sliding_window.fixed_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_643 {
    /**
     * @Description: [
     * 给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
     *
     * 请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
     *
     * 任何误差小于 10-5 的答案都将被视为正确答案。
     *
     * tips；
     * n == nums.length
     * 1 <= k <= n <= 10^5
     * -104 <= nums[i] <= 10^4
     * ]
     * @param nums
     * @param k
     * @return double
     * @author marks
     * @CreateDate: 2024/10/9 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double findMaxAverage(int[] nums, int k) {
        double result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 还是应该先把窗口给弄出来, 在慢慢滑动
     * for (int i = 0; i < k; i++) {
     *    sum += nums[i];
     * }
     * AC:2ms/54.89MB
     * ]
     * @param nums
     * @param k
     * @return double
     * @author marks
     * @CreateDate: 2024/10/9 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        ans = sum;
        for (int i = k; i < n; i++) {
            sum = sum + (nums[i] - nums[i - k]);
            ans = Math.max(ans, sum);
        }
        return ans * 1.0 / k;
    }
}
