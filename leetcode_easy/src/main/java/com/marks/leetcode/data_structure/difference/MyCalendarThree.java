package com.marks.leetcode.data_structure.difference;

import java.util.*;

/**
 * <p>项目名称: 我的日程安排表 III </p>
 * <p>文件名称: LeetCode_732 </p>
 * <p>描述:
 * 当 k 个日程存在一些非空交集时（即, k 个日程包含了一些相同时间），就会产生 k 次预订。
 * 给你一些日程安排 [startTime, endTime) ，请你在每个日程安排添加后，返回一个整数 k ，表示所有先前日程安排会产生的最大 k 次预订。
 * 实现一个 MyCalendarThree 类来存放你的日程安排，你可以一直添加新的日程安排。
 * MyCalendarThree() 初始化对象。
 * int book(int startTime, int endTime) 返回一个整数 k ，表示日历中存在的 k 次预订的最大值。
 *
 * </p>
 * <p>
 * tips:
 * 0 <= startTime < endTime <= 10^9
 * 每个测试用例，调用 book 函数最多不超过 400次
 * </p>
 *
 * <p> AC: 105ms/44.62MB </p>
 * @author marks
 * @version v1.0
 * @date 2025/2/18 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyCalendarThree {
    private TreeMap<Integer, Integer> diff;

    public MyCalendarThree() {
        diff = new TreeMap<>();
    }

    public int book(int startTime, int endTime) {
        int k = 0;
        diff.merge(startTime, 1, Integer::sum);
        diff.merge(endTime, -1, Integer::sum);

        int count = 0;
        for (Map.Entry<Integer, Integer> entry : diff.entrySet()) {
            count += entry.getValue();
            k = Math.max(k, count);
        }
        return k;
    }
}
