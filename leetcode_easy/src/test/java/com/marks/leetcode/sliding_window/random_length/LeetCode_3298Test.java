package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/30 11:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3298Test {

    @Test
    void validSubstringCount() {
        String word1 = "abcabc";
        String word2 = "abc";
        long result = new LeetCode_3298().validSubstringCount(word1, word2);
        System.out.println(result);
    }
}