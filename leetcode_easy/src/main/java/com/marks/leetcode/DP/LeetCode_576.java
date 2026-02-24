package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_576 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/11 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_576 {

    /**
     * @Description:
     * 给你一个大小为 m x n 的网格和一个球。球的起始坐标为 [startRow, startColumn] 。
     * 你可以将球移到在四个方向上相邻的单元格内（可以穿过网格边界到达网格之外）。你 最多 可以移动 maxMove 次球。
     *
     * 给你五个整数 m、n、maxMove、startRow 以及 startColumn ，找出并返回可以将球移出边界的路径数量。
     * 因为答案可能非常大，返回对 10^9 + 7 取余 后的结果。
     * tips:
     * 1 <= m, n <= 50
     * 0 <= maxMove <= 50
     * 0 <= startRow < m
     * 0 <= startColumn < n
     * @param: m
     * @param: n
     * @param: maxMove
     * @param: startRow
     * @param: startColumn
     * @return int
     * @author marks
     * @CreateDate: 2026/02/11 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findPaths(int m, int n, int maxMove, int startRow, int startColumn) {
        int result;
        result = method_01(m, n, maxMove, startRow, startColumn);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划, 需要找的是将球踢出边界的路径数量, 当球到达边界, 并且剩余步数大于0, 则该路径是有效路径
     * AC: 12ms/43.11MB
     * @param: m
     * @param: n
     * @param: maxMove
     * @param: startRow
     * @param: startColumn
     * @return int
     * @author marks
     * @CreateDate: 2026/02/11 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int m, int n, int maxMove, int startRow, int startColumn) {
        final int MOD = 1000000007;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int[][][] dp = new int[m][n][maxMove + 1];
        dp[startRow][startColumn][0] = 1;
        int ans = 0;
        for (int i = 1; i <= maxMove; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    for (int[] dir : dirs) {
                        int nextI = j + dir[0];
                        int nextJ = k + dir[1];
                        if (nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n) {
                            dp[nextI][nextJ][i] += dp[j][k][i - 1];
                            dp[nextI][nextJ][i] %= MOD;
                        } else {
                            ans += dp[j][k][i - 1];
                            ans %= MOD;
                        }
                    }
                }
            }
        }

        return ans;
    }

}
