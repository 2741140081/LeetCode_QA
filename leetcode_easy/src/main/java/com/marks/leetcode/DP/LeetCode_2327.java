package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2327 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2327 {
    private static final int MOD = (int) 1e9 + 7;

    /**
     * @Description:
     * 在第 1 天，有一个人发现了一个秘密。
     *
     * 给你一个整数 delay ，表示每个人会在发现秘密后的 delay 天之后，每天 给一个新的人 分享 秘密。
     * 同时给你一个整数 forget ，表示每个人在发现秘密 forget 天之后会 忘记 这个秘密。一个人 不能 在忘记秘密那一天及之后的日子里分享秘密。
     *
     * 给你一个整数 n ，请你返回在第 n 天结束时，知道秘密的人数。由于答案可能会很大，请你将结果对 10^9 + 7 取余 后返回。
     * @param: n
     * @param: delay
     * @param: forget
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        int result;
        result = method_01(n, delay, forget);
        return result;
    }

    /**
     * @Description:
     * 输入：n = 6, delay = 2, forget = 4
     * 输出：5
     * 1. i = 0, dp[0][0] = 1; i = 1, dp[1][1] = 1; i = 2, dp[2][0] = 1, dp[2][2] = 1;
     * i = 3, dp[3][0] = 1, dp[3][1] = 1, dp[3][3] = 1; i = 4, dp[4][0] = 1, dp[4][1] = 1, dp[4][2] = 1,
     * i = 5, dp[5][0] = 2, dp[5][1] = 1, dp[5][2] = 1, dp[5][3] = 1;
     * AC: 49ms/89.72MB
     * @param: n
     * @param: delay
     * @param: forget
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 17:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int delay, int forget) {
        long[][] dp = new long[n + 1][forget];
        dp[1][0] = 1;

        for (int i = 1; i < n; i++) {
            // 先处理忘记的人, 即将前一天的人数向后推动一天
            long sum = 0; // 记录可以分享的人数
            for (int j = forget - 1; j > 0; j--) {
                dp[i + 1][j] = dp[i][j - 1];
                if (j >= delay) {
                    sum = (sum + dp[i + 1][j]) % MOD;
                }
            }
            // 处理分享的人
            dp[i + 1][0] = sum % MOD;
        }
        long ans = 0;
        for (int i = 0; i < forget; i++) {
            ans = (ans + dp[n][i]) % MOD;
        }
        return (int) (ans % MOD);
    }

}
