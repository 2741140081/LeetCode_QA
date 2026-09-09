package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3871Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/9 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3871Test {

    @Test
    void countCommas() {
        long n = 1002;
        LeetCode_3871 leetCode_3871 = new LeetCode_3871();
        long result = leetCode_3871.countCommas(n);
        System.out.println(result);
    }
}