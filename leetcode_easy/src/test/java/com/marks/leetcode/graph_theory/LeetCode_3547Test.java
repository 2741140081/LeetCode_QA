package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3547Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 17:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3547Test {

    @Test
    void maxScore() {
        // 输入：n = 4, edges = [[0,1],[1,2],[2,3]]
        // 输出：23
        // 输入： n = 6, edges = [[0,3],[4,5],[2,0],[1,3],[2,4],[1,5]]
        int n = 6;
        int[][] edges = new int[][]{{0, 3}, {4, 5}, {2, 0}, {1, 3}, {2, 4}, {1, 5}};
        long result = new LeetCode_3547().maxScore(n, edges);
        System.out.println(result);
    }
}