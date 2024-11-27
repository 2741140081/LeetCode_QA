package com.marks.leetcode.binary_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/27 11:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_378 {
    
    /**
     * @Description: [
     * 给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * 请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
     *
     * 你必须找到一个内存复杂度优于 O(n^2) 的解决方案。
     *
     * tips:
     * n == matrix.length
     * n == matrix[i].length
     * 1 <= n <= 300
     * -10^9 <= matrix[i][j] <= 10^9
     * 题目数据 保证 matrix 中的所有行和列都按 非递减顺序 排列
     * 1 <= k <= n2
     * ]
     * @param matrix 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int kthSmallest(int[][] matrix, int k) {
        int result;
        result = method_01(matrix, k);
        return result;
    }

    private int method_01(int[][] matrix, int k) {
        return 0;
    }
}
