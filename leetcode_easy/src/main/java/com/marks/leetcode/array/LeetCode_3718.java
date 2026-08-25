package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3718 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/25 15:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3718 {

    // 给你一个整数数组 nums 和一个整数 k，请返回从 nums 中缺失的、最小的正整数 k 的倍数。
    // 倍数 指能被 k 整除的任意正整数。
    public int missingMultiple(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    // AC: 6ms/45.03MB
    private int method_01(int[] nums, int k) {
        Arrays.sort(nums);

        int multi = 1;

        for (int num : nums) {
            if (num > k * multi) {
                return k * multi;
            } else if (num == k * multi) {
                multi++;
            }
        }

        return k * multi;
    }

}
