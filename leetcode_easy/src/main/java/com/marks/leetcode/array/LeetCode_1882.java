package com.marks.leetcode.array;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1882 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/30 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1882 {

    /**
     * @Description: 
     * 给你两个 下标从 0 开始 的整数数组 servers 和 tasks ，长度分别为 n 和 m 。
     * servers[i] 是第 i 台服务器的 权重 ，而 tasks[j] 是处理第 j 项任务 所需要的时间（单位：秒）。
     * 你正在运行一个仿真系统，在处理完所有任务后，该系统将会关闭。每台服务器只能同时处理一项任务。
     * 第 0 项任务在第 0 秒可以开始处理，相应地，第 j 项任务在第 j 秒可以开始处理。
     * 处理第 j 项任务时，你需要为它分配一台 权重最小 的空闲服务器。如果存在多台相同权重的空闲服务器，请选择 下标最小 的服务器。
     * 如果一台空闲服务器在第 t 秒分配到第 j 项任务，那么在 t + tasks[j] 时它将恢复空闲状态。
     * 如果没有空闲服务器，则必须等待，直到出现一台空闲服务器，并 尽可能早 地处理剩余任务。
     * 如果有多项任务等待分配，则按照 下标递增 的顺序完成分配。
     * 如果同一时刻存在多台空闲服务器，可以同时将多项任务分别分配给它们。
     * 构建长度为 m 的答案数组 ans ，其中 ans[j] 是第 j 项任务分配的服务器的下标。
     * 返回答案数组 ans 。
     * tips:
     * servers.length == n
     * tasks.length == m
     * 1 <= n, m <= 2 * 10^5
     * 1 <= servers[i], tasks[j] <= 2 * 10^5
     * @param: servers
     * @param: tasks
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/30 16:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] assignTasks(int[] servers, int[] tasks) {
        int[] result;
        result = method_01(servers, tasks);
        return result;
    }

    /**
     * @Description:
     * 1. 需要使用优先队列存储服务器信息, 下一个空闲时间, 服务器权重, 服务器下标
     * 2. 任务 i, 需要在第 i 秒才能开始处理,
     * 3. 应该需要两个优先队列, 一个是处理任务, 还有一个是空闲队列, 都存储服务器信息
     * 4. 这个 startTime 需要提取出来, 是一个外部局部变量, 而不是每次 i 都重新赋值, startTime 相当于 currTime
     * AC: 237ms/172.54MB
     * @param: servers
     * @param: tasks
     * @return int[]
     * @author marks
     * @CreateDate: 2026/06/30 16:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] servers, int[] tasks) {
        int m = tasks.length, n = servers.length;
        int[] ans = new int[m];
        // 创建优先队列, 任务处理队列, 按照 任务处理完成时间 进行升序排序, {endTime, weight, index}
        PriorityQueue<int[]> processingQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        // 创建服务器空闲队列, 根据权重 > 下标 升序排序, {weight, index}
        PriorityQueue<int[]> idleQueue = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        // 将所有服务器添加到空闲队列中
        for (int i = 0; i < n; i++) {
            idleQueue.offer(new int[]{servers[i], i});
        }
        // 处理任务
        int startTime = 0;
        for (int i = 0; i < m; i++) {
            startTime = Math.max(startTime, i);
            // 先取出任务处理队列中已经处理完成的任务
            while (!processingQueue.isEmpty() && processingQueue.peek()[0] <= startTime) {
                int[] task = processingQueue.poll();
                idleQueue.offer(new int[]{task[1], task[2]});
            }
            // 判断是否有空闲服务器
            if (idleQueue.isEmpty()) {
                // 没有空闲服务器, 则需要等待处理队列中栈顶元素弹出
                startTime = processingQueue.peek()[0];
                while (!processingQueue.isEmpty() && processingQueue.peek()[0] <= startTime) {
                    int[] task = processingQueue.poll();
                    idleQueue.offer(new int[]{task[1], task[2]});
                }
            }
            // 取出空闲队列的栈顶元素, 添加到任务处理队列中
            int[] idleServer = idleQueue.poll();
            processingQueue.offer(new int[]{startTime + tasks[i], idleServer[0], idleServer[1]});
            ans[i] = idleServer[1];
        }

        return ans;
    }

}
