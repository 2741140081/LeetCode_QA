package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_99 {

    /**
     * @Description:
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：一个机器人每次只能向下或者向右移动一步。
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minPathSum(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
     * AC: 2ms/44.59MB
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        // 初始化第一行和第一列
        dp[0][0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }

}
