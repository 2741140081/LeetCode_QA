package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2039Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2039Test {

    @Test
    void networkBecomesIdle() {
        // 输入：edges = [[0,1],[1,2]], patience = [0,2,1]
        // 输出：8
        int[][] edges = {{0, 1}, {1, 2}};
        int[] patience = {0, 2, 1};
        int result = new LeetCode_2039().networkBecomesIdle(edges, patience);
        // assertEquals(8, result);
        System.out.println(result);
    }
}