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
 * @data 2024/10/25 10:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2904Test {

    @Test
    void shortestBeautifulSubstring() {
        // 输入：s = "100011001", k = 3
        String s = "100011001";
        int k = 3;
        String result = new LeetCode_2904().shortestBeautifulSubstring(s, k);
        System.out.println(result);
    }
}