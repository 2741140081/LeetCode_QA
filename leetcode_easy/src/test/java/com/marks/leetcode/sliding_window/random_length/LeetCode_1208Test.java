package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1208Test {

    @Test
    void equalSubstring() {
        // s = "abcd", t = "bcdf", maxCost = 3
        String s = "abcd";
        String t = "bcdf";
        int maxCost = 3;
        int result = new LeetCode_1208().equalSubstring(s, t, maxCost);
        System.out.println(result);
    }
}