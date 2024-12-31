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
 * @date 2024/12/31 11:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3341Test {

    @Test
    void minTimeToReach() {
        // moveTime = [[0,4],[4,4]]
        int[][] moveTime = {{0,4},{4,4}};
        int result = new LeetCode_3341().minTimeToReach(moveTime);
        System.out.println(result);
    }
}