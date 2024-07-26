package com.marks.leetcode.gridDP;

import java.util.Arrays;

/**
 * <p>项目名称: 扣分后的最大得分 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/26 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1937 {
    private int m;
    private int n;
    /**
     * @Description: [功能描述]
     * @param points
     * @return long
     * @author marks
     * @CreateDate: 2024/7/26 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxPoints(int[][] points) {
        long result = 0;
        result = method_01(points);
        return result;
    }

    /**
     * @Description: [具体实现]
     * 1.我想到的剪枝的方式是: 如果一行中的最大值减去 Max |c1 - c2| = n - 1;
     * int temp = point[i][j]
     * temp - n + 1 >= any(point[i][any])
     *
     * E1:
     * points = [[1,2,3],[1,5,1],[3,1,1]]
     * 1    2   3
     * 1    5   1
     * 3    1   1
     * max = 2
     * max = 3
     *
     * 2.
     * 由特殊到一般
     * max(V1+ Vm-1) = min(C1 + Cm-1)
     * 感觉可以直接dp来试试
     * 使用两个一维数组替代二维数组dp
     * dp[i][j] = dp[i-1][k] + abs(j-k) + points[i][j]
     * if j >= k
     * dp[i][j] = dp[i-1][k] + j - k + points[i][j]
     * if j < k
     * dp[i][j] = dp[i-1][k] - j + k + points[i][j]
     * @param points
     * @return long
     * @author marks
     * @CreateDate: 2024/7/26 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] points) {
        m = points.length;
        n = points[0].length;
        if (m == 1) {
            return Arrays.stream(points[0]).max().getAsInt();
        }

        long[] pre = new long[n]; // 记录上一行的值

        for (int i = 0; i < m; i++) {
            long[] cur = new long[n];//记录这一行的dp值
            long best = Long.MIN_VALUE;
            // 正序遍历
            // 假设当前的j = 3, 那么best 就是在0 1 2 3 中需要Math.max(pre[j] + j)的最大值, 假设找到的最大值 j = 1
            // 如此设定正好满足 k <= j的设定, 最大值只会在j的左边出现
            for (int j = 0; j < n; j++) {
                best = Math.max(best, pre[j] + j); // Math.max(dp[j] + j)
                cur[j] = Math.max(cur[j], points[i][j] + best - j);
            }

            best = Long.MIN_VALUE;
            // 倒序遍历
            for (int j = n - 1; j >= 0; j--) {
                best = Math.max(best, pre[j] - j); // Math.max(dp[j] - j)
                cur[j] = Math.max(cur[j], points[i][j] + best + j);
            }
            pre = cur;
        }
        return Arrays.stream(pre).max().getAsLong();
    }
}
