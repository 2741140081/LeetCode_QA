package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2161 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 9:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2161 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个整数 pivot 。
     * 请你将 nums 重新排列，使得以下条件均成立：
     * 所有小于 pivot 的元素都出现在所有大于 pivot 的元素 之前 。
     * 所有等于 pivot 的元素都出现在小于和大于 pivot 的元素 中间 。
     * 小于 pivot 的元素之间和大于 pivot 的元素之间的 相对顺序 不发生改变。
     * 更正式的，考虑每一对 pi，pj ，pi 是初始时位置 i 元素的新位置，pj 是初始时位置 j 元素的新位置。如果 i < j 且两个元素 都 小于（或大于）pivot，那么 pi < pj 。
     * 请你返回重新排列 nums 数组后的结果数组。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * -10^6 <= nums[i] <= 10^6
     * pivot 等于 nums 中的一个元素。
     * @param: nums
     * @param: pivot
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/08 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] pivotArray(int[] nums, int pivot) {
        int[] result;
        result = method_01(nums, pivot);
        return result;
    }

    /**
     * @Description:
     * 1. 简单思路是分别用两个 list 存储小于 和 大于 pivot 的元素，int count 统计 pivot 的个数
     * AC: 14ms/160.92MB
     * @param: nums
     * @param: pivot
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/08 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int pivot) {
        int n = nums.length;
        int count = 0;
        List<Integer> less = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();
        for (int num : nums) {
            if (num < pivot) {
                less.add(num);
            } else if (num > pivot) {
                greater.add(num);
            } else {
                count++;
            }
        }
        int[] ans = new int[n];
        int index = 0;
        for (Integer value : less) {
            ans[index] = value;
            index++;
        }
        for (int i = 0; i < count; i++) {
            ans[index] = pivot;
            index++;
        }
        for (Integer integer : greater) {
            ans[index] = integer;
            index++;
        }

        return ans;
    }

}
