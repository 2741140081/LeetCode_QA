package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_435Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_435Test {

    @Test
    void eraseOverlapIntervals() {
        // [[1,100],[11,22],[1,11],[2,12]]
        int[][] intervals = {{1, 100}, {11, 22}, {1, 11}, {2, 12}};
        int result = new LeetCode_435().eraseOverlapIntervals(intervals);
        System.out.println( result);
    }
}