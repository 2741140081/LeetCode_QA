package com.marks.leetcode.data_structure.common_enum_technique;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/10 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2909 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 。
     *
     * 如果下标三元组 (i, j, k) 满足下述全部条件，则认为它是一个 山形三元组 ：
     *
     * i < j < k
     * nums[i] < nums[j] 且 nums[k] < nums[j]
     * 请你找出 nums 中 元素和最小 的山形三元组，并返回其 元素和 。如果不存在满足条件的三元组，返回 -1 。
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/10 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumSum(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     *
     * AC:2ms/56.13MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/10 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int temp = nums[0];
        for (int i = 0; i < n; i++) {
            left[i] = temp;
            temp = Math.min(temp, nums[i]);
        }
        temp = nums[n - 1];
        for (int i = n - 1; i >= 0; i--) {
            right[i] = temp;
            temp = Math.min(temp, nums[i]);
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= n - 2; i++) {
            if (nums[i] > left[i] && nums[i] > right[i]) {
                ans = Math.min(ans, nums[i] + left[i] + right[i]);
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
