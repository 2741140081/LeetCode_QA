package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_74 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 10:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_74 {

    /**
     * @Description:
     * 给你一个满足下述两条属性的 m x n 整数矩阵：
     *
     * 每行中的整数从左到右按非严格递增顺序排列。
     * 每行的第一个整数大于前一行的最后一个整数。
     * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
     * @param: matrix
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/27 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        boolean result;
        result = method_01(matrix, target);
        return result;
    }

    /**
     * @Description:
     * 1. 先判断target在哪一行, 然后再去判断在那一列
     * AC: 0ms/43.05MB
     * @param: matrix
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/27 10:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int leftRow = 0, rightRow = m - 1;
        int targetRow = -1;
        while (leftRow <= rightRow) {
            int mid = leftRow + (rightRow - leftRow) / 2;
            if (matrix[mid][0] > target) {
                rightRow = mid - 1;
            } else {
                targetRow = mid;
                leftRow = mid + 1;
            }
        }
        int left = 0, right = n - 1;
        int targetCol = -1;
        if (targetRow != -1) {
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (matrix[targetRow][mid] > target) {
                    right = mid - 1;
                } else {
                    targetCol = mid;
                    left = mid + 1;
                }
            }
        }
        return targetCol != -1 && matrix[targetRow][targetCol] == target;
    }

}
