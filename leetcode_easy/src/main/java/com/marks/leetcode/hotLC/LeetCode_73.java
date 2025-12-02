package com.marks.leetcode.hotLC;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_73 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/1 17:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_73 {

    /**
     * @Description:
     * 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
     *
     * @param: matrix
     * @return void
     * @author marks
     * @CreateDate: 2025/12/01 17:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void setZeroes(int[][] matrix) {
        method_01(matrix);
    }

    /**
     * @Description:
     * 1. 使用额外的空间记录0的位置的行和列 Set<Integer> columns, rows; 使用Set 防止重复记录
     * 2. 假设 matrix[i][j] = 0, columns.add(j), rows.add(i);
     * 3. 分别遍历行和列，将matrix[i][j] = 0;
     * AC: 1ms/46.45MB
     *
     * 4. 进阶: 需要使用常数的额外空间来解决当前问题。
     * @param: matrix
     * @return void
     * @author marks
     * @CreateDate: 2025/12/01 17:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(int[][] matrix) {
        Set<Integer> columns = new HashSet<>();
        Set<Integer> rows = new HashSet<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    columns.add(j);
                    rows.add(i);
                }
            }
        }
        // 遍历columns, rows
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            if (rows.contains(i)) {
                Arrays.fill(matrix[i], 0);
            }
        }
        for (int i = 0; i < n; i++) {
            if (columns.contains(i)) {
                for (int j = 0; j < m; j++) {
                    matrix[j][i] = 0;
                }
            }
        }
    }

}
