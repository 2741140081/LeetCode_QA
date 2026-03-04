package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1582 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 11:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1582 {

    /**
     * @Description:
     * 给定一个 m x n 的二进制矩阵 mat，返回矩阵 mat 中特殊位置的数量。
     * 如果位置 (i, j) 满足 mat[i][j] == 1 并且行 i 与列 j 中的所有其他元素都是 0（行和列的下标从 0 开始计数），那么它被称为 特殊 位置。
     * tips:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 100
     * mat[i][j] 是 0 或 1。
     * @param: mat
     * @return int
     * @author marks
     * @CreateDate: 2026/03/04 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSpecial(int[][] mat) {
        int result;
        result = method_01(mat);
        return result;
    }

    /**
     * @Description:
     * 1. 首先想到的是前缀和, 统计每一行和每一列的元素和
     * @param: mat
     * @return int
     * @author marks
     * @CreateDate: 2026/03/04 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[] rowSum = new int[m];
        int[] colSum = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowSum[i] += mat[i][j];
                colSum[j] += mat[i][j];
            }
        }
        // 再次遍历 mat
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1 && rowSum[i] == 1 && colSum[j] == 1) {
                    ans++;
                }
            }
        }
        return ans;
    }

}
