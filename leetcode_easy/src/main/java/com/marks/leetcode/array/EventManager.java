package com.marks.leetcode.array;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: EventManager </p>
 * <p>描述: LeetCode_3885. 设计事件管理器 </p>
 * 给你一组初始事件列表，其中每个事件有一个唯一的 eventId 和一个 priority（优先级）。
 * 实现 EventManager 类：
 * EventManager(int[][] events) 使用给定事件初始化管理器，其中 events[i] = [eventIdi, priorityi]。
 * void updatePriority(int eventId, int newPriority) 更新具有 id 为 eventId 的 活跃 事件的优先级为 newPriority。
 * int pollHighest() 移除并返回具有 最高优先级 的 活跃事件 的 eventId。
 * 如果有多个活动事件具有相同的优先级，则返回 eventId 最小的事件。如果没有活跃事件，则返回 -1。
 * 如果一个事件没有被 pollHighest() 移除，则称其为 活跃事件。
 *
 * tips:
 * 1 <= events.length <= 10^5
 * events[i] = [eventId, priority]
 * 1 <= eventId <= 10^9
 * 1 <= priority <= 10^9
 * events 中的所有 eventId 值都是 唯一的 。
 * 1 <= newPriority <= 10^9
 * 对每次调用 updatePriority，eventId 都指向一个 活跃事件。
 * 对 updatePriority 和 pollHighest 的总调用次数最多为 10^5 次。
 * @author marks
 * @version v1.0
 * @date 2026/7/6 17:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class EventManager {
    private final Map<Integer, Integer> map;
    private final PriorityQueue<int[]> pq;
    private final PriorityQueue<int[]> lazyPq;

    /**
     * @Description:
     * 1. 使用 map 存储 eventId 和 priority
     * 2. 创建2个优先队列, 分别 pq 和 lazyPq, 用于存储 优先级和延迟删除队列
     * AC: 230ms/216.51MB
     * @param: events
     * @return
     * @author marks
     * @CreateDate: 2026/07/06 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public EventManager(int[][] events) {
        map = new HashMap<>();
        // 优先队列 {eventId, priority}, 对 priority 降序排序, 返回最大的 priority, 并且对 eventId 升序排序, 返回最小的 eventId
        pq = new PriorityQueue<>((a, b) -> a[1] != b[1] ? b[1] - a[1] : a[0] - b[0]);
        lazyPq = new PriorityQueue<>((a, b) -> a[1] != b[1] ? b[1] - a[1] : a[0] - b[0]);
        // 添加事件
        for (int[] event : events) {
            map.put(event[0], event[1]);
            pq.offer(event);
        }
    }

    public void updatePriority(int eventId, int newPriority) {
        // 获取旧的优先级
        int oldPriority = map.get(eventId);
        // 将旧的优先级添加到延迟删除队列中
        if (oldPriority != newPriority) {
            lazyPq.offer(new int[]{eventId, oldPriority});
            map.put(eventId, newPriority);
            pq.offer(new int[]{eventId, newPriority});
        }
        // 如果相同不进行添加操作
    }

    public int pollHighest() {
        // 先对 pq 与 lazyPq 进行判断, 如果两者栈顶元素相同, 则进行删除操作
        while (!pq.isEmpty() && !lazyPq.isEmpty()) {
            int[] pqTop = pq.peek();
            int[] lazyPqTop = lazyPq.peek();
            if (pqTop[0] == lazyPqTop[0] && pqTop[1] == lazyPqTop[1]) {
                // 优先级相同 并且 eventId 相同, 则进行删除操作
                pq.poll();
                lazyPq.poll();
            } else {
                // 跳出循环
                break;
            }
        }
        // 获取 pq 栈顶元素
        if (!pq.isEmpty()) {
            return pq.poll()[0];
        }
        return -1;
    }

}
