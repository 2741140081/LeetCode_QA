package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1668Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/3 17:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1668Test {

    @Test
    void maxRepeating() {
        LeetCode_1668 leetCode_1668 = new LeetCode_1668();
        int result = leetCode_1668.maxRepeating("aaabaaaabaaabaaaabaaaabaaaabaaaaba", "aaaba");
        System.out.println(result);
        assertEquals(5, result);
    }
}