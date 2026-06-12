package com.marks.leetcode.difference_array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2327 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/10 15:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2327 {

    /**
     * @Description:
     * 在第 1 天，有一个人发现了一个秘密。
     * 给你一个整数 delay ，表示每个人会在发现秘密后的 delay 天之后，每天 给一个新的人 分享 秘密。
     * 同时给你一个整数 forget ，表示每个人在发现秘密 forget 天之后会 忘记 这个秘密。一个人 不能 在忘记秘密那一天及之后的日子里分享秘密。
     * 给你一个整数 n ，请你返回在第 n 天结束时，知道秘密的人数。由于答案可能会很大，请你将结果对 10^9 + 7 取余 后返回。
     * tips:
     * 2 <= n <= 1000
     * 1 <= delay < forget <= n
     * @param: n
     * @param: delay
     * @param: forget
     * @return int
     * @author marks
     * @CreateDate: 2026/06/10 15:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        int result;
        result = method_01(n, delay, forget);
        return result;
    }

    /**
     * @Description:
     * E1: 输入：n = 6, delay = 2, forget = 4
     * 1. 差分数组, 假设第 i 天有 x 个人知道秘密, 需要在 i + delay 天后才能分享秘密, 并且会在第 i + forget 天后忘记秘密
     * 2. 知道秘密和能分享秘密的人需要区分开来, 那么diff 数组应该存储能分享秘密的人
     * 3. 第 day[0] = 1, day[1] = 1, day[2] = 2, day[3] = 3, day[4] = 3, day[5] = 5
     * @param: n
     * @param: delay
     * @param: forget
     * @return int
     * @author marks
     * @CreateDate: 2026/06/10 15:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int delay, int forget) {
        final int MOD = 1_000_000_007;
        int[] diff = new int[n + 1];
        diff[1] = 1;
        diff[2] = -1;
        int known = 0;
        long ans = 0;

        for (int i = 1; i <= n; i++) {
            // 加上 diff[i] 后，known 表示恰好在第 i 天得知秘密的人数
            known = (known + diff[i]) % MOD;
            // 统计在第 n 天没有忘记秘密的人数
            if (i >= n - forget + 1) {
                ans += known;
            }
            // 恰好在第 i 天得知秘密的人，会在第 [i+delay, i+forget-1] 天分享秘密
            if (i + delay <= n) {
                diff[i + delay] = (diff[i + delay] + known) % MOD;
            }
            if (i + forget <= n) {
                diff[i + forget] = (diff[i + forget] - known + MOD) % MOD; // +MOD 保证结果非负
            }
        }

        return (int) (ans % MOD);
    }

}
