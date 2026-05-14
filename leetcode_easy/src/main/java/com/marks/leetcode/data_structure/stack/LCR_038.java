package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_038 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/14 15:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_038 {

    /**
     * @Description:
     * 请根据每日 气温 列表 temperatures ，重新生成一个列表，要求其对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。
     * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
     * @param: temperatures
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/14 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int[] result;
        result = method_01(temperatures);
        return result;
    }

    /**
     * @Description:
     * 1. 简单的单调递减栈
     * 2. 假设当前位于 i 处, 栈中存储下标值, 栈顶元素小于
     * AC: 25ms/101.57MB
     * @param: temperatures
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/14 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        // 创建单调递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                ans[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return ans;
    }

}
