package com.marks.leetcode.difference_array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2327Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/10 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2327Test {

    @Test
    void peopleAwareOfSecret() {
        // 输入：n = 6, delay = 2, forget = 4
        int n = 6, delay = 2, forget = 4;
        LeetCode_2327 leetCode2327 = new LeetCode_2327();
        int result = leetCode2327.peopleAwareOfSecret(n, delay, forget);
        System.out.println(result);
    }
}