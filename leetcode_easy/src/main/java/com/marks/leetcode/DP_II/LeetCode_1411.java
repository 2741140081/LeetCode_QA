package com.marks.leetcode.DP_II;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1411 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/17 15:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1411 {

    /**
     * @Description:
     * 你有一个 n x 3 的网格图 grid ，你需要用 红，黄，绿 三种颜色之一给每一个格子上色，
     * 且确保相邻格子颜色不同（也就是有相同水平边或者垂直边的格子颜色不同）。
     *
     * 给你网格图的行数 n 。
     * 请你返回给 grid 涂色的方案数。由于答案可能会非常大，请你返回答案对 10^9 + 7 取余的结果。
     *
     * tips:
     * n == grid.length
     * grid[i].length == 3
     * 1 <= n <= 5000
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 15:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int numOfWays(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划
     * 2. int[][][] dp[i][j][k] 表示第 i 行，第 j 列，第 k 种颜色的方案数
     * 3. 状态转移方程: 可以从上或者左侧进行转移
     * 左侧转移方程: dp[i][j][k] = (dp[i][j][k] + dp[i][j - 1][x]) % MOD; 其中 x != k
     * 4. 完全不能这样写
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 15:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {

        return 0;
    }

}
