package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_088 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 14:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_088 {

    /**
     * @Description:
     * 数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。
     * 每当爬上一个阶梯都要花费对应的体力值，一旦支付了相应的体力值，就可以选择向上爬一个阶梯或者爬两个阶梯。
     * 请找出达到楼层顶部的最低花费。在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 14:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCostClimbingStairs(int[] cost) {
        int result;
        result = method_01(cost);
        return result;
    }

    /**
     * @Description:
     * dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i]
     * 1. n是一个阶梯数量, 到达顶层, 需要从n - 1 或者 n - 2 的阶梯才能到达顶层
     * 2. 所以返回 dp[n - 1] 或者 dp[n - 2] 的较小值
     * AC: 1ms/44.35MB
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 14:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < n; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        return Math.min(dp[n - 2], dp[n - 1]);
    }

}
