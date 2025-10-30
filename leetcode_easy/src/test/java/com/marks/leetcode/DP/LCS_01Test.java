package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCS_01Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/29 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCS_01Test {

    @Test
    void leastMinutes() {
        LCS_01 lcs_01 = new LCS_01();
        int result = lcs_01.leastMinutes(2);
        System.out.println(result);
        assertEquals(2, result);
    }
}