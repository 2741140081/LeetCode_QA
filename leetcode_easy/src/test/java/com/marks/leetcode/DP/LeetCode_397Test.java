package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/11 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_397Test {

    @Test
    void integerReplacement() {
        int n = 2147483647;
        LeetCode_397 leetCode_397 = new LeetCode_397();
        int result = leetCode_397.integerReplacement(n);
        System.out.println(result);
        assertTrue(result == 32);
    }
}