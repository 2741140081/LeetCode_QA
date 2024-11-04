package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/4 10:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_977 {
    /**
     * @Description: [
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
     *
     * tips:
     * 1 <= nums.length <= 10^4
     * -10^4 <= nums[i] <= 10^4
     * nums 已按 非递减顺序 排序
     * ]
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/4 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] sortedSquares(int[] nums) {
        int[] result;
//        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    private int[] method_02(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int) Math.pow(nums[i], 2);
        }
        Arrays.sort(nums);
        return nums;
    }

    /**
     * @Description: [
     * AC:1ms/46.29MB
     * ]
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/4 10:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int left = 0;
        int right = n - 1;
        int pos = n - 1;
        while (left < right) {
            int temp_left = Math.abs(nums[left]);
            int temp_right = Math.abs(nums[right]);
            if (temp_left >= temp_right) {
                ans[pos] = (int) Math.pow(temp_left, 2);
                left++;
                pos--;
            }else {
                ans[pos] = (int) Math.pow(temp_right, 2);
                right--;
                pos--;
            }
        }
        if (left == right) {
            ans[pos] = (int) Math.pow(nums[left], 2);
        }
        return ans;
    }

}
