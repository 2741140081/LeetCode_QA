package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/8 15:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_283 {
    /**
     * @Description: [
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     *
     * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
     *
     * tips:
     * 1 <= nums.length <= 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     * ]
     * @param nums
     * @return void
     * @author marks
     * @CreateDate: 2024/11/8 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void moveZeroes(int[] nums) {
//        method_01(nums);
        method_02(nums);
    }

    /**
     * @Description: [
     * 官方题解:
     * AC:2ms/44.77MB
     * ]
     * @param nums
     * @return void
     * @author marks
     * @CreateDate: 2024/11/8 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_02(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = 0;
        while (right < n) {
            if (nums[right] != 0) {
                swap(nums, left, right);
                left++;
            }
            right++;
        }
    }

    private void swap(int[] nums, int left, int right) {
        int temp = nums[right];
        nums[right] = nums[left];
        nums[left] = temp;
    }

    /**
     * @Description: [
     * E1:
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     *
     * AC:2ms/44.93MB
     * ]
     * @param nums
     * @return void
     * @author marks
     * @CreateDate: 2024/11/8 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = 0;
        int count = 0; // 记录[0 ~ right] 之间 '0' 的个数
        while (right < n) {
            if (nums[right] != 0) {
                if (count > 0) {
                    // count > 0
                    while (nums[left] != 0) {
                        // 找到第一个 nums[left] == 0
                        left++;
                    }
                    // 交换nums[left] 和 nums[right]
                    nums[left] = nums[right];
                    nums[right] = 0;
                    left++;
                }
            }else {
                // nums[right] == 0
                count++;
            }
            right++;
        }
        System.out.println("End");
    }
}
