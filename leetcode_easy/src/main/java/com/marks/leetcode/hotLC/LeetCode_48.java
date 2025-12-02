package com.marks.leetcode.hotLC;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_48 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 14:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_48 {

    /**
     * @Description:
     * 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
     * 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
     * @param: matrix
     * @return void
     * @author marks
     * @CreateDate: 2025/12/02 14:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void rotate(int[][] matrix) {
        method_01(matrix);
    }

    /**
     * @Description:
     * 1. 差不多能解决, 需要判断n, 如果n是奇数, 中心的不需要旋转, n / 2 算是层数, int target = n / 2;
     * 2. 起始点(0,0), 终点是(target, target), 先处理最外层的元素, 然后处理内层元素
     * 3. 旋转90度, 假设当前是(i, j), 旋转后(j, n - i - 1), (1, 1), n = 4, (1, 2)
     * AC: 0ms/42.91MB
     * @param: matrix
     * @return void
     * @author marks
     * @CreateDate: 2025/12/02 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(int[][] matrix) {
        int n = matrix.length;
        int target = n / 2;
        for (int i = 0; i < target; i++) {
            for (int j = i; j < n - i - 1; j++) {
                dfsSwap(matrix, i, j, n, 0, matrix[i][j]);
            }
        }
    }

    private void dfsSwap(int[][] matrix, int i, int j, int n, int count, int prev) {
        if (count == 4) {
            return;
        }
        int nextI = j;
        int nextJ = n - i - 1;
        int temp = matrix[nextI][nextJ];
        matrix[nextI][nextJ] = prev;
        prev = temp;
        dfsSwap(matrix, nextI, nextJ, n, count + 1, prev);
    }

}
