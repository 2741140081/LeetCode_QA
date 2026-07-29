package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1606 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/29 15:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1606 {

    /**
     * @Description:
     * 你有 k 个服务器，编号为 0 到 k-1 ，它们可以同时处理多个请求组。
     * 每个服务器有无穷的计算能力但是 不能同时处理超过一个请求 。请求分配到服务器的规则如下：
     * 第 i （序号从 0 开始）个请求到达。
     * 如果所有服务器都已被占据，那么该请求被舍弃（完全不处理）。
     * 如果第 (i % k) 个服务器空闲，那么对应服务器会处理该请求。
     * 否则，将请求安排给下一个空闲的服务器（服务器构成一个环，必要的话可能从第 0 个服务器开始继续找下一个空闲的服务器）。
     * 比方说，如果第 i 个服务器在忙，那么会查看第 (i+1) 个服务器，第 (i+2) 个服务器等等。
     * 给你一个 严格递增 的正整数数组 arrival ，表示第 i 个任务的到达时间，和另一个数组 load ，其中 load[i] 表示第 i 个请求的工作量（也就是服务器完成它所需要的时间）。
     * 你的任务是找到 最繁忙的服务器 。最繁忙定义为一个服务器处理的请求数是所有服务器里最多的。
     * 请你返回包含所有 最繁忙服务器 序号的列表，你可以以任意顺序返回这个列表。
     *
     * tips:
     * 1 <= k <= 10^5
     * 1 <= arrival.length, load.length <= 10^5
     * arrival.length == load.length
     * 1 <= arrival[i], load[i] <= 10^9
     * arrival 保证 严格递增 。
     * @param: k
     * @param: arrival
     * @param: load
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/29 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        List<Integer> result;
        result = method_01(k, arrival, load);
        return result;
    }

    /**
     * @Description:
     * 1. 使用优先队列存储服务器和完成任务的时间
     * 2. 现在一个关键点在于如何快速找到下一个空间服务器节点, 使用有序集合 TreeSet<Integer> free 维护空闲列表,
     * 查找 int j = i % k, 需要查找 j 服务器是否空间, 查找Integer next = free.higher(j - 1); if next == null => return free.first();(返回环路的起点)
     * 3. 现在问题以及清晰了, 使用优先队列处理服务器占用, 使用 TreeSet 有序集合处理下一个空闲服务器
     * AC: 129ms/96.02MB
     * @param: k
     * @param: arrival
     * @param: load
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/07/29 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int k, int[] arrival, int[] load) {
        // 记录每个服务器处理的请求数量
        int[] requestCount = new int[k];

        // 优先队列，按服务器完成时间排序，存储[完成时间, 服务器编号]
        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // TreeSet维护空闲服务器
        TreeSet<Integer> free = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            free.add(i);
        }

        // 处理每个请求
        for (int i = 0; i < arrival.length; i++) {
            int time = arrival[i];
            int duration = load[i];

            // 释放已完成的服务器
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] server = busy.poll();
                free.add(server[1]);
            }

            // 如果没有空闲服务器，跳过该请求
            if (free.isEmpty()) {
                continue;
            }

            // 寻找下一个可用服务器
            int j = i % k;
            Integer next = free.higher(j - 1);
            if (next == null) {
                next = free.first();
            }

            // 分配服务器
            free.remove(next);
            busy.offer(new int[]{time + duration, next});
            requestCount[next]++;
        }

        // 找出处理请求数最多的服务器
        int maxRequests = 0;
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] > maxRequests) {
                maxRequests = requestCount[i];
                result.clear();
                result.add(i);
            } else if (requestCount[i] == maxRequests) {
                result.add(i);
            }
        }

        return result;
    }


}
