package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3517Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 10:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3517Test {

    @Test
    void smallestPalindrome() {
        String s = "yey";
        LeetCode_3517 leetCode3517 = new LeetCode_3517();
        String result = leetCode3517.smallestPalindrome(s);
        System.out.println(result);
    }
}