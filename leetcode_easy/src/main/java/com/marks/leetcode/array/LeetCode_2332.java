package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2332 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/26 14:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2332 {

    /**
     * @Description:
     * 给你一个下标从 0 开始长度为 n 的整数数组 buses ，其中 buses[i] 表示第 i 辆公交车的出发时间。
     * 同时给你一个下标从 0 开始长度为 m 的整数数组 passengers ，其中 passengers[j] 表示第 j 位乘客的到达时间。
     * 所有公交车出发的时间互不相同，所有乘客到达的时间也互不相同。
     * 给你一个整数 capacity ，表示每辆公交车 最多 能容纳的乘客数目。
     * 每位乘客都会排队搭乘下一辆有座位的公交车。如果你在 y 时刻到达，公交在 x 时刻出发，满足 y <= x  且公交没有满，那么你可以搭乘这一辆公交。
     * 最早 到达的乘客优先上车。
     * 返回你可以搭乘公交车的最晚到达公交站时间。你 不能 跟别的乘客同时刻到达。
     * 注意：数组 buses 和 passengers 不一定是有序的。
     * tips:
     * n == buses.length
     * m == passengers.length
     * 1 <= n, m, capacity <= 10^5
     * 2 <= buses[i], passengers[i] <= 10^9
     * buses 中的元素 互不相同 。
     * passengers 中的元素 互不相同 。
     * @param: buses
     * @param: passengers
     * @param: capacity
     * @return int
     * @author marks
     * @CreateDate: 2026/06/26 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int latestTimeCatchTheBus(int[] buses, int[] passengers, int capacity) {
        int result;
        result = method_01(buses, passengers, capacity);
        return result;
    }

    /**
     * @Description:
     * 1. 需要返回最晚到达车站时间, 最大值是 int last = max + 1; 但是最大值不一定满足条件, 即公交车满员了, 最早到达时间是 int first = min - 1;
     * 2. last 定义不对, 由于需要在 maxBus 之前到达车站才有可能坐上车, 所以需要到达时间 int last = maxBus;
     * 3. 这种可以使用二分法来查找最晚到达车站时间
     * 4. 先对 buses 进行排序, 然后对 passengers 进行排序
     * AC: 61ms/98.98MB, 时间复杂度 nlogn, 主要是优先队列和排序的消耗.
     * @param: buses
     * @param: passengers
     * @param: capacity
     * @return int
     * @author marks
     * @CreateDate: 2026/06/26 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int prev;
    private int method_01(int[] buses, int[] passengers, int capacity) {
        int m = buses.length, n = passengers.length;
        Arrays.sort(buses);
        Arrays.sort(passengers);
        // 需要根据 buses[i] 来安排 passengers[j]
        int left = 0; // passengers[left] 的索引
        int ans = Math.min(1, passengers[0] - 1);
        prev = 0;
        // 添加优先队列
        PriorityQueue<Integer> queue = new PriorityQueue<>(); // 小根堆
        for (int bus : buses) {
            while (left < n && passengers[left] <= bus) {
                queue.offer(passengers[left]);
                left++;
            }

            int maxTime = findMaxTime(queue, capacity, bus);
            if (maxTime != -1) {
                ans = Math.max(ans, maxTime);
            }
        }

        return ans;
    }

    private int findMaxTime(PriorityQueue<Integer> queue, int capacity, int bus) {
        int ans = -1;
        while (capacity > 0 && !queue.isEmpty()) {
            int time = queue.poll();
            if (time - prev > 1) {
                // 有空隙可以添加一个时间
                ans = time - 1;
            }

            prev = time;
            capacity--;
        }
        if (capacity > 0) { // 此时queue为空
            if (prev != bus) {
                ans = bus;
            }
        }
        return ans;
    }


}
