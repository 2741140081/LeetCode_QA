package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: SortedStack </p>
 * <p>描述:
 * 栈排序。 编写程序，对栈进行排序使最小元素位于栈顶。
 * 最多只能使用一个其他的临时栈存放数据，但不得将元素复制到别的数据结构（如数组）中。
 * 该栈支持如下操作：push、pop、peek 和 isEmpty。
 * 当栈为空时，peek 返回 -1。
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/12 17:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SortedStack {
    private Deque<Integer> stack;
    private Deque<Integer> temp;
    /**
     * @Description:
     * 1. 最开始时, 栈是空的状态
     * 2. 需要一个单调递减栈, 存储元素
     * 3. 当 push 元素时, 栈为空 或者 val <= 栈顶元素, 直接入栈
     * 3.1 当 val > stack.peek() 时, 需要将栈内元素弹出, 直到 val <= 栈顶元素, 或者栈为空
     * 4. 将temp 临时栈中的元素重新压入栈中
     *
     * AC: 64ms/46.37MB
     * @return
     * @author marks
     * @CreateDate: 2026/05/12 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public SortedStack() {
        stack = new ArrayDeque<>();
        temp = new ArrayDeque<>();
    }

    public void push(int val) {
        while (!stack.isEmpty() && val > stack.peek()) {
            temp.push(stack.pop());
        }
        stack.push(val);
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
    }

    public void pop() {
        if (stack.isEmpty()) {
            return;
        }
        stack.pop();
    }

    public int peek() {
        return stack.isEmpty() ? -1 : stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
