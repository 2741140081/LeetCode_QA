package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2531Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 15:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2531Test {

    @Test
    void isItPossible() {
        String word1 = "ab";
        String word2 = "abcc";
        LeetCode_2531 leetCode_2531 = new LeetCode_2531();
        boolean result = leetCode_2531.isItPossible(word1, word2);
        System.out.println(result);
    }
}