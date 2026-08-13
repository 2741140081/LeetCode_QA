package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1444Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/22 10:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1444Test {

    @Test
    void ways() {
        // 输入：pizza = ["A..","AAA","..."], k = 3
        String[] pizza = {"A..","AAA","..."};
        int k = 3;
        LeetCode_1444 leetCode_1444 = new LeetCode_1444();
        int ways = leetCode_1444.ways(pizza, k);
        System.out.println(ways);
    }
}