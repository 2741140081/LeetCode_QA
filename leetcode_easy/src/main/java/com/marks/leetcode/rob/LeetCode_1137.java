package com.marks.leetcode.rob;

/**
 * <p>项目名称:  </p>
 * <p>文件名称: 泰波那契数 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/11 17:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1137 {
    /**
     * @Description: [
     * 泰波那契序列 Tn 定义如下：
     *
     * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
     *
     * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 17:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int tribonacci(int n) {
        int result = 0;
        result = method_01(n);
        return result;
    }
    /**
     * @Description: [
     * AC:0ms/39.48MB
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 17:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 1;
        // n >= 3
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 3] + dp[i - 2] + dp[i - 1];
        }
        return dp[n];
    }
}
