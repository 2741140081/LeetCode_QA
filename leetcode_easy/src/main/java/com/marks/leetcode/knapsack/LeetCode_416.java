package com.marks.leetcode.knapsack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/31 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_416 {
    /**
     * @Description: [
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * 可以看做是如果数组总和是偶数
     * 那么, target = sum / 2
     * 那么问题可以看做是在数组中寻找总和为target的子序列, 类似与LeetCode_2915
     * ]
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2024/7/31 16:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canPartition(int[] nums) {
        boolean result = false;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * 与LeetCode_2915一致, 基本一样, 仅仅只是目标值是数组总和的一半
     *
     * ]
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2024/7/31 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int n = nums.length;
        if (sum % 2 != 0 || n == 1) {
            return false;
        }
        int target = sum / 2;
        int[][] dp = new int[2][target + 1];
        for (int i = 0; i <= target; i++) {
            dp[0][i] = Integer.MIN_VALUE;
        }
        int curr = 0;
        dp[0][0] = 0;
        for (int i = 1; i < n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = nums[i-1];
            for (int j = 0; j <= target; j++) {
                if (temp > j) {
                    dp[curr][j] = dp[pre][j];
                }else {
                    dp[curr][j] = Math.max(dp[pre][j], dp[pre][j - temp] + 1);
                }
            }
        }
        int res = dp[curr][target];

        return res <= 0 ? false : true;
    }
}
