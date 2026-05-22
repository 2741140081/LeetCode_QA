package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1488Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/22 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1488Test {

    @Test
    void avoidFlood() {
        int[] rains = {1,2,0,0,2,1};
        LeetCode_1488 leetCode1488 = new LeetCode_1488();
        int[] ints = leetCode1488.avoidFlood(rains);
    }
}