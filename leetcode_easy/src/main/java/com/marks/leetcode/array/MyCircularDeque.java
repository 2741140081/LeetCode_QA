package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MyCircularDeque </p>
 * <p>描述: LeetCode_641 </p>
 * 设计实现双端队列。
 * 实现 MyCircularDeque 类:
 * MyCircularDeque(int k) ：构造函数,双端队列最大为 k 。
 * boolean insertFront()：将一个元素添加到双端队列头部。 如果操作成功返回 true ，否则返回 false 。
 * boolean insertLast() ：将一个元素添加到双端队列尾部。如果操作成功返回 true ，否则返回 false 。
 * boolean deleteFront() ：从双端队列头部删除一个元素。 如果操作成功返回 true ，否则返回 false 。
 * boolean deleteLast() ：从双端队列尾部删除一个元素。如果操作成功返回 true ，否则返回 false 。
 * int getFront() )：从双端队列头部获得一个元素。如果双端队列为空，返回 -1 。
 * int getRear() ：获得双端队列的最后一个元素。 如果双端队列为空，返回 -1 。
 * boolean isEmpty() ：若双端队列为空，则返回 true ，否则返回 false  。
 * boolean isFull() ：若双端队列满了，则返回 true ，否则返回 false 。
 *
 * tips:
 * 1 <= k <= 1000
 * 0 <= value <= 1000
 * insertFront, insertLast, deleteFront, deleteLast, getFront, getRear, isEmpty, isFull  调用次数不大于 2000 次
 * @author marks
 * @version v1.0
 * @date 2026/7/23 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyCircularDeque {
    private int[] arr;
    private int head;
    private int tail;
    private int n;
    private int count;

    /**
     * @Description:
     * 1. 由于双端队列的大小是固定的, 所以可以使用一个数组来实现
     * 2. 由于需要从首尾都可以执行插入, 需要两个指针分别指向待插入的首尾部分
     * 3. 由于 value >= 0, 所以初始化时, 数组中的元素都初始化为 -1
     * AC: 5ms/45.8MB
     * @param: k
     * @return
     * @author marks
     * @CreateDate: 2026/07/23 10:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public MyCircularDeque(int k) {
        arr = new int[k];
        Arrays.fill(arr, -1);
        head = 0;
        tail = k - 1;
        n = k;
        count = 0;
    }

    public boolean insertFront(int value) {
        // 判断 arr[head] == -1
        if (arr[head] == -1) {
            arr[head] = value;
            head = (head + 1) % n;
            count++;
            return true;
        }

        return false;
    }

    public boolean insertLast(int value) {
        if (arr[tail] == -1) {
            arr[tail] = value;
            tail = (tail - 1 + n) % n;
            count++;
            return true;
        }
        return false;
    }

    public boolean deleteFront() {
        int idx = (head - 1 + n) % n;
        if (arr[idx] != -1) {
            arr[idx] = -1;
            head = (head - 1 + n) % n;
            count--;
            return true;
        }

        return false;
    }

    public boolean deleteLast() {
        int idx = (tail + 1) % n;
        if (arr[idx] != -1) {
            arr[idx] = -1;
            tail = (tail + 1) % n;
            count--;
            return true;
        }
        return false;
    }

    public int getFront() {
        int idx = (head - 1 + n) % n;
        return arr[idx];
    }

    public int getRear() {
        int idx = (tail + 1) % n;
        return arr[idx];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == n;
    }

}
