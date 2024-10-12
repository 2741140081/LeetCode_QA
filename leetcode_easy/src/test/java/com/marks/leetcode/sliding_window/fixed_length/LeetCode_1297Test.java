package com.marks.leetcode.sliding_window.fixed_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1297Test {

    @Test
    void maxFreq() {
        // s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
        String s = "aababcaab";
        int maxLetters = 2;
        int minSize = 3;
        int maxSize = 4;
        int result = new LeetCode_1297().maxFreq(s, maxLetters, minSize, maxSize);
        System.out.println(result);

    }
}