package com.marks.leetcode.gridDP;

import java.util.Arrays;

/**
 * <p>项目名称: 摘樱桃 II </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/26 15:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1463 {
    private int m;
    private int n;
    public int cherryPickup(int[][] grid) {
        int result = 0;
        result = method_01(grid);
        return result;

    }

    /**
     * @Description: [具体算法实现]
     * 1. 依据提示: 定义int[][][] dp = new int[m][n][2]
     * 其中dp[i][j][0]表示机器人1最大 cherry数目
     * 其中dp[i][j][1]表示机器人2最大 cherry数目
     * 定义初始值为
     * dp[0][0][0] = grid[0][0]
     * dp[0][n-1][1] = grid[0][n-1]
     * 2. 我先让机器人1先进行采集, 然后机器人2再去采集, 防止出现机器人1/2同时到达1个方格的情况发生
     * 当机器人采集完成后, 直接将grid[i][j] 的值置为0, 即grid[i][j] = 0, 有效防止采集同一个方格情况
     * 状态转移方程
     * dp[i][j][0] = Math.max(dp[i-1, i, i+1][j-1] + grid[i][j])
     * 3. 遇到的问题, 还是那个判断是否会踩在同一个格子情况, 感觉需要更细致的判断
     * 应该从整体看待
     * i, j
     * i, k
     * f[i][j][k] = Max(
     * f[i-1][j-1][k-1],
     * f[i-1][j-1][k],
     * f[i-1][j-1][k+1],
     * f[i-1][j][k-1],
     * f[i-1][j][k],
     * f[i-1][j][k+1],
     * f[i-1][j+1][k-1],
     * f[i-1][j+1][k],
     * f[i-1][j+1][k+1]
     * ) + grid[i][j] + grid[i][k]
     * j != k
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/7/26 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int[][][] dp = new int[m + 1][n + 2][n + 2];

        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < n && j < (i + 1); j++) {
                for (int k = Math.max(j + 1, n - 1 - i); k < n; k++) {
                    dp[i][j + 1][k + 1] = maxValue(
                            dp[i+1][j][k], dp[i + 1][j][k+1], dp[i + 1][j][k+2],
                            dp[i+1][j+1][k], dp[i + 1][j+1][k+1], dp[i + 1][j+1][k+2],
                            dp[i+1][j+2][k], dp[i + 1][j+2][k+1], dp[i + 1][j+2][k+2]) + grid[i][j] + grid[i][k];
                }
            }
        }
        return 0;
    }

    private int maxValue(int x, int... values) {
        for (int val : values) {
            x = Math.max(x, val);
        }
        return x;
    }
}
