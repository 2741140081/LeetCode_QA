package com.marks.leetcode.data_structure.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: 最近的请求次数 </p>
 * <p>文件名称: LeetCode_933  </p>
 * <p>描述: 写一个 RecentCounter 类来计算特定时间范围内最近的请求。
 * 请你实现 RecentCounter 类：
 * RecentCounter() 初始化计数器，请求数为 0 。
 * int ping(int t) 在时间 t 添加一个新请求，其中 t 表示以毫秒为单位的某个时间，并返回过去 3000 毫秒内发生的所有请求数（包括新请求）。
 * 确切地说，返回在 [t-3000, t] 内发生的请求数。
 * 保证 每次对 ping 的调用都使用比之前更大的 t 值。
 * </p>
 * <p>AC:20ms/53.07MB </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/22 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class RecentCounter {
    private Queue<Integer> queue;

    public RecentCounter() {
        queue = new LinkedList<>();
    }

    public int ping(int t) {
        queue.offer(t);
        int value = t - 3000;
        while (queue.peek() < value) {
            queue.poll();
        }
        return queue.size();
    }
}
