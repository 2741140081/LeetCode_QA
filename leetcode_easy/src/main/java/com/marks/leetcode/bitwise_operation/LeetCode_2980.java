package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/23 11:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2980 {

    /**
     * @Description:
     * 给你一个 正整数 数组 nums 。
     * 你需要检查是否可以从数组中选出 两个或更多 元素，满足这些元素的按位或运算（ OR）结果的二进制表示中 至少 存在一个尾随零。
     *
     * 例如，数字 5 的二进制表示是 "101"，不存在尾随零，而数字 4 的二进制表示是 "100"，存在两个尾随零。
     *
     * 如果可以选择两个或更多元素，其按位或运算结果存在尾随零，返回 true；否则，返回 false 。
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/23 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasTrailingZeros(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. nums[i] & 1 == 0, count++
     * AC: 0ms/43.2MB
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2025/9/23 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & 1) == 0) {
                // 当前 nums[i] 有一个尾随0, xx0 & 001 = 000
                count++;
            }
        }

        return count > 1;
    }

}
