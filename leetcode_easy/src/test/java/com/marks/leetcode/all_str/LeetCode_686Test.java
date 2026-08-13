package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_686Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 11:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_686Test {

    @Test
    void repeatedStringMatch() {
        // 输入：a = "abcd", b = "cdabcdab"
        LeetCode_686 leetCode_686 = new LeetCode_686();
        int result = leetCode_686.repeatedStringMatch("abcd", "cdabcdab");
        System.out.println(result);
    }
}