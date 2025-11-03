package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3389Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/30 15:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3389Test {

    @Test
    void makeStringGood() {
        LeetCode_3389 leetCode_3389 = new LeetCode_3389();
        String s = "zcttzt";
        int result = leetCode_3389.makeStringGood(s);
        System.out.println(result);
        assertEquals(2, result);

    }
}