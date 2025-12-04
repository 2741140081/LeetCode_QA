package com.marks.leetcode.hotLC;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LRUCache </p>
 * <p>描述: LeetCode_146. LRU 缓存 </p>
 * <p>
 * 请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
 *
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * AC: 49ms/127.57MB
 *
 * 直接使用LinkedHashMap实现
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/4 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LRUCache {
    private final int capacity;
    private Map<Integer, DLinkedNode> cache;
    private DLinkedNode head, tail;

    /**
     * @Description:
     *  以 正整数 作为容量 capacity 初始化 LRU 缓存
     * @param: capacity
     * @return
     * @author marks
     * @CreateDate: 2025/12/04 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public LRUCache(int capacity) {
        LinkedHashMap<Integer,Integer> map = new LinkedHashMap<>(capacity, 0.75f, true);
        cache = new HashMap<>();
        this.capacity = capacity;
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * @Description:
     * 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
     * 用Map + 队列存储(先进先出), 不能用队列存储, 因为get操作相当于使用了一次, 所以不能用队列存储
     * 1. 使用链表来处理先进先出, 需要一个头节点和尾节点, 双端链表
     * 2. 遇到get(key), map.get(key) => NodeList curr; curr.prev.next = curr.next; curr.next.prev = curr.prev;
     * curr.next = head.next; curr.next.prev = curr; head.next = curr; curr.prev = head;
     * @param: key
     * @return int
     * @author marks
     * @CreateDate: 2025/12/04 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int get(int key) {
        if (cache.containsKey(key)) {
            DLinkedNode curr = cache.get(key);
            int ans = curr.value;
            moveToHead(curr);
            return ans;
        } else {
            return -1;
        }
    }

    private void moveToHead(DLinkedNode curr) {
        removeNode(curr);
        addToHead(curr);
    }

    private void addToHead(DLinkedNode curr) {
        curr.prev = head;
        curr.next = head.next;
        head.next = curr;
        curr.next.prev = curr;
    }

    private void removeNode(DLinkedNode curr) {
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
    }

    /**
     * @Description:
     * 如果关键字 key 已经存在，则变更其数据值 value ；
     * 如果不存在，则向缓存中插入该组 key-value 。
     * 如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
     * @param: key
     * @param: value
     * @return void
     * @author marks
     * @CreateDate: 2025/12/04 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void put(int key, int value) {
        // 检查容量
        if (cache.containsKey(key)) {
            DLinkedNode curr = cache.get(key);
            curr.value = value;
            moveToHead(curr);
        } else {
            int size = cache.size();
            if (size == capacity) {
                // 删除尾部节点
                DLinkedNode moveNode = removeTail();
                cache.remove(moveNode.key);
            }
            // 添加新节点
            DLinkedNode newNode = new DLinkedNode(value, key);
            cache.put(key, newNode);
            addToHead(newNode);
        }
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }

    // 自定义双端链表
    class DLinkedNode {
        private int value;
        private int key;
        private DLinkedNode prev;
        private DLinkedNode next;

        // 空参构造函数
        public DLinkedNode() {

        }

        public DLinkedNode(int value, int key) {
            this.value = value;
            this.key = key;
            this.prev = null;
            this.next = null;
        }
    }

}
