package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1444 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/21 15:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1444 {

    /**
     * @Description:
     * 给你一个 rows x cols 大小的矩形披萨和一个整数 k ，
     * 矩形包含两种字符： 'A' （表示苹果）和 '.' （表示空白格子）。
     * 你需要切披萨 k-1 次，得到 k 块披萨并送给别人。
     * 切披萨的每一刀，先要选择是向垂直还是水平方向切，再在矩形的边界上选一个切的位置，将披萨一分为二。
     * 如果垂直地切披萨，那么需要把左边的部分送给一个人，如果水平地切，那么需要把上面的部分送给一个人。
     * 在切完最后一刀后，需要把剩下来的一块送给最后一个人。
     * 请你返回确保每一块披萨包含 至少 一个苹果的切披萨方案数。
     * 由于答案可能是个很大的数字，请你返回它对 10^9 + 7 取余的结果。
     *
     * tips:
     * 1 <= rows, cols <= 50
     * rows == pizza.length
     * cols == pizza[i].length
     * 1 <= k <= 10
     * pizza 只包含字符 'A' 和 '.' 。
     * @param: pizza
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/21 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int ways(String[] pizza, int k) {
        int result;
        result = method_01(pizza, k);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划, dp[i][j][k]: 表示剩余的披萨坐标左上角位置(i, j), 并且已经执行了 k 次切割操作, dp[0][0][0] = 1
     * 2. dp[i][j][k], 可以有垂直切割或者水平切割得到
     * 2.1 如果由垂直切割得到, 那么前一个切割点的坐标是(i, [0 ~ j - 1]),
     * 所以 dp[i][j][k] = dp[i][j][k] + dp[i][x][k - 1], x 的取值范围是 0 ~ i - 1
     * 2.2 如果由水平切割得到, 同理 dp[i][j] = dp[i][j][k] + dp[x][j][k - 1], x 的取值范围是 0 ~ i - 1
     * 3. 下一个问题是前一次切割是否有效, 即所切割的部分是否包含至少一个 'A', 可以使用二维前缀和来查询区间 (x1, y1) ~ (x2, y2) 的区间和 sum.
     * sum > 0 则表示切割是有效的
     * 4. 初始化: 对第一次切割进行初始化, 由于第一次切割只能选择(0,j) 或者 (i, 0) 点分别作为垂直切割和水平切割的切割点, 并且方案数是1
     * AC: 15ms/42.9MB
     * @param: pizza
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/21 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] prefix;
    private int MOD = (int) 1e9 + 7;

    private int method_01(String[] pizza, int k) {
        int m = pizza.length, n = pizza[0].length();
        // 先构建一个 grid 矩阵, 用于存储二维前缀和
        int cnt = 0;
        int[][] grid = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pizza[i].charAt(j) == 'A') {
                    grid[i][j] = 1;
                    cnt++;
                }
            }
        }
        if (cnt < k) {
            return 0;
        } else if (k == 1) {
            return 1;
        }
        prefix = new int[m + 1][n + 1];
        buildPrefix(grid);
        long[][][] dp = new long[m][n][k];
        // 执行第一次切割, 分垂直切割和水平切割
        for (int i = 1; i < n; i++) {
            // (0, i) 作为切割点
            int sum1 = queryRangeSum(0, 0, m - 1, i - 1);
            int sum2 = queryRangeSum(0, i, m - 1, n - 1);
            if (sum1 > 0 && sum2 > 0)
                dp[0][i][1] = 1;
        }
        // 水平切割
        for (int i = 1; i < m; i++) {
            // (i, 0) 作为切割点
            int sum1 = queryRangeSum(0, 0, i - 1, n - 1);
            int sum2 = queryRangeSum(i, 0, m - 1, n - 1);
            if (sum1 > 0 && sum2 > 0)
                dp[i][0][1] = 1;
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int p = 2; p < k; p++) {
                    // 垂直切割, i 保持不变, j 变化
                    for (int y = 0; y <= j; y++) {
                        // (i, j) 作为剩余的披萨的左上角落顶点
                        int sum1 = queryRangeSum(i, y, m - 1, j - 1);
                        int sum2 = queryRangeSum(i, j, m - 1, n - 1);
                        if (sum1 > 0 && sum2 > 0) {
                            dp[i][j][p] = (dp[i][j][p] + dp[i][y][p - 1]) % MOD;
                        }
                    }

                    // 水平切割
                    for (int x = 0; x <= i; x++) {
                        int sum1 = queryRangeSum(x, j, i - 1, n - 1);
                        int sum2 = queryRangeSum(i, j, m - 1, n - 1);
                        if (sum1 > 0 && sum2 > 0)
                            dp[i][j][p] = (dp[i][j][p] + dp[x][j][p - 1]) % MOD;
                    }
                }
            }
        }
        long ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = (ans + dp[i][j][k - 1]) % MOD;
            }
        }

        return (int) (ans % MOD);
    }

    private void buildPrefix(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = grid[i - 1][j - 1] + prefix[i-1][j] + prefix[i][j-1] - prefix[i-1][j-1];
            }
        }
    }

    private int queryRangeSum(int x1, int y1, int x2, int y2) {
        return prefix[x2 + 1][y2 + 1] - prefix[x1][y2 + 1] - prefix[x2 + 1][y1] + prefix[x1][y1];
    }

}
