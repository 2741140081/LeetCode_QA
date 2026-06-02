package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1590Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/2 16:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1590Test {

    @Test
    void minSubarray() {
        // [8,32,31,18,34,20,21,13,1,27,23,22,11,15,30,4,2]
        LeetCode_1590 leetCode_1590 = new LeetCode_1590();
        int minSubarray = leetCode_1590.minSubarray(new int[]{8, 32, 31, 18, 34, 20, 21, 13, 1, 27, 23, 22, 11, 15, 30, 4, 2}, 148);
        System.out.println(minSubarray);
    }
}