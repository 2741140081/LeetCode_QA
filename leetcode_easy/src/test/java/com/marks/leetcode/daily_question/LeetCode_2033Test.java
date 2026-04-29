package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2033Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/28 11:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2033Test {

    @Test
    void minOperations() {
        // grid = [[1,5],[2,3]], x = 1
        int[][] grid = {{1,5},{2,3}};
        int x = 1;
        int res = new LeetCode_2033().minOperations(grid, x);
        System.out.println(res);
    }
}