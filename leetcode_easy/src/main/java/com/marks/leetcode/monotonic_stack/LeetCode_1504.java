package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;

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
     * AC:13ms/44.5MB
     * ]
     * @param mat
     * @return int
     * @author marks
     * @CreateDate: 2024/12/4 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] pre = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    pre[i][j] = mat[i][j];
                } else if (mat[i][j] == 0) {
                    pre[i][j] = 0;
                } else if (mat[i][j] == 1) {
                    pre[i][j] = pre[i][j - 1] + 1;
                }
            }
        }
        int ans = 0;
        Deque<int[]> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < m; j++) {
                int height = 1;
                while (!stack.isEmpty() && stack.peek()[0] > pre[j][i]) {
                    sum -= stack.peek()[1] * (stack.peek()[0] - pre[j][i]);
                    height += stack.peek()[1];
                    stack.poll();
                }
                sum += pre[j][i];
                ans += sum;
                stack.push(new int[] {pre[j][i], height});
            }

            stack.clear();
        }
        return ans;
    }
}
