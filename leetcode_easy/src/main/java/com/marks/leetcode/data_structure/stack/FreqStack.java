package com.marks.leetcode.data_structure.stack;

import java.util.*;

/**
 * <p>项目名称: 最大频率栈 </p>
 * <p>文件名称: LeetCode_895 </p>
 * <p>描述:
 * 设计一个类似堆栈的数据结构，将元素推入堆栈，并从堆栈中弹出出现频率最高的元素。
 *
 * 实现 FreqStack 类:
 *
 * FreqStack() 构造一个空的堆栈。
 * void push(int val) 将一个整数 val 压入栈顶。
 * int pop() 删除并返回堆栈中出现频率最高的元素。
 * 如果出现频率最高的元素不只一个，则移除并返回最接近栈顶的元素。
 * E1:
 * FreqStack = new FreqStack();
 * freqStack.push (5);//堆栈为 [5]
 * freqStack.push (7);//堆栈是 [5,7]
 * freqStack.push (5);//堆栈是 [5,7,5]
 * freqStack.push (7);//堆栈是 [5,7,5,7]
 * freqStack.push (4);//堆栈是 [5,7,5,7,4]
 * freqStack.push (5);//堆栈是 [5,7,5,7,4,5]
 * freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,5,7,4]。
 * freqStack.pop ();//返回 7 ，因为 5 和 7 出现频率最高，但7最接近顶部。堆栈变成 [5,7,5,4]。
 * freqStack.pop ();//返回 5 ，因为 5 出现频率最高。堆栈变成 [5,7,4]。
 * freqStack.pop ();//返回 4 ，因为 4, 5 和 7 出现频率最高，但 4 是最接近顶部的。堆栈变成 [5,7]。
 *
 * </p>
 *
 * <p>
 *     方式1:PriorityQueue<int[]> queue 作为数据存储, int[0]: 数字出现的频率, 入栈的index, 不对, 入栈会有多个index
 *     方式1存在问题
 * </p>
 *
 * <p>
 *     直接看官解: AC:37ms/54.59MB
 * </p>
 * @author marks
 * @version v1.0
 * @date 2025/1/17 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class FreqStack {
    private Map<Integer, Integer> map;
    private Map<Integer, Deque<Integer>> group;
    private int maxFreq;
    public FreqStack() {
        map = new HashMap<>();
        group = new HashMap<>();
        maxFreq = 0;
    }

    public void push(int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
        group.putIfAbsent(map.get(val), new ArrayDeque<>());
        group.get(map.get(val)).push(val);
        maxFreq = Math.max(maxFreq, map.get(val));
    }

    public int pop() {
        int curr_value = group.get(maxFreq).peek();
        map.put(curr_value, map.get(curr_value) - 1);
        group.get(maxFreq).pop();
        if (group.get(maxFreq).isEmpty()) {
            maxFreq--;
        }
        return curr_value;
    }
}
