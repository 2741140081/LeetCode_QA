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
 * @date 2025/9/25 15:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3133Test {

    @Test
    void minEnd() {
        int n = 4, x = 1;
        LeetCode_3133 leetCode_3133 = new LeetCode_3133();
        long result = leetCode_3133.minEnd(n, x);
        System.out.println(result);
        assertEquals(result, 7);
    }
}