package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_367Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/1 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_367Test {

    @Test
    void isPerfectSquare() {
        int num = 808201;
        LeetCode_367 leetCode_367 = new LeetCode_367();
        boolean result = leetCode_367.isPerfectSquare(num);
        System.out.println(result);
        assertTrue(result);
    }
}