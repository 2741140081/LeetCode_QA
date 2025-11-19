package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_717Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/18 14:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_717Test {

    @Test
    void isOneBitCharacter() {
        int[] bits = {1, 1, 0};
        LeetCode_717 leetCode_717 = new LeetCode_717();
        boolean result = leetCode_717.isOneBitCharacter(bits);
        System.out.println(result);
    }
}