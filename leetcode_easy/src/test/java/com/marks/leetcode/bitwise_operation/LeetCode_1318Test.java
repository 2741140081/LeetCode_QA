package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/23 11:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1318Test {

    @Test
    void minFlips() {
        int a = 2, b = 6, c = 5;

        LeetCode_1318 leetCode_1318 = new LeetCode_1318();
        int result = leetCode_1318.minFlips(a, b, c);
        assertEquals(3, result);

        a = 1;
        b = 2;
        c = 3;
        int result2 = leetCode_1318.minFlips(a, b, c);
        assertEquals(0, result2);
    }
}