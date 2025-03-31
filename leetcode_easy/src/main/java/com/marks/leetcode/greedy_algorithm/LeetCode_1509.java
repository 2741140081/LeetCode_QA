package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/31 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1509 {
    /**
     * @Description:
     * 给你一个数组 nums 。
     * 每次操作你可以选择 nums 中的任意一个元素并将它改成 任意值 。
     *
     * 在 执行最多三次移动后 ，返回 nums 中最大值与最小值的最小差值。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDifference(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * AC: 19ms/55.73MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        if (n < 4) {
            return 0;
        }
        int rs_1 = nums[n - 1] - nums[3];
        int rs_2 = nums[n - 2] - nums[2];
        int rs_3 = nums[n - 3] - nums[1];
        int rs_4 = nums[n - 4] - nums[0];
        return Math.min(Math.min(rs_1, rs_2), Math.min(rs_3, rs_4));
    }
}
