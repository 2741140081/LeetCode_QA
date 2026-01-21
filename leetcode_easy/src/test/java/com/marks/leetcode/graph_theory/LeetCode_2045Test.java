package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2045Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/19 16:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2045Test {

    @Test
    void secondMinimum() {
        // case 1
        // 输入：n = 5, edges = [[1,2],[1,3],[1,4],[3,4],[4,5]], time = 3, change = 5
        // 输出：13

        // case 2
        // n = 7, edges = [[1,2],[1,3],[2,5],[2,6],[6,5],[5,7],[3,4],[4,7]], time = 4, change = 7
        // 输出：22
        LeetCode_2045 leetCode_2045 = new LeetCode_2045();
//        int n = 5;
//        int[][] edges = {{1,2},{1,3},{1,4},{3,4},{4,5}};
//        int time = 3;
//        int change = 5;
//        int result = leetCode_2045.secondMinimum(n, edges, time, change);
        int n = 7;
        int[][] edges = {{1,2},{1,3},{2,5},{2,6},{6,5},{5,7},{3,4},{4,7}};
        int time = 4;
        int change = 7;
        int result = leetCode_2045.secondMinimum(n, edges, time, change);
        System.out.println(result);

    }
}