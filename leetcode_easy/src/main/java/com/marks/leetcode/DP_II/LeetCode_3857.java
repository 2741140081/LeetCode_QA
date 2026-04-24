package com.marks.leetcode.DP_II;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3857 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/24 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3857 {

    /**
     * @Description:
     * 给你一个整数 n。
     * 在一次操作中，你可以将整数 x 拆分为两个正整数 a 和 b，使得 a + b = x。
     * 此操作的代价是 a * b。
     * 返回将整数 n 拆分为 n 个 1 所需的最小总代价。
     *
     * tips:
     * 1 <= n <= 500
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/24 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int n) {
        int result;
        result = method_01(n);
        result = method_02(n);
        result = method_03(n);
        return result;
    }

    /**
     * @Description:
     * f(n) = f(n - 1) + n - 1; 可以推导得到 f(n) = n - 1 + n - 2 + .... + 0;
     * f(n) = n * (n - 1) / 2
     * AC: 0ms/41.7MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/24 10:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int n) {
        return n * (n - 1) / 2;
    }

    /**
     * @Description:
     * AC: 1ms/42MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/24 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n) {
        int prev = 0;
        int curr;
        for (int i = 1; i <= n; i++) {
            curr = prev + i - 1;
            prev = curr;
        }

        return prev;
    }

    /**
     * @Description:
     * 1. 先验证是平分得到的结果小还是直接分离出1的结果更小, 使用 n = 10 测试两种情况
     * 10 = (5) + (5) + 5 * 5 = (2) + (3) + 2 * 3 + (2) + (3) + 2 * 3 + 5 * 5 = 25 + 12 + 2 * (2) + 2 * (3)
     * 2. 在推导的过程中看到一些情况, f(1) = 0, f(2) = 1, f(3) = f(1) + f(2) + 1 * 2 = 3, f(4) = f(2) + f(2) + 2 * 2 = f(1) + f(3) + 1 * 3 = 6,
     * f(5) = f(2) + f(3) + 2 * 3 = 10;
     * f(6) = f(3) + f(3) + 3 * 3 = 15
     * f(6) = f(2) + f(4) + 2 * 4 = 15
     * f(6) = f(1) + f(5) + 1 * 5 = 15
     * 3. 经过推导得到 f(n) = f(n - 1) + n - 1; 并且 f(1) = 0;
     * AC: 1ms/42MB
     * 4. 由于dp[i] 只与dp[i - 1] 有关, 所以可以使用两个变量来存储, @See method_02
     * 5. 然后得到一个等差数列相加 (0 + n - 1) * n / 2 => n * (n - 1) / 2
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/24 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + i - 1;
        }
        return dp[n];
    }
}
