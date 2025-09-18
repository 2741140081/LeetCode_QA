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
 * @date 2025/9/18 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2429Test {

    @Test
    void minimizeXor() {
        int n = 9;
        int bit = Integer.highestOneBit(n);
        System.out.println(bit);
        int num1 = 1, num2 = 12;
        LeetCode_2429 leetCode_2429 = new LeetCode_2429();
        int result = leetCode_2429.minimizeXor(num1, num2);
        System.out.println(result);
        assertTrue(result == 3);
    }
}