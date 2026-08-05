package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2106Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/5 10:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2106Test {

    @Test
    void maxTotalFruits() {
        // fruits = [[1,9],[2,10],[3,1],[5,6],[6,3],[8,2],[9,2],[11,4],[18,10],[22,8],[25,2],[26,2],[30,4],[31,5],[33,9],[34,1],[39,10]], startPos = 5, k = 4
        int[][] fruits = {{1,9},{2,10},{3,1},{5,6},{6,3},{8,2},{9,2},{11,4},{18,10},{22,8},{25,2},{26,2},{30,4},{31,5},{33,9},{34,1},{39,10}};
        int startPos = 19, k = 9;
        LeetCode_2106 leetCode2106 = new LeetCode_2106();
        int result = leetCode2106.maxTotalFruits(fruits, startPos, k);
        System.out.println(result);
    }
}