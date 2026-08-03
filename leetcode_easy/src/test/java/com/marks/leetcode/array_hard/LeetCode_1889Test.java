package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1889Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/3 17:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1889Test {

    @Test
    void minWastedSpace() {
        // packages = [2,3,5], boxes = [[4,8],[2,8]]
        int[] packages = {2, 3, 5};
        int[][] boxes = {{4, 8}, {2, 8}};
        assertEquals(6, new LeetCode_1889().minWastedSpace(packages, boxes));
    }
}