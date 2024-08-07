package com.marks.leetcode.knapsack.multiple_knapsack;

/**
 * <p>项目名称: 多重背包问题 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 14:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2585 {

    public final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [
     * 考试中有 n 种类型的题目。给你一个整数 target 和一个下标从 0 开始的二维整数数组 types ，
     * 其中 types[i] = [counti, marksi] 表示第 i 种类型的题目有 counti 道，每道题目对应 marksi 分。
     * 返回你在考试中恰好得到 target 分的方法数。由于答案可能很大，结果需要对 109 +7 取余。
     * 注意，同类型题目无法区分。
     * 比如说，如果有 3 道同类型题目，那么解答第 1 和第 2 道题目与解答第 1 和第 3 道题目或者第 2 和第 3 道题目是相同的
     *
     * tips:
     * 1 <= target <= 1000
     * n == types.length
     * 1 <= n <= 50
     * types[i].length == 2
     * 1 <= counti, marksi <= 50
     * ]
     * @param target
     * @param types
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 14:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int waysToReachTarget(int target, int[][] types) {
        int result = 0;
        result = method_01(target, types);
        return result;
    }

    /**
     * @Description: [
     * 输入：target = 6, types = [[6,1],[3,2],[2,3]]
     * 输出：7
     * 解释：要获得 6 分，你可以选择以下七种方法之一：
     * - 解决 6 道第 0 种类型的题目：1 + 1 + 1 + 1 + 1 + 1 = 6
     * - 解决 4 道第 0 种类型的题目和 1 道第 1 种类型的题目：1 + 1 + 1 + 1 + 2 = 6
     * - 解决 2 道第 0 种类型的题目和 2 道第 1 种类型的题目：1 + 1 + 2 + 2 = 6
     * - 解决 3 道第 0 种类型的题目和 1 道第 2 种类型的题目：1 + 1 + 1 + 3 = 6
     * - 解决 1 道第 0 种类型的题目、1 道第 1 种类型的题目和 1 道第 2 种类型的题目：1 + 2 + 3 = 6
     * - 解决 3 道第 1 种类型的题目：2 + 2 + 2 = 6
     * - 解决 2 道第 2 种类型的题目：3 + 3 = 6
     *
     * 1. 我先假设这是一个完全背包问题, 即有3中类型的硬币 coins[1, 2, 3], 数量不限
     * 2. 与LeetCode_518.java 类似求解方案数
     * 3. 会存在一个中间变量 int num = j / temp; // 可以拿取的最大数量 temp = coins[i - 1] = types[i - 1][1]
     * 4. 答案就很明显 int num = Math.min(j / temp, types[i - 1][0]), 这两者的最小值即为我可以拿的改硬币的最大数量
     * 5.
     * ]
     * @param target
     * @param types
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int target, int[][] types) {
        int n = types.length;
        int[][] dp = new int[2][target + 1];
        dp[0][0] = 1;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = types[i - 1][1];
            for (int j = 0; j <= target; j++) {
                dp[curr][j] = 0;
                int tempCount = j / temp;
                int num = Math.min(tempCount, types[i - 1][0]);
                for (int k = 0; k <= num; k++) {
                    if (j >= k * temp) {
                        dp[curr][j] = (dp[curr][j] + dp[pre][j - k * temp]) % MOD;
                    }else {
                        dp[curr][j] = dp[pre][j] % MOD;
                    }
                }
            }
        }
        return dp[curr][target] % MOD;
    }
}
