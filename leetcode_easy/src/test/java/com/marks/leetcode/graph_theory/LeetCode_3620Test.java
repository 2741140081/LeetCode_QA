package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3620Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/21 17:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3620Test {

    @Test
    void findMaxPathScore() {
        // 输入: edges = [[0,1,7],[1,4,5],[0,2,6],[2,3,6],[3,4,2],[2,4,6]], online = [true,true,true,false,true], k = 12
        int[][] edges = {{0, 1, 7}, {1, 4, 5}, {0, 2, 6}, {2, 3, 6}, {3, 4, 2}, {2, 4, 6}};
        boolean[] online = {true, true, true, false, true};
        int k = 12;
        LeetCode_3620 leetCode_3620 = new LeetCode_3620();
        int res = leetCode_3620.findMaxPathScore(edges, online, k);
        System.out.println(res);
    }
}