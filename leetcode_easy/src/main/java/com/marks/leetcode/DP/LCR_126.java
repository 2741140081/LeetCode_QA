package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_126 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_126 {

    /**
     * @Description:
     * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     * F(0) = 0，F(1) = 1
     * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     * 给定 n ，请计算 F(n) 。
     * 答案需要取模 1e9+7(1000000007) ，如计算初始结果为：1000000008，请返回 1。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int fib(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/41.63MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        final int MOD = 1000000007;
        if (n <= 1) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;
        }

        return dp[n];
    }

}
