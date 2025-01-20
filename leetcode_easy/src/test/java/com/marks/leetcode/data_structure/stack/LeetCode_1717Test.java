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
 * @date 2025/1/20 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1717Test {

    @Test
    void maximumGain() {
        // s = "cdbcbbaaabab", x = 4, y = 5
        String s = "cdbcbbaaabab";
        int x = 4, y = 5;
        int result = new LeetCode_1717().maximumGain(s, x, y);
        System.out.println(result);
    }
}