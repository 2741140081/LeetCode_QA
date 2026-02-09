package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_127 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_127 {

    /**
     * @Description:
     * 今天的有氧运动训练内容是在一个长条形的平台上跳跃。
     * 平台有 num 个小格子，每次可以选择跳 一个格子 或者 两个格子。请返回在训练过程中，学员们共有多少种不同的跳跃方式。
     * 结果可能过大，因此结果需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     * @param: num
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int trainWays(int num) {
        int result;
        result = method_01(num);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/41.1MB
     * @param: num
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int num) {
        final int MOD = 1000000007;
        if (num <= 1) {
            return num;
        }
        int[] dp = new int[num + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= num; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
        }

        return dp[num];
    }

}
