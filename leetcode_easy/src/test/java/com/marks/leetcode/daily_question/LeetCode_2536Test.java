package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2536Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/14 11:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2536Test {

    @Test
    void rangeAddQueries() {
        int n = 3;
        int[][] queries = {{1,1,2,2},{0,0,1,1}};
        LeetCode_2536 leetCode_2536 = new LeetCode_2536();
        int[][] result = leetCode_2536.rangeAddQueries(n, queries);
    }
}