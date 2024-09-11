package com.marks.leetcode.rob;

/**
 * <p>项目名称:  </p>
 * <p>文件名称: 斐波那契数 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/11 17:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_509 {
    /**
     * @Description: [
     * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     *
     * F(0) = 0，F(1) = 1
     * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     * 给定 n ，请计算 F(n)
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 17:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int fib(int n) {
        int result = 0;
        result = method_01(n);
        return result;
    }

    /**
     * @Description: [
     * AC:0ms/39.50MB
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/9/11 17:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        if (n == 0) {
            return 0;
        }else if (n == 1) {
            return 1;
        }
        // n > 1
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
