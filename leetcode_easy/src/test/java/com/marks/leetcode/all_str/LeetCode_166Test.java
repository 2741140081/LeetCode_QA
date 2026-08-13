package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_166Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 9:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_166Test {

    @Test
    void fractionToDecimal() {
        LeetCode_166 leetCode_166 = new LeetCode_166();
        String result = leetCode_166.fractionToDecimal(-2147483648, -1);
        System.out.println(result);
    }
}