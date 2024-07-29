package com.marks.leetcode.gridDP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/29 11:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_741 {
    private int n;

    /**
     * @Description: [功能描述]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/7/29 11:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     * tips:
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 50
     * grid[i][j] 为 -1、0 或 1
     * grid[0][0] != -1
     * grid[n - 1][n - 1] != -1
     */
    public int cherryPickup(int[][] grid) {
        int result = 0;
//        result = method_01(grid);
        result = method_02(grid);
//        result = method_03(grid);
        return result;
    }

    /**
     * @Description: [method_01 失败, 第一趟不是找最大值, 会错失部分结果。
     * 我觉得可以模仿摘樱桃 II, 定义两个机器人, 分别从(0, 0) 和 (n-1, n-1)出发摘樱桃,
     * 可以用dfs来试试, 开始method_02
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/7/29 11:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        n = grid[0].length;
        int[][] dp = new int[n][n];
        // 初始化
        dp[0][0] = grid[0][0];
        grid[0][0] = 0;
        for (int i = 1; i < n; i++) {
            if (grid[0][i] == -1 || dp[0][i-1] == -1) {
                dp[0][i] = -1;
            }else {
                dp[0][i] = dp[0][i - 1] + grid[0][i];
            }
        }
        for (int i = 1; i < n; i++) {
            if (grid[i][0] == -1 || dp[i-1][0] == -1) {
                dp[i][0] = -1;
            }else {
                dp[i][0] = dp[i - 1][0] + grid[i][0];
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] == -1 || (dp[i][j-1] == -1 && dp[i-1][j] == -1)) {
                    dp[i][j] = -1;
                }else {
                    if (dp[i][j-1] > dp[i-1][j]) {
                        grid[i][j-1] = 0;
                        dp[i][j] = dp[i][j-1] + grid[i][j];
                    }else {
                        grid[i-1][j] = 0;
                        dp[i][j] = dp[i-1][j] + grid[i][j];
                    }
                }
            }
        }
        grid[n-1][n-1] = 0;
        int res = dp[n-1][n-1];

        // 先添加一个判断, 是否可达
        // 如果不可达直接返回 0
        if (res == -1) {
            return 0;
        }

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], 0);
        }

        for (int i = 1; i < n; i++) {
            if (grid[n-1][i] == -1 || dp[n-1][i-1] == -1) {
                dp[n-1][i] = -1;
            }else {
                dp[n-1][i] = dp[n-1][i - 1] + grid[n-1][i];
            }
        }

        for (int i = 1; i < n; i++) {
            if (grid[i][n-1] == -1 || dp[i-1][n-1] == -1) {
                dp[i][n-1] = -1;
            }else {
                dp[i][n-1] = dp[i - 1][n-1] + grid[i][n-1];
            }
        }
        for (int i = n-2; i >= 0; i--) {
            for (int j = n-2; j >= 0; j--) {
                if (grid[i][j] == -1 || (grid[i][j+1] == -1 && grid[i+1][j] == -1)) {
                    dp[i][j] = -1;
                }else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j+1]) + grid[i][j];
                }
            }
        }
        res += dp[0][0];
        return res;
    }

    /**
     * 将该问题看做是两个机器人从(0, 0) 出发到 (n-1, n-1) 之和的最大值
     * 我是否可以用四维数组来表示最大值
     * dp[i][j] [x][y]
     * @param grid
     * @return
     */
    private int method_02(int[][] grid) {
        n = grid.length;
        int[][][] dp = new int[2*n - 1][n][n];
        for (int i = 0; i < 2 * n - 1; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE);
            }
        }
        dp[0][0][0] = grid[0][0];
        for (int k = 1; k < 2*n - 1; k++) {
            for (int i = Math.max(0, k- (n-1)); i <= Math.min(k, n-1); i++) {
                int j = k - i;
                if (grid[i][j] == -1) {
                    continue;
                }
                for (int x = i; x <= Math.min(k, n-1); x++) {
                    int y = k - x;
                    if (grid[x][y] == -1) {
                        continue;
                    }
                    // 都向右
                    int res = dp[k-1][i][x];
                    // 向下 向右
                    if (i > 0) {
                        res = Math.max(res, dp[k-1][i-1][x]);
                    }
                    // 向右 向下
                    if (x > 0) {
                        res = Math.max(res, dp[k-1][i][x-1]);
                    }
                    // 都向下
                    if (i > 0 && x > 0) {
                        res = Math.max(res, dp[k-1][i-1][x-1]);
                    }

                    res += grid[i][j];
                    if (i != x) {
                        res += grid[x][y];
                    }
                    dp[k][i][x] = res;
                }
            }
        }
        return Math.max(0, dp[2*n - 2][n - 1][n - 1]);
    }

    /**
     * 四维数字试试看
     * @param grid
     * @return
     */
    private int method_03(int[][] grid) {
        n = grid.length;
        int[][][][] dp = new int[n][n][n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int x = 0; x < n; x++) {
                    Arrays.fill(dp[i][j][x], Integer.MIN_VALUE);
                }
            }
        }

        dp[0][0][0][0] = grid[0][0];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == -1) {
                    continue;
                }
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < n; y++) {
                        if (grid[x][y] == -1) {
                            continue;
                        }

                        int res = Integer.MIN_VALUE;
                        // 都向右
                        if (j > 0 && y > 0) {
                            res = Math.max(res, dp[i][j-1][x][y-1]);
                        }
                        // 都向下
                        if (i > 0 && x > 0) {
                            res = Math.max(res, dp[i-1][j][x-1][y]);
                        }
                        if (i > 0 && y > 0) {
                            res = Math.max(res, dp[i-1][j][x][y-1]);
                        }
                        if (j > 0 && x > 0) {
                            res = Math.max(res, dp[i][j-1][x-1][y]);
                        }
                        if (i==x && j == y) {
                            dp[i][j][x][y] = res + grid[i][j];
                        }else {
                            dp[i][j][x][y] = res + grid[i][j] + grid[x][y];
                        }
                    }
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
