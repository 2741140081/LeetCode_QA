package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_098 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/5 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_098 {

    /**
     * @Description:
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     * @param: m
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/05 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int uniquePaths(int m, int n) {
        int result;
        result = method_01(m, n);
        return result;
    }


    /**
     * @Description:
     * dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
     * AC: 1ms/41.44MB
     * @param: m
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/05 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m-1][n-1];
    }

}
