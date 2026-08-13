package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1353 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/1 8:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1353 {

    /**
     * @Description:
     * 给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表示会议 i 开始于 startDayi ，结束于 endDayi 。
     * 你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。在任意一天 d 中只能参加一场会议。
     * 请你返回你可以参加的 最大 会议数目。
     * 1 <= events.length <= 10^5
     * events[i].length == 2
     * 1 <= startDayi <= endDayi <= 10^5
     * @param: events
     * @return int
     * @author marks
     * @CreateDate: 2026/07/01 8:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxEvents(int[][] events) {
        int result;
        result = method_01(events);
        result = method_02(events);
        return result;
    }

    /**
     * @Description:
     * 1. 官方题解
     * AC: 71ms/101.13MB
     * @param: events
     * @return int
     * @author marks
     * @CreateDate: 2026/07/01 9:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] events) {
        int n = events.length;
        int maxDay = 0;
        for (int[] event : events) {
            maxDay = Math.max(maxDay, event[1]);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Arrays.sort(events, (a, b) -> a[0] - b[0]);
        int ans = 0;
        for (int i = 1, j = 0; i <= maxDay; i++) {
            while (j < n && events[j][0] <= i) {
                // 当前会议 events[i][0] <= i <= events[i][1], 则放入pq 中
                pq.offer(events[j][1]);
                j++;
            }
            while (!pq.isEmpty() && pq.peek() < i) {
                // 取出过期的会议, 由于 endDay < i, 这些会议已经过期了
                pq.poll();
            }
            if (!pq.isEmpty()) {
                // 取出一个最早结束的会议
                pq.poll();
                ans++;
            }
        }

        return ans;
    }

    /**
     * @Description:
     * 1. 由于 startDay <= endDay, 所以可以对 startDay 进行排序
     * 2. int currDay 为当前天数, 如果当前 currDay > endDay[i], 则 i++
     * 3. currDay <= endDay[i], 更新 currDay, currDay = Math.max(currDay, startDay[i]), 最后 currDay++, i++;
     * 4. 到底应该对 startDay 排序还是 endDay 排序? 先尝试对 startDay 排序, 如果 startDay 相同, 对 endDay 升序排序
     * 错误case: events = [[1,2],[1,2],[3,3],[1,5],[1,5]]
     * 5. 考虑的太片面了, 还是应该考虑贪心, 应该使用优先队列, 然后需要更新case 中的 startDay, 保留 endDay, 在优先队列中对两者都是升序排序, 但是 startDay > endDay;
     * 6. currDay <= endDay, 需要考虑 currDay 与 startDay 的大小关系
     * 6.1 currDay <= startDay, 更新 currDay = startDay + 1, ans++;
     * 6.2 currDay > startDay, 则更新当前 event = {currDay, endDay}, 将 event 从新插入 pq 中
     * 超时, 通过的测试用例: 42/45, 看看官方题解吧
     * @param: events
     * @return int
     * @author marks
     * @CreateDate: 2026/07/01 8:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] events) {
        // 创建优先队列, {startDay, endDay} 升序排序, 优先级 startDay > endDay
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        // 将 events 数据存入 pq 中
        for (int[] event : events) {
            pq.offer(event);
        }

        int currDay = 1;
        int ans = 0;

        while (!pq.isEmpty()) {
            int[] event = pq.poll();
            int startDay = event[0], endDay = event[1];
            if (currDay <= endDay) {
                // currDay <= endDay
                if (currDay <= startDay) {
                    currDay = startDay + 1;
                    ans++;
                } else {
                    // currDay > startDay, 重新入队排序
                    pq.offer(new int[]{currDay, endDay});
                }
            }
        }

        return ans;
    }

}
