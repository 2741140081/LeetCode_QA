package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2054Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/23 11:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2054Test {

    @Test
    void maxTwoEvents() {
        // events = [[1,3,2],[4,5,2],[1,5,5]]
        int[][] events = {{1,3,2},{4,5,2},{1,5,5}};
        LeetCode_2054 leetCode_2054 = new LeetCode_2054();
        int result = leetCode_2054.maxTwoEvents(events);
        System.out.println(result);
    }
}