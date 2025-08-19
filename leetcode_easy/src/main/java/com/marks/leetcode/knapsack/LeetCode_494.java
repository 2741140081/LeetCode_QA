package com.marks.leetcode.knapsack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/31 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_494 {
    /**
     * @Description: [
     * 给你一个非负整数数组 nums 和一个整数 target 。
     * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
     * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
     * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/7/31 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findTargetSumWays(int[] nums, int target) {
        int result = 0;
        result = method_01(nums, target);
        result = method_02(nums, target);
        return result;
    }

    /**
     * @Description:
     *
     * AC: 1338ms(5.03%)/40.29MB(99.06%)
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2025/8/15 17:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private int ans;
    private int method_02(int[] nums, int target) {
        n = nums.length;
        ans = 0;
        int sum = 0;
        dfs(nums, 0, sum, target);
        return ans;
    }

    private void dfs(int[] nums, int index, int sum, int target) {
        if (index == n) {
            if (sum == target) {
                ans++;
            }
        }
        dfs(nums, index + 1, sum + nums[index], target);
        dfs(nums, index + 1, sum - nums[index], target);
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,1,1,1,1], target = 3
     * 输出：5
     * 解释：一共有 5 种方法让最终目标和为 3 。
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     *
     * 一个非负整数的数组和是一个定值 sum
     * if target > sum return 0
     * 假设我第一个值取负数
     * 假设存在
     * target == sum - 2 (i_1 +.....+i_n)
     * i_1 + ..... + i_n = target_new = (sum - target)/2
     * 现在情况是在nums[] 数组中找到和为target_new 的组合数目
     *
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/7/31 16:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int target) {
        int sum = Arrays.stream(nums).sum();
        // Math.abs(target) < sum
        // 判断对2取余是否合法
        if ((sum - target) % 2 != 0 || Math.abs(target) > sum) {
            // 不合法
            return 0;
        }
        int target_new = (sum - target) / 2;
        int n = nums.length;
        int[][] dp = new int[2][target_new+1];

        dp[0][0] = 1;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = nums[i-1];
            for (int j = 0; j <= target_new; j++) {
                /*
                j < temp (nums[i-1]) 时: 则不能选temp, dp[i][j] = dp[i-1][j]
                j >= temp 时: 如果不选temp, dp[i-1][j]; 如果选temp, dp[i-1][j-temp],
                即dp[i][j] = dp[i-1][j] + dp[i-1][j-temp]
                总上所述:可得如下转态转移方程
                 */
                dp[curr][j] = dp[pre][j];
                if (j >= temp) {
                    dp[curr][j] += dp[pre][j - temp];
                }
            }
        }

        return dp[curr][target_new];
    }
}
