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
        result = method_02(heights);
        return result;
    }

    /**
     * @Description: [
     * <p>官方题解方法一:</p>
     * E1:int[] heights = [6,7,5,2,4,5,9,3]
     * <p>1.暴力解法, </p>
     * 从heights[i], 分别遍历[0, i - 1], [i + 1, n - 1] 找到left < i < right,
     * 并且heights[left / right] >= heights[i], 面积 ans = (right - left + 1) * heights[i]。
     * 这种方法时间复杂的是O(n^2), 超时
     * <p>2.单调栈: </p>
     * <p>2.1 找heights[i] 的 left:从左往右遍历, 一次遍历找到heights[i]左侧比heights[i]小, 并且最接近的一个值,
     * 如果不存在, 则使用高度为0, 下标为 -1 作为其哨兵.</p>
     * <p>案例遍历执行过程:int[n] left</p>
     * <p>a. i = 0, stack是空的, 无法从栈中找到元素, 所以i = 0时, 设置为哨兵 -1, 即left[0] = -1.
     * 然后将i = 0入栈, {0(6)}, 0表示下标, (6)表示值, 方便演示, 实际中只需要入栈下标即可, 值可以用 heights[stack.peek()]获取</p>
     * <p>b. i = 1, heights[stack.peek()] = 6 < 7, 0(6) 即是i = 2时, 左侧最近且小于heights[1]的下标, left[1] = 0</p>
     * <p>c. i = 2, 5 < 7, 维持栈中元素的单调递增, 将元素出栈, stack.poll(7), stack.poll(6),
     * 此时stack 为空, left[2] = -1, 将2(5) 入栈</p>
     * <p>d. i = 3, 2 < 5, 出栈, 栈空, left[3] = -1, 3(2)入栈</p>
     * <p>e. i = 4, 4 > 2, left[4] = stack.peek() = 3, 4(4)入栈</p>
     * <p>f. i = 5, heights[5] = 5 > stack.peek() = 4, left[5] = 4, 5(5) 入栈</p>
     * <p>g. i = 6, h[6] = 9 > 5, left[6] = 5, 6(9)入栈</p>
     * <p>h. i = 7, h[7] = 3 < 9, 出栈:6(9), 5(5), 4(4), 栈内元素剩余3(2), left[7] = 3</p>
     * <p>遍历结束 left[] = {-1, 0, -1, -1, 3, 4, 5, 3}</p>
     * <p>2.2 找heights[i] 的 right:从右往左遍历, 找right[], 与上面同理, 但是right的哨兵在i = n处, 设置为n</p>
     *
     * <p>n = 8, int[] heights = [6,7,5,2,4,5,9,3]
     * i = 7, right[7] = 8, right[6] = 7, right[5] = 7, right[4] = 7, right[3] = 8, right[2] = 3,
     * right[1] = 2, right[0] = 2 </p>
     * <p> right[] = {2, 2, 3, 8, 7, 7, 7, 8} </p>
     * 思路理清楚了, 开始写代码
     * AC:30ms/56.04MB
     * ]
     * @param heights
     * @return int
     * @author marks
     * @CreateDate: 2024/12/2 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int method_02(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.poll();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                stack.poll();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            /*
            int[] heights = [6,7,5,2,4,5,9,3]
            {-1, 0, -1, -1, 3, 4, 5, 3}
            {2, 2, 3, 8, 7, 7, 7, 8}
             */
            ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
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
