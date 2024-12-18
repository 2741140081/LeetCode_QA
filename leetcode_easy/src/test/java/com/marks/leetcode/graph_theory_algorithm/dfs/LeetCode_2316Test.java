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
 * @date 2024/12/18 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2316Test {

    @Test
    void countPairs() {
        // 输入：n = 7, edges = {{0,2},{0,5},{2,4},{1,6},{5,4}}
//        int n = 11;
//        int[][] edges = {{5,0},{1,0},{10,7},{9,8},{7,2},{1,3},{0,2},{8,5},{4,6},{4,2}};
        int n = 7;
        int[][] edges = {{0,2},{0,5},{2,4},{1,6},{5,4}};
        long result = new LeetCode_2316().countPairs(n, edges);
        System.out.println(result);
    }
}