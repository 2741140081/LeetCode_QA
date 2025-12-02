package com.marks.leetcode.hotLC;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_240 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_240 {

    /**
     * @Description:
     * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
     * 每行的元素从左到右升序排列。
     * 每列的元素从上到下升序排列。
     * @param: matrix
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/02 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        boolean result;
        result = method_01(matrix, target);
        result = method_02(matrix, target);
        return result;
    }

    /**
     * @Description:
     * 官方题解: Z字形查找
     * 解析:
     * 我们可以从矩阵 matrix 的右上角 (0,n−1) 进行搜索。
     * 在每一步的搜索过程中，如果我们位于位置 (x,y)，
     * 那么我们希望在以 matrix 的左下角为左下角、以 (x,y) 为右上角的矩阵中进行搜索，即行的范围为 [x,m−1]，列的范围为 [0,y]：
     * 如果 matrix[x,y]=target，说明搜索完成；
     * 如果 matrix[x,y]>target，由于每一列的元素都是升序排列的，那么在当前的搜索矩阵中，
     * 所有位于第 y 列的元素都是严格大于 target 的，因此我们可以将它们全部忽略，即将 y 减少 1；
     *
     * 如果 matrix[x,y]<target，由于每一行的元素都是升序排列的，那么在当前的搜索矩阵中，
     * 所有位于第 x 行的元素都是严格小于 target 的，因此我们可以将它们全部忽略，即将 x 增加 1。
     *
     * 在搜索的过程中，如果我们超出了矩阵的边界，那么说明矩阵中不存在 target。
     * 时间复杂度O(m+n)
     * @param: matrix
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/02 15:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int x = 0, y = n - 1;
        while (x < m && y >= 0) {
            if (matrix[x][y] == target) {
                return true;
            }
            if (matrix[x][y] > target) {
                --y;
            } else {
                ++x;
            }
        }
        return false;
    }

    /**
     * @Description:
     * 1. 还是用暴力吧
     * AC: 4ms/47.49MB
     * @param: matrix
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/02 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] <= target && matrix[i][n - 1] >= target) {
                int left = 0, right = n - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (matrix[i][mid] == target) {
                        return true;
                    } else if (matrix[i][mid] < target) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }

            }
        }
        return false;
    }

}
