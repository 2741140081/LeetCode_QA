package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3259 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 17:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3259 {

    /**
     * @Description:
     * 来自未来的体育科学家给你两个整数数组 energyDrinkA 和 energyDrinkB，数组长度都等于 n。
     * 这两个数组分别代表 A、B 两种不同能量饮料每小时所能提供的强化能量。
     * 你需要每小时饮用一种能量饮料来 最大化 你的总强化能量。
     * 然而，如果从一种能量饮料切换到另一种，你需要等待一小时来梳理身体的能量体系（在那个小时里你将不会获得任何强化能量）。
     * 返回在接下来的 n 小时内你能获得的 最大 总强化能量。
     * 注意 你可以选择从饮用任意一种能量饮料开始。
     *
     * tips:
     * n == energyDrinkA.length == energyDrinkB.length
     * 3 <= n <= 10^5
     * 1 <= energyDrinkA[i], energyDrinkB[i] <= 10^5
     * @param: energyDrinkA
     * @param: energyDrinkB
     * @return long
     * @author marks
     * @CreateDate: 2026/03/30 17:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxEnergyBoost(int[] energyDrinkA, int[] energyDrinkB) {
        long result;
        result = method_01(energyDrinkA, energyDrinkB);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划
     * 2. int[][] dp = new int[n][2]; dp[i][0] 表示第 i 小时，选择 A 喝的能量； dp[i][1] 表示第 i 小时，选择 B 喝的能量
     * 3. 转移方程: dp[i][0] = Math,max(dp[i - 1][0], dp[i - 2][1]) + energyDrinkA[i];
     * dp[i][1] = Math,max(dp[i - 1][1], dp[i - 2][0]) + energyDrinkB[i];
     * 4. return Math.max(dp[n - 1][0], dp[n - 1][1]);
     * 5. 看错返回值, 需要返回long, 所以创建数组需要使用 long[][] 数组
     * AC: 31ms/147.38MB
     * @param: energyDrinkA
     * @param: energyDrinkB
     * @return long
     * @author marks
     * @CreateDate: 2026/03/30 17:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] energyDrinkA, int[] energyDrinkB) {
        int n = energyDrinkA.length;
        long[][] dp = new long[n + 1][2];
        dp[0][0] = 0;
        dp[0][1] = 0;
        // 把 i = 1也要处理, 否则 i - 2 会越界
        dp[1][0] = energyDrinkA[0];
        dp[1][1] = energyDrinkB[0];
        for (int i = 2; i <= n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 2][1]) + energyDrinkA[i - 1];
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0]) + energyDrinkB[i - 1];
        }
        return Math.max(dp[n][0], dp[n][1]);
    }

}
