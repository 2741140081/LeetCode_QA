package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1277 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/5 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1277 {

    /**
     * @Description:
     * 给你一个 m * n 的矩阵，矩阵中的元素不是 0 就是 1，请你统计并返回其中完全由 1 组成的 正方形 子矩阵的个数。
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countSquares(int[][] matrix) {
        int result;
        result = method_01(matrix);
        return result;
    }

    /**
     * @Description: [方法描述]
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    ans += dp[i][j];
                }
            }
        }
        return ans;
    }

}
