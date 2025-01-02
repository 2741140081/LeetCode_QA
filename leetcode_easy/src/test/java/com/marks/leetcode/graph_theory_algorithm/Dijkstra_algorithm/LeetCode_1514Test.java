package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/2 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1514Test {

    @Test
    void maxProbability() {
        // n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], start = 0, end = 2
        int n = 3,  start = 0, end = 2;
        int[][] edges = {{0,1},{1,2},{0,2}};
        double[] succProb = {0.5,0.5,0.2};
        double result = new LeetCode_1514().maxProbability(n, edges, succProb, start, end);
        System.out.println(result);
    }
}