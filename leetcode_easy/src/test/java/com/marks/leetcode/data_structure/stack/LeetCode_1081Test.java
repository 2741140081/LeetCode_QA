package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1081Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/13 15:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1081Test {

    @Test
    void smallestSubsequence() {
        // s = "bcabc"
        String s = "bcabc";
        LeetCode_1081 leetCode_1081 = new LeetCode_1081();
        String result = leetCode_1081.smallestSubsequence(s);
        System.out.println( result);
    }
}