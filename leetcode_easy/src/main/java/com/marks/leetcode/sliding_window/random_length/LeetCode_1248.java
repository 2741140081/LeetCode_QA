package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1248 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k。如果某个连续子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     *
     * 请返回这个数组中 「优美子数组」 的数目。
     * tips:
     * 1 <= nums.length <= 50000
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= nums.length
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,1,2,1,1], k = 3
     * 输出：2
     * 解释：包含 3 个奇数的子数组是 [1,1,2,1] 和 [1,2,1,1] 。
     *
     * AC:11ms/53.61MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 15:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int left_1 = 0; // >= k
        int left_2 = 0; // >= k + 1
        int sum_1 = 0;
        int sum_2 = 0;
        int ans = 0;

        for (int right = 0; right < n; right++) {
            int temp = nums[right] % 2 == 0 ? 0 : 1;
            sum_1 += temp;
            sum_2 += temp;
            while (sum_1 >= k && left_1 <= right ) {
                sum_1 -= (nums[left_1] % 2 == 0 ? 0 : 1);
                left_1++;
            }

            while (sum_2 > k && left_2 <= right ) {
                sum_2 -= (nums[left_2] % 2 == 0 ? 0 : 1);
                left_2++;
            }

            ans += left_1 - left_2;

        }

        return ans;
    }
}
