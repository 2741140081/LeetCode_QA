package com.marks.leetcode.DP_II;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3332Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/22 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3332Test {

    @Test
    void maxScore() {
        // n = 3, k = 2, stayScore = [[3,4,2],[2,1,2]], travelScore = [[0,2,1],[2,0,4],[3,2,0]]
        int n = 3, k = 2;
        int[][] stayScore = {{3,4,2}, {2,1,2}};
        int[][] travelScore = {{0,2,1}, {2,0,4}, {3,2,0}};
        LeetCode_3332 leetCode3332 = new LeetCode_3332();
        int result = leetCode3332.maxScore(n, k, stayScore, travelScore);
        System.out.println(result);
    }
}