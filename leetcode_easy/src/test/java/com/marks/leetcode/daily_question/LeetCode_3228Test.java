package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3228Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/13 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3228Test {

    @Test
    void maxOperations() {
        LeetCode_3228 leetCode_3228 = new LeetCode_3228();
        String s = "1001101";
        int result = leetCode_3228.maxOperations(s);
        System.out.println(result);
        assertEquals(4, result);
    }
}