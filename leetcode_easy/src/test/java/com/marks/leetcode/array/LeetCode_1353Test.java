package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1353Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/1 9:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1353Test {

    @Test
    void maxEvents() {
        // events = [[1,2],[1,2],[3,3],[1,5],[1,5]]
        int[][] events = {{1,2}, {1,2}, {3,3}, {1,5}, {1,5}};
        int result = new LeetCode_1353().maxEvents(events);
        System.out.println(result);
    }
}