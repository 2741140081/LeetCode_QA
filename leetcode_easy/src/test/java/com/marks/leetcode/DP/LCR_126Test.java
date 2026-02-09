package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_126Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 14:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCR_126Test {

    @Test
    void fib() {
        int n = 100;
        LCR_126 lcr_126 = new LCR_126();
        int result = lcr_126.fib(n);
        System.out.println(result);
    }
}