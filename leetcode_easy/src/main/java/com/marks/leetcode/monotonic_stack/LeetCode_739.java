package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [单调栈] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/27 17:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_739 {
    /**
     * @Description: [
     * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
     * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
     *
     * tips:
     * 1 <= temperatures.length <= 10^5
     * 30 <= temperatures[i] <= 100
     * ]
     * @param temperatures 
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/27 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int[] result;
//        result = method_01(temperatures);
        result = method_02(temperatures);
        return result;
    }

    /**
     * @Description: [
     * 1. 优化栈中存储, 只存储下标
     * 2. 使用new ArrayDeque<>() 替代 new Stack<>()
     *
     * AC:25ms/55.73MB
     * ]
     * @param temperatures
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 9:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int num = temperatures[i];
            while (!stack.isEmpty() && temperatures[stack.peek()] < num) {
                Integer preIndex = stack.poll();
                ans[preIndex] = i - preIndex;
            }
            stack.push(i);
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入: temperatures = [73,74,75,71,69,72,76,73]
     * 输出: [1,1,4,2,1,1,0,0]
     * 暴力肯定超时
     *
     * 单调栈:
     * AC:167ms/55.27MB
     * 找到时间复杂度太高的原因:
     * 在Java中，Stack类是继承自Vector的一个后进先出（LIFO）的栈实现，
     * 但由于Vector类本身是同步的，这使得Stack在不需要同步的场景下显得有些过重。 所以会时间长
     *
     * 优化, 栈中不需要存储温度的信息, 因为已经存放了下标, 可以通过temperatures[stack.peek()] = temperatures[index]
     * @see LeetCode_739#method_02(int[])
     * ]
     * @param temperatures 
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/27 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        Stack<int[]> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            int num = temperatures[i];
            while (!stack.isEmpty() && stack.peek()[0] < num) {
                int[] head = stack.pop();
                ans[head[1]] = i - head[1];
            }
            stack.push(new int[]{temperatures[i], i});
        }
        return ans;
    }
}
