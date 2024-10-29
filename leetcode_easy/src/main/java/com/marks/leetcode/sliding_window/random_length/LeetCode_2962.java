package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/29 11:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2962 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个 正整数 k 。
     *
     * 请你统计有多少满足 「 nums 中的 最大 元素」至少出现 k 次的子数组，并返回满足这一条件的子数组的数目。
     *
     * 子数组是数组中的一个连续元素序列。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= k <= 10^5
     * ]
     * @param nums 
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/29 17:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countSubarrays(int[] nums, int k) {
        long result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 输入：nums = [1,3,2,3,3], k = 2
     * 输出：6
     * 解释：包含元素 3 至少 2 次的子数组为：[1,3,2,3]、[1,3,2,3,3]、[3,2,3]、[3,2,3,3]、[2,3,3] 和 [3,3] 。
     * 与LeetCode_1358几乎一致
     * AC:12ms/64.52MB
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/29 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        int max = Arrays.stream(nums).max().getAsInt();
        long ans = 0;
        int left = n - 1;
        int count = nums[n - 1] == max ? 1 : 0;
        for (int right = n - 1; right >= 0; right--) {

            while (count < k && left > 0) {
                left--;
                if (nums[left] == max) {
                    count++;
                }
            }
            if (count == k) {
                ans = ans + left + 1;
            }
            if (nums[right] == max) {
                count--;
            }

        }
        return ans;
    }
}
