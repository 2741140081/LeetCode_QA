package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3429 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/13 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3429 {

    /**
     * @Description:
     * 给你一个 偶数 整数 n，表示沿直线排列的房屋数量，以及一个大小为 n x 3 的二维数组 cost，其中 cost[i][j] 表示将第 i 个房屋涂成颜色 j + 1 的成本。
     *
     * 如果房屋满足以下条件，则认为它们看起来 漂亮：
     *
     * 不存在 两个 涂成相同颜色的相邻房屋。
     * 距离行两端 等距 的房屋不能涂成相同的颜色。例如，如果 n = 6，则位置 (0, 5)、(1, 4) 和 (2, 3) 的房屋被认为是等距的。
     * 返回使房屋看起来 漂亮 的 最低 涂色成本。
     *
     * tips:
     * 2 <= n <= 10^5
     * n 是偶数。
     * cost.length == n
     * cost[i].length == 3
     * 0 <= cost[i][j] <= 10^5
     * @param: n
     * @param: cost
     * @return long
     * @author marks
     * @CreateDate: 2025/11/13 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minCost(int n, int[][] cost) {
        long result;
        result = method_01(n, cost);
        return result;
    }

    /**
     * @Description:
     * 1. dp[i - 1][0], dp[i - 1][1], dp[i - 1][2]; dp[i][0], dp[i][1], dp[i][2];
     * 2. dp[n - i][0], dp[n - i][1], dp[n - i][2]; dp[n - 1 - i][0], dp[n - 1 - i][1], dp[n - 1 - i][2];
     * i_0: i-1: 1 和 2, n - 1 -i: 1 和 2, n - i: 可以取 0, 1 和 2;
     * dp[i][0] =  cost[i][0] + Math.min(sum1, sum2);
     *
     * sum1 = dp[i - 1][1] + Math.min(cost[n - 1 - i][1] + Math.min(dp[n - i][0], dp[n - i][2]), cost[n - 1 - i][2] + dp[n - i][0])
     * sum2 = dp[i - 1][2] + Math.min(cost[n - 1 - i][2] + Math.min(dp[n - i][0], dp[n - i][1]), cost[n - 1 - i][1] + dp[n - i][0])
     * @param: n
     * @param: cost
     * @return long
     * @author marks
     * @CreateDate: 2025/11/13 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int[][] cost) {

        return 0;
    }

}
