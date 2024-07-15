package com.marks.leetcode.rob;

public class LeetCode_213 {
    /**
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
     * 同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
     * E1:
     * 输入：nums = [2,3,2]
     * 输出：3
     * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
     *
     * E2:
     * 输入：nums = [1,2,3,1]
     * 输出：4
     * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     *
     * Tips:
     * 由于 House[1] 和 House[n] 相邻，因此不能一起抢劫。
     * 因此，问题变成抢劫 House[1]-House[n-1] 或 House[2]-House[n]，具体取决于哪个选择提供更多的钱。
     * 现在问题已经退化到入室盗窃，已经解决了。
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    private int method_01(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return nums[0];
        }
        if (len == 2) {
            return Math.max(nums[0], nums[1]);
        }
        // 房屋数量大于2
        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < len - 1; i++) {
            dp[i] = Math.max(dp[i-2] + nums[i], dp[i-1]);
        }
        int[] dp_1 = new int[len];
        dp_1[0] = 0;
        dp_1[1] = nums[1];
        for (int i = 2; i < len; i++) {
            dp_1[i] = Math.max(dp_1[i-2] + nums[i], dp_1[i-1]);
        }
        int result = Math.max(dp[len-2], dp_1[len-1]);

        return result;
    }
}
