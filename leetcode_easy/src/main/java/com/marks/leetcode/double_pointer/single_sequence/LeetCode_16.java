package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_16 {
    /**
     * @Description: [
     * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
     *
     * 返回这三个数的和。
     *
     * 假定每组输入只存在恰好一个解。
     *
     * tips:
     * 3 <= nums.length <= 1000
     * -1000 <= nums[i] <= 1000
     * -10^4 <= target <= 10^4
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int threeSumClosest(int[] nums, int target) {
        int result = 0;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [-1,2,1,-4], target = 1
     * 输出：2
     *
     * AC:11ms/42.00MB
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/5 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int n = nums.length;
        int ans;

        Arrays.sort(nums);
        ans = nums[0] + nums[1] + nums[2]; // 初始化
        int abs = Math.abs(ans - target);
        for (int i = 0; i < n - 2; i++) {
            int temp = target - nums[i];
            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int num = nums[left] + nums[right];
                if (num > temp) {
                    if (Math.abs(num - temp) < abs) {
                        abs = Math.abs(num - temp);
                        ans = num + nums[i];
                    }
                    right--;
                } else if (num < temp) {
                    if (Math.abs(num - temp) < abs) {
                        abs = Math.abs(num - temp);
                        ans = num + nums[i];
                    }
                    left++;
                }else {
                    // num == target, is closest
                    ans = num + nums[i];
                    return ans;
                }
            }
        }
        return ans;
    }
}
