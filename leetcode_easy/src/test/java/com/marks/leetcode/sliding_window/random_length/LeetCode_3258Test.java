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
 * @data 2024/10/30 15:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3258Test {

    @Test
    void countKConstraintSubstrings() {
        // s = "10101", k = 1
        String s = "10101";
        int k = 1;
        int result = new LeetCode_3258().countKConstraintSubstrings(s, k);
        System.out.println(result);
    }
}