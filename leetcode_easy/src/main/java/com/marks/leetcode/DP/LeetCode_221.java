package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_221 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/12 9:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_221 {

    /**
     * @Description:
     * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
     *
     * tips:
     * m == matrix.length
     * n == matrix[i].length
     * 1 <= m, n <= 300
     * matrix[i][j] 为 '0' 或 '1'
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2025/11/12 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximalSquare(char[][] matrix) {
        int result;
        result = method_01(matrix);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：matrix = [
     * ["1","0","1","0","0"],
     * ["1","0","1","1","1"],
     * ["1","1","1","1","1"],
     * ["1","0","0","1","0"]]
     * 输出：4
     * 1. dp[i][j] = Math.min(dp[i-1][j-1], dp[i - 1][j], dp[i][j - 1]) + 1
     * AC: 7ms/69.59MB
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2025/11/12 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                    }
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }

        return ans * ans;
    }

}
