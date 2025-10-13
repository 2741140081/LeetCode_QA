package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2811Test {

    @Test
    void canSplitArray() {
        LeetCode_2811 leetCode_2811 = new LeetCode_2811();
        boolean result = leetCode_2811.canSplitArray(List.of(2, 2, 4, 5), 5);
        assertTrue(result);
    }
}