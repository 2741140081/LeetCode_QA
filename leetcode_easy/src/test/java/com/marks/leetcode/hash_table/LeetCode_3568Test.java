package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3568Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/5 16:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3568Test {

    @Test
    void minMoves() {
        String[] classroom = {"S.", "XL"};
        int energy = 2;
        LeetCode_3568 leetCode_3568 = new LeetCode_3568();
        System.out.println(leetCode_3568.minMoves(classroom, energy));
    }
}