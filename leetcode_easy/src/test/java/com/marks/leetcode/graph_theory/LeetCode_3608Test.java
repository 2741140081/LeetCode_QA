package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3608Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 14:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3608Test {

    @Test
    void minTime() {
        // 输入： n = 3, edges = [[0,1,2],[1,2,4]], k = 3
        int n = 3;
        int[][] edges = {{0,1,2},{1,2,4}};
        int k = 3;
        int result = new LeetCode_3608().minTime(n, edges, k);
        System.out.println(result);
    }
}