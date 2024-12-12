package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/4 10:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_85 {
    /**
     * @Description: [
     * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
     *
     * tips:
     * rows == matrix.length
     * cols == matrix[0].length
     * 1 <= row, cols <= 200
     * matrix[i][j] 为 '0' 或 '1'
     * ]
     * @param matrix
     * @return int
     * @author marks
     * @CreateDate: 2024/12/4 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximalRectangle(char[][] matrix) {
        int result;
        result = method_01(matrix);
        return result;
    }

    /**
     * @Description: [
     * 输入：matrix = [
     * ["1","0","1","0","0"],
     * ["1","0","1","1","1"],
     * ["1","1","1","1","1"],
     * ["1","0","0","1","0"]
     * ]
     * 输出：6
     * [
     * [1, 0, 1, 0, 0]
     * [1, 0, 1, 2, 3]
     * [1, 2, 3, 4, 5]
     * [1, 0, 0, 1, 0]
     * ]
     * 看这个pre[][] 就知道遍历每一列[0, 3, 5, 0], 这不就是找最大面积吗[3, 3] * 2 = 6
     * 所以应该遍历每一行, 找到面积最大值
     *
     * AC:12ms/44.65MB
     * ]
     * @param matrix
     * @return int
     * @author marks
     * @CreateDate: 2024/12/4 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] pre = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    pre[i][j] = matrix[i][j] == '1' ? 1 : 0;
                } else if (matrix[i][j] == '0') {
                    pre[i][j] = 0;
                } else if (matrix[i][j] == '1') {
                    pre[i][j] = pre[i][j - 1] + 1;
                }
            }
        }
        int ans = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int[] left = new int[m];
            int[] right = new int[m];
            Arrays.fill(right, m);
            for (int j = 0; j < m; j++) {
                while (!stack.isEmpty() && pre[stack.peek()][i] >= pre[j][i]) {
                    right[stack.peek()] = j;
                    stack.poll();
                }
                left[j] = stack.isEmpty() ? -1 : stack.peek();
                stack.push(j);
            }
            stack.clear();
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, (right[j] - left[j] - 1) * pre[j][i]);
            }
        }
        return ans;
    }
}
