package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1129Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1129Test {

    @Test
    void shortestAlternatingPaths() {
        // n = 5, redEdges = [[0,1],[1,2],[2,3],[3,4]], blueEdges = [[1,2],[2,3],[3,1]]
        int n = 5;
        int[][] redEdges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        int[][] blueEdges = {{1, 2}, {2, 3}, {3, 1}};
        LeetCode_1129 leetCode_1129 = new LeetCode_1129();
        int[] result = leetCode_1129.shortestAlternatingPaths(n, redEdges, blueEdges);
    }
}