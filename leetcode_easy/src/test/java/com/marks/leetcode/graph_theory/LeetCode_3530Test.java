package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3530Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 17:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3530Test {

    @Test
    void maxProfit() {
        // int n = 5; edges = [[1,2],[0,3],[1,4],[2,3],[1,3]], score = [50913,47946,97391,27488,69147]
        int n = 5;
        int[][] edges = {{1, 2}, {0, 3}, {1, 4}, {2, 3}, {1, 3}};
        int[] score = {50913, 47946, 97391, 27488, 69147};
        LeetCode_3530 leetCode_3530 = new LeetCode_3530();
        int maxProfit = leetCode_3530.maxProfit(n, edges, score);
        System.out.println(maxProfit);
    }
}