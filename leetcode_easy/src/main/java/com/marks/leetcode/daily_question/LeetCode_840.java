package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_840 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/30 10:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_840 {

    /**
     * @Description:
     * 3 x 3 的幻方是一个填充有 从 1 到 9  的不同数字的 3 x 3 矩阵，其中每行，每列以及两条对角线上的各数之和都相等。
     * 给定一个由整数组成的row x col 的 grid，其中有多少个 3 × 3 的 “幻方” 子矩阵？
     * 注意：虽然幻方只能包含 1 到 9 的数字，但 grid 可以包含最多15的数字
     * tips:
     * row == grid.length
     * col == grid[i].length
     * 1 <= row, col <= 10
     * 0 <= grid[i][j] <= 15
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/12/30 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numMagicSquaresInside(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 感觉没什么很好的思路, 直接暴力遍历
     * AC: 0ms/42.55MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2025/12/30 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0; // 统计的幻方数量
        for (int i = 0; i < m - 2; i++) {
            for (int j = 0; j < n - 2; j++) {
                ans += isMagic(grid, i, j) ? 1 : 0;
            }
        }
        return ans;
    }

    private boolean isMagic(int[][] grid, int i, int j) {
        // 1. 获取幻方
        int[] frequency = new int[16]; // 记录是否重复
        for (int x = i; x < i + 3; x++) {
            for (int y = j; y < j + 3; y++) {
                int num = grid[x][y];
                if (num < 1 || num > 9 || frequency[num] > 0) {
                    return false;
                }
                frequency[num]++;
            }
        }
        // 判断是否幻方, 即3行, 3列, 2对角线
        int sum = grid[i][j] + grid[i][j + 1] + grid[i][j + 2];
        for (int x = i; x < i + 3; x++) {
            int rowSum = grid[x][j] + grid[x][j + 1] + grid[x][j + 2];
            if (rowSum != sum) {
                return false;
            }
        }
        for (int y = j; y < j + 3; y++) {
            int colSum = grid[i][y] + grid[i + 1][y] + grid[i + 2][y];
            if (colSum != sum) {
                return false;
            }
        }
        int leftDiagonalSum = grid[i][j] + grid[i + 1][j + 1] + grid[i + 2][j + 2];
        if (leftDiagonalSum != sum) {
            return false;
        }
        int rightDiagonalSum = grid[i][j + 2] + grid[i + 1][j + 1] + grid[i + 2][j];
        return rightDiagonalSum == sum;
    }

}
