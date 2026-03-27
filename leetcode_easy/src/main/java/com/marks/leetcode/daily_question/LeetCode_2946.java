package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2946 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/27 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2946 {

    /**
     * @Description:
     * 给你一个下标从 0 开始且大小为 m x n 的整数矩阵 mat 和一个整数 k 。请你将矩阵中的 奇数 行循环 右 移 k 次，偶数 行循环 左 移 k 次。
     * 如果初始矩阵和最终矩阵完全相同，则返回 true ，否则返回 false 。
     *
     * tips:
     * 1 <= mat.length <= 25
     * 1 <= mat[i].length <= 25
     * 1 <= mat[i][j] <= 25
     * 1 <= k <= 50
     * @param: mat
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/27 15:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean areSimilar(int[][] mat, int k) {
        boolean result;
        result = method_01(mat, k);
        return result;
    }

    /**
     * @Description:
     * 1. 取第 i 行 和 第 i + k 行, 判断是否相等
     * 2. 处理循环问题, i + k % n 取余即可
     * 3. 判断 i 的奇偶性, 奇数行循环右移, 偶数行循环左移
     * AC: 2ms/46.17MB
     * @param: mat
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/03/27 15:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;
        for (int i = 0; i < m; i++) {
            int[] row1 = mat[i];
            // 保存移动后的行数据
            int[] updateRow = new int[n];
            // 判断 i 的奇偶性
            if (i % 2 == 0) {
                // 偶数行循环左移
                for (int j = 0; j < n; j++) {
                    int index = ((j - k + k*n)) % n;
                    updateRow[index] = row1[j];
                }
            } else {
                // 奇数行循环右移
                for (int j = 0; j < n; j++) {
                    int index = ((j + k) % n);
                    updateRow[index] = row1[j];
                }
            }
            // 判断是否相等
            if (!Arrays.equals(row1, updateRow)) {
                return false;
            }

        }
        return true;
    }

}
