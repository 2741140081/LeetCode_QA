package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1288Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/6 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1288Test {

    @Test
    void removeCoveredIntervals() {
        // 输入：intervals = [[1,4],[3,6],[2,8]]
        int[][] intervals = {{3,10},{4,10},{5,11}};
        LeetCode_1288 lc = new LeetCode_1288();
        int result = lc.removeCoveredIntervals(intervals);
        System.out.println(result);
    }
}