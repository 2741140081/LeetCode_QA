package com.marks.leetcode.data_structure.queue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: 设计自助结算系统 </p>
 * <p>文件名称: LCR_184 </p>
 * <p>描述:
 * 请设计一个自助结账系统，该系统需要通过一个队列来模拟顾客通过购物车的结算过程，需要实现的功能有：
 *
 * get_max()：获取结算商品中的最高价格，如果队列为空，则返回 -1
 * add(value)：将价格为 value 的商品加入待结算商品队列的尾部
 * remove()：移除第一个待结算的商品价格，如果队列为空，则返回 -1
 * 注意，为保证该系统运转高效性，以上函数的均摊时间复杂度均为 O(1)
 * </p>
 *
 * <p> AC: 17ms/50.30MB </p>
 * @author marks
 * @version v1.0
 * @date 2025/2/7 11:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Checkout {
    private Deque<Integer> deque;
    private Queue<Integer> queue;

    public Checkout() {
        deque = new LinkedList<>();
        queue = new LinkedList<>();
    }

    public int get_max() {
        if (!deque.isEmpty()) {
            return deque.peekFirst();
        } else {
            return -1;
        }
    }

    public void add(int value) {
        while (!deque.isEmpty() && deque.peekLast() <= value) {
            deque.pollLast();
        }
        deque.offerLast(value);
        queue.offer(value);
    }

    public int remove() {
        if (queue.isEmpty()) {
            return -1;
        }
        int ans = queue.poll();
        if (ans == deque.peekFirst()) {
            deque.pollFirst();
        }
        return ans;
    }
}
