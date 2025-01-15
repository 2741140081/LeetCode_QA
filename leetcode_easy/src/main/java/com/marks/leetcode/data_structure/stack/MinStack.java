package com.marks.leetcode.data_structure.stack;

import java.util.*;

/**
 * <p>项目名称: 最小栈 </p>
 * <p>文件名称: LeetCode_155 </p>
 * <p>描述:
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * 实现 MinStack 类:
 *
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 * </p>
 * <p>AC:25ms/45.99MB  使用的是优先队列</p>
 * <p>AC:4ms/44.20MB  查看题解后使用栈</p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/15 15:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MinStack {
    private Deque<Integer> data_stack;
    private Deque<Integer> min_stack;
    public MinStack() {
        data_stack = new ArrayDeque<>();
        min_stack = new ArrayDeque<>();
    }

    public void push(int val) {
        data_stack.push(val);
        if (min_stack.isEmpty()) {
            min_stack.push(val);
        }else {
            min_stack.push(Math.min(min_stack.peek(), val));
        }
    }

    public void pop() {
        data_stack.pop();
        min_stack.pop();
    }

    public int top() {
        return data_stack.peek();
    }

    public int getMin() {
        return min_stack.peek();
    }
}
