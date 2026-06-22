package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_306Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_306Test {

    @Test
    void isAdditiveNumber() {
        // "199100199"
        LeetCode_306 leetCode_306 = new LeetCode_306();
        boolean result = leetCode_306.isAdditiveNumber("1203");
        System.out.println(result);
    }
}