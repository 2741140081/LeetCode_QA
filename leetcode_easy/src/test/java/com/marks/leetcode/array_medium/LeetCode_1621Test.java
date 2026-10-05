package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1621Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/16 15:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1621Test {

    @Test
    void numberOfSets() {
        // 输入：n = 4, k = 2
        int n = 4, k = 2;

        // 输入：n = 3, k = 1
        n = 3;
        k = 1;
        // 输入：n = 30, k = 7
        n = 30;
        k = 7;

        int result = new LeetCode_1621().numberOfSets(n, k);
        System.out.println(result);

    }
}