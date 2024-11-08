package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/8 14:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_27 {
    /**
     * @Description: [
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
     *
     * 假设 nums 中不等于 val 的元素数量为 k，要通过此题，您需要执行以下操作：
     *
     * 更改 nums 数组，使 nums 的前 k 个元素包含不等于 val 的元素。nums 的其余元素和 nums 的大小并不重要。
     * 返回 k。
     * ]
     * @param nums
     * @param val
     * @return int
     * @author marks
     * @CreateDate: 2024/11/8 14:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int removeElement(int[] nums, int val) {
        int result = 0;
        result = method_01(nums, val);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [3,2,2,3], val = 3
     * 输出：2, nums = [2,2,_,_]
     * AC:0ms/40.92MB
     * ]
     * @param nums 
     * @param val
     * @return int
     * @author marks
     * @CreateDate: 2024/11/8 14:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int val) {
        int n = nums.length;
        int left = 0;
        int right = n;

        while (left < right) {
            if (nums[left] == val) {
                nums[left] = nums[right - 1];
                right--;
            } else {
                // nums[left] != val
                left++;
            }
        }
        return left;
    }
}
