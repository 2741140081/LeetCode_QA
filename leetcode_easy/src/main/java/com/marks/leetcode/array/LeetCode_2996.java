package com.marks.leetcode.array;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2996 </p>
 * <p>描述:
 * 给你一个下标从 0 开始的整数数组 nums 。
 * 如果一个前缀 nums[0..i] 满足对于 1 <= j <= i 的所有元素都有 nums[j] = nums[j - 1] + 1 ，
 * 那么我们称这个前缀是一个 顺序前缀 。特殊情况是，只包含 nums[0] 的前缀也是一个 顺序前缀 。
 * 请你返回 nums 中没有出现过的 最小 整数 x ，满足 x 大于等于 最长 顺序前缀的和。
 *
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/11 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2996 {

    // AC: 2ms/43.33MB
    public int missingInteger(int[] nums) {
        int n = nums.length;
        int prevSum = nums[0];
        Set<Integer> set = new HashSet<>();
        int i = 1;
        for (;i < n; i++) {
            set.add(nums[i]);
            if (nums[i] == nums[i - 1] + 1) {
                prevSum += nums[i];
            } else {
                break;
            }
        }
        while (i < n) {
            set.add(nums[i]);
            i++;
        }

        while (true) {
            if (!set.contains(prevSum)) {
                return prevSum;
            }
            prevSum++;
        }
    }

}
