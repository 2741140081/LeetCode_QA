package com.marks.leetcode.data_structure.heap;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/10 14:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1834 {

    /**
     * @Description:
     * 给你一个二维数组 tasks ，用于表示 n 项从 0 到 n - 1 编号的任务。其中 tasks[i] = [enqueueTimei, processingTimei]
     * 意味着第 i 项任务将会于 enqueueTimei 时进入任务队列，需要 processingTimei 的时长完成执行。
     *
     * 现有一个单线程 CPU ，同一时间只能执行 最多一项 任务，该 CPU 将会按照下述方式运行：
     *
     * 如果 CPU 空闲，且任务队列中没有需要执行的任务，则 CPU 保持空闲状态。
     * 如果 CPU 空闲，但任务队列中有需要执行的任务，则 CPU 将会选择 执行时间最短 的任务开始执行。
     * 如果多个任务具有同样的最短执行时间，则选择下标最小的任务开始执行。
     *
     * 一旦某项任务开始执行，CPU 在 执行完整个任务 前都不会停止。
     * CPU 可以在完成一项任务后，立即开始执行一项新任务。
     * 返回 CPU 处理任务的顺序。
     *
     * tips:
     * tasks.length == n
     * 1 <= n <= 10^5
     * 1 <= enqueueTimei, processingTimei <= 10^9
     * @param tasks
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/10 14:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] getOrder(int[][] tasks) {
        int[] result;
        result = method_01(tasks);
        return result;
    }

    /**
     * @Description:
     * 犯了一个错误, 结束时间是错误的, 因为开始的时间是time, 原本开始时间是start, 结束是end, 但是真正的结束时间是end = time + spend
     * 设置 time = Math.max(time, start); 取开始的最大值
     * AC: 209ms/93.82MB
     * @param tasks
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/10 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] tasks) {
        int n = tasks.length;
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                if (o1[1] != o2[1]) {
                    return o1[1] - o2[1];
                } else {
                    return o1[2] - o2[2];
                }
            }
        });

        PriorityQueue<int[]> wait = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0];
            } else {
                return o1[1] - o2[1];
            }
        });

        for (int i = 0; i < n; i++) {
            queue.offer(new int[]{tasks[i][0], tasks[i][1], i});
        }

        List<Integer> list = new ArrayList<>();
        int time = queue.peek()[0];
        while (!queue.isEmpty()) {
            while (wait.isEmpty() || (!queue.isEmpty() && queue.peek()[0] <= time)) {
                int[] curr_node = queue.poll();
                wait.offer(new int[]{curr_node[1], curr_node[2], curr_node[0]});
            }
            if (!wait.isEmpty()) {
                int[] cpu = wait.poll();
                time = Math.max(time, cpu[2]);
                time = time + cpu[0];
                list.add(cpu[1]);
            }
        }
        while (!wait.isEmpty()) {
            list.add(wait.poll()[1]);
        }
        int[] ans = list.stream().mapToInt(Integer::intValue).toArray();
        return ans;
    }
}
