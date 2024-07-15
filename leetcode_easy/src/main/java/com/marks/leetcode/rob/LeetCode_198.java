package com.marks.leetcode.rob;

public class LeetCode_198 {
    /**
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     *
     * E1:
     * 输入：[1,2,3,1]
     * 输出：4
     * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     * 偷窃到的最高金额 = 1 + 3 = 4 。
     *
     * E2:
     * 输入：[2,7,9,3,1]
     * 输出：12
     * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
     * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * 思路1: 因为不能偷取相邻房间的金额, 即可以将数据分成奇数和偶数
     * 判断奇数金额总和与偶数金额总和比较
     *
     * 备注:思路1错误, 被案例误导, 其实可以隔着2个房间偷
     * 即:
     * 思路2: 小偷已经偷取第 i 个房屋的金额, 下一个可以偷取的房屋为 i + 2 or i + 3
     * eg: [2,7,9,3,1]
     * 已经偷取了第 0 个房屋的金额2, 下一个可以偷取的房屋是 2(9) or 3(3), 如果是更远的房屋 4(1), 则则num[i+2] + num[i+4] > num[i+4]
     * dp[i] = Math.max(dp[i-2] + num[i], dp[i-3] + num[i])
     *
     * 思路2有问题?
     * 修改:
     * 官方题解思路:
     * 如果房屋数量大于2间房屋, 对于第 k 间房屋有以下两个选项
     * 1. 不偷取第 k 间房屋, 则偷取最大总金额为k-1间房屋的总金额
     * 2. 偷取第 k 间房屋, 则偷取的总金额为k-2 + num[k]
     * 因此表达式为: dp[k] = Math.max(dp[k-2] + num[k], dp[k-1])
     *
     * dp[i] = dp[i-1] + nums[i]
     * @param nums
     * @return
     */
    private int method_01(int[] nums) {
        // eg: [1,2,3,1] result: 4
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i-2] + nums[i], dp[i-1]);
        }
        return dp[nums.length-1];
    }
}
