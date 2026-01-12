package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1266Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/12 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1266Test {

    @Test
    void minTimeToVisitAllPoints() {
        // points = [[1,1],[3,4],[-1,0]]
        LeetCode_1266 solution = new LeetCode_1266();
        int[][] points = new int[][]{{1, 1}, {3, 4}, {-1, 0}};
        int result = solution.minTimeToVisitAllPoints(points);
        System.out.println(result);
    }
}