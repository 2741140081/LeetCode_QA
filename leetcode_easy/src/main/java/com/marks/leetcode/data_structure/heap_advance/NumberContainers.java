package com.marks.leetcode.data_structure.heap_advance;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * <p>项目名称: 设计数字容器系统 </p>
 * <p>文件名称: LeetCode_2349 </p>
 * <p>描述:
 * 设计一个数字容器系统，可以实现以下功能：
 *
 * 在系统中给定下标处 插入 或者 替换 一个数字。
 * 返回 系统中给定数字的最小下标。
 * 请你实现一个 NumberContainers 类：
 *
 * NumberContainers() 初始化数字容器系统。
 * void change(int index, int number) 在下标 index 处填入 number 。如果该下标 index 处已经有数字了，那么用 number 替换该数字。
 * int find(int number) 返回给定数字 number 在系统中的最小下标。如果系统中没有 number ，那么返回 -1 。
 * </p>
 *
 * change(index, number): 可以用Map实现
 *
 * find(number): 优先队列找到最小下标
 *
 * 但是 change() 会改变
 * 1. 如果 number不变, index 改变, 会影响 find 的查询
 * 2. 如果 index 不变, number 改变, 也会影响 find 的查询
 *
 * 既然 change() 方法会影响 find 的查询结果, 那么干脆每次执行完 change 之后, 整体重新遍历所有的数据,
 * 以便 find() 需要的数据是正确的
 *
 * 基于以上的思路,
 * 1. change(), 需要用一个Map<Integer, Integer>, 存储 key = index, value = number
 * 2. find(), Map<num, queue>
 *
 * 我这个优先队列是一样的,
 * AC: 88ms/94.96MB
 *
 * 但是更好的是用 TreeSet 来替代 PriorityQueue
 * @author marks
 * @version v1.0
 * @date 2025/3/7 15:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NumberContainers {
    private Map<Integer, Integer> map;
    private Map<Integer, PriorityQueue<Integer>> queueMap;

    public NumberContainers() {
        map = new HashMap<>();
        queueMap = new HashMap<>();
    }

    public void change(int index, int number) {
        if (map.containsKey(index) && map.get(index) != number) {
            int pre_value = map.get(index);
            map.put(index, number);
            PriorityQueue<Integer> queue = queueMap.get(pre_value);
            PriorityQueue<Integer> curr_queue = new PriorityQueue<>();
            while (!queue.isEmpty()) {
                if (queue.peek() != index) {
                    curr_queue.offer(queue.poll());
                } else {
                    queue.poll();
                }
            }
            if (!curr_queue.isEmpty()) {
                queueMap.put(pre_value, curr_queue);
            } else {
                queueMap.remove(pre_value);
            }

            if (queueMap.containsKey(number)) {
                queueMap.get(number).offer(index);
            } else {
                queueMap.put(number, new PriorityQueue<>());
                queueMap.get(number).offer(index);
            }

        } else if (!map.containsKey(index)){
            map.put(index, number);
            if (queueMap.containsKey(number)) {
                queueMap.get(number).offer(index);
            } else {
                queueMap.put(number, new PriorityQueue<>());
                queueMap.get(number).offer(index);
            }
        }
    }

    public int find(int number) {
        if (queueMap.containsKey(number) && !queueMap.get(number).isEmpty()) {
            return queueMap.get(number).peek();
        } else {
            return -1;
        }
    }
}
