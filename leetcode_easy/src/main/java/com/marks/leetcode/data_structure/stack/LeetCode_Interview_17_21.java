package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_17_21 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/15 9:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_17_21 {

    /**
     * @Description:
     * 给定一个直方图(也称柱状图)，假设有人从上面源源不断地倒水，最后直方图能存多少水量?直方图的宽度为 1。
     *
     * @param: height
     * @return int
     * @author marks
     * @CreateDate: 2026/05/15 9:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int trap(int[] height) {
        int result;
        result = method_01(height);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：[0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     * 1. 如果使用单调递减栈来处理雨水问题, 先分析, 假设当前位于 i 处, 并且栈是一个单调递减栈, 栈中存放数组索引, int topIndex = stack.peek();
     * 2. 讨论 h[i] 与 h[topIndex] 的大小关系:
     * 2.1 h[i] < h[topIndex]: 无需出栈操作, 直接入栈即可, 如果是一个单调递减序列, 一定没有雨水可以存.
     * 2.2 h[i] >= h[topIndex]:进行出栈操作, int popIndex = stack.pop(); 判断栈是否为空,
     * 如果栈为空, 相当与是一个递增序列, 也无法存雨水, 直接出栈即可.
     * 如果栈不为空, int topIndex = stack.peek(); int min = Math.min(height[i], height[topIndex]); min 为两侧的柱子高度的较小值,
     * int h = min - height[popIndex]; 然后计算宽度, int w = i - stack.peek() - 1; 对于整体的贡献度 ans += (h * w);
     * AC: 3ms/44.04MB
     * @param: height
     * @return int
     * @author marks
     * @CreateDate: 2026/05/15 9:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] height) {
        int n = height.length;
        if (n < 3) {
            return 0;
        }
        Deque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[i] >= height[stack.peek()]) {
                int popIndex = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int topIndex = stack.peek();
                int min = Math.min(height[i], height[topIndex]);
                int h = min - height[popIndex];
                int w = i - stack.peek() - 1;
                ans += (h * w);
            }
            stack.push(i);
        }
        return ans;
    }
}
