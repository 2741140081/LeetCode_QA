package com.marks.leetcode.data_structure.segment_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class MyCalendarTwoTest {

    @Test
    void book() {
        // ["MyCalendarTwo", "book", "book", "book", "book", "book", "book"]
        //[[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]

        MyCalendarTwo calendarTwo = new MyCalendarTwo();
        System.out.println("book(10, 20) => " + calendarTwo.book(10, 20));
        System.out.println("book(50, 60) => " + calendarTwo.book(50, 60));
        System.out.println("book(10, 40) => " + calendarTwo.book(10, 40));
        System.out.println("book(5, 15) => " + calendarTwo.book(5, 15));
        System.out.println("book(5, 10) => " + calendarTwo.book(5, 10));
        System.out.println("book(25, 55) => " + calendarTwo.book(25, 55));
    }
}