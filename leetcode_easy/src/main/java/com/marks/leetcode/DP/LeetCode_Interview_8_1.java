package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/16 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_8_1 {

    /**
     * @Description:
     * 三步问题。有个小孩正在上楼梯，楼梯有 n 阶台阶，小孩一次可以上 1 阶、2 阶或 3 阶。
     * 实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模 1000000007。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int waysToStep(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * dp[i] = dp[i-1] + dp[i-2] + dp[i-3]
     * AC: 23ms/44.55MB
     * 对于这种题, 需要注意以下两点
     * 1. 特殊情况特殊处理
     * 2. 防止溢出
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        final int MOD = 1000000007;
        // 处理特殊情况
        if (n <= 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = ((dp[i - 1] + dp[i - 2]) % MOD + dp[i - 3]) % MOD;
        }
        return dp[n];
    }

}
