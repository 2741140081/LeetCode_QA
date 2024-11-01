package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_930 {
    /**
     * @Description: [
     * 给你一个二元数组 nums ，和一个整数 goal ，请你统计并返回有多少个和为 goal 的 非空 子数组。
     *
     * 子数组 是数组的一段连续部分。
     * ]
     * @param nums
     * @param goal
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSubarraysWithSum(int[] nums, int goal) {
        int result = 0;
        result = method_01(nums, goal);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,0,1,0,1], goal = 2
     * 输出：4
     *
     * AC:2ms/47.03MB
     * ]
     * @param nums
     * @param goal
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int goal) {
        int n = nums.length;
        int left_1 = 0; // >= goal
        int left_2 = 0; // >= goal + 1
        int sum_1 = 0;
        int sum_2 = 0;
        int ans = 0;

        for (int right = 0; right < n; right++) {
            sum_1 += nums[right];
            sum_2 += nums[right];
            while (sum_1 >= goal && left_1 <= right ) {
                sum_1 -= nums[left_1++];
            }

            while (sum_2 > goal && left_2 <= right ) {
                sum_2 -= nums[left_2++];
            }

            ans += left_1 - left_2;

        }

        return ans;
    }
}
