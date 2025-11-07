package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_650 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/5 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_650 {

    /**
     * @Description:
     * 最初记事本上只有一个字符 'A' 。你每次可以对这个记事本进行两种操作：
     *
     * Copy All（复制全部）：复制这个记事本中的所有字符（不允许仅复制部分字符）。
     * Paste（粘贴）：粘贴 上一次 复制的字符。
     * 给你一个数字 n ，你需要使用最少的操作次数，在记事本上输出 恰好 n 个 'A' 。返回能够打印出 n 个 'A' 的最少操作次数。
     *
     * tips:
     * 1 <= n <= 1000
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/05 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSteps(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 假设 n = 80, 40, 20, 10, 5, 4, 3, 2, 1
     * 2. n = 80, int[i][j], i 表示 n, j 表示 base(当前复制的数量)
     * dp[1][1] = 1; dp[2][1] = 2, dp[2][2] = 3; dp[3][1] = dp[2][1] + 1 = 3;
     * dp[4][1] = 4, dp[4][2] = 4, dp[4][4] = 5;
     * dp[5][]
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/05 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int method_01(int n) {
        int[] f = new int[n + 1];
        for (int i = 2; i <= n; ++i) {
            f[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; ++j) {
                if (i % j == 0) {
                    f[i] = Math.min(f[i], f[j] + i / j);
                    f[i] = Math.min(f[i], f[i / j] + j);
                }
            }
        }
        return f[n];
    }

}
