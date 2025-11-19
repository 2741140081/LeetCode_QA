package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1931Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/18 11:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1931Test {

    @Test
    void colorTheGrid() {
        int m = 5, n = 5;
        LeetCode_1931 leetCode_1931 = new LeetCode_1931();
        int result = leetCode_1931.colorTheGrid(m, n);
        System.out.println(result);
        assertEquals(580986, result);
    }
}