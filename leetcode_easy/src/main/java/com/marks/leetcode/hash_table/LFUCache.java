package com.marks.leetcode.hash_table;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LFUCache </p>
 * <p>描述: [
 * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
 * 实现 LFUCache 类：
 * LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
 * int get(int key) - 如果键 key 存在于缓存中，则获取键的值，否则返回 -1 。
 * void put(int key, int value) - 如果键 key 已存在，则变更其值；如果键不存在，请插入键值对。
 * 当缓存达到其容量 capacity 时，则应该在插入新项之前，移除最不经常使用的项。
 * 在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最久未使用 的键。
 * 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
 * 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。
 *
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 *
 * tips:
 * 1 <= capacity <= 10^4
 * 0 <= key <= 10^5
 * 0 <= value <= 10^9
 * 最多调用 2 * 10^5 次 get 和 put 方法
 *
 * ] </p>
 * 1. 需要一个计算器, 每一个计数器 id 值 对于 一个 key
 * 2. 对于最久未使用的 key, 如何确定? 假设 有 x 个 key, 他们的 cnt 值相同, 那么如何确定 最久未使用的 key?
 * 3. 好像是没有思路, 如何存储计数于 key 的关系?
 * 4. 应该需要一个数据结构, int index = 0; 作为每一个值进行put 操作时 index++;
 * 5. 需要有一个数据结构, 存储 cnt 和 index 以及 key, 首先需要对 cnt 进行升序排序, cnt 值相同时, 对 index 进行升序排序, 找到 index 最小的 key
 * need todo
 * @author marks
 * @version v1.0
 * @date 2026/5/20 15:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LFUCache {

    private Map<Integer, Integer> cache;
    private int maxCapacity;
    private int index;

    public LFUCache(int capacity) {
        maxCapacity = capacity;
        index = 0;
        cache = new HashMap<>();
    }

    public int get(int key) {
        return 0;
    }

    public void put(int key, int value) {

    }

}

class Node implements Comparable<Node>{
    private int cnt;
    private int index;
    private int key;
    private int value;

    @Override
    public int compareTo(Node o) {
        return this.cnt == o.cnt ? this.index - o.index : this.cnt - o.cnt;
    }
}