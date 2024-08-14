package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: 特殊数组 I </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/13 16:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3151 {
    /**
     * @Description: [
     * 如果数组的每一对相邻元素都是两个奇偶性不同的数字，则该数组被认为是一个 特殊数组 。
     * Aging 有一个整数数组 nums。如果 nums 是一个 特殊数组 ，返回 true，否则返回 false。
     * ]
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2024/8/13 16:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isArraySpecial(int[] nums) {
        boolean flag = false;
        flag = method_01(nums);
        return flag;
    }

    private boolean method_01(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        for (int i = 1; i < nums.length; i++) {
            int curr = nums[i] % 2;
            int pre = nums[i - 1] % 2;
            if (curr == pre) {
                return false;
            }
        }
        return true;
    }
}
