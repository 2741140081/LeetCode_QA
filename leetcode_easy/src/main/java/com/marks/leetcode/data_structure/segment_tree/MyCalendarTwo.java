package com.marks.leetcode.data_structure.segment_tree;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: 我的日程安排表 II </p>
 * <p>文件名称: LeetCode_731 </p>
 * <p>描述:
 * 实现一个程序来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。
 *
 * 当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生 三重预订。
 *
 * 事件能够用一对整数 startTime 和 endTime 表示，在一个半开区间的时间 [startTime, endTime) 上预定。实数 x 的范围为  startTime <= x < endTime。
 *
 * 实现 MyCalendarTwo 类：
 *
 * MyCalendarTwo() 初始化日历对象。
 * boolean book(int startTime, int endTime) 如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。否则，返回 false 并且不要将该日程安排添加到日历中。
 *
 * tips:
 * 0 <= start < end <= 10^9
 * 最多调用 book 1000 次。
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 15:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyCalendarTwo {
    Map<Integer, int[]> tree;

    public MyCalendarTwo() {
        tree = new HashMap<>();
    }

    /**
     * @Description:
     * 1. 数组范围是 10 ^ 9, 我们应该将其离散化, 即使用 SetTree, 即使 这个离散化 可能每次 book() 成功后都需要重新离散化, 时间复杂度 O(N), N 为 time[] 的长度
     *
     * ["MyCalendarTwo", "book", "book", "book", "book", "book", "book"]
     * [[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
     *
     * @param startTime
     * @param endTime
     * @return boolean
     * @author marks
     * @CreateDate: 2025/3/25 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean book(int startTime, int endTime) {
        update(startTime, endTime - 1, 1, 0, (int) 1e9, 1);
        tree.putIfAbsent(1, new int[2]);
        if (tree.get(1)[0] > 2) {
            update(startTime, endTime - 1, -1, 0, (int) 1e9, 1);
            return false;
        }
        return true;
    }

    private void update(int start, int end, int val, int left, int right, int index) {
        if (right < start || left > end) {
            return;
        }

        tree.putIfAbsent(index, new int[2]);
        if (start <= left && right <= end) {
            tree.get(index)[0] += val;
            tree.get(index)[1] += val;
        } else {
            int mid = (right - left) / 2 + left;
            update(start, end, val, left, mid, 2 * index);
            update(start, end, val, mid + 1, right, 2 * index + 1);

            tree.putIfAbsent(2 * index, new int[2]);
            tree.putIfAbsent(2 * index + 1, new int[2]);

            tree.get(index)[0] = tree.get(index)[1] + Math.max(tree.get(2 * index)[0], tree.get(2 * index + 1)[0]);
        }
    }
}
