package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_413 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/24 11:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_413 {

    /**
     * @Description:
     * 如果一个数列 至少有三个元素 ，并且任意两个相邻元素之差相同，则称该数列为等差数列。
     *
     * 例如，[1,3,5,7,9]、[7,7,7,7] 和 [3,-1,-5,-9] 都是等差数列。
     * 给你一个整数数组 nums ，返回数组 nums 中所有为等差数组的 子数组 个数。
     *
     * 子数组 是数组中的一个连续序列。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfArithmeticSlices(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /***
     * @Description:
     * 需要注意的是需要3个元素才能组成等差数列
     * 1. 如果等差数列的长度假设为 n, n = 3, 子数组个数为 sum1 = 1; n = 4, sum2 = 3; n = 5, sum3 = 3 + 3 + 1 = 7;
     * 可以推出递推规则 dp[n] = dp[n - 1] + dp[n - 1] + 1, dp[3] = 1;
     * 我的递推公式存在问题, dp[n] = dp[n - 1] + (n - 2);
     * dp[3] = 1, dp[4] = 3, dp[5] = 6, dp[6] = 10, dp[7] = 15;
     * AC: 0ms/42.52MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/24 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        if (nums.length < 3) {
            return 0;
        }
        int ans = 0;
        int count = 2;
        int[] dp = new int[nums.length + 1];
        dp[3] = 1;
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == nums[i - 1] - nums[i - 2]) {
                count++;
                if (dp[count] == 0) {
                    dp[count] = dp[count - 1] + count - 2;
                }
            } else {
                ans += dp[count];
                count = 2;
            }
        }
        ans += dp[count];
        return ans;
    }
}
