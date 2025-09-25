package com.marks.leetcode.bitwise_operation;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/25 10:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2419 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums 。
     *
     * 考虑 nums 中进行 按位与（bitwise AND）运算得到的值 最大 的 非空 子数组。
     *
     * 换句话说，令 k 是 nums 任意 子数组执行按位与运算所能得到的最大值。那么，只需要考虑那些执行一次按位与运算后等于 k 的子数组。
     * 返回满足要求的 最长 子数组的长度。
     *
     * 数组的按位与就是对数组中的所有数字进行按位与运算。
     *
     * 子数组 是数组中的一个连续元素序列。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/9/25 10:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestSubarray(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [1,2,3,3,2,2]
     * 输出：2
     * AC: 8ms/59.86MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/25 10:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 0;
        int max = Arrays.stream(nums).max().getAsInt(); // 数组的最大值
        int len = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == max) {
                len++;
                ans = Math.max(ans, len);
            } else {
                len = 0;
            }
        }
        return ans;
    }

}
