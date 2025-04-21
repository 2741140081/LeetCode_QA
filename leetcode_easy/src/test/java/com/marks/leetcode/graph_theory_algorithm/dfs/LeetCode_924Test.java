package com.marks.leetcode.graph_theory_algorithm.dfs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/23 11:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_924Test {

    @Test
    void minMalwareSpread() {
//        int[][] graph = {{1,1,0},{1,1,0},{0,0,1}};
        int[][] graph = {{1,0,0,0,0,0},{0,1,1,0,0,0},{0,1,1,0,0,0},{0,0,0,1,1,1},{0,0,0,1,1,1},{0,0,0,1,1,1}};
//        int[] initial = {0, 1, 2};
        int[] initial = {2, 3};
        int result = new LeetCode_924().minMalwareSpread(graph, initial);
        System.out.println(result);
    }
}