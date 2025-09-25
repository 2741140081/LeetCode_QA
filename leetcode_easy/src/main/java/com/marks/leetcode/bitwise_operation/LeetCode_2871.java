package com.marks.leetcode.bitwise_operation;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/25 10:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2871 {

    /**
     * @Description:
     * 给你一个只包含 非负 整数的数组 nums 。
     *
     * 我们定义满足 l <= r 的子数组 nums[l..r] 的分数为 nums[l] AND nums[l + 1] AND ... AND nums[r] ，其中 AND 是按位与运算。
     *
     * 请你将数组分割成一个或者更多子数组，满足：
     *
     * 每个 元素都 只 属于一个子数组。
     * 子数组分数之和尽可能 小 。
     * 请你在满足以上要求的条件下，返回 最多 可以得到多少个子数组。
     *
     * 一个 子数组 是一个数组中一段连续的元素。
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^6
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/9/25 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSubarrays(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    
    /**
     * @Description:
     * 1. 理解有点问题, 需要的是子数组的分数之和最小,
     * AC: 4ms/55.28MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/25 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int min = nums[0];
        for (int i = 0; i < nums.length; i++) {
            min &= nums[i];
        }

        int ans = 0;
        int tmp = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            tmp &= nums[i];
            if (tmp == min) {
                ans++;
                tmp = Integer.MAX_VALUE;
            }
        }
        return min == 0 ? ans : 1;
    }

}
