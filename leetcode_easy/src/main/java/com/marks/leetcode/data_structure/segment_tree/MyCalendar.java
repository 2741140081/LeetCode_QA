package com.marks.leetcode.data_structure.segment_tree;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MyCalendar </p>
 * <p>描述: LCR 058. 我的日程安排表 I </p>
 *
 * 请实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内没有其他安排，则可以存储这个新的日程安排。
 * MyCalendar 有一个 book(int start, int end)方法。
 * 它意味着在 start 到 end 时间内增加一个日程安排，注意，这里的时间是半开区间，
 * 即 [start, end), 实数 x 的范围为，  start <= x < end。
 * 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生重复预订。
 * 每次调用 MyCalendar.book方法时，如果可以将日程安排成功添加到日历中而不会导致重复预订，
 * 返回 true。否则，返回 false 并且不要将该日程安排添加到日历中。
 * 请按照以下步骤调用 MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 *
 * tips:
 * 每个测试用例，调用 MyCalendar.book 函数最多不超过 1000次。
 * 0 <= start < end <= 10^9
 * @author marks
 * @version v1.0
 * @date 2026/6/16 15:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyCalendar {
    private Set<Integer> tree;
    private Set<Integer> lazy;

    /**
     * @Description:
     *
     * @return
     * @author marks
     * @CreateDate: 2026/06/16 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public MyCalendar() {
        tree = new HashSet<>();
        lazy = new HashSet<>();
    }

    public boolean book(int start, int end) {
        if (query(start, end - 1, 0, 1000000000, 1)) {
            return false;
        }
        update(start, end - 1, 0, 1000000000, 1);
        return true;
    }

    private boolean query(int start, int end, int l, int r, int idx) {
        if (start > r || end < l) {
            return false;
        }

        if (lazy.contains(idx)) {
            return true;
        }
        if (start <= l && r <= end) {
            return tree.contains(idx);
        } else {
            int mid = l + (r - l) / 2;
            if (end <= mid) {
                // 左侧
                return query(start, end, l, mid, idx * 2);
            } else if (start > mid) {
                // 右侧
                return query(start, end, mid + 1, r, idx * 2 + 1);
            } else {
                // 中间
                return query(start, end, l, mid, idx * 2) || query(start, end, mid + 1, r, idx * 2 + 1);
            }
        }
    }

    private void  update(int start, int end, int l, int r, int idx) {
        if (r < start || end < l) {
            return;
        }
        if (start <= l && r <= end) {
            tree.add(idx);
            lazy.add(idx);;
        } else {
            int mid = l + (r - l) / 2;
            update(start, end, l, mid, idx * 2);
            update(start, end, mid + 1, r, idx * 2 + 1);
            tree.add(idx);
        }
    }

}
