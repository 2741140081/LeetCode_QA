package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1931 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/18 11:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1931 {

    private static final int MOD = 1000000007;
    /**
     * @Description:
     * 给你两个整数 m 和 n 。构造一个 m x n 的网格，其中每个单元格最开始是白色。
     * 请你用 红、绿、蓝 三种颜色为每个单元格涂色。所有单元格都需要被涂色。
     * 涂色方案需要满足：不存在相邻两个单元格颜色相同的情况 。返回网格涂色的方法数。因为答案可能非常大， 返回 对 10^9 + 7 取余 的结果。
     * @param: m
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/18 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int colorTheGrid(int m, int n) {
        int result;
//        result = method_01(m, n);
        result = method_02(m, n);
        return result;
    }

    /**
     * @Description:
     * 1. 主要是动态转移方程： dp[i][j][k], k 为 0,1,2 颜色
     * 2. 预处理首行和首列, dp[i][j][k] 由 dp[i - 1][j][x](up) 和 dp[i][j - 1][x](left) 转换得到
     * if x_up != k && x_left != k, dp[i][j][k] += (dp[i - 1][j][x_up] * dp[i - 1][j][x_left])
     * @param: m
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int m, int n) {
        long[][][] dp = new long[m][n][3];
        // 处理首行
        dp[0][0][0] = 1;
        dp[0][0][1] = 1;
        dp[0][0][2] = 1;
        for (int i = 1; i < n; i++) {
            for (int k = 0; k < 3; k++) {
                dp[0][i][k] = (dp[0][i - 1][(k + 1) % 3] + dp[0][i - 1][(k + 2) % 3]) % MOD;
            }
        }
        // 处理首列
        for (int i = 1; i < m; i++) {
            for (int k = 0; k < 3; k++) {
                dp[i][0][k] = (dp[i - 1][0][(k + 1) % 3] + dp[i - 1][0][(k + 2) % 3]) % MOD;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                for (int k = 0; k < 3; k++) {
                    long sum = 0;
                    for (int z = 0; z < 3; z++) {
                        if (z == k) {
                            continue;
                        }
                        for (int l = 0; l < 3; l++) {
                            if (l == k) {
                                continue;
                            }
                            sum = (sum + dp[i - 1][j][z] * dp[i][j - 1][l]) % MOD;
                        }
                    }
                    dp[i][j][k] = sum % MOD;
                }
            }
        }
        long ans = 0;
        for (int i = 0; i < 3; i++) {
            ans = (ans + dp[m - 1][n - 1][i]) % MOD;
        }

        return (int) ans % MOD;
    }

    /**
     * @Description:
     * 1. dp[i][j][k] 表示第 i 行，第 j 列，颜色为 k 的方案数, k 为 0,1,2 分别表示三种颜色
     * 2. 状态转移方程：首行: dp[i][j][0] = dp[i-1][j][1] + dp[i-1][j][2]; dp[i][j][1] =  dp[i-1][j][0] + dp[i-1][j][2]
     * dp[i][j][k] = sum(dp[i][j - 1][z != k])
     * 3. 如果不是首行或者首列, 则状态转移方程为： dp[i][j][k] = sum(dp[i - 1][j - 1][z != k]) + sum(dp[i - 1][j][z != k])
     * need todo 存在问题, 状态转移方程不对
     * @param: m
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/18 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int m, int n) {
        long[][][] dp = new long[m][n][3];
        // 处理首行
        dp[0][0][0] = 1;
        dp[0][0][1] = 1;
        dp[0][0][2] = 1;
        for (int i = 1; i < n; i++) {
            for (int k = 0; k < 3; k++) {
                dp[0][i][k] = (dp[0][i - 1][(k + 1) % 3] + dp[0][i - 1][(k + 2) % 3]) % MOD;
            }
        }
        // 处理首列
        for (int i = 1; i < m; i++) {
            for (int k = 0; k < 3; k++) {
                dp[i][0][k] = (dp[i - 1][0][(k + 1) % 3] + dp[i - 1][0][(k + 2) % 3]) % MOD;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                for (int k = 0; k < 3; k++) {
                    long sum = 0;
                    for (int z = 0; z < 3; z++) {
                        for (int l = 0; l < 3; l++) {
                            if (z != k && l != k) {
                                sum = (sum + dp[i - 1][j][z] + dp[i][j - 1][l]) % MOD;
                            }
                        }
                    }
                    dp[i][j][k] = sum % MOD;
                }
            }
        }
        long ans = 0;
        for (int i = 0; i < 3; i++) {
            ans = (ans + dp[m - 1][n - 1][i]) % MOD;
        }
        return (int) ans % MOD;
    }
}
