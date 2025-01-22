package com.marks.leetcode.data_structure.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: 用队列实现栈 </p>
 * <p>文件名称: LeetCode_225 </p>
 * <p>描述: 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通栈的全部四种操作（push、top、pop 和 empty）。
 * 实现 MyStack 类：
 * void push(int x) 将元素 x 压入栈顶。
 * int pop() 移除并返回栈顶元素。
 * int top() 返回栈顶元素。
 * boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
 * 注意：
 * 你只能使用队列的标准操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 这些操作。
 * 你所使用的语言也许不支持队列。 你可以使用 list （列表）或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。 </p>
 * <p>AC:0ms/40.50MB </p>
 *
 * <p>
 * [1, 2, 3, 4, 5]
 * 1
 * [2, 1]
 * [3, 2, 1]
 * [4, 3, 2, 1]
 * [5, 4, 3, 2, 1]
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/22 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyStack {
    Queue<Integer> in;
    Queue<Integer> out;
    public MyStack() {
        in = new LinkedList<>();
        out = new LinkedList<>();
    }

    public void push(int x) {
        if (!out.isEmpty()) {
            in.offer(out.poll());
        }
        out.offer(x);
    }

    public int pop() {
        int size = in.size();
        int top = out.poll();
        for (int i = 0; i <= size - 2; i++) {
            out.offer(in.poll());
        }
        Queue<Integer> temp = in;
        in = out;
        out = temp;
        return top;
    }

    public int top() {
        return out.peek();
    }

    public boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }
}
