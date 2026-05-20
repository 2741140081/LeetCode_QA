package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_039 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/15 14:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_039 {

    /**
     * @Description:
     * 给定非负整数数组 heights ，数组中的数字用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     *
     * tips:
     * 1 <= heights.length <=10^5
     * 0 <= heights[i] <= 10^4
     * @param: heights
     * @return int
     * @author marks
     * @CreateDate: 2026/05/15 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestRectangleArea(int[] heights) {
        int result;
        result = method_01(heights);
        return result;
    }

    /**
     * @Description:
     * 1. 感觉还是比较简单的, 即有一个数组 h[], 在 h[] 中找到子数组 h[i, j], 并且h[i, j] 的最小值 int min = Math.min(h[i ~ j]),
     * 宽度是 j - i + 1, 高度是 min, 那么这个子数组的矩形面积就是 min * (j - i + 1)
     * 2. 可以用一个单调递增的栈存储数组 h[] 的下标值, 当遇到出栈时, 这个区间中最小值 min = stack.pop() 表示 [stack.peek() + 1, i - 1].
     * AC: 18ms/65.01MB
     * @param: heights
     * @return int
     * @author marks
     * @CreateDate: 2026/05/15 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] heights) {
        int n = heights.length;
        int ans = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                int cur = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                ans = Math.max(ans, heights[cur] * (i - left - 1));
            }
            stack.push(i);
        }
        // 处理剩余的栈
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek();
            ans = Math.max(ans, heights[cur] * (n - left - 1));
        }

        return ans;
    }

}
