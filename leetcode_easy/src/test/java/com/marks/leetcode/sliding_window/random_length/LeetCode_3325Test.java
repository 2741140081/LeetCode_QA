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
 * @data 2024/10/29 17:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3325Test {

    @Test
    void numberOfSubstrings() {
        // s = "abacb", k = 2
        String s = "abacb";
        int k = 2;
        int result = new LeetCode_3325().numberOfSubstrings(s, k);
        System.out.println(result);
    }
}