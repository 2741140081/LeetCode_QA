package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3129 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 10:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3129 {

    /**
     * @Description:
     * 给你 3 个正整数 zero ，one 和 limit 。
     *
     * 一个 二进制数组 arr 如果满足以下条件，那么我们称它是 稳定的 ：
     *
     * 0 在 arr 中出现次数 恰好 为 zero 。
     * 1 在 arr 中出现次数 恰好 为 one 。
     * arr 中每个长度超过 limit 的 子数组 都 同时 包含 0 和 1 。
     * 请你返回 稳定 二进制数组的 总 数目。
     *
     * 由于答案可能很大，将它对 109 + 7 取余 后返回。
     * tips:
     * 1 <= zero, one, limit <= 200
     * @param: zero
     * @param: one
     * @param: limit
     * @return int
     * @author marks
     * @CreateDate: 2026/03/09 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfStableArrays(int zero, int one, int limit) {
        int result;
        result = method_01(zero, one, limit);
        return result;
    }

    /**
     * @Description:
     * 1. zero 个 0 和 one 个 1, n = zero + one, limit 相当于是一个窗口, 不能存在连续的 1 和 0,
     * 连续0 和 1的个数 <= limit
     * 2. 想不到怎么处理, 直接看题解吧, need todo
     * 3. 定义dp[i][j][k], 其中 i表示 0 的个数, j 表示 1 的个数, k 表示 0 或者 1 结尾
     * @param: zero
     * @param: one
     * @param: limit
     * @return int
     * @author marks
     * @CreateDate: 2026/03/09 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int zero, int one, int limit) {
        final long MOD = 1000000007;
        long[][][] dp = new long[zero + 1][one + 1][2];
        for (int i = 0; i <= Math.min(zero, limit); i++) {
            dp[i][0][0] = 1;
        }
        for (int j = 0; j <= Math.min(one, limit); j++) {
            dp[0][j][1] = 1;
        }
        for (int i = 1; i <= zero; i++) {
            for (int j = 1; j <= one; j++) {
                if (i > limit) {
                    dp[i][j][0] = dp[i - 1][j][0] + dp[i - 1][j][1] - dp[i - limit - 1][j][1];
                } else {
                    dp[i][j][0] = dp[i - 1][j][0] + dp[i - 1][j][1];
                }
                dp[i][j][0] = (dp[i][j][0] % MOD + MOD) % MOD;
                if (j > limit) {
                    dp[i][j][1] = dp[i][j - 1][1] + dp[i][j - 1][0] - dp[i][j - limit - 1][0];
                } else {
                    dp[i][j][1] = dp[i][j - 1][1] + dp[i][j - 1][0];
                }
                dp[i][j][1] = (dp[i][j][1] % MOD + MOD) % MOD;
            }
        }
        return (int) ((dp[zero][one][0] + dp[zero][one][1]) % MOD);
    }

}
