package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3546 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25 11:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3546 {

    /**
     * @Description:
     * 给你一个由正整数组成的 m x n 矩阵 grid。你的任务是判断是否可以通过 一条水平或一条垂直分割线 将矩阵分割成两部分，使得：
     * 分割后形成的每个部分都是 非空 的。
     * 两个部分中所有元素的和 相等 。
     * 如果存在这样的分割，返回 true；否则，返回 false。
     *
     * tips:
     * 1 <= m == grid.length <= 10^5
     * 1 <= n == grid[i].length <= 10^5
     * 2 <= m * n <= 10^5
     * 1 <= grid[i][j] <= 10^5
     * @param: grid
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/25 11:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canPartitionGrid(int[][] grid) {
        boolean result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 分别计算每行的和，每列的和, 然后通过前缀和, 判断是否存在 target = sum / 2; sum是偶数
     * 2. sum 如果是奇数, 则直接返回false, 另外由于 10 ^ 10 的sum 范围, 需要使用 long
     * AC: 5ms/156.91MB
     * @param: grid
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/25 11:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long[] rowSum = new long[m];
        long[] colSum = new long[n];
        long sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowSum[i] += grid[i][j];
                colSum[j] += grid[i][j];
                sum += grid[i][j];
            }
        }
        // 判断 sum 是否是偶数
        if (sum % 2 != 0) {
            return false;
        }
        long target = sum / 2;
        long preSum = 0;
        for (int i = 0; i < m; i++) {
            preSum += rowSum[i];
            if (preSum == target) {
                return true;
            }
        }
        preSum = 0;
        for (int j = 0; j < n; j++) {
            preSum += colSum[j];
            if (preSum == target) {
                return true;
            }
        }

        return false;
    }

}
