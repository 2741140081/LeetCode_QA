package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1510 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/10 14:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1510 {

    /**
     * @Description:
     * Alice 和 Bob 两个人轮流玩一个游戏，Alice 先手。
     * 一开始，有 n 个石子堆在一起。每个人轮流操作，正在操作的玩家可以从石子堆里拿走 任意 非零 平方数 个石子。
     * 如果石子堆里没有石子了，则无法操作的玩家输掉游戏。
     * 给你正整数 n ，且已知两个人都采取最优策略。如果 Alice 会赢得比赛，那么返回 True ，否则返回 False 。
     *
     * tips:
     * 1 <= n <= 10^5
     * @param: n
     * @return boolean
     * @author marks
     * @CreateDate: 2026/08/10 14:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean winnerSquareGame(int n) {
        boolean result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 为了使得自己赢得比赛, 需要使得剩余数量是 x^2 + 1 的形式
     * 2. 去石头的数量是 1, 2, 3, 4, 5,....,316 总计 316 个数. 也就是说最小取石头数是1块
     * 3. 逐帧分析, 当前自己取完后剩余的石头数, 必输情况[1, 3, 6], 22 => [9 + 9 + 4] [16 + 4 + 1 + 1]
     * 13 => [9, 4] [1, 9, 1, 1, 1], [1, 4, 4, 4]
     * 4. 如果使用动态规划, dp[1] = true, dp[2] = !dp[1], dp[3] = !dp[2], dp[4] = true | !dp[3]
     * [T, F, T, T, F, T, F, T, T, F, T, F, T, T, F, T, F, T, T, F]
     * 5. 执行动态规划
     * AC: 14ms/41.89MB
     * @param: n
     * @return boolean
     * @author marks
     * @CreateDate: 2026/08/10 14:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            // 对当前数开平方
            int sqrt = (int) Math.sqrt(i);
            if (sqrt * sqrt == i) {
                dp[i] = true;
            } else {
                boolean flag = false;
                for (int j = 1; j <= sqrt; j++) {
                    int prev = i - (j * j);
                    if (!dp[prev]) {
                        flag = true;
                        break;
                    }
                }
                dp[i] = flag;
            }
        }

        return dp[n];
    }

}
