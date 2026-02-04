package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_375 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/3 15:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_375 {

    /**
     * @Description:
     * 我们正在玩一个猜数游戏，游戏规则如下：
     *
     * 我从 1 到 n 之间选择一个数字。
     * 你来猜我选了哪个数字。
     * 如果你猜到正确的数字，就会 赢得游戏 。
     * 如果你猜错了，那么我会告诉你，我选的数字比你的 更大或者更小 ，并且你需要继续猜数。
     * 每当你猜了数字 x 并且猜错了的时候，你需要支付金额为 x 的现金。如果你花光了钱，就会 输掉游戏 。
     * 给你一个特定的数字 n ，返回能够 确保你获胜 的最小现金数，不管我选择那个数字 。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/03 15:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getMoneyAmount(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 10
     * 输出：16
     * 1. 看起来像是二叉搜索树, 怎么给二叉树节点添加值?
     * 2. dp[1] = 0, dp[2] = 1, dp[3] = 2, dp[4] = 5, dp[5] = 6, dp[6] = 8
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/03 15:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 1; i--) {
            for (int j = i + 1; j <= n; j++) {
                dp[i][j] = j + dp[i][j - 1];
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], k + Math.max(dp[i][k - 1], dp[k + 1][j]));
                }
            }
        }
        return dp[1][n];
    }

}
