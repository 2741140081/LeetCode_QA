package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/3 10:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3123Test {

    @Test
    void findAnswer() {
        // 输入：n = 6, edges = {{0,1,4},{0,2,1},{1,3,2},{1,4,3},{1,5,1},{2,3,1},{3,5,3},{4,5,2}}
        int n = 6;
        int[][] edges = {{0,1,4},{0,2,1},{1,3,2},{1,4,3},{1,5,1},{2,3,1},{3,5,3},{4,5,2}};
        boolean[] result = new LeetCode_3123().findAnswer(n, edges);
    }
}