package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1986Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 15:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1986Test {

    @Test
    void minSessions() {
        // 输入：tasks = [3,1,3,1,1], sessionTime = 8
        int[] tasks = {3,1,3,1,1};
        int sessionTime = 8;
        LeetCode_1986 leetCode_1986 = new LeetCode_1986();
        int result = leetCode_1986.minSessions(tasks, sessionTime);
        System.out.println(result);
        assertEquals(2, result);
    }
}