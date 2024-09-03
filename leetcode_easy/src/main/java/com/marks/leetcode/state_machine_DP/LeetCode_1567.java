package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/3 16:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1567 {
    /**
     * @Description: [
     * 给你一个整数数组 nums ，请你求出乘积为正数的最长子数组的长度。
     * 一个数组的子数组是由原数组中零个或者更多个连续数字组成的数组。
     * 请你返回乘积为正数的最长子数组长度。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/3 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getMaxLen(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * 关键点
     * 1. 任何数 * 0  = 0, 因此遇到0时需要重置count = 0
     * 2. 需要连续的数组
     * 3. 如果连续的子数组的乘积为负数,
     * 如案例:[1,2,3,5,-6,4,0,10] 则ans = 4
     * 还是用动态规划吧
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/9/3 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] positive = new int[n]; // 记录最长正数长度
        int[] negative = new int[n]; // 记录负数长度
        positive[0] = nums[0] > 0 ? 1 : 0;
        negative[0] = nums[0] < 0 ? 1 : 0;
        int ans = positive[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] > 0) {
                positive[i] = positive[i - 1] + 1;
                negative[i] = negative[i - 1] > 0 ? negative[i - 1] + 1 : 0;
            } else if (nums[i] < 0) {
                positive[i] = negative[i - 1] > 0 ? negative[i - 1] + 1 : 0;
                negative[i] = positive[i - 1] + 1;
            }else {
                // nums[i] == 0
                positive[i] = 0;
                negative[i] = 0;
            }
            ans = Math.max(ans, positive[i]);
        }
        return ans;
    }
}
