package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/29 10:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1358Test {

    @Test
    void numberOfSubstrings() {
        // s = "abcabc"
        String s = "abcabc";
        int result = new LeetCode_1358().numberOfSubstrings(s);
        System.out.println(result);
    }
}