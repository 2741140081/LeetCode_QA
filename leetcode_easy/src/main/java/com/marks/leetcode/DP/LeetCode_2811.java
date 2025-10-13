package com.marks.leetcode.DP;

import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 15:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2811 {

    /**
     * @Description:
     * 给你一个长度为 n 的数组 nums 和一个整数 m 。请你判断能否执行一系列操作，将数组拆分成 n 个 非空 数组。
     *
     * 一个数组被称为 好 的，如果：
     *
     * 子数组的长度为 1 ，或者
     * 子数组元素之和 大于或等于  m 。
     * 在每一步操作中，你可以选择一个 长度至少为 2 的现有数组（之前步骤的结果） 并将其拆分成 2 个子数组，而得到的 每个 子数组都需要是好的。
     *
     * 如果你可以将给定数组拆分成 n 个满足要求的数组，返回 true ；否则，返回 false 。
     * tips:
     * 1 <= n == nums.length <= 100
     * 1 <= nums[i] <= 100
     * 1 <= m <= 200
     * @param nums 
     * @param m
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/13 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canSplitArray(List<Integer> nums, int m) {
        boolean result;
        result = method_01(nums, m);
        return result;
    }

    /**
     * @Description:
     * 差不多理解了题目的意思, 即将nums数组分步拆分成n个非空数组
     * 1. 如果我能将nums拆分成n个非空数组, 最后一次拆分是一个长度为2的子数组, 并且sum >= m
     * 2. 也就是说如果nums中存在一个连续的长度为2的子数组, 并且子数组和大于等于m, 那么就能将nums拆分成n个非空数组
     * 3. 用一个固定长度的滑动窗口来得到长度为2的子数组, 并且判断最大的子数组和 max >= m
     * AC: 1ms/42.20MB
     * @param nums 
     * @param m 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/13 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(List<Integer> nums, int m) {
        int n = nums.size();
        if (n <= 2) {
            return true;
        }
        int sum = nums.get(0) + nums.get(1);
        int left = 0;
        int max = sum;
        for (int right = 2; right < n; right++) {
            sum = sum + nums.get(right) - nums.get(left);
            left++;
            max = Math.max(max, sum);
        }
        
        return max >= m;
    }

}
