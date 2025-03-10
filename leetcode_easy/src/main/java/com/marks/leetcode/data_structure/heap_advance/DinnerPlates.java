package com.marks.leetcode.data_structure.heap_advance;

import java.util.*;

/**
 * <p>项目名称: 餐盘栈 </p>
 * <p>文件名称: LeetCode_1172 </p>
 * <p>描述:
 * 我们把无限数量 ∞ 的栈排成一行，按从左到右的次序从 0 开始编号。每个栈的的最大容量 capacity 都相同。
 *
 * 实现一个叫「餐盘」的类 DinnerPlates：
 *
 * DinnerPlates(int capacity) - 给出栈的最大容量 capacity。
 * void push(int val) - 将给出的正整数 val 推入 从左往右第一个 没有满的栈。
 * int pop() - 返回 从右往左第一个 非空栈顶部的值，并将其从栈中删除；如果所有的栈都是空的，请返回 -1。
 * int popAtStack(int index) - 返回编号 index 的栈顶部的值，并将其从栈中删除；如果编号 index 的栈是空的，请返回 -1。
 * </p>
 *
 * 思考,
 * 1. 需要栈的容量, 即size()
 * 2. 无数个栈, 我们需要index 来区分不同的栈
 * 3. 我们分开存储已经full的栈和不是 not full 的栈, 但是我们不需要真的将他们分开存储,
 * 4. 而是使用一个优先队列来排序 根据index的值, 也就是优先队列中 queue<Map<index, val>> 这种结构, 根据 map.key 排序, 升序排序
 *
 * pop() 需要优化, 否则这个操作的整体复杂度是 O(n^2)
 * 再去加一个优先队列, 存储非空栈的id, 降序排序
 *
 * 刚刚看了一下, 优先队列感觉没有TreeSet好用
 *
 * AC: 179ms/184.38ms
 *
 * 全部改成用TreeSet来试试看
 * @author marks
 * @version v1.0
 * @date 2025/3/10 10:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class DinnerPlates {
    private int maxSize;
    private int id; // 记录下标, 即将这个 id 分配给每个栈
    private Map<Integer, Deque<Integer>> map;
    private TreeSet<Integer> queue; // 存储未填满的栈的下标, 升序排序
    private TreeSet<Integer> treeSet; // 存储非空栈的下标, 降序排序



    public DinnerPlates(int capacity) {
        id = -1;
        maxSize = capacity;
        map = new HashMap<>();
        queue = new TreeSet<>();
        treeSet = new TreeSet<>(Comparator.reverseOrder());
    }

    /**
     * @Description:
     * 将给出的正整数 val 推入 从左往右第一个 没有满的栈。
     * @param val
     * @return void
     * @author marks
     * @CreateDate: 2025/3/10 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void push(int val) {
        if (!queue.isEmpty()) {
            int curr_id = queue.first();
            map.get(curr_id).push(val);
            if (map.get(curr_id).size() == maxSize) {
                queue.remove(curr_id); // 如果是未满的栈, 从新添加到 queue 中
            }
            treeSet.add(curr_id);
        } else {
            // 添加一个新的栈, 并且分配一个id, 将其保存到 map 中, 如果当前 stack 的 size 小于 maxsize, 将其 id 添加到 queue 中
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(val);
            map.put(++id, stack);
            if (stack.size() < maxSize) {
                queue.add(id);
            }
            treeSet.add(id);
        }
    }

    /**
     * @Description:
     * 返回 从右往左第一个 非空栈顶部的值，并将其从栈中删除；如果所有的栈都是空的，请返回 -1。
     *
     * 这个 while 需要优化, 怎么优化?
     *
     * @return int
     * @author marks
     * @CreateDate: 2025/3/10 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int pop() {
        int ans = -1;
        if (treeSet.size() == 0) {
            return ans;
        }
        int max_id = treeSet.first();
        queue.add(max_id);

        ans = map.get(max_id).poll();
        if (map.get(max_id).size() == 0) {
            treeSet.remove(max_id);
        }
        return ans;
    }

    /**
     * @Description:
     * 返回编号 index 的栈顶部的值，并将其从栈中删除；如果编号 index 的栈是空的，请返回 -1。
     * @param index
     * @return int
     * @author marks
     * @CreateDate: 2025/3/10 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int popAtStack(int index) {
        int ans = -1;
        if (index > id || map.get(index).size() == 0) {
            return ans;
        }
        // 我们可以在 map 中查到 index 对应的栈的信息
        ans = map.get(index).poll();
        queue.add(index);

        if (map.get(index).size() == 0) {
            treeSet.remove(index);
        }
        return ans;
    }
}
