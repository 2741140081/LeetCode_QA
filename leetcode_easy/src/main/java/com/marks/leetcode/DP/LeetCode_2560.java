package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2560 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/24 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2560 {

    /**
     * @Description:
     * 沿街有一排连续的房屋。每间房屋内都藏有一定的现金。现在有一位小偷计划从这些房屋中窃取现金。
     * 由于相邻的房屋装有相互连通的防盗系统，所以小偷 不会窃取相邻的房屋 。
     * 小偷的 窃取能力 定义为他在窃取过程中能从单间房屋中窃取的 最大金额 。
     * 给你一个整数数组 nums 表示每间房屋存放的现金金额。形式上，从左起第 i 间房屋中放有 nums[i] 美元。
     * 另给你一个整数 k ，表示窃贼将会窃取的 最少 房屋数。小偷总能窃取至少 k 间房屋。
     * 返回小偷的 最小 窃取能力。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= (nums.length + 1)/2
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCapability(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 得到nums[] 数组的最大值和最小值, left = min(nums[i]), right = max(nums[i])
     * 2. 使用二分查找来确定最小的窃取能力, int mid = left + (right - left) / 2; 判断能否在 nums[] 数组中找到 k 个符合要求的数字,
     * k 个互不相邻的数字, 并且满足 nums[i] <= mid
     * 3. 针对2的步骤, 对于 nums[i], 如果nums[i] <= mid, dp[i][1] = dp[i-1][0] + 1; 否则 dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1])
     * AC: 230ms/89.84MB
     * 时间复杂度: O(n * log T)
     * 空间复杂度: O(n * log T)
     * T 是 nums[] 数组中的最大值 与 最小值之间的差
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/24 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int left = Arrays.stream(nums).min().getAsInt();
        int right = Arrays.stream(nums).max().getAsInt();
        int ans = right;
        while (left < right) {
            int mid = (right - left) / 2 + left;
            if (checkConditionIsMeet(nums, mid, k)) {
                ans = Math.min(ans, mid);
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

    private boolean checkConditionIsMeet(int[] nums, int target, int k) {
        int n = nums.length;
        int[][] dp = new int[n][2];
        // 初始化dp[0]
        dp[0][0] = 0;
        if (nums[0] <= target) {
            dp[0][1] = 1;
        }
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            if (nums[i] <= target) {
                dp[i][1] = Math.max(dp[i - 1][0] + 1, dp[i - 1][1]);
            } else {
                dp[i][1] = dp[i - 1][1];
            }
            if (dp[i][1] >= k || dp[i][0] >= k) {
                return true;
            }
        }
        return false;
    }

}
