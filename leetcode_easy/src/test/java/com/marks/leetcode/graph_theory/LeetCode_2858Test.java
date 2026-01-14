package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2858Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/14 11:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2858Test {

    @Test
    void minEdgeReversals() {
        // 输入：n = 4, edges = [[2,0],[2,1],[1,3]]
        // 输出：[1,1,0,2]
        int n = 4;
        int[][] edges = {{2, 0}, {2, 1}, {1, 3}};
        LeetCode_2858 leetCode_2858 = new LeetCode_2858();
        int[] result = leetCode_2858.minEdgeReversals(n, edges);
    }
}