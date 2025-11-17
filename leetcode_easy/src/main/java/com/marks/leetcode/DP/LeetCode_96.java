package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_96 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/17 11:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_96 {

    /**
     * @Description:
     * 给你一个整数 n ，求恰由 n 个节点组成且节点值从 1 到 n 互不相同的 二叉搜索树 有多少种？
     * 返回满足题意的二叉搜索树的种数。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/17 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numTrees(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. n = 1, res = 1; n = 2, res = 2; n = 3, res = 2 + 1 + 2 = 5;
     * 2. n = 4, res = 2 * dp[3] + 2 * dp[2] = 10 + 4 = 14;
     * 3. n = 5, res = 2 * dp[4] + 2 * dp[3] + dp[2] * dp[2] = 28 + 10 + 4 = 42
     * 4. n = 6, res = dp[0] * dp[5] + dp[1] * dp[4] + dp[2] * dp[3] * 2 =
     * AC: 0ms/41.24MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/17 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }


}
