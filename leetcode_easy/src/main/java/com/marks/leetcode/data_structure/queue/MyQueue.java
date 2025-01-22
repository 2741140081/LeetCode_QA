package com.marks.leetcode.data_structure.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: 用栈实现队列 </p>
 * <p>文件名称: LeetCode_232 </p>
 * <p>描述: [类型描述] </p>
 *
 * <p>
 *     [1, 2, 3, 4, 5]
 *     [1, 2] =>[2, 1]
 *     [3, 2, 1]
 *     [4, 3, 2, 1]
 *     [5, 4, 3, 2, 1]
 * </p>
 * <p>AC: 0ms/40.71MB</p>
 * @author marks
 * @version v1.0
 * @date 2025/1/22 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyQueue {
    private Deque<Integer> deque;
    private Deque<Integer> stack;

    public MyQueue() {
        deque = new ArrayDeque<>();
        stack = new ArrayDeque<>();
    }

    /**
     * @Description:
     * [1]
     * [1], p;2
     *
     * @param x
     * @return void
     * @author marks
     * @CreateDate: 2025/1/22 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void push(int x) {
        int size = deque.size();
        for (int i = 0; i < size; i++) {
            stack.push(deque.pop());
        }
        stack.push(x);
        while (!stack.isEmpty()) {
            deque.push(stack.pop());
        }
    }

    public int pop() {
        return deque.poll();
    }

    public int peek() {
        return deque.peek();
    }

    public boolean empty() {
        return deque.isEmpty();
    }
}
