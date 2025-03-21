package com.marks.leetcode.data_structure.queue;

import java.util.Arrays;

/**
 * <p>项目名称: 设计循环队列 </p>
 * <p>文件名称: LeetCode_622 </p>
 * <p>描述:
 * 设计你的循环队列实现。 循环队列是一种线性数据结构，其操作表现基于 FIFO（先进先出）原则并且队尾被连接在队首之后以形成一个循环。它也被称为“环形缓冲器”。
 * 循环队列的一个好处是我们可以利用这个队列之前用过的空间。在一个普通队列里，一旦一个队列满了，我们就不能插入下一个元素，即使在队列前面仍有空间。但是使用循环队列，我们能使用这些空间去存储新的值。
 * 你的实现应该支持如下操作：
 *
 * MyCircularQueue(k): 构造器，设置队列长度为 k 。
 * Front: 从队首获取元素。如果队列为空，返回 -1 。
 * Rear: 获取队尾元素。如果队列为空，返回 -1 。
 * enQueue(value): 向循环队列插入一个元素。如果成功插入则返回真。
 * deQueue(): 从循环队列中删除一个元素。如果成功删除则返回真。
 * isEmpty(): 检查循环队列是否为空。
 * isFull(): 检查循环队列是否已满。
 * </p>
 * <p> AC: 5ms/43.95MB </p>
 * @author marks
 * @version v1.0
 * @date 2025/1/22 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyCircularQueue {
    private int head = 0;
    private int tail = 0;
    private int[] array;

    private int n;

    public MyCircularQueue(int k) {
        array = new int[k];
        n = k;
        Arrays.fill(array, -1);
    }

    /**
     * @Description:
     * 向循环队列插入一个元素。如果成功插入则返回真。
     * @param value
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/22 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean enQueue(int value) {
        if (array[tail] == -1) {
            array[tail] = value;
            tail = (tail + 1) % n;
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description:
     * 从循环队列中删除一个元素。如果成功删除则返回真。
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/22 17:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean deQueue() {
        if (array[head] == -1) {
            return false;
        } else {
            array[head] = -1;
            head = (head + 1) % n;
            return true;
        }
    }

    /**
     * @Description:
     * 从队首获取元素。如果队列为空，返回 -1 。
     * @return int
     * @author marks
     * @CreateDate: 2025/1/22 17:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int Front() {
        return array[head];
    }

    public int Rear() {
        if (tail == 0) {
            return array[n - 1];
        } else {
            return array[tail - 1];
        }
    }

    public boolean isEmpty() {
        return array[head] == -1;
    }

    public boolean isFull() {
        return (head == tail && array[head] != -1);
    }
}
