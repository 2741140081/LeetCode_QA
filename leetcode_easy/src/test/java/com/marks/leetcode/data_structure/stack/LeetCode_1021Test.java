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
 * @date 2025/1/20 11:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1021Test {

    @Test
    void removeOuterParentheses() {
        String s = "(()())(())(()(()))";
        String result = new LeetCode_1021().removeOuterParentheses(s);
        System.out.println(result);
    }
}