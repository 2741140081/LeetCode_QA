package com.marks.leetcode.monotonic_stack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/4 11:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1504 {
    /**
     * @Description: [
     * 给你一个 m x n 的二进制矩阵 mat ，请你返回有多少个 子矩形 的元素全部都是 1 。
     *
     * tips:
     * 1 <= m, n <= 150
     * mat[i][j] 仅包含 0 或 1
     * ]
     * @param mat 
     * @return int
     * @author marks
     * @CreateDate: 2024/12/4 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSubmat(int[][] mat) {
        int result;
        result = method_01(mat);
        return result;
    }

    /**
     * @Description: [
     * 输入：mat = [
     * [1,0,1],
     * [1,1,0],
     * [1,1,0]
     * ]
     * pre[][]
     * [1, 0, 1]
     * [1, 2, 0]
     * [1, 2, 0]
     *
     * [-1, -1, -1]
     * [-1,  0, -1]
     * [-1,  0, -1]
     * 输出：13
     * ]
     * @param mat
     * @return int
     * @author marks
     * @CreateDate: 2024/12/4 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] mat) {
        return 0;
    }
}
