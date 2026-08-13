package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3499Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/21 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3499Test {

    @Test
    void maxActiveSectionsAfterTrade() {
        // 01101001
        String s = "01101001";
        LeetCode_3499 leetCode3499 = new LeetCode_3499();
        int result = leetCode3499.maxActiveSectionsAfterTrade(s);
        System.out.println(result);
    }
}