package com.marks.leetcode.bitwise_operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/10 10:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3226Test {

    @Test
    void minChanges() {
        LeetCode_3226 leetCode_3226 = new LeetCode_3226();
        int result = leetCode_3226.minChanges(13, 4);
        System.out.println(result);
        assertEquals(result, 2);
        result = leetCode_3226.minChanges(14, 13);
        System.out.println(result);
        assertEquals(result, -1);
    }
}