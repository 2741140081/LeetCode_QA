package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_040 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/15 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_040 {

    /**
     * @Description:
     * 给定一个由 0 和 1 组成的矩阵 matrix ，找出只包含 1 的最大矩形，并返回其面积。
     * 注意：此题 matrix 输入格式为一维 01 字符串数组。
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/05/15 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximalRectangle(String[] matrix) {
        int result;
        result = method_01(matrix);
        return result;
    }

    /**
     * @Description:
     * 1. 将 martrix 转为 int[][], 并且使用前缀和, 预处理每一列的前缀和
     * 2. 然后对每一列进行单调栈求解, 获取最大面积, 分析一下, 使用单调递增还是递减栈, 栈中存储值还坐标值? 如何计算矩形的面积
     * 3. 假设有一列的值是p[i] = [1,0,3,5,3,1], 那么这个问题转换为找到数组p[i] 中的子数组p[i~j]使得面积最大, 面积计算公式为子数组的最小值 min = Math.min(p[i]~p[j]) * (j-i+1),
     * 4. 使用一个单调递增栈, 存储下标, 假设栈顶元素 int topIndex = stack.peek(), 当前位于 i 处, 讨论 p[i] 与 p[topIndex] 的大小关系:
     * 4.1 p[i] > p[topIndex]: 直接入栈即可
     * 4.2 p[i] <= p[topIndex]: 栈顶元素出栈, int popIndex = stack.pop(); 接着需要讨论栈是否为空,
     * 如果栈为空: 则[0 ~ i - 1] 范围内的最小值是 p[popIndex], 宽度是 i, 面积是 min * i,
     * 如果栈不为空: 则[stack.peek() ~ i - 1] 范围内的最小值是 p[popIndex], 宽度是 i - stack.peek(), 面积是 min * (i - stack.peek()).
     * 5. 然后处理栈中剩余的元素, n 是数组的宽度, int popIndex = stack.pop(); min = p[popIndex]; 面积是 (n - stack.peek()) * min,
     * 6. 假设 stack = {1,2,3} 当前 i 位于 4 处, p[4][i] = 3 <= p[3][i], 5 需要进行出栈操作, 并且 5 在 [3,3] 这个区间的最小值是5,4 - 2 - 1 = 1
     * AC: 10ms/43.4MB
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/05/15 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length();
        int[][] prefixSum = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果当前 matrix[i][j] == 1, 则 prefixSum[i][j] = prefixSum[i][j-1] + 1, 但是需要注意越界问题
                if (matrix[i].charAt(j) == '1') {
                    prefixSum[i][j] = j == 0 ? 1 : prefixSum[i][j-1] + 1;
                }
            }
        }
        // 创建单调递增栈
        Deque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        // 处理每一列
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                while (!stack.isEmpty() && prefixSum[stack.peek()][i] >= prefixSum[j][i]) {
                    int popIndex = stack.pop();
                    int min = prefixSum[popIndex][i];
                    int width = j - (stack.isEmpty() ? 0 : (stack.peek() + 1));
                    ans = Math.max(ans, min * width);
                }
                stack.push(j);
            }
            // 处理 stack 中剩余的元素
            while (!stack.isEmpty()) {
                int popIndex = stack.pop();
                int min = prefixSum[popIndex][i];
                // [topIndex + 1 ~ n - 1] 之间的最小值是 min, 宽度是 n - 1 - (topIndex + 1) + 1 =
                int width = stack.isEmpty() ? m : m - stack.peek() - 1;
                ans = Math.max(ans, min * width);
            }
        }

        return ans;
    }

}
