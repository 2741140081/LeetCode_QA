package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/8 11:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1942 {

    /**
     * @Description:
     * 有 n 个朋友在举办一个派对，这些朋友从 0 到 n - 1 编号。派对里有 无数 张椅子，编号为 0 到 infinity 。当一个朋友到达派对时，他会占据 编号最小 且未被占据的椅子。
     *
     * 比方说，当一个朋友到达时，如果椅子 0 ，1 和 5 被占据了，那么他会占据 2 号椅子。
     * 当一个朋友离开派对时，他的椅子会立刻变成未占据状态。如果同一时刻有另一个朋友到达，可以立即占据这张椅子。
     *
     * 给你一个下标从 0 开始的二维整数数组 times ，其中 times[i] = [arrivali, leavingi] 表示第 i 个朋友到达和离开的时刻，同时给你一个整数 targetFriend 。所有到达时间 互不相同 。
     *
     * 请你返回编号为 targetFriend 的朋友占据的 椅子编号 。
     *
     * tips:
     * n == times.length
     * 2 <= n <= 10^4
     * times[i].length == 2
     * 1 <= arrivali < leavingi <= 10^5
     * 0 <= targetFriend <= n - 1
     * 每个 arrivali 时刻 互不相同
     * @param times
     * @param targetFriend
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 14:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestChair(int[][] times, int targetFriend) {
        int result;
        result = method_01(times, targetFriend);
        return result;
    }

    /**
     * @Description:
     * AC: 97ms/49.70MB
     * 优化一下time的取值, 不是time++, 而是改成取下一个queue.peek()[0], 并且将release.peek()[0] <= time 中的seat_number释放
     * AC: 81ms/49.75MB
     * @param times
     * @param targetFriend
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 14:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] times, int targetFriend) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        PriorityQueue<int[]> release = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        PriorityQueue<Integer> seat = new PriorityQueue<>(Comparator.comparingInt(o -> o));
        for (int i = 0; i < times.length; i++) {
            queue.offer(new int[] {times[i][0], times[i][1], i});
            seat.offer(i);
        }
        int time = queue.peek()[0];
        while (!queue.isEmpty()) {
            // 离开时间可能相同
            while (!release.isEmpty() && release.peek()[0] <= time) {
                seat.offer(release.poll()[1]);
            }

            int[] curr_node = queue.poll();
            if (targetFriend == curr_node[2]) {
                return seat.poll();
            }
            release.offer(new int[]{curr_node[1], seat.poll()}); // {leaving_i, i, seat_number}, 这个好像不需要i, 因为已经与i无关
            time = queue.peek()[0];
        }
        return -1;
    }
}
