package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: 座位预约管理系统 </p>
 * <p>文件名称: LeetCode_1845 </p>
 * <p>描述:
 * 请你设计一个管理 n 个座位预约的系统，座位编号从 1 到 n 。
 *
 * 请你实现 SeatManager 类：
 *
 * SeatManager(int n) 初始化一个 SeatManager 对象，它管理从 1 到 n 编号的 n 个座位。所有座位初始都是可预约的。
 * int reserve() 返回可以预约座位的 最小编号 ，此座位变为不可预约。
 * void unreserve(int seatNumber) 将给定编号 seatNumber 对应的座位变成可以预约。
 * </p>
 *
 * <p> AC: 109ms/86.97MB </p>
 * @author marks
 * @version v1.0
 * @date 2025/2/8 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class SeatManager {
    private PriorityQueue<Integer> queue;

    public SeatManager(int n) {
        queue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
        for (int i = 1; i <= n; i++) {
            queue.offer(i);
        }
    }

    public int reserve() {
        if (!queue.isEmpty()) {
            return queue.poll();
        } else {
            return -1;
        }
    }

    public void unreserve(int seatNumber) {
        queue.offer(seatNumber);
    }
}
