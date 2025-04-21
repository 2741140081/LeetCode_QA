package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/21 10:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_32Test {

    @Test
    void longestValidParentheses() {
//        String s = ")()())";
        String s = "(()";
        int result = new LeetCode_32().longestValidParentheses(s);
        System.out.println(result);
    }
}