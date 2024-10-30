package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/30 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_713 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
     * tips:
     * 1 <= nums.length <= 3 * 10^4
     * 1 <= nums[i] <= 1000
     * 0 <= k <= 10^6
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/30 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }
    /**
     * @Description: [
     * 官方题解滑动窗口
     * AC:4ms/46.53MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/30 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        int prod = 1;
        int left = 0;

        for (int right = 0; right < n; right++) {
            prod *= nums[right];
            while (prod >= k && left <= right) {
                prod /= nums[left];
                left++;
            }
            if (prod < k) {
                ans += (right - left + 1);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [10,5,2,6], k = 100
     * 输出：8
     * 解释：8 个乘积小于 100 的子数组分别为：[10]、[5]、[2]、[6]、[10,5]、[5,2]、[2,6]、[5,2,6]。
     * 需要注意的是 [10,5,2] 并不是乘积小于 100 的子数组。
     *
     * 可能需要考虑的几个问题,
     * 1. 乘积的大小可能会超过Integer.Max_Value, 会发生溢出, 感觉应该用Long来存储
     * 2. 计算时, 找到是子数组[i, j], 假设i * ...* j < k, 符合条件, 此时ans += (j - i + 1)
     * 3. 感觉这种不太行, 中间的值会被计算多次
     * [5, 2, 6] 3种
     *
     * AC:5ms/46.77MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/30 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        if (k <= 1) {
            return 0;
        }
        int n = nums.length;
        int left = n - 1;
        long res = nums[left];
        int ans = 0;

        for (int right = n - 1; right >= 0; right--) {

            while (res < k && left > 0) {
                ans += (right - left + 1);
                left--;
                res = res * nums[left];
            }
            if (res < k && left == 0) {
                ans += (right - left + 1);
                break;
            }

            res = res / nums[right];

        }
        return ans;
    }
}
