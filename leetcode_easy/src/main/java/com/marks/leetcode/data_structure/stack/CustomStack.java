package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称: 设计一个支持增量操作的栈 </p>
 * <p>文件名称: LeetCode_1381 </p>
 * <p>描述:
 * 请你设计一个支持对其元素进行增量操作的栈。
 *
 * 实现自定义栈类 CustomStack ：
 * CustomStack(int maxSize)：用 maxSize 初始化对象，maxSize 是栈中最多能容纳的元素数量。
 * void push(int x)：如果栈还未增长到 maxSize ，就将 x 添加到栈顶。
 * int pop()：弹出栈顶元素，并返回栈顶的值，或栈为空时返回 -1 。
 * void inc(int k, int val)：栈底的 k 个元素的值都增加 val 。如果栈中元素总数小于 k ，则栈中的所有元素都增加 val 。
 * </p>
 * <p>使用栈 + 额外栈复制 AC:25ms/45.87MB</p>
 * <p>使用List来试试 AC:17ms/45.10MB</p>
 * <p>使用int[] + 顶部指针top 试试 AC:5ms/44.48MB</p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/15 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CustomStack {
    private Deque<Integer> stack;
    private List<Integer> list;

    private int[] arr;
    private int top; // 相当于顶部指针
    private int size;
    public CustomStack(int maxSize) {
        arr = new int[maxSize];
        top = 0;
        this.size = maxSize;
    }

    public void push(int x) {
        if (top < size) {
            arr[top] = x;
            top++;
        }
    }

    public int pop() {
        if (top > 0) {
            int value = arr[top - 1];
            top--;
            return value;
        }else {
            return -1;
        }
    }

    public void increment(int k, int val) {
        for (int i = 0; i < Math.min(top, k); i++) {
            arr[i] += val;
        }
    }
}
