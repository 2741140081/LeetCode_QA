package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/9 16:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_679Test {

    @Test
    void judgePoint24() {
        LeetCode_679 leetCode_679 = new LeetCode_679();
        boolean result = leetCode_679.judgePoint24(new int[]{4, 1, 8, 7});
        System.out.println(result);
        assertTrue(result);
        result = leetCode_679.judgePoint24(new int[]{3, 3, 8, 8});
        System.out.println(result);
        assertTrue(result);
    }
}