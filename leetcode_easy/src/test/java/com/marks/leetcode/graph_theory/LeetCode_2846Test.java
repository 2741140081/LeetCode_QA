package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2846Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2846Test {

    @Test
    void minOperationsQueries() {
        // 输入：n = 8, edges = [[1,2,6],[1,3,4],[2,4,6],[2,5,3],[3,6,6],[3,0,8],[7,0,2]], queries = [[4,6],[0,4],[6,5],[7,4]]
        // 输出：[1,2,2,3]
        int n = 8;
        int[][] edges = {{1, 2, 6}, {1, 3, 4}, {2, 4, 6}, {2, 5, 3}, {3, 6, 6}, {3, 0, 8}, {7, 0, 2}};
        int[][] queries = {{4, 6}, {0, 4}, {6, 5}, {7, 4}};
        int[] result = new LeetCode_2846().minOperationsQueries(n, edges, queries);
        // result 转为 List<Integer>, 然后  sout
        System.out.println(Arrays.toString(result));
    }
}