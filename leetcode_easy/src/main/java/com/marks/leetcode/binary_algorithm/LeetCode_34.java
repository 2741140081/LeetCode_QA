package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/13 10:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_34 {
    /**
     * @Description: [
     * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
     *
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     *
     * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
     * ]
     * @param nums 
     * @param target
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/13 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result;
        result = method_01(nums, target);
        result = method_02(nums, target);
        return result;
    }

    /**
     * @Description: [
     * AC:1ms/44.89MB
     * ]
     * @param nums
     * @param target
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/13 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == target && nums[right] == target) {
                return new int[] {left, right};
            }
            if (nums[left] != target) {
                left++;
            }
            if (nums[right] != target) {
                right--;
            }
        }

        return new int[] {-1, -1};
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     *
     * AC:0ms/45.09MB
     * ]
     * @param nums
     * @param target
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/13 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int first = -1;
        int last = -1;
        while (left <= right) {
            int mid = (right - left)/2 + left;
            if (nums[mid] == target) {
                first = mid;
                right = mid - 1;
            }else if (nums[mid] < target) {
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }

        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int mid = (right - left)/2 + left;
            if (nums[mid] == target) {
                last = mid;
                left = mid + 1;
            }else if (nums[mid] < target) {
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return new int[]{first, last};
    }
}
