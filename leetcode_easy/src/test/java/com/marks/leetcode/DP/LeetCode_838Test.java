package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_838Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/29 16:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_838Test {

    @Test
    void pushDominoes() {
        LeetCode_838 leetCode_838 = new LeetCode_838();
        String result = leetCode_838.pushDominoes(".L.R...LR..L..");
        System.out.println(result);
        assertEquals("LL.RR.LLRRLL..", result);
        result = leetCode_838.pushDominoes("RR.L");
        System.out.println(result);
        assertEquals("RR.L", result);
    }
}