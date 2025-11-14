package com.marks.leetcode.DP;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3148 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/14 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3148 {

    /**
     * @Description:
     * 给你一个由 正整数 组成、大小为 m x n 的矩阵 grid。
     * 你可以从矩阵中的任一单元格移动到另一个位于正下方或正右侧的任意单元格（不必相邻）。
     * 从值为 c1 的单元格移动到值为 c2 的单元格的得分为 c2 - c1 。
     *
     * 你可以从 任一 单元格开始，并且必须至少移动一次。
     *
     * 返回你能得到的 最大 总得分。
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/11/14 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScore(List<List<Integer>> grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 使用动态规划解决
     * 2. 当我们处于 (i, j) 这个位置的时候，可以向右移动或者向下移动, 也可以不进行移动(前提是已经移动了一次)
     * 3. 得到最大的得分, 得分只与起始点和终点有关 score = grid[i_end][j_end] - grid[i_start][j_start]
     * AC: 17ms/107.5MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/11/14 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<List<Integer>> grid) {
        int ans = Integer.MIN_VALUE;
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int curr = grid.get(i).get(j);
                if (i == 0 && j == 0) {
                    dp[i][j] = curr;
                } else if (i == 0) {
                    ans = Math.max(ans, curr - dp[i][j - 1]);
                    dp[i][j] = Math.min(dp[i][j - 1], curr);
                } else if (j == 0) {
                    ans = Math.max(ans, curr - dp[i - 1][j]);
                    dp[i][j] = Math.min(dp[i - 1][j], curr);
                } else {
                    int min = Math.min(dp[i - 1][j], dp[i][j - 1]);
                    ans = Math.max(ans, curr - min);
                    dp[i][j] = Math.min(curr, min);
                }
            }
        }
        
        return ans;
    }

}
