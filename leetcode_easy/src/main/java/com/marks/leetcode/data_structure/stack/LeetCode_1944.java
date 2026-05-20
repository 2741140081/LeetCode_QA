package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1944 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/18 14:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1944 {

    /**
     * @Description:
     * 有 n 个人排成一个队列，从左到右 编号为 0 到 n - 1 。给你以一个整数数组 heights ，每个整数 互不相同，heights[i] 表示第 i 个人的高度。
     * 一个人能 看到 他右边另一个人的条件是这两人之间的所有人都比他们两人 矮 。
     * 更正式的，第 i 个人能看到第 j 个人的条件是 i < j 且 min(heights[i], heights[j]) > max(heights[i+1], heights[i+2], ..., heights[j-1]) 。
     * 请你返回一个长度为 n 的数组 answer ，其中 answer[i] 是第 i 个人在他右侧队列中能 看到 的 人数 。
     *
     * tips:
     * n == heights.length
     * 1 <= n <= 10^5
     * 1 <= heights[i] <= 10^5
     * heights 中所有数 互不相同 。
     * @param: heights
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/18 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] canSeePersonsCount(int[] heights) {
        int[] result;
        result = method_01(heights);
        return result;
    }

    /**
     * @Description:
     * 1. 由于所有数都不相同, 所以不考虑相等情况, 并且[i, i + 1, i + 2, ..., j] 假设 i 可以看到 j,需要一个从右向左遍历,
     * 栈中是一个单调递减栈, 如果有出栈发生, 则当前结果 ans[i]++; 执行完成后, 如果栈不为空, 则 ans[i]++; 栈空不执行 ans[i]++;
     * AC: 22ms/113.54MB
     * @param: heights
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/18 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        // 创建单调递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        // 从右向左遍历
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[i] > heights[stack.peek()]) {
                ans[i]++;
                stack.pop();
            }
            if (!stack.isEmpty()) {
                ans[i]++;
            }
            stack.push(i);
        }

        return ans;
    }

}
