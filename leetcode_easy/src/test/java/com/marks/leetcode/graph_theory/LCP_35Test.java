package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_35Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/14 17:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_35Test {

    @Test
    void electricCarPlan() {
        // 输入：paths = [[1,3,3],[3,2,1],[2,1,3],[0,1,4],[3,0,5]], cnt = 6, start = 1, end = 0, charge = [2,10,4,1]
        // 输出：43
        int[][] paths = {{1, 3, 3}, {3, 2, 1}, {2, 1, 3}, {0, 1, 4}, {3, 0, 5}};
        int cnt = 6;
        int start = 1;
        int end = 0;
        int[] charge = {2, 10, 4, 1};
        LCP_35 lcp_35 = new LCP_35();
        int result = lcp_35.electricCarPlan(paths, cnt, start, end, charge);
        System.out.println(result);
    }
}