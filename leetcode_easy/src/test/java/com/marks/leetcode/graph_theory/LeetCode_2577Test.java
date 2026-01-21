package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2577Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/16 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2577Test {

    @Test
    void minimumTime() {
        // [[0,0,75279],[1,15637,55696],[1805,52060,39416]]
        int[][] grid = {{0, 1, 2}, {1, 2, 3}, {2, 3, 4}};
        int result = new LeetCode_2577().minimumTime(grid);
        System.out.println(result);
    }
}