package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2302 {
    /**
     * @Description: [
     * 一个数组的 分数 定义为数组之和 乘以 数组的长度。
     *
     * 比方说，[1, 2, 3, 4, 5] 的分数为 (1 + 2 + 3 + 4 + 5) * 5 = 75 。
     * 给你一个正整数数组 nums 和一个整数 k ，请你返回 nums 中分数 严格小于 k 的 非空整数子数组数目。
     *
     * 子数组 是数组中的一个连续元素序列。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= 10^15
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/11/1 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countSubarrays(int[] nums, long k) {
        long result = 0;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [2,1,4,3,5], k = 10
     * 输出：6
     * AC:3ms/60.33MB
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/11/1 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, long k) {
        int n = nums.length;
        int left = 0; // 左边界
        long ans = 0;
        long sum = 0; // 窗口的元素和

        for (int right = 0; right < n; right++) {
            sum += nums[right];
            while (sum * (right - left + 1) >= k && left <= right) {
                sum -= nums[left++];
            }
            int currLen = right - left + 1;
            if (sum * currLen < k) {
                ans += currLen;
            }
        }
        return ans;
    }
}
