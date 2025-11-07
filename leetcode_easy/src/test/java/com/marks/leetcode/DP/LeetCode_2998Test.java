package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2998Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/4 17:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2998Test {

    @Test
    void minimumOperationsToMakeEqual() {
        LeetCode_2998 leetCode_2998 = new LeetCode_2998();
        int x = 54, y = 2;
        int result = leetCode_2998.minimumOperationsToMakeEqual(x, y);
        System.out.println(result);
        assertEquals(4, result);
    }
}