package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2536 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/14 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2536 {

    /**
     * @Description:
     * 给你一个正整数 n ，表示最初有一个 n x n 、下标从 0 开始的整数矩阵 mat ，矩阵中填满了 0 。
     *
     * 另给你一个二维整数数组 query 。
     * 针对每个查询 query[i] = [row1i, col1i, row2i, col2i] ，请你执行下述操作：
     *
     * 找出 左上角 为 (row1i, col1i) 且 右下角 为 (row2i, col2i) 的子矩阵，将子矩阵中的 每个元素 加 1 。
     * 也就是给所有满足 row1i <= x <= row2i 和 col1i <= y <= col2i 的 mat[x][y] 加 1 。
     * 返回执行完所有操作后得到的矩阵 mat 。
     * @param: n
     * @param: queries
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/11/14 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] result;
        result = method_01(n, queries);
        result = method_02(n, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 查看提示, 使用前缀和来解决
     * AC: 23ms/75.09MB
     * @param: n
     * @param: queries
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/11/14 11:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_02(int n, int[][] queries) {
        int[][] prev = new int[n][n + 1];
        for (int[] query : queries) {
            int row1i = query[0];
            int col1i = query[1];
            int row2i = query[2];
            int col2i = query[3];
            for (int i = row1i; i <= row2i; i++) {
                prev[i][col1i]++;
                prev[i][col2i + 1]--;
            }
        }
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = prev[i][j] + (j == 0 ? 0 : ans[i][j - 1]);
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 直接执行不就可以吗?
     * 暴力还是可以解决但是时间复杂度有点高
     * AC: 591ms/75.68MB
     * @param: n
     * @param: queries
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/11/14 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int n, int[][] queries) {
        int[][] ans = new int[n][n];
        for (int[] query : queries) {
            int row1i = query[0];
            int col1i = query[1];
            int row2i = query[2];
            int col2i = query[3];
            for (int i = row1i; i <= row2i; i++) {
                for (int j = col1i; j <= col2i; j++) {
                    ans[i][j]++;
                }
            }
        }
        return ans;
    }

}
