package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2065Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/31 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2065Test {

    @Test
    void maximalPathQuality() {
        // 输入：values = [0,32,10,43], edges = [[0,1,10],[1,2,15],[0,3,10]], maxTime = 49
        //输出：75
        int[] values = {0,32,10,43};
        int[][] edges = {{0,1,10},{1,2,15},{0,3,10}};
        int maxTime = 49;
        int res = new LeetCode_2065().maximalPathQuality(values, edges, maxTime);
        assertEquals(75, res);
        System.out.println(res);
    }
}