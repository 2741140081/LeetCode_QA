package com.marks.leetcode.data_structure.union_find;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_765Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/6 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_765Test {

    @Test
    void minSwapsCouples() {
        LeetCode_765 leetCode_765 = new LeetCode_765();
        int result = leetCode_765.minSwapsCouples(new int[]{3, 2, 1, 0});
        assertEquals(0, result);
    }
}