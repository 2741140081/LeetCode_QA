package com.marks.leetcode.greedy_algorithm;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2587 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 。你可以将 nums 中的元素按 任意顺序 重排（包括给定顺序）。
     *
     * 令 prefix 为一个数组，它包含了 nums 重新排列后的前缀和。
     * 换句话说，prefix[i] 是 nums 重新排列后下标从 0 到 i 的元素之和。
     * nums 的 分数 是 prefix 数组中正整数的个数。
     *
     * 返回可以得到的最大分数。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 10:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScore(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * AC: 30ms/56.50MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 10:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        long preSum = 0;
        int ans = 0;
        for (int i = nums.length - 1; i >= 0; i--) {
            preSum += nums[i];
            ans = preSum > 0 ? ans + 1 : ans;
        }
        return ans;
    }
}
