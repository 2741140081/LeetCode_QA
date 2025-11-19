package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2008Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 16:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2008Test {

    @Test
    void maxTaxiEarnings() {
        LeetCode_2008 leetCode_2008 = new LeetCode_2008();
        int n = 20;
        int[][] rides = {{1, 6, 1}, {3, 10, 2}, {10, 12, 3}, {11, 12, 2}, {12, 15, 2}, {13, 18, 1}};
        long result = leetCode_2008.maxTaxiEarnings(n, rides);
        System.out.println(result);
        assertEquals(20, result);
    }
}