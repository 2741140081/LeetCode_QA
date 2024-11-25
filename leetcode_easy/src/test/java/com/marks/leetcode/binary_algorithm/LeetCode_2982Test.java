package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/25 15:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2982Test {

    @Test
    void maximumLength() {
        String s = "dceeddedcedcdcdedcdddeeedddsssdcdcdeeeccdccedeeedd";
        int result = new LeetCode_2982().maximumLength(s);
        System.out.println(result);
    }
}