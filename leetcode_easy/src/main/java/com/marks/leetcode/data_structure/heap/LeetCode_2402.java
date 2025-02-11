package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/11 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2402 {

    /**
     * @Description:
     * 给你一个整数 n ，共有编号从 0 到 n - 1 的 n 个会议室。
     *
     * 给你一个二维整数数组 meetings ，其中 meetings[i] = [starti, endi] 表示一场会议将会在 半闭 时间区间 [starti, endi) 举办。所有 starti 的值 互不相同 。
     *
     * 会议将会按以下方式分配给会议室：
     *
     * 每场会议都会在未占用且编号 最小 的会议室举办。
     * 如果没有可用的会议室，会议将会延期，直到存在空闲的会议室。延期会议的持续时间和原会议持续时间 相同 。
     * 当会议室处于未占用状态时，将会优先提供给原 开始 时间更早的会议。
     * 返回举办最多次会议的房间 编号 。如果存在多个房间满足此条件，则返回编号 最小 的房间。
     *
     * 半闭区间 [a, b) 是 a 和 b 之间的区间，包括 a 但 不包括 b 。
     * @param n
     * @param meetings
     * @return int
     * @author marks
     * @CreateDate: 2025/2/11 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int mostBooked(int n, int[][] meetings) {
        int result;
        result = method_01(n, meetings);
        return result;
    }

    /**
     * @Description:
     * 提炼信息
     * 1. 如果room不为空, 选择id最小的room
     * 2. 需要一个变量 time 记录时间, 方便跳时间, pre[] 记录room_id的使用次数
     * 3. 还需要一个优先队列, 存储正在举行当中的会议 meeting<int[]>, 存储结束时间 end_time 升序排序, room_id.
     * 4. queue 存储meetings信息, {原始开始时间, 持续时间}
     * 5. time 需要合理的改变为Math.max(end_time, next_meeting_start_time, time)
     *
     * 初步debug, 发现问题
     * Q1: time的跳转会产生问题, room 不为空,
     *
     * AC: 143ms/94.75MB
     * @param n
     * @param meetings
     * @return int
     * @author marks
     * @CreateDate: 2025/2/11 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] meetings) {
        int[] pre = new int[n];
        Queue<Integer> room = new PriorityQueue<>(Comparator.comparingInt(o -> o));
        for (int i = 0; i < n; i++) {
            room.offer(i);
        }

        Queue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        for (int[] meeting : meetings) {
            queue.offer(new int[] {meeting[0], meeting[1], meeting[1] - meeting[0]});
        }

        Queue<long[]> meeting = new PriorityQueue<>(Comparator.comparingLong(o -> o[0])); // 正在举行当中的会议, end_time 升序

        long time = queue.peek()[0];
        int ans = -1;
        int count = 0;
        while (!queue.isEmpty()) {
            time = Math.max(time, queue.peek()[0]);

            // 取出结束的会议, 并且跳转time, 释放room_id
            while (!meeting.isEmpty() && meeting.peek()[0] <= time) {
                long[] end_meet = meeting.poll();
                time = Math.max(time, end_meet[0]);
                room.offer((int) end_meet[1]);
            }
            // 将空的会议室填满
            while (!room.isEmpty() && !queue.isEmpty() && queue.peek()[0] <= time) {
                int room_id = room.poll();
                int[] curr_meet = queue.poll();
                long end_time = time + curr_meet[2];
                meeting.offer(new long[]{end_time, room_id});
                pre[room_id]++;
                if (pre[room_id] > count || (pre[room_id] == count && room_id < ans)) {
                    ans = room_id;
                    count = Math.max(count, pre[room_id]);
                }
            }

            if (room.isEmpty()) {
                long[] end_meet = meeting.poll();
                time = Math.max(time, end_meet[0]);
                room.offer((int) end_meet[1]);
            }
        }
        // meeting 中正在举行的会议, 不会影响pre[]

        return ans;
    }
}
