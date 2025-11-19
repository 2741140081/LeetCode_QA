package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1641Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1641Test {

    @Test
    void countVowelStrings() {
        int n = 33;
        LeetCode_1641 leetCode_1641 = new LeetCode_1641();
        int result = leetCode_1641.countVowelStrings(n);
        System.out.println(result);
        assertEquals(66045, result);
    }
}