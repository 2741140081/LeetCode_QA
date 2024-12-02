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
 * @date 2024/11/29 15:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_84 {
    /**
     * @Description: [
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     *
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     *
     * tips:
     * 1 <= heights.length <=10^5
     * 0 <= heights[i] <= 10^4
     * ]
     * @param heights 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/29 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestRectangleArea(int[] heights) {
        int result;
        result = method_01(heights);
        return result;
    }

    /**
     * @Description: [
     * 输入：heights = [2,1,5,6,2,3]
     * 输出：10
     * 解释：最大的矩形为图中红色区域，面积为 10
     * 3, 2, 0
     * ]
     * @param heights 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/29 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(right, n);

        Deque<Integer> mono_stack = new ArrayDeque<Integer>();
        for (int i = 0; i < n; ++i) {
            while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                right[mono_stack.peek()] = i;
                mono_stack.pop();
            }
            left[i] = (mono_stack.isEmpty() ? -1 : mono_stack.peek());
            mono_stack.push(i);
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }
}
