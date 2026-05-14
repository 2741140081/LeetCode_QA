package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2784 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/14 10:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2784 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，如果它是数组 base[n] 的一个排列，我们称它是个 好 数组。
     * base[n] = [1, 2, ..., n - 1, n, n] （换句话说，它是一个长度为 n + 1 且包含 1 到 n - 1 恰好各一次，包含 n  两次的一个数组）。
     * 比方说，base[1] = [1, 1] ，base[3] = [1, 2, 3, 3] 。
     * 如果数组是一个好数组，请你返回 true ，否则返回 false 。
     * 注意：数组的排列是这些数字按任意顺序排布后重新得到的数组。
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/14 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isGood(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 简单题目简单做, 排序, 然后验证
     * AC: 5ms/44.61MB
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/14 10:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length - 1;
        if (n < 2) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return false;
            }
        }
        return nums[n] == nums[n - 1];
    }

}
