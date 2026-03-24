package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2906 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/24 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2906 {

    /**
     * @Description:
     * 给你一个下标从 0 开始、大小为 n * m 的二维整数矩阵 grid ，
     * 定义一个下标从 0 开始、大小为 n * m 的的二维矩阵 p。如果满足以下条件，则称 p 为 grid 的 乘积矩阵 ：
     * 对于每个元素 p[i][j] ，它的值等于除了 grid[i][j] 外所有元素的乘积。乘积对 12345 取余数。
     * 返回 grid 的乘积矩阵。
     * tips:
     * 1 <= n == grid.length <= 10^5
     * 1 <= m == grid[i].length <= 10^5
     * 2 <= n * m <= 10^5
     * 1 <= grid[i][j] <= 10^9
     * @param: grid
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/03/24 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] constructProductMatrix(int[][] grid) {
        int[][] result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 乘法取余: (a × b) mod m = [(a mod m) × (b mod m)] mod m
     * 1. grid[i][j] > 0 的, 所以不需要考虑0的影响
     * 2. 先通过乘法取余得到乘积结果, 再用乘积结果/grid[i][j] 进行除法取余数, 得到 p[i][j]
     * 3. 看了一下提示, 提示使用前缀和后缀乘积来得到结果, p[i][j] = (prefix[i][j] * suffix[i][j]) % MOD
     * AC: 21ms/90.23MB
     * @param: grid
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/03/24 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] grid) {
        final int MOD = 12345;
        int m = grid.length;
        int n = grid[0].length;
        // 前缀乘积
        int len = m * n + 1;
        long[] prefix = new long[len];
        prefix[0] = 1;
        // 后缀乘积
        long[] suffix = new long[len];
        suffix[len - 1] = 1;
        int index = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefix[index] = (prefix[index - 1] * grid[i][j]) % MOD;
                index++;
            }
        }
        index = len - 2;
        int[][] ans = new int[m][n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                suffix[index] = (suffix[index + 1] * grid[i][j]) % MOD;
                index--;
            }
        }
        index = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = (int) ((prefix[index] * suffix[index + 1]) % MOD);
                index++;
            }
        }

        return ans;
    }

}
