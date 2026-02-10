package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_17_16 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_17_16 {

    /**
     * @Description:
     * 一个有名的按摩师会收到源源不断的预约请求，每个预约都可以选择接或不接。
     * 在每次预约服务之间要有休息时间，因此她不能接受相邻的预约。
     * 给定一个预约请求序列，替按摩师找到最优的预约集合（总预约时间最长），返回总的分钟数。
     *
     * 注意：本题相对原题稍作改动
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int massage(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划
     * 2. 转移方程：dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1])
     * AC: 0ms/41.99MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 10:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return nums[0];
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[n - 1];
    }
}
