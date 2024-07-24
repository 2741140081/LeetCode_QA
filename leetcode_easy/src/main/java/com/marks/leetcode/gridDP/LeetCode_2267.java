package com.marks.leetcode.gridDP;

import java.util.ArrayList;

/**
 * <p>项目名称: LeetCode </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/24 17:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2267 {
    private int m;
    private int n;
    /**
     * @Description: [一个括号字符串是一个 非空 且只包含 '(' 和 ')' 的字符串。
     * 如果下面 任意 条件为 真 ，那么这个括号字符串就是 合法的 。
     * 字符串是 () 。
     * 字符串可以表示为 AB（A 连接 B），A 和 B 都是合法括号序列。
     * 字符串可以表示为 (A) ，其中 A 是合法括号序列。
     * 给你一个 m x n 的括号网格图矩阵 grid 。网格图中一个 合法括号路径 是满足以下所有条件的一条路径：
     *
     * 路径开始于左上角格子 (0, 0) 。
     * 路径结束于右下角格子 (m - 1, n - 1) 。
     * 路径每次只会向 下 或者向 右 移动。
     * 路径经过的格子组成的括号字符串是 合法 的。
     * 如果网格图中存在一条 合法括号路径 ，请返回 true ，否则返回 false 。]
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * grid[i][j] 要么是 '(' ，要么是 ')' 。
     *
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/7/24 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasValidPath(char[][] grid) {
        boolean result = false;
        result = method_01(grid);
        return result;
    }

    /**
     * 1    1   1   1   1
     * 1    2   3   4   5
     * 1    3   6   10  15
     * 1    4   10  20  35
     * @Description: [功能描述: 暴力破解, 找出全部可能性]
     * @param grid
     * @return boolean
     * @author marks
     * @CreateDate: 2024/7/24 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int len = m + n - 1;
        // 先判断最外层是否符合条件
        if (grid[0][0] == ')' || grid[m-1][n-1] == '(' || len % 2 != 0) {
            return false;
        }
        String[][] memo = new String[m][n];
        int[][] dp = new int[m][n];
        // 初始化边界
        memo[0][0] = String.valueOf(grid[0][0]);
        dp[0][0] = 1;
        for (int i = 1; i < n; i++) {
            memo[0][i] = memo[0][i - 1] + grid[0][i];
            dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            memo[i][0] = memo[i - 1][0] + grid[i][0];
            dp[i][0] = 1;
        }
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
                for (int k = 0; k < dp[i][j]; k++) {

                }
            }
        }


        return false;
    }
}
