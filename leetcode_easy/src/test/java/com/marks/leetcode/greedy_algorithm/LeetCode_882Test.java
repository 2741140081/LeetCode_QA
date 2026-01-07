package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_882Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_882Test {

    @Test
    void reachableNodes() {
        // 输入：edges = [[0,1,10],[0,2,1],[1,2,2]], maxMoves = 6, n = 3
        // 输出：13
        int[][] edges = {{0,1,10},{0,2,1},{1,2,2}};
        int maxMoves = 6;
        int n = 3;
        int result = new LeetCode_882().reachableNodes(edges, maxMoves, n);
//        assertEquals(13, result);
        System.out.println(result);
    }
}