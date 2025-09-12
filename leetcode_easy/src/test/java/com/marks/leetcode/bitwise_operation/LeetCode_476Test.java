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
 * @date 2025/9/11 16:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_476Test {

    @Test
    void findComplement() {
        int num = 5;
        LeetCode_476 leetCode_476 = new LeetCode_476();
        int result = leetCode_476.findComplement(num);
        System.out.println(result);
        assertEquals(2, result);
    }
}