package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;

/**
 * <p>项目名称: 最长递增子序列(longest increasing subsequence) </p>
 * <p>文件名称: 最长递增子序列模板 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/14 14:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_300 {
    /**
     * @Description: [
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     * 子序列 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的
     * 子序列
     * tips:
     * 1 <= nums.length <= 2500
     * -10^4 <= nums[i] <= 10^4
     * 。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/14 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lengthOfLIS(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * 动态规划
     * dp[i]=max(dp[j])+1,其中0≤j<i且num[j]<num[i]
     * E1:
     * 输入：nums = [10,9,2,5,3,7,101,18]
     * 输出：4
     * 解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/14 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
