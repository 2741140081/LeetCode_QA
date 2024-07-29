package com.marks.leetcode.knapsack;

import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/29 17:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2915 {
    public int lengthOfLongestSubsequence(List<Integer> nums, int target) {
        int result = 0;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description: [
     * 不太能直观的看出dp的状态转移方程
     * 先看案例
     * E1:
     * nums = [1,2,3,4,5], target = 9
     * [4, 5] = 9
     * [1, 3, 5]
     * [2, 3, 5]
     *
     * 我想到一个方式是
     * 倒序
     * for r n
     *
     *
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/7/29 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     * tips:
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * 1 <= target <= 1000
     */
    private int method_01(List<Integer> nums, int target) {
        int n = nums.size();

        int[][] dp = new int[n][target + 1];
        Arrays.fill(dp[0], 1);


        for (int i = 1; i < n; i++) {
            for (int j = nums.get(i); j <= target; j++) {
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-nums.get(i-1)]);
            }
        }
        return 0;
    }


}
