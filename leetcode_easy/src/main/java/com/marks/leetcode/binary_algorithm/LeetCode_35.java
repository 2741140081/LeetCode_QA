package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/13 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_35 {
    /**
     * @Description: [
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     *
     * 请必须使用时间复杂度为 O(log n) 的算法。
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/13 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int searchInsert(int[] nums, int target) {
        int result;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入: nums = [1,3,5,6], target = 2
     * left = 0, right = 3, mid = 1, nums[1] = 3
     * 3 > target, right = 0, left = 0, mid = 0, nums[0] = 1
     * 1 < target, left = 1, right = 0, break
     * return left;
     *
     * 输入: nums = [1,3,5,6], target = 7
     * left = 0, right = 3, mid = 1, nums[1] = 3
     * target > 3, left = 2, right = 3, mid = 2, nums[2] = 5
     * target > 5, left = 3, right = 3, mid = 3, nums[3] = 6
     * target > 6, left = 4, right = 3, break
     * return left;
     *
     * AC:0ms/42.00MB
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/13 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}
