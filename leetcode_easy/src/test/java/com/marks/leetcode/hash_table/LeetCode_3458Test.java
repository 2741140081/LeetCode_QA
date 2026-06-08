package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3458Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/4 15:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3458Test {

    @Test
    void maxSubstringLength() {
        // 输入： s = "cjd", k = 3
        String s = "ggggggglhxhdwxrnwvuwggg";
        int k = 5;
        LeetCode_3458 leetCode_3458 = new LeetCode_3458();
        boolean flag = leetCode_3458.maxSubstringLength(s, k);
        System.out.println(flag);
    }
}